package com.chmei.nzbcommon.util.excel;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.chmei.nzbcommon.util.WebUtils;

public class ExportExcelTemplate {

    /**
     * 当前工作簿
     */
    private Workbook workbook;
    /**
     * 当前工作sheet表
     */
    private Sheet sheet;
    /**
     * 当前表编号
     */
    private int sheetIndex;
    /**
     * 是否有样式行
     */
    private boolean hasStyleRow;
    /**
     * 样式行
     */
    private List<CellWrapper> styleRow = new ArrayList<CellWrapper>();;
    /**
     * 当前列数
     */
    private int currentColumnIndex;
    /**
     * 当前行
     */
    private Row currentRow;
    /**
     * 当前行数
     */
    private int currentRowIndex;

    /**
     * 数据的初始化列数
     */
    private int initColumnIndex;
    /**
     * 数据的初始化行数
     */
    private int initRowIndex;


    /**
     * 最后一行的数据
     */
    private int lastRowIndex;
    
    
    private ExportExcelTemplate() {
    }

    public static ExportExcelTemplate getInstance(String templatePath, int sheetIndex,boolean hasStyleRow) throws InvalidFormatException, IOException {
        ExportExcelTemplate template = new ExportExcelTemplate();
        template.sheetIndex = sheetIndex;
        template.hasStyleRow = hasStyleRow;
        template.loadTemplate(templatePath);
        return template;
    }

    /*-----------------------------------初始化模板开始-----------------------------------*/

    private void loadTemplate(String templatePath) throws IOException, InvalidFormatException{
    	
    	String path = WebUtils.getClassPath() + templatePath;
    	
    	/*
    	 * 将模板复制一份，不让程序操作源模板
    	 */
    	String destpath=WebUtils.getClassPath()+templatePath.substring(templatePath.lastIndexOf("/")+1, templatePath.lastIndexOf("."))+"xx"+templatePath.substring(templatePath.lastIndexOf("."));
    	File sourcefile=new File(path);
    	File destfile=new File(destpath);
    	FileUtils.copyFile(sourcefile, destfile);
    	
    	// 读取模板文件
       // this.workbook = WorkbookFactory.create(new File(path));
    	 this.workbook = WorkbookFactory.create(destfile);
        
        this.sheet = this.workbook.getSheetAt(this.sheetIndex);
        initModuleConfig();
        
        this.lastRowIndex = this.sheet.getLastRowNum();
        
        this.initColumnIndex = 0;
        destfile.delete();
    }

    /**
     * 初始化数据信息
     */
    private void initModuleConfig() {
       	
    	if(this.hasStyleRow){
    		
    		Row lastRow = this.sheet.getRow(this.sheet.getLastRowNum());
    		
    		for(Cell c : lastRow){
    			
    			String key = c.getStringCellValue().trim();
    			if(StringUtils.isBlank(key)) {
    				//throw new ExcelException("列字段英文标识不能为空！");
    				}
    			else {
    				styleRow.add(new CellWrapper(key,c.getCellStyle()));
    				}  			
    		}
    		this.currentRowIndex = this.sheet.getLastRowNum();
    		this.sheet.removeRow(lastRow);
    	}
      	
    }

    
    private void clearCell(Cell cell) {
        cell.setCellStyle(null);
        cell.setCellValue("");
    }


    /*-----------------------------------数据填充开始------------------------------------*/

    public void fillSheetByMaps(List<Map<String,Object>> datas){
    	
    	if(datas == null || datas.size() <= 0)
    		return;
    	
    	for(Map<String,Object> data : datas){
    		
    		createNewRow();
    		
    		for(CellWrapper c : styleRow){
    			
    			String key = c.getKey();
    			Object o = data.get(key);			
    			createCell(o);
    		}
    		
    	}
    	
    }
    
    /**
     * 根据map替换相应的常量，通过Map中的值来替换{key}的值
     *
     * @param data 替换映射
     */
    public void extendData(Map<String, Object> data) {
    	
    	if (data == null)
    	{
    		replaceRexToBlank();
            return;
    	}
        for (Row row : this.sheet) {
            for (Cell c : row) {
                if (c.getCellTypeEnum() != CellType.STRING)
                    continue;
                String str = c.getStringCellValue().trim();
                String value = parseString(str,data);
                c.setCellValue(value);
            }
        }   
        replaceRexToBlank();
        
    }
    
    
    
    /**
     * 创建新行，在使用时只要添加完一行，需要调用该方法创建
     */
    public void createNewRow() {
    	
    	this.currentRow = this.sheet.createRow(this.currentRowIndex);
        this.currentRowIndex++;
        this.currentColumnIndex = this.initColumnIndex;
        
    }

    /**
     * <p>设置Excel元素样式及内容</p>
     *
     * @param value    内容
     * @param styleKey 样式
     */
    public void createCell(Object value) {
        
    	Cell cell = this.currentRow.createCell(currentColumnIndex);
        setCellStyle(cell);
        cell.setCellValue(parseObjectToString(value));
        this.currentColumnIndex++;
    }

    /**
     * 设置某个元素的样式
     *
     * @param cell     cell元素
     * @param styleKey 样式标识
     */
    private void setCellStyle(Cell cell) {
        cell.setCellStyle(styleRow.get(this.currentColumnIndex).getCellStyle());
        cell.setCellType(CellType.STRING);
    }
    
    
    private String parseObjectToString(Object value){
    	
    	if (null == value || "".equals(value)) { 
            return "";
        }

    	else if (String.class == value.getClass()) {
            return (String) value;
        }

    	else if (int.class == value.getClass()) {
            return Integer.valueOf((int)value).toString();
        }

    	else if (Integer.class == value.getClass()) {
            return ((Integer) value).toString();
        }
        
    	else if (float.class == value.getClass()) {
           // return new BigDecimal(Float.valueOf((float) value)).stripTrailingZeros().toPlainString();
    		//return Float.valueOf((float) value).toString();
    		return new DecimalFormat("###0.00").format(value);
        }

    	else if (Float.class == value.getClass()) {
            //return new BigDecimal((Float) value).stripTrailingZeros().toPlainString();
    		//return ((Float) value).toString();
    		return new DecimalFormat("###0.00").format(value);
        }
        
    	else if (double.class == value.getClass()) {
           // return Double.valueOf((double) value).toString();
    		return new DecimalFormat("###0.00").format(value);
        }

    	else if (Double.class == value.getClass()) {
          //  return ((Double) value).toString();
    		return new DecimalFormat("###0.00").format(value);
        }

    	else if (Date.class == value.getClass()) {
            return ((Date) value).toString();
        }
    	else if (Calendar.class == value.getClass()) {
            return ((Calendar) value).toString();
        }
    	else{
    		return value.toString();
    	}

    }
    
    private void replaceRexToBlank(){
    	
    	for (Row row : this.sheet) {
            for (Cell c : row) {
                if (c.getCellTypeEnum() != CellType.STRING)
                    continue;
                String str = c.getStringCellValue().trim();
                String value = parseRexToBlank(str);
                c.setCellValue(value);
            }
        }
    	
    }
    
    private String parseString(String str,Map<String,Object> param){
    	
    	String reg = "\\$\\{[a-z_A-Z]+\\}";
    	
    	Pattern pattern = Pattern.compile(reg);
    	Matcher matcher = pattern.matcher(str);
    	while(matcher.find()){
    		
	    	String matchWord = matcher.group(0);
	    	String key = matchWord.substring(2, matchWord.length()-1);
	    	String value = parseObjectToString(param.get(key));
	    	str = str.replace(matchWord, value);
    	
    	}
    	return str;
    	
    }
    
    
    private String parseRexToBlank(String str){
    	
    	String reg = "\\$\\{[a-z_A-Z]+\\}";
    	
    	Pattern pattern = Pattern.compile(reg);
    	Matcher matcher = pattern.matcher(str);
    	while(matcher.find()){
    		
	    	String matchWord = matcher.group(0);
	    	str = str.replace(matchWord, "");
    	
    	}
    	return str;
    }
    /**
     * 将文件写到某个输出流中
     *
     * @param os 输出流
     * @throws IOException IO异常
     */
    public void write2Stream(OutputStream os) throws IOException {

        this.workbook.write(os);
    }
    
    
    public void closeWorkbook() throws IOException{
    	
    	this.workbook.close();
    	
    }

}
