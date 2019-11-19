package com.chmei.nzbcommon.util.excel;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.chmei.nzbcommon.util.DateUtil;
import com.chmei.nzbcommon.util.WebUtils;
import com.chmei.nzbcommon.util.excel.exception.ExcelException;

public class ImportExcelTemplate {
	
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
     * 最后一行的数据
     */
    private int lastRowIndex;
    
    /**
     * 列键值
     */
    private List<CellWrapper> cellWrappers = new ArrayList<CellWrapper>();
    
    private ImportExcelTemplate() {
    }

    public static ImportExcelTemplate getInstance(String templatePath, int sheetIndex) throws InvalidFormatException, IOException {
    	ImportExcelTemplate template = new ImportExcelTemplate();
        template.sheetIndex = sheetIndex;
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
    	this.workbook = WorkbookFactory.create(destfile);
        
        this.sheet = this.workbook.getSheetAt(this.sheetIndex);
        initModuleConfig();
        
      
        destfile.delete();
    }

    
	/**
	 * 初始化数据信息
	 */
	private void initModuleConfig() {

		this.lastRowIndex = this.sheet.getLastRowNum();
		Row lastRow = this.sheet.getRow(this.sheet.getLastRowNum());

		for (Cell c : lastRow) {

			String key = c.getStringCellValue().trim();
			if (StringUtils.isEmpty(key) || StringUtils.isBlank(key)) {
				continue;
			} else {
				
				CellWrapper cw;
				String[] keyArr = key.split("\\*\\*\\*");
				
				cw = new CellWrapper(keyArr[0],c.getColumnIndex());
				
				for(int i = 1; i < keyArr.length ; i++) {
					
					String[] keyChildArr = keyArr[i].split("===");
					if(keyChildArr.length < 2)
						continue;
					
					if("formatter".equals(keyChildArr[0]))
						cw.setFormatter(keyChildArr[1]);
	
				}
				
				this.cellWrappers.add(cw);
			}
		}

	}
      	
	
	/*-----------------------------------数据填充开始------------------------------------*/

    public List<Map<String,Object>> fillMapsBySheet(InputStream in,int sheetIndex) throws EncryptedDocumentException, InvalidFormatException, IOException{
    	
        if(in == null)
        	throw new ExcelException("输入流不能为空！");
        
        List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
        
        Workbook inWorkbook = WorkbookFactory.create(in);
        
        FormulaEvaluator evaluator = inWorkbook.getCreationHelper().createFormulaEvaluator();
        
        Sheet sheet = inWorkbook.getSheetAt(sheetIndex);
        
        if(sheet.getLastRowNum() <= this.lastRowIndex)
        	return result;
        
        for(int i = this.lastRowIndex ; i <= sheet.getLastRowNum() ; i++ ) {
        	
        	Map<String,Object> rowMap = new LinkedHashMap<String,Object>();
        	Row row = sheet.getRow(i);
        	for(CellWrapper cellWrapper : cellWrappers) {
        		rowMap.put(cellWrapper.getKey(), getCellValueByCell(row.getCell(cellWrapper.getCellIndex()),evaluator,cellWrapper));
        	}
        	
        	result.add(rowMap);
        	
        }
        
        return result;
        
    	
    }
    
    //获取单元格各类型值，返回字符串类型
    private String getCellValueByCell(Cell cell,FormulaEvaluator evaluator,CellWrapper cellWrapper) {
        //判断是否为null或空串
        if (cell==null || cell.toString().trim().equals("")) {
            return null;
        }
        String cellValue = "";
        int cellType=cell.getCellType();
        if(cellType==Cell.CELL_TYPE_FORMULA){ //表达式类型
            cellType=evaluator.evaluate(cell).getCellType();
        }
         
        switch (cellType) {
        case Cell.CELL_TYPE_STRING: //字符串类型
            cellValue= cell.getStringCellValue().trim();
            cellValue=StringUtils.isEmpty(cellValue) ? "" : cellValue; 
            break;
        case Cell.CELL_TYPE_BOOLEAN:  //布尔类型
            cellValue = String.valueOf(cell.getBooleanCellValue()); 
            break; 
        case Cell.CELL_TYPE_NUMERIC: //数值类型
             if (HSSFDateUtil.isCellDateFormatted(cell)) {  //判断日期类型
            	 if(cellWrapper.getFormatter() == null)
            		 cellValue = DateUtil.date2String(cell.getDateCellValue(), "yyyy-MM-dd");
            	 else
            		 cellValue = DateUtil.date2String(cell.getDateCellValue(), cellWrapper.getFormatter());
             } else {  //否
                 cellValue = new DecimalFormat("#.######").format(cell.getNumericCellValue()); 
                 
             } 
             
            break;
        default: //其它类型，取空串吧
            cellValue = "";
            break;
        }
        
        if(StringUtils.isBlank(cellValue))
       	 	return null;
        return cellValue;
    }
     
    
    public void closeWorkbook() throws IOException{
    	
    	this.workbook.close();
    	
    }
    
  
}
