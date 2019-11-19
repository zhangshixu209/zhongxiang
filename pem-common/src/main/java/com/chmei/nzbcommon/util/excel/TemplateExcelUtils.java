package com.chmei.nzbcommon.util.excel;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

public class TemplateExcelUtils {
    public static final Logger logger = LoggerFactory.getLogger(TemplateExcelUtils.class);
    public static Workbook loadTemplate(Resource template) {
        try {
            return WorkbookFactory.create(template.getInputStream());
        } catch (Exception e) {
            logger.info("Excel", "读取Excel模板失败", e);
        }
        return null;
    }
    public static Resource copyToTemp(Resource source) {
        String tempPath = System.getProperty("java.io.tmpdir");
        String fileName = source.getFilename();
        if(fileName == null) {
            fileName = "tmp.dat";
        }
        File target = new File(tempPath,fileName);
        try {
            FileUtils.copyInputStreamToFile(source.getInputStream(), target);
            return new FileSystemResource(target);
        } catch (IOException e) {
            logger.info("资源操作", "资源复制失败", e);
        }
        return null;
    }
    public static void fillSheetFrom(Sheet sheet,int fromRowIndex,
            List<Map<String,Object>> data,List<String> columnsKey) {
        int currentRow = fromRowIndex;
        for(Map<String, Object> rowData:data) {
            Row row = sheet.createRow(currentRow++);
            int currentCell = 0;
            for(String columnKey:columnsKey) {
                Object value = rowData.get(columnKey);
                if(value != null) {
                    Cell cell = row.createCell(currentCell++);
                    cell.setCellValue(value.toString());
                }
            }
        }
    }
}
