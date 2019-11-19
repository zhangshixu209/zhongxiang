package com.chmei.nzbcommon.util.excel;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 导出excel公用类
 * @ClassName: ExcelDownUtils 
 * @Description: TODO
 * @author 翟超锋   mail - 357111361@qq.com
 * @date 2018年1月5日 下午3:16:30 
 *
 */
public class ExcelDownUtils {

	/** log日志 */
	private static Logger logger = LoggerFactory.getLogger(ExcelDownUtils.class);
	
	/**lock*/
	private static final Object lock1 = new Object();//add for sonar
	
	/**导出数据到excel通用导出
	 * 备注：设置所有列导出为文本
	 * 
	 * @param response   HttpServletResponse
	 * @param filename   下载文件名称
	 * @param headers    头
	 * @param Conters    容
	 * @param sheetnnum  sheet个数
	 * @param sheetName  sheet名称
	 * @return  成功 true | 失败 false
	 */
	public static boolean downloadExcelUtil(HttpServletResponse response,String filename,
			                                List<String[]> headers,List<List<Map<String,Object>>> Conters,
			                                int sheetnnum,String sheetName[]){
		//只要有空返回null
		if(headers == null || headers.size()==0 || Conters == null || Conters.size()==0 ){
			return false;
		}
		//sheet个数
		if(sheetnnum != sheetName.length){
			return false;
		}
		InputStream in = null;
		ServletOutputStream output = null;
		try {
			synchronized (lock1) {//闭环执行 // add 
				HSSFWorkbook wb = new HSSFWorkbook();  
				//设置文本样式
				HSSFCellStyle cellStyle = wb.createCellStyle();
				HSSFDataFormat format = wb.createDataFormat();
			    cellStyle.setDataFormat(format.getFormat("@"));
			    
				for(int k=0;k<sheetName.length;k++){
					HSSFSheet sheet = wb.createSheet(sheetName[k]); 
				    HSSFCellStyle style = wb.createCellStyle();  
					// 设置居中样式  
					style.setAlignment(HSSFCellStyle.ALIGN_CENTER); 
					style.setBorderBottom(HSSFCellStyle.BORDER_THIN);// 下边框
					style.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
					style.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框
					style.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
					style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//垂直居中
					style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
					style.setFillForegroundColor(HSSFColor.YELLOW.index);
					
					HSSFFont headerFont = (HSSFFont) wb.createFont();
					headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 字体加粗
					style.setFont(headerFont);
					// 添加表格头  
					HSSFRow row = sheet.createRow((int) 0);
					String[] header = headers.get(k);
					for (int i = 0; i < header.length; i++) {  
					    HSSFCell cell = row.createCell(i);  
					    cell.setCellStyle(cellStyle);
					    cell.setCellStyle(style); 
					    cell.setCellValue(header[i]);  
					    //设置宽度
                        sheet.setColumnWidth(i, 256*15); 
					}  
					//内容
					List<Map<String,Object>> Conter = Conters.get(k);
					for (int j = 0; j < Conter.size(); j++) {  
						row = sheet.createRow((int) j+1);  
						Map<String,Object> map = Conter.get(j);
						for(int n = 0; n<map.size(); n++) {
							HSSFCell cell = row.createCell(n);  
							cell.setCellStyle(cellStyle);
						    cell.setCellValue((map.get(header[n])+"").trim()==null?"":(map.get(header[n])+"").trim());  
						}
					}  
				}
			//io流出
			response.setHeader("Content-disposition", "attachment;"
							+ "filename="
							+ new String(filename.getBytes("GBK"), "ISO_8859_1")
							+ ".xls");
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Type","application/vnd.ms-excel");
			output = response.getOutputStream();
			wb.write(output);
			output.flush();
			output.close();
			}
		} catch (Exception e) {
			logger.error("DownExcel--->通用导出excel出错！", e);
			e.printStackTrace();
		} finally {
			try {
				if (null != in) {
				    in.close();
				}
				if (null != output) {
					output.close();
				}
			} catch (IOException e) {
				logger.error("DownExcel--->通用导出excel-succes，关闭io失败！", e);
				e.printStackTrace();
			}
		}
		return true;
	}
	
	
	   /**导出数据到excel通用导出
     * 备注：设置所有列导出为文本
     * 
     * @param response   HttpServletResponse
     * @param filename   下载文件名称
     * @param headers    头部行
     * @param conters    数据
     * @param sheetnnum  sheet个数
     * @param sheetName  sheet名称
     * @param keys 获取数据键
     * @return  成功 true | 失败 false
     */
    public static boolean downloadExcelUtil(HttpServletResponse response, String filename,
                                            List<String[]> headers, List<List<Map<String, Object>>> conters,
                                            int sheetnnum, String[] sheetName, String[] keys) {
        //只要有空返回null
        if (headers == null || headers.size() == 0 || conters == null || conters.size() == 0) {
            return false;
        }
        //sheet个数
        if (sheetnnum != sheetName.length) {
            return false;
        }
        InputStream in = null;
        ServletOutputStream output = null;
        try {
            synchronized (lock1) { //闭环执行 // add 
                HSSFWorkbook wb = new HSSFWorkbook();  
                //设置文本样式
                HSSFCellStyle cellStyle = wb.createCellStyle();
                HSSFDataFormat format = wb.createDataFormat();
                cellStyle.setDataFormat(format.getFormat("@"));
                
                for (int k = 0; k < sheetName.length; k++) {
                    HSSFSheet sheet = wb.createSheet(sheetName[k]); 
                    HSSFCellStyle style = wb.createCellStyle();  
                    // 设置居中样式  
//                    style.setAlignment(HSSFCellStyle.ALIGN_CENTER); 
                    style.setAlignment(HorizontalAlignment.CENTER);
//                    style.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 下边框
                    style.setBorderBottom(BorderStyle.THIN);
//                    style.setBorderLeft(HSSFCellStyle.BORDER_THIN); // 左边框
                    style.setBorderLeft(BorderStyle.THIN);
//                    style.setBorderRight(HSSFCellStyle.BORDER_THIN); // 右边框
                    style.setBorderRight(BorderStyle.THIN);
//                    style.setBorderTop(HSSFCellStyle.BORDER_THIN); // 上边框
                    style.setBorderTop(BorderStyle.THIN);
//                    style.setVerticalAlignment(CellStyle.VERTICAL_CENTER); //垂直居中
                    style.setVerticalAlignment(VerticalAlignment.CENTER);
//                    style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                    style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                    HSSFPalette customPalette = wb.getCustomPalette();
                    customPalette.setColorAtIndex((short) 51, (byte) 198, (byte) 239, (byte) 206);
                    style.setFillForegroundColor((short) 51);
                    
                    HSSFFont headerFont = (HSSFFont) wb.createFont();
//                    headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 字体加粗
                    headerFont.setBold(true);
                    headerFont.setColor(HSSFColor.GREEN.index);
                    style.setFont(headerFont);
                    // 添加表格头  
                    HSSFRow row = sheet.createRow((int) 0);
                    String[] header = headers.get(k);
                    for (int i = 0; i < header.length; i++) {  
                        HSSFCell cell = row.createCell(i);  
                        cell.setCellStyle(cellStyle);
                        cell.setCellStyle(style); 
                        cell.setCellValue(header[i]);  
                        //设置宽度
                        sheet.setColumnWidth(i, 256 * 15); 
                        /*sheet.setColumnWidth(i, (int)(header[i].getBytes().length * 1.2d * 256 > 12 * 256 ?
                        		                      header[i].getBytes().length * 0.8d * 256 : 12 * 256));*/
                    }  
                    //内容
                    List<Map<String, Object>> conter = conters.get(k);
                    for (int j = 0; j < conter.size(); j++) {  
                        row = sheet.createRow((int) j + 1);  
                        Map<String, Object> map = conter.get(j);
                        for (int n = 0; n < map.size(); n++) {
                            HSSFCell cell = row.createCell(n);  
                            cell.setCellStyle(cellStyle);
                            cell.setCellValue((map.get(keys[n])) == null ? "" : (map.get(keys[n]) + "").trim());  
                        }
                    }  
                }
            //io流出
                response.setHeader("Content-disposition", "attachment;"
                            + "filename="
                            + new String(filename.getBytes("GBK"), "ISO_8859_1")
                            + ".xls");
                response.setContentType("application/octet-stream");
                response.setHeader("Content-Type", "application/vnd.ms-excel");
                output = response.getOutputStream();
                wb.write(output);
                output.flush();
                output.close();
            }
        } catch (Exception e) {
            logger.error("DownExcel--->通用导出excel出错！", e);
//            e.printStackTrace();
        } finally {
            try {
                if (null != in) {
                    in.close();
                }
                if (null != output) {
                    output.close();
                }
            } catch (IOException e) {
                logger.error("DownExcel--->通用导出excel-succes，关闭io失败！", e);
//                e.printStackTrace();
            }
        }
        return true;
    }

}
