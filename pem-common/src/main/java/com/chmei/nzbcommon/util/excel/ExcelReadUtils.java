package com.chmei.nzbcommon.util.excel;

import java.io.FileInputStream;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.chmei.nzbcommon.calc.Calc;
import com.chmei.nzbcommon.util.StringUtil;

/**
 * 读取excel工具类 导入excel文件
 * @author liuzl
 * @since 2018-11-26
 */
public class ExcelReadUtils {
    /**
     * 03Excel格式
     */
    private static final String ext03 = "xls";
    
    /**
     * 07Excel格式
     */
    private static final String ext07 = "xlsx";
    
    /**
     * 私有构造器
     */
    private ExcelReadUtils() {
    }
    

    /**
     * 使用io流解析excle
     *  type 1:03；2:07
     * @Title: readExcelType 
     * @param ios 文件路径
     * @param sheetNum sheet个数
     * @param format 日期格式
     * @param type 类型
     * @return List<Map<String,Object>> 读取数据
     * @throws Exception 异常信息
     */
    public static Map<String, Object> readExcelIO(InputStream ios, int type, int sheetNum, String format) throws Exception {
        if (ios == null) {
            return null;
        } else {
            if (type == 1) { // 03
                return readXls(null, ios, sheetNum, format);
            } else {
                return readXlsx(null, ios, sheetNum, format); // 07
            }
        }
    }
    /**
     * 读取excel 默认日期格式yyyy-mm-dd
     * @param ios 输入流
     * @param type 文件类型
     * @param sheetNum sheet数量
     * @return Map<String, Object> 读取数据  
     * @throws Exception 异常信息
     */
    public static Map<String, Object> readExcelIO(InputStream ios, int type, int sheetNum)  throws Exception {
        if (ios == null) {
            return null;
        } else {
            if (type == 1) { // 03
                return readXls(null, ios, sheetNum, "YYYY-MM-DD");
            } else {
                return readXlsx(null, ios, sheetNum, "YYYY-MM-DD"); // 07
            }
        }
    }
    
    /**
     * 解析2003excel 备注：第一行是头，第二行开始是
     * @Title: readXls 
     * @param filePath 文件路径
     * @param ios 输入流
     * @param sheetNum sheet数
     * @param format 日期格式
     * @throws IOException   异常信息
     * @return Map<String, Object> 返回数据    
     * @throws Exception 异常信息
     */
    private static Map<String, Object> readXls(String filePath, InputStream ios, int sheetNum, String format) throws Exception {
        Map<String, Object> result = new HashMap<>();
        //放入 excel内容
        List<Map<String, Object>> list = new ArrayList<>();
        InputStream is = null;
        HSSFWorkbook hssfWorkbook = null;
        try {
            if (ios == null) {
                is = new FileInputStream(filePath);
                hssfWorkbook = new HSSFWorkbook(is);
            } else {
                hssfWorkbook = new HSSFWorkbook(ios);
            }
        } catch (Exception e) {
//            e.printStackTrace();
            throw e;
        }
        // Read the Sheet
        for (int numSheet = 0; numSheet < sheetNum; numSheet++) {
            HSSFSheet hssfSheet = null;
            int columnNum = 0;
            HSSFRow hssfRow = null;
            int lastRow = 0;
            hssfSheet = hssfWorkbook.getSheetAt(numSheet);
            hssfRow = hssfSheet.getRow(0);
            columnNum = hssfRow.getPhysicalNumberOfCells();
            lastRow = hssfSheet.getLastRowNum();
            //列表 头部
            List<String> listHead = new ArrayList<String>();
            Map<String, Object> mapContent = null;
            // 获取内容
            for (int rowNum = 0; rowNum <= lastRow; rowNum++) {
                if (rowNum == 1) { //放入头部
                    result.put("header", listHead);
                }
                if (rowNum > 0) {
                    mapContent = new HashMap<String, Object>();
                }
                mapContent = new HashMap<String, Object>();
                String cellValue = "";
                hssfRow = hssfSheet.getRow(rowNum);
                for (int i = 0; i < columnNum; i++) {
                    HSSFCell cellh = hssfRow.getCell(i);
                    //判断是否还存在
                    if (cellh == null || cellh.equals("")) {
                        continue;
                    }
                    int cellType = cellh.getCellType();
                    switch (cellType) {
                        case HSSFCell.CELL_TYPE_STRING: // 文本
                            cellValue = cellh.getRichStringCellValue().getString();
                            break;
                        case HSSFCell.CELL_TYPE_NUMERIC: // 数字、日期
                            if (DateUtil.isCellDateFormatted(cellh)) {
                                SimpleDateFormat fmt = new SimpleDateFormat(format);
                                cellValue = fmt.format(cellh.getDateCellValue());
                            } else {
                                DecimalFormat df = new DecimalFormat("0.0000");
                                // DecimalFormat df = new DecimalFormat(NUM_POINT);
                                cellValue = Calc.formatDecimal(df.format(cellh.getNumericCellValue()));
                                
                            }
                            break;
                        case HSSFCell.CELL_TYPE_BOOLEAN: // 布尔型
                            cellValue = String.valueOf(cellh.getBooleanCellValue());
                            break;
                        case HSSFCell.CELL_TYPE_BLANK: // 空白
                            cellValue = cellh.getStringCellValue();
                            break;
                        case HSSFCell.CELL_TYPE_ERROR: // 错误
                            cellValue = "";
                            break;
                        case HSSFCell.CELL_TYPE_FORMULA: // 公式
                            cellh.setCellType(Cell.CELL_TYPE_STRING);
                            cellValue = String.valueOf(cellh.getRichStringCellValue().getString());
                            break;
                        default:
                            cellValue = "";
                    }
                    // 第一行
                    if (rowNum == 0) {
                        listHead.add(cellValue.trim());
                        continue;
                    } else {
                        mapContent.put(listHead.get(i), cellValue.trim());
                    }
                }
                if (rowNum > 0) {
                    list.add(mapContent);
                }
            }
        }
        result.put("content", list);
        hssfWorkbook.close();
        return result;
    }

    /**
     * 解析2010excel 备注：第一行是头，第二行开始是
     * @Title: readXlsx 
     * @Description: TODO
     * @param filePath 文件路径
     * @param ios 输入流
     * @param sheetNum sheet数量
     * @param format 日期格式
     * @return Map<String, Object> 返回数据
     * @throws Exception 异常信息
     */
    private static Map<String, Object> readXlsx(String filePath, InputStream ios, int sheetNum, String format) throws Exception {
        Map<String, Object> result = new HashMap<>();
        //放入 excel内容
        List<Map<String, Object>> list = new ArrayList<>();
        InputStream is = null;
        XSSFWorkbook xssfWorkbook = null;
        try {
            if (ios == null) {
                is = new FileInputStream(filePath);
                xssfWorkbook = new XSSFWorkbook(is);
            } else {
                xssfWorkbook = new XSSFWorkbook(ios);
            }
        } catch (Exception e) {
//            e.printStackTrace();
            throw e;
        }
        // Read the Sheet
        for (int numSheet = 0; numSheet < sheetNum; numSheet++) {
            XSSFSheet xssfSheet = null;
            XSSFRow xssfRow = null;
            int columnNum = 0;
            int lastRow = 0;
            xssfSheet = xssfWorkbook.getSheetAt(numSheet);
            xssfRow = xssfSheet.getRow(0);
            columnNum = xssfRow.getPhysicalNumberOfCells();
            lastRow = xssfSheet.getLastRowNum();
            //头部列表
            List<String> listHead = new ArrayList<String>();
            Map<String, Object> mapContent = null;
            // 获取内容
            for (int rowNum = 0; rowNum <= lastRow; rowNum++) {
                if (rowNum == 1) { //放入头部
                    result.put("header", listHead);
                }
                if (rowNum > 0) {
                    mapContent = new HashMap<String, Object>();
                }
                String cellValue = "";
                xssfRow = xssfSheet.getRow(rowNum);
                for (int i = 0; i < columnNum; i++) {
                    if (xssfRow == null) {
                        continue;
                    }
                    Cell cell = xssfRow.getCell(i);
                    //判断是否还存在
                    if (cell == null || cell.equals("")) {
                        continue;
                    }
                    
                    int cellType = cell.getCellType();
                    switch (cellType) {
                        case Cell.CELL_TYPE_STRING: // 文本
                            cellValue = cell.getRichStringCellValue().getString();
                            break;
                        case Cell.CELL_TYPE_NUMERIC: // 数字、日期
                            if (DateUtil.isCellDateFormatted(cell)) {
                                SimpleDateFormat fmt = new SimpleDateFormat(format);
                                cellValue = fmt.format(cell.getDateCellValue());
                            } else {
                                DecimalFormat df = new DecimalFormat("0.0000");
                                // DecimalFormat df = new DecimalFormat(NUM_POINT);
                                cellValue = Calc.formatDecimal(df.format(cell.getNumericCellValue()));
                            }
                            break;
                        case Cell.CELL_TYPE_BOOLEAN: // 布尔型
                            cellValue = String.valueOf(cell.getBooleanCellValue());
                            break;
                        case Cell.CELL_TYPE_BLANK: // 空白
                            cellValue = cell.getStringCellValue();
                            break;
                        case Cell.CELL_TYPE_ERROR: // 错误
                            cellValue = "";
                            break;
                        case Cell.CELL_TYPE_FORMULA: // 公式
                            cell.setCellType(Cell.CELL_TYPE_STRING);
                            cellValue = String.valueOf(cell.getRichStringCellValue().getString());
                            break;
                        default:
                            cellValue = "";
                    }
                    // 第一行
                    if (rowNum == 0) {
                        listHead.add(cellValue.trim());
                        continue;
                    } else {
                        mapContent.put(listHead.get(i), cellValue.trim());
                    }
                }
                if (rowNum > 0) {
                    if (mapContent.size() > 0) {
                        list.add(mapContent);
                    }
                }
            }
        }
//        TestAddplan.addPlanWeight(list);
        xssfWorkbook.close();
        result.put("content", list);
        return result;
    }
    
    //——————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
    /**
     * 解析03Excel 数据从第二行开始解析 自定义表头参数
     * @param filePath 文件地址
     * @param ios 输入流
     * @param headers 传入的表头参数  返回的mao的键
     * @param format 若有日期 则表示日期的格式
     * @return 返回数据
     * @throws Exception 异常信息
     */
    private static List<Map<String, Object>> readXls(String filePath,
            InputStream ios, List<String> headers, String format)
            throws Exception {
        // 放入 excel内容
        List<Map<String, Object>> list = new ArrayList<>();
        InputStream is = null;
        HSSFWorkbook hssfWorkbook = null;
        try {
            if (ios == null) {
                is = new FileInputStream(filePath);
                hssfWorkbook = new HSSFWorkbook(is);
            } else {
                hssfWorkbook = new HSSFWorkbook(ios);
            }
        } catch (Exception e) {
            throw e;
        }
        // Read the Sheet
        HSSFSheet hssfSheet = null;
        int columnNum = 0;
        HSSFRow hssfRow = null;
        int lastRow = 0;
        hssfSheet = hssfWorkbook.getSheetAt(0);
        hssfRow = hssfSheet.getRow(0);
        columnNum = hssfRow.getPhysicalNumberOfCells();
        //按照模板列 判断 若小于模板列数 则不允许导入
        if (columnNum < headers.size()) {
            hssfWorkbook.close();
            throw new Exception("请导入正确的文件模板格式");
        }
        lastRow = hssfSheet.getLastRowNum();
        Map<String, Object> mapContent = null;
        // 获取内容
        for (int rowNum = 1; rowNum <= lastRow; rowNum++) {
            if (rowNum > 0) {
                mapContent = new HashMap<String, Object>();
            }
            String cellValue = "";
            hssfRow = hssfSheet.getRow(rowNum);
            if (hssfRow == null) {
                continue;
            }
            int columnSize = 0;//计数器 表示有多少个空列
            for (int i = 0; i < headers.size(); i++) {
                HSSFCell cellh = hssfRow.getCell(i);
                int cellType = -9999;//不存在类型
                if(cellh != null){
                	cellType = cellh.getCellType();
                }
                switch (cellType) {
                    case HSSFCell.CELL_TYPE_STRING: // 文本
                        cellValue = cellh.getRichStringCellValue().getString();
                        break;
                    case HSSFCell.CELL_TYPE_NUMERIC: // 数字、日期
                        if (DateUtil.isCellDateFormatted(cellh)) {
                            SimpleDateFormat fmt = new SimpleDateFormat(format);
                            cellValue = fmt.format(cellh.getDateCellValue());
                        } else {
                            DecimalFormat df = new DecimalFormat("0.0000");
                            // DecimalFormat df = new DecimalFormat(NUM_POINT);
                            cellValue = Calc.formatDecimal(df.format(cellh.getNumericCellValue()));
                        }
                        break;
                    case HSSFCell.CELL_TYPE_BOOLEAN: // 布尔型
                        cellValue = String.valueOf(cellh.getBooleanCellValue());
                        break;
                    case HSSFCell.CELL_TYPE_BLANK: // 空白
                        cellValue = cellh.getStringCellValue();
                        break;
                    case HSSFCell.CELL_TYPE_ERROR: // 错误
                        cellValue = "";
                        break;
                    case HSSFCell.CELL_TYPE_FORMULA: // 公式
                        cellh.setCellType(Cell.CELL_TYPE_STRING);
                        cellValue = String.valueOf(cellh.getRichStringCellValue()
                                .getString());
                        break;
                    default:
                        cellValue = "";
                }
                if(cellValue.trim().equals("")){
                	columnSize++;
                }
                // 第一行
                mapContent.put(headers.get(i), cellValue.trim());
            }
            if (rowNum > 0 && !mapContent.isEmpty()) {
            	//保证没有值过滤；列不足过滤
            	if(columnSize == columnNum || mapContent.size() < columnNum){
            		continue;
            	}
                list.add(mapContent);
            }
        }
        hssfWorkbook.close();
        return list;
    }
    
    /**
     * 解析07Excel 数据从第二行开始解析 自定义表头参数
     * @param filePath 文件地址
     * @param ios 输入流
     * @param headers 传入的表头参数  返回的mao的键
     * @param format 若有日期 则表示日期的格式
     * @return 返回数据
     * @throws Exception 异常信息
     */
    private static List<Map<String, Object>> readXlsx(String filePath,
            InputStream ios, List<String> headers, String format)
            throws Exception {
        // 放入 excel内容
        List<Map<String, Object>> list = new ArrayList<>();
        InputStream is = null;
        XSSFWorkbook xssfWorkbook = null;
        try {
            if (ios == null) {
                is = new FileInputStream(filePath);
                OPCPackage opcPackage = OPCPackage.open(is);
                xssfWorkbook = new XSSFWorkbook(opcPackage);
            } else {
                OPCPackage opcPackage = OPCPackage.open(ios);
                xssfWorkbook = new XSSFWorkbook(opcPackage);
            }
        } catch (Exception e) {
//            e.printStackTrace();
            throw e;
        }
        // Read the Sheet
        XSSFSheet xssfSheet = null;
        XSSFRow xssfRow = null;
        int columnNum = 0;
        int lastRow = 0;
        xssfSheet = xssfWorkbook.getSheetAt(0);
        xssfRow = xssfSheet.getRow(0);
        columnNum = xssfRow.getPhysicalNumberOfCells();
        if (columnNum < headers.size()) {
            xssfWorkbook.close();
            throw new Exception("请导入正确的文件格式");
        }
        lastRow = xssfSheet.getLastRowNum();
        // 头部列表
        Map<String, Object> mapContent = null;
        // 获取内容
        for (int rowNum = 1; rowNum <= lastRow; rowNum++) {
            if (rowNum > 0) {
                mapContent = new HashMap<String, Object>();
            }
            String cellValue = "";
            xssfRow = xssfSheet.getRow(rowNum);
            if (xssfRow == null) {
                continue;
            }
            int columnSize = 0;//计数器 表示有多少个空列
            for (int i = 0; i < headers.size(); i++) {
                Cell cell = xssfRow.getCell(i);
                int cellType = -9999;//不存在类型
                if(cell != null){
                	cellType = cell.getCellType();
                }
                switch (cellType) {
                    case Cell.CELL_TYPE_STRING: // 文本
                        cellValue = cell.getRichStringCellValue().getString();
                        break;
                    case Cell.CELL_TYPE_NUMERIC: // 数字、日期
                        if (DateUtil.isCellDateFormatted(cell)) {
                            SimpleDateFormat fmt = new SimpleDateFormat(format);
                            cellValue = fmt.format(cell.getDateCellValue());
                        } else {
                            DecimalFormat df = new DecimalFormat("0.0000");
                            // DecimalFormat df = new DecimalFormat(NUM_POINT);
                            cellValue = Calc.formatDecimal(df.format(cell.getNumericCellValue()));
                        }
                        break;
                    case Cell.CELL_TYPE_BOOLEAN: // 布尔型
                        cellValue = String.valueOf(cell.getBooleanCellValue());
                        break;
                    case Cell.CELL_TYPE_BLANK: // 空白
                        cellValue = cell.getStringCellValue();
                        break;
                    case Cell.CELL_TYPE_ERROR: // 错误
                        cellValue = "";
                        break;
                    case Cell.CELL_TYPE_FORMULA: // 公式
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        cellValue = String.valueOf(cell.getRichStringCellValue()
                                .getString());
                        break;
                    default:
                        cellValue = "";
                }
                if(cellValue.trim().equals("")){
                	columnSize++;
                }
                // 第一行
                mapContent.put(headers.get(i), cellValue.trim());
            }
            if (rowNum > 0) {
                if (mapContent.size() > 0) {
                	//保证没有值过滤；列不足过滤
                	if(columnSize == columnNum || mapContent.size() < columnNum){
                		continue;
                	}
                    list.add(mapContent);
                }
            }
        }
        xssfWorkbook.close();
        return list;
    }
    /**
     * 读取excel方法
     * @param ios 文件流
     * @param type 文件类型
     * @param headers 文件定义头部
     * @param format 日期格式
     * @return List<Map<String,Object>> 返回数据
     * @throws Exception 异常信息
     */
    public static List<Map<String, Object>> readExcel(InputStream ios, int type, List<String> headers, String format) throws Exception {
        if (ios == null) {
            return null;
        } else {
            if (type == 1) { // 03
                return readXls(null, ios, headers, format);
            } else {
                return readXlsx(null, ios, headers, format); // 07
            }
        }
    }
    /**
     * 读取excel方法
     * @param ios 文件流
     * @param type 文件类型
     * @param headers 文件定义头部
     * @return List<Map<String,Object>> 返回数据
     * @throws Exception 异常信息
     */
    public static List<Map<String, Object>> readExcel(InputStream ios, int type, List<String> headers) throws Exception {
        if (ios == null) {
            return null;
        } else {
            if (type == 1) { // 03
                return readXls(null, ios, headers, "YYYY-MM-DD");
            } else {
                return readXlsx(null, ios, headers, "YYYY-MM-DD"); // 07
            }
        }
    }
    /**
     * 读取excel文件
     * @param filePath 文件路径
     * @param headers 文件定义表头
     * @param format 日期格式
     * @return List<Map<String,Object>> 返回数据
     * @throws Exception 异常信息
     */
    public static List<Map<String, Object>> readExcel(String filePath, List<String> headers, String format) throws Exception {
        if (StringUtil.isEmpty(filePath)) {
            return null;
        } else {
            int index = filePath.lastIndexOf(".") + 1;
            String ext = filePath.substring(index);
            if (ext03.equals(ext)) {
                return readXls(null, new FileInputStream(filePath), headers, format);
            } else if (ext07.equals(ext)) {
                return readXlsx(null, new FileInputStream(filePath), headers, format); // 07
            } else {
                throw new Exception("传入的不是excel文件");
            }
        }
    }
    
    /**
     * 读取excel文件 文件中日期默認格式yyyy-mm-dd
     * @param filePath 文件路径
     * @param headers 文件定义表头
     * @return List<Map<String,Object>> 返回数据
     * @throws Exception 异常信息
     */
    public static List<Map<String, Object>> readExcel(String filePath, List<String> headers) throws Exception {
        if (StringUtil.isEmpty(filePath)) {
            return null;
        } else {
            int index = filePath.lastIndexOf(".");
            String ext = filePath.substring(index + 1);
            if (ext03.equals(ext)) {
                return readXls(null, new FileInputStream(filePath), headers, "YYYY-MM-DD");
            } else if (ext07.equals(ext)) {
                return readXlsx(null, new FileInputStream(filePath), headers, "YYYY-MM-DD"); // 07
            } else {
                throw new Exception("传入的不是excel文件");
            }
        }
    }
    
    /**
     * 读取excel文件
     * @param fileName 文件名称
     * * @param ios 输入流
     * @param headers 文件定义表头
     * @param format 日期格式
     * @return List<Map<String,Object>> 返回数据
     * @throws Exception 异常信息
     */
    public static List<Map<String, Object>> readExcel(String fileName, InputStream ios, List<String> headers, String format) throws Exception {
        if (StringUtil.isEmpty(fileName)) {
            return null;
        } else {
            int index = fileName.lastIndexOf(".") + 1;
            String ext = fileName.substring(index);
            if (ext03.equals(ext)) {
                return readXls(null, ios, headers, format);
            } else if (ext07.equals(ext)) {
                return readXlsx(null, ios, headers, format); // 07
            } else {
                throw new Exception("传入的不是excel文件");
            }
        }
    }
    
    /**
     * 读取excel文件
     * @param fileName 文件名称
     * @param ios 输入流
     * @param headers 文件定义表头
     * @return List<Map<String, Object>> 返回数据
     * @throws Exception 异常信息
     */
    public static List<Map<String, Object>> readExcel(String fileName, InputStream ios, List<String> headers) throws Exception {
        if (StringUtil.isEmpty(fileName)) {
            return null;
        } else {
            int index = fileName.lastIndexOf(".") + 1;
            String ext = fileName.substring(index);
            if (ext03.equals(ext)) {
                return readXls(null, ios, headers, "YYYY-MM-DD");
            } else if (ext07.equals(ext)) {
                return readXlsx(null, ios, headers, "YYYY-MM-DD"); // 07
            } else {
                throw new Exception("传入的不是excel文件");
            }
        }
    }
    /**
     * 
     * @Description: 质检导入
     * @author: wuxiandeng
     * @date: 2019年3月29日 上午9:52:28
     */
    public static List<Map<String,Object>> readExcel(String fileName, InputStream ios, List<String> headers, int row) throws Exception{
        if (StringUtil.isEmpty(fileName)) {
            return null;
        } else {
            int index = fileName.lastIndexOf(".") + 1;
            String ext = fileName.substring(index);
            if (ext03.equals(ext)) {
                return readXls(null, ios, headers, "yyyyMM", row);
            } else if (ext07.equals(ext)) {
                return readXlsx(null, ios, headers, "yyyyMM", row); // 07
            } else {
                throw new Exception("传入的不是excel文件");
            }
        }
    }

    /**
     * 
     * @Description: 质检导入
     * @author: wuxiandeng
     * @date: 2019年3月29日 上午9:52:46
     */
    private static List<Map<String, Object>> readXls(String filePath,
            InputStream ios, List<String> headers, String format, int row)
            throws Exception {
        // 放入 excel内容
        List<Map<String, Object>> list = new ArrayList<>();
        InputStream is = null;
        HSSFWorkbook hssfWorkbook = null;
        try {
            if (ios == null) {
                is = new FileInputStream(filePath);
                hssfWorkbook = new HSSFWorkbook(is);
            } else {
                hssfWorkbook = new HSSFWorkbook(ios);
            }
        } catch (Exception e) {
//            e.printStackTrace();
            throw e;
        }
        // Read the Sheet
        HSSFSheet hssfSheet = null;
        int columnNum = 0;
        HSSFRow hssfRow = null;
        int lastRow = 0;
        hssfSheet = hssfWorkbook.getSheetAt(0);
        hssfRow = hssfSheet.getRow(0);
        columnNum = hssfRow.getPhysicalNumberOfCells();
        if (columnNum != headers.size()) {
            hssfWorkbook.close();
            throw new Exception("请导入正确的文件格式");
        }
        lastRow = hssfSheet.getLastRowNum();
        Map<String, Object> mapContent = null;
        // 获取内容
        for (int rowNum = row; rowNum <= lastRow; rowNum++) {
            if (rowNum > 0) {
                mapContent = new HashMap<String, Object>();
            }
            String cellValue = "";
            hssfRow = hssfSheet.getRow(rowNum);
            for (int i = 0; i < columnNum; i++) {
                HSSFCell cellh = hssfRow.getCell(i);
                // 判断是否还存在
                if (cellh == null || cellh.equals("")) {
                    continue;
                }
                int cellType = cellh.getCellType();
                switch (cellType) {
                    case HSSFCell.CELL_TYPE_STRING: // 文本
                        cellValue = cellh.getRichStringCellValue().getString();
                        break;
                    case HSSFCell.CELL_TYPE_NUMERIC: // 数字、日期
                        if (DateUtil.isCellDateFormatted(cellh)) {
                            SimpleDateFormat fmt = new SimpleDateFormat(format);
                            cellValue = fmt.format(cellh.getDateCellValue());
                        } else {
                            DecimalFormat df = new DecimalFormat("0.0000");
                            // DecimalFormat df = new DecimalFormat(NUM_POINT);
                            cellValue = Calc.formatDecimal(df.format(cellh.getNumericCellValue()));
                        }
                        break;
                    case HSSFCell.CELL_TYPE_BOOLEAN: // 布尔型
                        cellValue = String.valueOf(cellh.getBooleanCellValue());
                        break;
                    case HSSFCell.CELL_TYPE_BLANK: // 空白
                        cellValue = cellh.getStringCellValue();
                        break;
                    case HSSFCell.CELL_TYPE_ERROR: // 错误
                        cellValue = "";
                        break;
                    case HSSFCell.CELL_TYPE_FORMULA: // 公式
                        cellh.setCellType(Cell.CELL_TYPE_STRING);
                        cellValue = String.valueOf(cellh.getRichStringCellValue()
                                .getString());
                        break;
                    default:
                        cellValue = "";
                }
                // 第一行
                mapContent.put(headers.get(i), cellValue.trim());
            }
            if (rowNum > 0) {
                list.add(mapContent);
            }
        }
        hssfWorkbook.close();
        return list;
    }
    
    /**
     * 
     * @Description: 质检导入
     * @author: wuxiandeng
     * @date: 2019年3月29日 上午9:52:59
     */
    private static List<Map<String, Object>> readXlsx(String filePath,
            InputStream ios, List<String> headers, String format, int row)
            throws Exception {
        // 放入 excel内容
        List<Map<String, Object>> list = new ArrayList<>();
        InputStream is = null;
        XSSFWorkbook xssfWorkbook = null;
        try {
            if (ios == null) {
                is = new FileInputStream(filePath);
                OPCPackage opcPackage = OPCPackage.open(is);
                xssfWorkbook = new XSSFWorkbook(opcPackage);
            } else {
                OPCPackage opcPackage = OPCPackage.open(ios);
                xssfWorkbook = new XSSFWorkbook(opcPackage);
            }
        } catch (Exception e) {
//            e.printStackTrace();
            throw e;
        }
        // Read the Sheet
        XSSFSheet xssfSheet = null;
        XSSFRow xssfRow = null;
        int columnNum = 0;
        int lastRow = 0;
        xssfSheet = xssfWorkbook.getSheetAt(0);
        if (xssfSheet == null) {
            xssfWorkbook.close();
            throw new Exception("请导入正确的文件格式");
        }
        xssfRow = xssfSheet.getRow(0);
        if (xssfRow == null) {
            xssfWorkbook.close();
            throw new Exception("请导入正确的文件格式");
        }
        columnNum = xssfRow.getPhysicalNumberOfCells();
        if (columnNum != headers.size()) {
            xssfWorkbook.close();
            throw new Exception("请导入正确的文件格式");
        }
        lastRow = xssfSheet.getLastRowNum();
        // 头部列表
        Map<String, Object> mapContent = null;
        // 获取内容
        for (int rowNum = row; rowNum <= lastRow; rowNum++) {
            if (rowNum > 0) {
                mapContent = new HashMap<String, Object>();
            }
            String cellValue = "";
            xssfRow = xssfSheet.getRow(rowNum);
            for (int i = 0; i < columnNum; i++) {
                if (xssfRow == null) {
                    continue;
                }
                Cell cell = xssfRow.getCell(i);
                // 判断是否还存在
                if (cell == null || cell.equals("")) {
                    continue;
                }

                int cellType = cell.getCellType();
                switch (cellType) {
                    case Cell.CELL_TYPE_STRING: // 文本
                        cellValue = cell.getRichStringCellValue().getString();
                        break;
                    case Cell.CELL_TYPE_NUMERIC: // 数字、日期
                        if (DateUtil.isCellDateFormatted(cell)) {
                            SimpleDateFormat fmt = new SimpleDateFormat(format);
                            cellValue = fmt.format(cell.getDateCellValue());
                        } else {
                            DecimalFormat df = new DecimalFormat("0.0000");
                            // DecimalFormat df = new DecimalFormat(NUM_POINT);
                            cellValue = Calc.formatDecimal(df.format(cell.getNumericCellValue()));
                        }
                        break;
                    case Cell.CELL_TYPE_BOOLEAN: // 布尔型
                        cellValue = String.valueOf(cell.getBooleanCellValue());
                        break;
                    case Cell.CELL_TYPE_BLANK: // 空白
                        cellValue = cell.getStringCellValue();
                        break;
                    case Cell.CELL_TYPE_ERROR: // 错误
                        cellValue = "";
                        break;
                    case Cell.CELL_TYPE_FORMULA: // 公式
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        cellValue = String.valueOf(cell.getRichStringCellValue()
                                .getString());
                        break;
                    default:
                        cellValue = "";
                }
                // 第一行
                mapContent.put(headers.get(i), cellValue.trim());
            }
            if (rowNum > 0) {
                if (mapContent.size() > 0) {
                    list.add(mapContent);
                }
            }
        }
        xssfWorkbook.close();
        return list;
    }
}
