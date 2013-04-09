/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.cismet.projecttracker.report.excel;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CreationHelper;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.util.HashMap;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class ReportWorkbook {

    //~ Static fields/initializers ---------------------------------------------

    private static Logger logger = Logger.getLogger(ReportWorkbook.class);

    //~ Instance fields --------------------------------------------------------

    private HSSFWorkbook wb;
    private HashMap<HSSFSheet, ReportSheetWrapper> sheetCache = new HashMap<HSSFSheet, ReportSheetWrapper>();

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new ReportWorkbook object.
     */
    public ReportWorkbook() {
        wb = new HSSFWorkbook();
    }

    /**
     * Creates a new ReportWorkbook object.
     *
     * @param   workbookTemplate  DOCUMENT ME!
     *
     * @throws  IOException  DOCUMENT ME!
     */
    public ReportWorkbook(final InputStream workbookTemplate) throws IOException {
        try {
            wb = new HSSFWorkbook(workbookTemplate);
        } catch (NullPointerException e) {
            logger.error("cannot find the report template", e);
            throw new IOException(e);
        }
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * creates a new sheet.
     *
     * @param   sheetName  the name of the new sheet
     *
     * @return  the new sheet
     */
    public ReportSheetWrapper createSheet(final String sheetName) {
        return new ReportSheetWrapper(wb.createSheet(sheetName), false);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   name  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public ReportSheetWrapper getSheet(final String name) {
        final HSSFSheet sheet = wb.getSheet(name);
        ReportSheetWrapper result = sheetCache.get(sheet);

        if (result == null) {
            result = new ReportSheetWrapper(sheet, false);
            sheetCache.put(sheet, result);
        }

        return result;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  index  DOCUMENT ME!
     */
    public void removeSheetAt(final int index) {
        wb.removeSheetAt(index);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   name  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public int getSheetIndex(final String name) {
        return wb.getSheetIndex(name);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   stream  DOCUMENT ME!
     *
     * @throws  IOException  DOCUMENT ME!
     */
    public void write(final OutputStream stream) throws IOException {
        wb.write(stream);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   sheetIndex  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public ReportSheetWrapper cloneSheet(final int sheetIndex) {
        final HSSFSheet sheet = wb.cloneSheet(sheetIndex);
        final ReportSheetWrapper result = new ReportSheetWrapper(sheet, false);
        sheetCache.put(sheet, result);

        return result;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public HSSFCellStyle createCellStyle() {
        return wb.createCellStyle();
    }

    /**
     * DOCUMENT ME!
     *
     * @param  sheetIx  DOCUMENT ME!
     * @param  name     DOCUMENT ME!
     */
    public void setSheetName(final int sheetIx, final String name) {
        wb.setSheetName(sheetIx, name);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public CreationHelper getCreationHelper() {
        return wb.getCreationHelper();
    }
}
