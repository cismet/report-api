/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.cismet.projecttracker.report.excel;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CreationHelper;


/**
 *
 * @author therter
 */
public class ReportWorkbook {
    private static Logger logger = Logger.getLogger(ReportWorkbook.class);
    private HSSFWorkbook wb;
    private HashMap<HSSFSheet, ReportSheetWrapper> sheetCache = new HashMap<HSSFSheet, ReportSheetWrapper>();


    public ReportWorkbook() {
        wb = new HSSFWorkbook();
    }

    public ReportWorkbook(InputStream workbookTemplate) throws IOException {
        try {
            wb = new HSSFWorkbook( workbookTemplate );
        } catch (NullPointerException e) {
            logger.error("cannot find the report template", e);
            throw new IOException(e);
        }
    }

    /**
     * creates a new sheet
     * @param sheetName the name of the new sheet
     * @return the new sheet
     */
    public ReportSheetWrapper createSheet(String sheetName) {
        return new ReportSheetWrapper(wb.createSheet(sheetName), false );
    }

    public ReportSheetWrapper getSheet(String name) {
        HSSFSheet sheet = wb.getSheet(name);
        ReportSheetWrapper result = sheetCache.get(sheet);

        if (result == null) {
            result = new ReportSheetWrapper( sheet, false );
            sheetCache.put(sheet, result);
        }

        return result;
    }
    
    public void removeSheetAt(int index) {
        wb.removeSheetAt(index);
    }


    public int getSheetIndex(String name) {
        return wb.getSheetIndex(name);
    }


    public void write(OutputStream stream) throws IOException {
        wb.write(stream);
    }

    public ReportSheetWrapper cloneSheet(int sheetIndex) {
        HSSFSheet sheet = wb.cloneSheet(sheetIndex);
        ReportSheetWrapper result = new ReportSheetWrapper(sheet, false);
        sheetCache.put(sheet, result);

        return result;
    }

    public HSSFCellStyle createCellStyle() {
        return wb.createCellStyle();
    }


    /**
     * @param sheetIx
     * @param name
     */
    public void setSheetName(int sheetIx, String name) {
        wb.setSheetName(sheetIx, name);
    }

    public CreationHelper getCreationHelper() {
        return wb.getCreationHelper();
    }
}
