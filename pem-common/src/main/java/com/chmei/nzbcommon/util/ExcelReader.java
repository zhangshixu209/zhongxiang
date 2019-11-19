package com.chmei.nzbcommon.util;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.UnsupportedFileFormatException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chmei.nzbcommon.exception.NzbCommonException;


/**
 * 读取Excel文件，支持03和07格式
 *
 */
public class ExcelReader {
	private static final Logger LOG = LoggerFactory
			.getLogger(ExcelReader.class);
	
	private ExcelReader(){
	}


	/**
	 * 读取Excel文件
	 * 
	 * @param fileName
	 *            文件名，带后缀扩展名
	 * @param in
	 *            输入流
	 * @param ignoreRows
	 *            忽略的行数
	 * @param totalCols
	 *           读取最大列数
	 * @return 每一行保存为Map结构， Map中当Key为"flag"的值为"false"表示该行有错误，错误信息存在"error"中；
	 *         "flag"的值为"true"表示该行数据有效，Key为标题名，Value为值
	 * @throws IOException
	 */
	public static List<Map<String,String>> readExcel(String beforeUniqueKey, String fileName,
			InputStream in, int ignoreRows, int totalCols,List<Map<String, String>> ignoreList) throws IOException {
		String ext = fileName.lastIndexOf(".") == -1 ? "" : fileName
				.substring(fileName.lastIndexOf(".") + 1);
		if ("xls".equals(ext)) {
			return read2003Excel(beforeUniqueKey, in, ignoreRows, totalCols,ignoreList);
		} else if ("xlsx".equals(ext)) {
			return read2007Excel(beforeUniqueKey, in, ignoreRows, totalCols,ignoreList);
		} else {
			throw new IOException("不支持的文件类型!");
		}
	}

	/**
	 * 读取03格式Excel文件
	 * 
	 * @param in
	 *            输入流
	 * @param ignoreRows
	 *            忽略的行数
	 * @param totalCols
	 *            列总数
	 * @return 每一行保存为Map结构， Map中当Key为"flag"的值为"false"表示该行有错误，错误信息存在"error"中；
	 *         "flag"的值为"true"表示该行数据有效，Key为标题名，Value为值
	 * @throws IOException
	 */
	public static List<Map<String,String>> read2003Excel(String beforeUniqueKey, InputStream in,
			int ignoreRows, int totalCols,List<Map<String, String>> ignoreList) throws IOException {
		return readExcel(beforeUniqueKey, in, ignoreRows, totalCols, true,ignoreList);
	}

	/**
	 * 读取07格式Excel
	 * 
	 * @param in
	 *            输入流
	 * @param ignoreRows
	 *            忽略的行数
	 * @param totalCols
	 *            列总数
	 * @return 每一行保存为Map结构， Map中当Key为"flag"的值为"false"表示该行有错误，错误信息存在"error"中；
	 *         "flag"的值为"true"表示该行数据有效，Key为标题名，Value为值
	 * @throws IOException
	 */
	public static List<Map<String, String>> read2007Excel(String beforeUniqueKey, InputStream in,
			int ignoreRows, int totalCols,List<Map<String, String>> ignoreList) throws IOException {
		return readExcel(beforeUniqueKey, in, ignoreRows, totalCols, false,ignoreList);
	}
	
	/**
	 * 读取Excel
	 * @param in	输入流
	 * @param ignoreRows	忽略的行数
	 * @param totalCols		列总数
	 * @param is2003 是否是03格式
	 * @return 每一行保存为Map结构， Map中当Key为"flag"的值为"false"表示该行有错误，错误信息存在"error"中；
	 *         "flag"的值为"true"表示该行数据有效，Key为标题名，Value为值
	 * @throws IOException
	 */
	private static List<Map<String, String>> readExcel(String beforeUniqueKey, InputStream in,
			int ignoreRows, int totalCols, boolean is2003,List<Map<String, String>> ignoreList) throws IOException {
		List<Map<String, String>> list = new LinkedList<>();
		List<String> title = new ArrayList<>();
		
		Workbook wb;
		Sheet sheet;
		Row row;
		String mtd;
		if(is2003) {
			wb = new HSSFWorkbook(in);
			sheet = wb.getSheetAt(0);
			mtd = "read2003Excel{}";
		} else {
			wb = new XSSFWorkbook(in);
			sheet = wb.getSheetAt(0);
			mtd = "read2007Excel{}";
		}

		LOG.info(mtd, "Begin Read Excel <<<");
			// 忽略指定行数再开始读取
			for (int i = ignoreRows; i <= sheet.getPhysicalNumberOfRows(); i++) {
				row = sheet.getRow(i);
				String rowNo = Integer.toString(i + 1);
				Map<String, String> line = new LinkedHashMap<>();
//				line.put("row", rowNo);
//				line.put("flag", "true");
				if (row != null) {
					int cols = getColumnNum(row, totalCols, title);
					boolean isBreak = false;//是否结束读取
					boolean  isFilter = false;//是否过滤
					for (int j = 0; j <= cols - 1; j++) {
						Object value;
						Cell cell = row.getCell(j);
						if (cell != null && !"".equals(cell.toString().trim())) {
							value = parseCell(cell);
							if (i == ignoreRows) {
								if(j==0&&!"号码".equals(String.valueOf(value).trim())) {
									//号码列名错误
									throw new NzbCommonException("样本文件号码列名错误："+value);
								}else if(j==1&&!"姓名".equals(String.valueOf(value).trim())) {
									//号码列名错误
									throw new NzbCommonException("样本文件姓名列名错误："+value);
								}
								
								// 记录标题
								title.add(String.valueOf(value).trim());
							} else if (j == 0) {
								// 数据行首列必须是手机号
								//如果为空结束读取
								if("".equals(value.toString())) {
									isBreak = true;
									break;
								}else if(!isMobileNumber(value.toString())) {
									isFilter = true;
								}
							}else if (j == 1) {
								//姓名为空的字段过滤
								if("".equals(value.toString())) {
									isFilter = true;
								}
							}else if(value.toString().length()>50){
								//其他列数据长度最大50
								isFilter = true;
							}
							line.put(title.get(j), String.valueOf(value).trim());
						} 
					}
					if (i == ignoreRows) {
					LOG.info(mtd, String.format("title : %s", Arrays.toString(title.toArray(new String[] {}))));
					} else {
						if(isBreak) {
							break;
						}else if(isFilter) {
							ignoreList.add(line);
						}else {
							list.add(line);
						}
						//LOG.info(mtd, String.format("row %s : %s", rowNo, line));
						//ignoreBlankLine(beforeUniqueKey, String.valueOf(i-ignoreRows), list, line, title, mtd,ignoreList);
						
					}
				} else {
					log(line, mtd, String.format("row %s is empty !", rowNo));
				}
			}
			LOG.info(mtd, "End Read Excel >>>");
		in.close();
		return list;
	}
	 /**
     * 导出Excel
     * @param sheetName sheet名称
     * @param title 标题
     * @param values 内容
     * @param wb HSSFWorkbook对象
     * @return
     */
    public static HSSFWorkbook getHSSFWorkbook(String sheetName,String []title,List<Map<String, String>> list, HSSFWorkbook wb){

        // 第一步，创建一个HSSFWorkbook，对应一个Excel文件
        if(wb == null){
            wb = new HSSFWorkbook();
        }

        // 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet(sheetName);

        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
        HSSFRow row = sheet.createRow(0);

        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

        //声明列对象
        HSSFCell cell = null;

        //创建标题
        for(int i=0;i<title.length;i++){
            cell = row.createCell(i);
            cell.setCellValue(title[i]);
            cell.setCellStyle(style);
        }

        //创建内容
        for(int i=0;i<list.size();i++){
            row = sheet.createRow(i + 1);
            for(int j=0;j<title.length;j++){
                //将内容按顺序赋给对应的列对象
                row.createCell(j).setCellValue(list.get(i).get(title[j]));
            }
        }
        return wb;
    }
	/**
	 * 判断要读取多少列
	 * @param row
	 * @param totalCols 允许读取的列数
	 * @param title 标题行
	 * @return
	 */
	private static int getColumnNum(Row row, int totalCols, List<String> title) {
		int cols = row.getLastCellNum();
		if (cols > totalCols) {
			cols = totalCols;
		} else if (!title.isEmpty() && title.size() > cols) {
			cols = title.size();
		}
		return cols;
	}
	
	/**
	 * 读取单元格
	 * @param line 记录行
	 * @param title 标题行
	 * @param row
	 * @param i
	 * @param j
	 * @param ignoreRows 忽略行数
	 * @param mtd 方法标识
	 * @throws IOException
	 */
	private static void parseOneCell(
			Map<String, String> line, List<String> title,
			Row row, int i, int j, int ignoreRows, String mtd){
		Object value;
		Cell cell = row.getCell(j);
		
		if (cell != null && !"".equals(cell.toString().trim())) {
			line.put("isNotBlank", "true");
			value = parseCell(cell);
			if (i == ignoreRows) {
				// 记录标题
				title.add(String.valueOf(value).trim());
			} else if (j == 0 && !isMobileNumber(value.toString())) {
				// 数据行首列必须是手机号
				line.put("isNotBlank", "false");
			}else if(value.toString().length()>50){
				//其他列数据长度最大50
				line.put("isNotBlank", "false");
			}
			line.put(title.get(j), String.valueOf(value).trim());
		} 
		/*else {
			// 单元格为null或空
			value = checkEmpty(title, i, ignoreRows);
		}*/
		/*if (i != ignoreRows && title.get(j) != null && !"".equals(title.get(j))) {
			// 记录行数据(非标题行、列名不为空)
			if (!"*".equals(value)) {
				line.put(title.get(j), String.valueOf(value).trim());
			}
		}*/
	}
	
	/**
	 * 解析单元格内容
	 * @param cell
	 * @return
	 */
	private static Object parseCell(Cell cell) {
		Object value;
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_STRING:
			value = cell.getStringCellValue();
			break;
		case Cell.CELL_TYPE_NUMERIC:
			DecimalFormat df = new DecimalFormat("0");// 格式化数字
			SimpleDateFormat sdf = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");// 格式化日期字符串
			String cellType = cell.getCellStyle().getDataFormatString();
			value = ("@".equals(cellType) || "General".equals(cellType))
					? df.format(cell.getNumericCellValue()) 
					: DateUtil.date2String(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()));
			break;
		case Cell.CELL_TYPE_BOOLEAN:
			value = cell.getBooleanCellValue();
			break;
		default:
			value = cell.toString();
		}
		return value;
	}
	
	/**
	 * 记录错误
	 * @param line 记录行
	 * @param mtd 方法标识
	 * @param msg 错误消息
	 */
	private static void log(
			Map<String, String> line, String mtd, String msg) {
		line.put("flag", "false");
		line.put("error", msg);
		//LOG.info(mtd, msg);
	}
	
	/**
	 * 记录错误并抛异常
	 * @param line 记录行
	 * @param mtd 方法标识
	 * @param msg 错误消息
	 * @throws IOException
	 */
	private static void logAndThrow(
			Map<String, String> line, String mtd, String msg) 
			throws IOException {
		line.put("flag", "false");
		line.put("error", msg);
		LOG.info(mtd, msg);
		throw new IOException(msg);
	}
	
	/**
	 * 检查单元格为空的情况
	 * @param i 
	 * @param ignoreRows 忽略行数
	 * @throws IOException
	 */
	private static Object checkEmpty(List<String> title, 
			int i, int ignoreRows) throws IOException {
		if (i == ignoreRows) {
			// 标题行为空，插入空标题，返回空字符串
			title.add("");
			return "";
		} else {
			// 其它赋值为"*"
			return "*";
		}
	}
	
	/**
	 * 忽略空白行 校验数据格式是否合法 添加非法记录数据
	 * @param list
	 * @param line
	 * @param title
	 * @param mtd
	 * @throws IOException 
	 */
	private static void ignoreBlankLine(String beforeUniqueKey, String field,
			List<Map<String, String>> list, Map<String, String> line, 
			List<String> title, String mtd,List<Map<String, String>> ignore) throws IOException {
		try {
			if(line.get("isNotBlank") != null) {
				if("*".equals(line.get(title.get(0)))) {
					// 如果不是空白行，检查第一列手机号是否为空
					line.remove("isNotBlank");
					ignore.add(line);
				} else if("false".equals(line.get("isNotBlank"))){
					line.remove("isNotBlank");
					ignore.add(line);
				}else {
					line.remove("isNotBlank");
					list.add(line);
				}
			}
		} catch (Exception e) {
			LOG.error("ignoreBlankLine", e.getMessage(), e);
		}
	}
	
	/**
	 * 判断字符串是否是合法的中国移动号码
	 * @param mobiles
	 * @return
	 */
	public static boolean isMobileNumber(String mobiles) {
//		return Pattern.compile("^(13[4-9]|147|15[7-9]|15[0-2]|178|18[2-4]|18[7-8])[0-9]{8}$")
//				.matcher(mobiles).matches();
		try {
			Long.parseLong(mobiles);
		} catch (Exception e) {
			LOG.error(mobiles+"样本入库号码校验失败！");
			return false;
		}
		if(mobiles.length()<11||mobiles.length()>12) {
			LOG.error(mobiles+"样本入库号码校验失败！");
			return false;
		}
		return true;
	}

	/**
	 * 2007
	 * @param in 输入流
	 * @param rowStar 起始行
	 * @param cellLength 结束列
	 * @return
	 * @throws IOException
	 */
	public static List<Map<String, String>> readXlsx(InputStream in,
			int rowStar, int cellLength,List<String> head) throws IOException {
		XSSFWorkbook hssfWorkbook = new XSSFWorkbook(in);
		List<Map<String, String>> list = new ArrayList<>();
		// 循环工作表Sheet
		for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
			Sheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);

			if (hssfSheet == null) {
				continue;
			}

			// 循环行Row
			for (int rowNum = rowStar; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
				Row hssfRow = hssfSheet.getRow(rowNum);
				if (hssfRow == null) {
					continue;
				}

				//循环单元格cell
				Map<String, String> rowMap = new HashMap<>();
				for (int cellNum = 0; cellNum < cellLength; cellNum++) {
					Cell cell = hssfRow.getCell(cellNum);
					String value=cell.getStringCellValue();
					rowMap.put(head.get(cellNum), value != null ? value.trim() : value);
				}
				list.add(rowMap);
			}
		}
		return list;
	}
	
	/**
	 * 2003
	 * @param in 输入流
	 * @param rowStar 起始行
	 * @param cellLength 结束列
	 * @return
	 * @throws Exception
	 */
	public static List<Map<String, String>> readXls(InputStream in,
			int rowStar, int cellLength,List<String> head) throws IOException {
		HSSFWorkbook hssfWorkbook = new HSSFWorkbook(in);
		List<Map<String, String>> list = new ArrayList<>();
		// 循环工作表Sheet
		for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
			HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);

			if (hssfSheet == null) {
				continue;
			}

			// 循环行Row
			for (int rowNum = rowStar; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
				HSSFRow hssfRow = hssfSheet.getRow(rowNum);
				if (hssfRow == null) {
					continue;
				}

				//循环单元格cell
				Map<String, String> rowMap = new HashMap<>();
				for (int cellNum = 0; cellNum < cellLength; cellNum++) {
					HSSFCell cell = hssfRow.getCell(cellNum);
					String value=cell.getStringCellValue();
					rowMap.put(head.get(cellNum),value != null ? value.trim() : value);
				}
				list.add(rowMap);
			}
		}
		return list;
	}
	
	/**
	 * 读取Excel文件
	 * 
	 * @param fileName
	 *            文件名，带后缀扩展名
	 * @param in
	 *            输入流
	 * @param ignoreRows
	 *            忽略的行数
	 * @param totalCols
	 *            列总数
	 * @return 每一行保存为Map结构， Map中当Key为"flag"的值为"false"表示该行有错误，错误信息存在"error"中；
	 *         "flag"的值为"true"表示该行数据有效，Key为标题名，Value为值
	 * @throws IOException
	 * @throws OcpException 
	 */
	public static Map<String, String> readStarInfo(String beforeUniqueKey, String fileName,
			InputStream in, int ignoreRows, int totalCols) throws IOException {
		String ext = fileName.lastIndexOf(".") == -1 ? "" : fileName
				.substring(fileName.lastIndexOf(".") + 1);
		try {
			if ("xls".equals(ext)) {
				return read2003Star(beforeUniqueKey, in, ignoreRows, totalCols);
			} else if ("xlsx".equals(ext)) {
				return read2007Star(beforeUniqueKey, in, ignoreRows, totalCols);
			} else {
				Map<String, String> resultMap = new HashMap<String, String>();
				resultMap.put("code", "104");
				resultMap.put("message", "系统仅支持xls与xlsx的文件类型！");
				return resultMap;
			}
		} catch (UnsupportedFileFormatException e) {
			LOG.error(e.getMessage(), e);
			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("code", "105");
			resultMap.put("message", "文件存储格式或被强行更改，请转换后重新导入！");
			return resultMap;
		}
	}

	/**
	 * 读取03格式Excel文件
	 * 
	 * @param in
	 *            输入流
	 * @param ignoreRows
	 *            忽略的行数
	 * @param totalCols
	 *            列总数
	 * @return 每一行保存为Map结构， Map中当Key为"flag"的值为"false"表示该行有错误，错误信息存在"error"中；
	 *         "flag"的值为"true"表示该行数据有效，Key为标题名，Value为值
	 * @throws IOException
	 * @throws OcpException 
	 */
	public static Map<String, String> read2003Star(String beforeUniqueKey, InputStream in,
			int ignoreRows, int totalCols) throws IOException{
		return readStar(beforeUniqueKey, in, ignoreRows, totalCols, true);
	}

	/**
	 * 读取07格式Excel
	 * 
	 * @param in
	 *            输入流
	 * @param ignoreRows
	 *            忽略的行数
	 * @param totalCols
	 *            列总数
	 * @return 每一行保存为Map结构， Map中当Key为"flag"的值为"false"表示该行有错误，错误信息存在"error"中；
	 *         "flag"的值为"true"表示该行数据有效，Key为标题名，Value为值
	 * @throws IOException
	 * @throws OcpException 
	 */
	public static Map<String, String> read2007Star(String beforeUniqueKey, InputStream in,
			int ignoreRows, int totalCols) throws IOException {
		return readStar(beforeUniqueKey, in, ignoreRows, totalCols, false);
	}
	
	/**
	 * 读取Excel
	 * @param in	输入流
	 * @param ignoreRows	忽略的行数
	 * @param totalCols		列总数
	 * @param is2003 是否是03格式
	 * @return 每一行保存为Map结构， Map中当Key为"flag"的值为"false"表示该行有错误，错误信息存在"error"中；
	 *         "flag"的值为"true"表示该行数据有效，Key为标题名，Value为值
	 * @throws IOException
	 * @throws OcpException 
	 */
	private static Map<String, String> readStar(String beforeUniqueKey, InputStream in,
			int ignoreRows, int totalCols, boolean is2003) throws IOException {
		List<String> title = new ArrayList<>();
		
		Workbook wb;
		Sheet sheet;
		Row row;
		String mtd;
		if(is2003) {
			wb = new HSSFWorkbook(in);
			sheet = wb.getSheetAt(0);
			mtd = "read2003Excel";
		} else {
			wb = new XSSFWorkbook(in);
			sheet = wb.getSheetAt(0);
			mtd = "read2007Excel";
		}

		LOG.info(mtd, "Begin Read Star Information <<<");
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("code", "0");
		resultMap.put("message", "success！");
			// 忽略指定行数再开始读取
			for (int i = ignoreRows; i <= sheet.getPhysicalNumberOfRows(); i++) {
				row = sheet.getRow(i);
				String rowNo = Integer.toString(i + 1);
				Map<String, String> line = new LinkedHashMap<>();
				line.put("row", rowNo);
				line.put("flag", "true");
				if (row != null) {
					for (int j = 0; j <= totalCols - 1; j++) {
						judgeStarCell(line, title, row, i, j, ignoreRows, resultMap);
						if(resultMap.containsKey("formatError")){
							return resultMap;
						}
					}
					if (i > ignoreRows && null != line.get("isNotBlank")) {
						line.remove("isNotBlank");
						/*CacheUtil.rpush(beforeUniqueKey, JsonUtil.convertObject2Json(line));
						if(!isEffective){
							CacheUtil.expire(beforeUniqueKey, RedisKey.MEMBER_IMPORT_STARINFO_REDIS_KEY.MEMER_IMPORT_STARINFO_EFFECTIVE_TIME);
							isEffective = true;
						}*/
					}
				} else {
					log(line, mtd, String.format("row %s is empty !", rowNo));
				}
			}
			LOG.info(mtd, "End Read Star Information >>>");
		in.close();
		LOG.info(mtd, "Read Star Information >>>");
		return resultMap;
	}
	
	/**
	 * 读取单元格
	 * @param line 记录行
	 * @param title 标题行
	 * @param row
	 * @param i
	 * @param j
	 * @param ignoreRows 忽略行数
	 * @throws IOException
	 */
	private static void judgeStarCell(
			Map<String, String> line, List<String> title,
			Row row, int i, int j, int ignoreRows, Map<String, String> resultMap) throws IOException {
		Object value;
		Cell cell = row.getCell(j);
		if (cell != null && !"".equals(cell.toString().trim())) {
			value = parseCell(cell);
			if (i == ignoreRows) {
				// 记录标题
				storeRecord(title, j);
			} else if(!isformatRow(String.valueOf(value).trim(), j)){
				formartError(line, i, j, resultMap);
			}else{
				storeRecord(line, j, String.valueOf(value).trim());
			}
			line.put("isNotBlank", "true");
		} else {
			// 单元格为null或""
			//formartError(line, i, j, resultMap);
		}
	}
	
	private static void storeRecord(List<String> title, int cols){
		switch (cols) {
		case 0:
			title.add("agentNum");
			break;
		case 1:
			title.add("starLevelId");
			break;
		case 2:
			title.add("registerTimeScore");
			break;
		case 3:
			title.add("avgSaleNumScore");
			break;
		case 4:
			title.add("avgDailyCommunicateTimeScore");
			break;
		case 5:
			title.add("avgDailyCallNumScore");
			break;
		case 6:
			title.add("qualityScore");
			break;
		default:
			break;
		}
	}
	
	private static void storeRecord(Map<String, String> line, int cols, String value){
		switch (cols) {
		case 0:
			line.put("agentNum", value);
			break;
		case 1:
			line.put("starLevelId", value);
			break;
		case 2:
			line.put("registerTimeScore", value);
			break;
		case 3:
			line.put("avgSaleNumScore", value);
			break;
		case 4:
			line.put("avgDailyCommunicateTimeScore", value);
			break;
		case 5:
			line.put("avgDailyCallNumScore", value);
			break;
		case 6:
			line.put("qualityScore", value);
			break;
		default:
			break;
		}
	}
	
	private static boolean isformatRow(String num, int cols) {
		boolean flag = true;
		switch (cols) {
		case 0:
			if(!num.startsWith("ZB")){
				flag = false;
			}
			break;
		case 1:
			if(!isScopeNumber(num, 1, 5)){
				flag = false;
			}
			break;
		case 2:
			if(!isScopeNumber(num, 0, 20)){
				flag = false;
			}
			break;
		case 3:
			if(!isScopeNumber(num, 0, 20)){
				flag = false;
			}
			break;
		case 4:
			if(!isScopeNumber(num, 0, 20)){
				flag = false;
			}
			break;
		case 5:
			if(!isScopeNumber(num, 0, 10)){
				flag = false;
			}
			break;
		case 6:
			if(!isScopeNumber(num, 0, 30)){
				flag = false;
			}
			break;
		default:
			break;
		}
		return flag;	
	}
	
	private static boolean isScopeNumber(String num, int minValue, int maxValue) {
		boolean flag = false;
		try{
			if (num.trim().length()<3 && Integer.parseInt(num)>=minValue&&Integer.parseInt(num)<=maxValue) {
				flag = true;
			}
		}catch(Exception e){
			LOG.error(e.getMessage(), e);
		}
		return flag;
	}
	
	/**
	 * 记录错误并抛异常
	 * @param line 记录行
	 * @param mtd 方法标识
	 * @param msg 错误消息
	 * @throws IOException
	 */
	private static void formartError(Map<String, String> line, int i, int j, Map<String, String> resultMap) 
			throws IOException {
		String mString = String.format("文件中第%d行第%d列格式不正确 !", i + 1, j+1);
		line.put("flag", "false");
		line.put("error", mString);
		resultMap.put("code", "103");
		resultMap.put("message", mString);
		resultMap.put("formatError", "true");
		LOG.error(mString);
	}
	
	
	/**
	 * 读取Excel文件
	 * 
	 * @param fileName
	 *            文件名，带后缀扩展名
	 * @param in
	 *            输入流
	 * @param ignoreRows
	 *            忽略的行数
	 * @param totalCols
	 *            列总数
	 * @return 每一行保存为Map结构， Map中当Key为"flag"的值为"false"表示该行有错误，错误信息存在"error"中；
	 *         "flag"的值为"true"表示该行数据有效，Key为标题名，Value为值
	 * @throws IOException
	 * @throws OcpException 
	 */
	public static Map<String, String> readTaskInfo(String beforeUniqueKey, String fileName,
			InputStream in, int ignoreRows, int totalCols) throws IOException{
		String ext = fileName.lastIndexOf(".") == -1 ? "" : fileName
				.substring(fileName.lastIndexOf(".") + 1);
		try {
			if ("xls".equals(ext)) {
				return read2003Task(beforeUniqueKey, in, ignoreRows, totalCols);
			} else if ("xlsx".equals(ext)) {
				return read2007Task(beforeUniqueKey, in, ignoreRows, totalCols);
			} else {
				Map<String, String> resultMap = new HashMap<String, String>();
				resultMap.put("code", "104");
				resultMap.put("message", "系统仅支持xls与xlsx的文件类型！");
				return resultMap;
			}
		} catch (UnsupportedFileFormatException e) {
			LOG.error(e.getMessage(), e);
			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("code", "105");
			resultMap.put("message", "文件存储格式或被强行更改，请转换后重新导入！");
			return resultMap;
		}
	}
	
	
	/**
	 * 读取03格式Excel文件
	 * 
	 * @param in
	 *            输入流
	 * @param ignoreRows
	 *            忽略的行数
	 * @param totalCols
	 *            列总数
	 * @return 每一行保存为Map结构， Map中当Key为"flag"的值为"false"表示该行有错误，错误信息存在"error"中；
	 *         "flag"的值为"true"表示该行数据有效，Key为标题名，Value为值
	 * @throws IOException
	 * @throws OcpException 
	 */
	public static Map<String, String> read2003Task(String beforeUniqueKey, InputStream in,
			int ignoreRows, int totalCols) throws IOException{
		return readTask(beforeUniqueKey, in, ignoreRows, totalCols, true);
	}

	/**
	 * 读取07格式Excel
	 * 
	 * @param in
	 *            输入流
	 * @param ignoreRows
	 *            忽略的行数
	 * @param totalCols
	 *            列总数
	 * @return 每一行保存为Map结构， Map中当Key为"flag"的值为"false"表示该行有错误，错误信息存在"error"中；
	 *         "flag"的值为"true"表示该行数据有效，Key为标题名，Value为值
	 * @throws IOException
	 * @throws OcpException 
	 */
	public static Map<String, String> read2007Task(String beforeUniqueKey, InputStream in,
			int ignoreRows, int totalCols) throws IOException {
		return readTask(beforeUniqueKey, in, ignoreRows, totalCols, false);
	}
	
	
	/**
	 * 读取会员批量导入Excel
	 * @param in	输入流
	 * @param ignoreRows	忽略的行数
	 * @param totalCols		列总数
	 * @param is2003 是否是03格式
	 * @return 每一行保存为Map结构， Map中当Key为"flag"的值为"false"表示该行有错误，错误信息存在"error"中；
	 *         "flag"的值为"true"表示该行数据有效，Key为标题名，Value为值
	 * @throws IOException
	 * @throws OcpException 
	 */
	private static Map<String, String> readTask(String beforeUniqueKey, InputStream in,
			int ignoreRows, int totalCols, boolean is2003) throws IOException {
		List<String> title = new ArrayList<>();
		
		Workbook wb;
		Sheet sheet;
		Row row;
		String mtd;
		if(is2003) {
			wb = new HSSFWorkbook(in);
			sheet = wb.getSheetAt(0);
			mtd = "read2003Excel";
		} else {
			wb = new XSSFWorkbook(in);
			sheet = wb.getSheetAt(0);
			mtd = "read2007Excel";
		}

		LOG.info(mtd, "Begin Read Task Information <<<");
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("code", "0");
		resultMap.put("message", "success！");
			// 忽略指定行数再开始读取
			for (int i = ignoreRows; i <= sheet.getPhysicalNumberOfRows(); i++) {
				row = sheet.getRow(i);
				String rowNo = Integer.toString(i + 1);
				Map<String, String> line = new LinkedHashMap<>();
				line.put("row", rowNo);
				line.put("flag", "true");
				if (row != null) {
					for (int j = 0; j <= totalCols - 1; j++) {
						judgeTaskCell(line, title, row, i, j, ignoreRows, resultMap);
						if(resultMap.containsKey("formatError")){
							return resultMap;
						}
					}
					if (i > ignoreRows && null != line.get("isNotBlank")) {
						line.remove("isNotBlank");
						/*CacheUtil.rpush(beforeUniqueKey, JsonUtil.convertObject2Json(line));
						if(!isEffective){
							CacheUtil.expire(beforeUniqueKey, RedisKey.MEMBER_IMPORT_STARINFO_REDIS_KEY.MEMER_IMPORT_STARINFO_EFFECTIVE_TIME);
							isEffective = true;
						}*/
					}
				} else {
					log(line, mtd, String.format("row %s is empty !", rowNo));
				}
			}
			LOG.info(mtd, "End Read Star Information >>>");
		in.close();
		LOG.info(mtd, "Read Star Information >>>");
		return resultMap;
	}
	
	/**
	 * 读取单元格
	 * @param line 记录行
	 * @param title 标题行
	 * @param row
	 * @param i
	 * @param j
	 * @param ignoreRows 忽略行数
	 * @throws IOException
	 */
	private static void judgeTaskCell(
			Map<String, String> line, List<String> title,
			Row row, int i, int j, int ignoreRows, Map<String, String> resultMap) throws IOException {
		Object value;
		Cell cell = row.getCell(j);
		if (cell != null && !"".equals(cell.toString().trim())) {
			value = parseCell(cell);
			if (i == ignoreRows) {
				// 记录标题
				TaskRecord(title, j);
			} else if(!isTaskFormatRow(String.valueOf(value).trim(), j)){
				formartTaskError(line, i, j, resultMap);
			}else{
				TaskRecord(line, j, String.valueOf(value).trim());
			}
			line.put("isNotBlank", "true");
		} else {
			// 单元格为null或""
			formartTaskError(line, i, j, resultMap);
		}
	}
	
	private static void TaskRecord(List<String> title, int cols){
		switch (cols) {
		case 0:
			title.add("agentNum");
			break;
		case 1:
			title.add("code");
			break;
		default:
			break;
		}
	}
	
	private static void TaskRecord(Map<String, String> line, int cols, String value){
		switch (cols) {
		case 0:
			line.put("agentNum", value);
			break;
		case 1:
			line.put("code", value);
			break;
		default:
			break;
		}
	}
	
	private static boolean isTaskFormatRow(String num, int cols) {
		boolean flag = true;
		switch (cols) {
		case 0:
			if(!num.startsWith("ZB")){
				flag = false;
			}
			break;
		case 1:
			if(StringUtil.isEmpty(num)||num.length()>40){
				flag = false;
			}
			break;
		default:
			break;
		}
		return flag;	
	}
	/**
	 * 记录错误并抛异常
	 * @param line 记录行
	 * @param mtd 方法标识
	 * @param msg 错误消息
	 * @throws IOException
	 */
	private static void formartTaskError(Map<String, String> line, int i, int j, Map<String, String> resultMap) 
			throws IOException {
		String mString = String.format("文件中第%s行第%s列格式不正确 !", i + 1, j+1);
		line.put("flag", "false");
		line.put("error", mString);
		resultMap.put("code", "103");
		resultMap.put("message", mString);
		resultMap.put("formatError", "true");
		LOG.error(mString);
	}

	/**
	 * 读取离职会员到redis中。
	 * @param redisKey
	 * @param fileName  文件名，带后缀扩展名
	 * @param in 输入流
	 * @param ignoreRows 忽略的行数
	 * @param totalCols 列数
	 * @return
	 * @throws IOException 
	 * @throws OcpException 
	 */
	public static Map<String, String> readLockMember4Import(String redisKey, String fileName, InputStream in, int ignoreRows,
			int totalCols) throws IOException { 
		String ext = fileName.lastIndexOf(".") == -1 ? "" : fileName
				.substring(fileName.lastIndexOf(".") + 1);
		try {
			if ("xls".equals(ext) || "".equals(ext)) {
				Workbook wb;
				Sheet sheet;
				Row row;
				if("xls".equals(ext)) {
					wb = new HSSFWorkbook(in);
				}else {
					wb = new XSSFWorkbook(in);
				}
				sheet = wb.getSheetAt(0);
				LOG.info("readLockMember4Import Read Task Information <<<");
				Map<String, String> resultMap = new HashMap<String, String>();
				resultMap.put("code", "0");
				resultMap.put("message", "success！");
				
					// 忽略指定行数再开始读取
					for (int i = ignoreRows; i <= sheet.getPhysicalNumberOfRows(); i++) {
						row = sheet.getRow(i);
						String rowNo = Integer.toString(i + 1);
						Map<String, String> line = new LinkedHashMap<>();
						line.put("row", rowNo);
						line.put("flag", "true");
						if (row != null) {
							for (int j = 0; j <= totalCols - 1; j++) {
								readCellForLockMember(row, resultMap, i, line, j);
								if(resultMap.containsKey("formatError")){
									return resultMap;
								}
							}
							if (i > ignoreRows-1 && null != line.get("isNotBlank")) {
								line.remove("isNotBlank");
								/*CacheUtil.rpush(redisKey, JsonUtil.convertObject2Json(line));
								if(!isEffective){
									CacheUtil.expire(redisKey, RedisKey.MEMBER_IMPORT_STARINFO_REDIS_KEY.MEMER_IMPORT_STARINFO_EFFECTIVE_TIME);
									isEffective = true;
								}*/
							}
						} else {
							LOG.info("readLockMember4Import "+String.format("row %s is empty !", rowNo) ); 
						}
					}
				wb.close();
				in.close();
				LOG.info("readLockMember4Import End Read Star Information >>>");
				return resultMap;
			} else {
				Map<String, String> resultMap = new HashMap<String, String>();
				resultMap.put("code", "104");
				resultMap.put("message", "系统仅支持xls与xlsx的文件类型！");
				return resultMap;
			}
		} catch (UnsupportedFileFormatException e) {
			LOG.error(e.getMessage(), e);
			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("code", "105");
			resultMap.put("message", "文件存储格式或被强行更改，请转换后重新导入！");
			return resultMap;
		}
	}


	/**
	 * @param row excel行对象
	 * @param resultMap 返回结果 
	 * @param i 行号
	 * @param line 标题
	 * @param j 列好
	 * @throws IOException
	 */
	private static void readCellForLockMember(Row row, Map<String, String> resultMap, int i, Map<String, String> line,
			int j) throws IOException {
		String valueStr;
		Cell cell = row.getCell(j);
		if (cell != null && !"".equals(cell.toString().trim())) {
			valueStr = String.valueOf(parseCell(cell)).trim();
			if(StringUtils.isNotEmpty(valueStr)) {
				if(j==0) {//第一列为工号
					if(!valueStr.startsWith("ZB")){
						formartTaskError(line, i, j, resultMap);
					}else {
						line.put("agentNum", valueStr);
					}
				}
				if(j==1) {//第二列为手机号
					line.put("userMobile", valueStr);
				}
				if(j==2) {//第三列为所属公司
					line.put("userCompany", valueStr);
				}
				line.put("isNotBlank", "true");
			}else {
				formartTaskError(line, i, j, resultMap);
			}
		}else {
			formartTaskError(line, i, j, resultMap);
		}
	}

}
