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

import java.util.HashMap;
import java.util.Iterator;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.HashMap;
import java.util.Iterator;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class ReportSheetWrapper implements Iterable<Row> {

    //~ Static fields/initializers ---------------------------------------------

    private static Logger logger = Logger.getLogger(ReportSheetWrapper.class);

    //~ Instance fields --------------------------------------------------------

    private HSSFSheet sheet;
    private HashMap<Integer, Integer> addedRows = new HashMap<Integer, Integer>();
    private int lastRowNumber = 0;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new ReportSheetWrapper object.
     *
     * @param  sheet          DOCUMENT ME!
     * @param  useAsTemplate  true, if a copy of the given sheet should be used
     */
    public ReportSheetWrapper(final HSSFSheet sheet, final boolean useAsTemplate) {
        if (useAsTemplate) {
            this.sheet = sheet.getWorkbook().cloneSheet(sheet.getWorkbook().getSheetIndex(sheet));
        } else {
            this.sheet = sheet;
        }

        for (int i = 0; i <= sheet.getLastRowNum(); ++i) {
            addedRows.put(i, i);
        }

        lastRowNumber = sheet.getLastRowNum();
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @param   row  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public int convertToInternalRowNumber(final int row) {
        Integer result = addedRows.get(row);

        if (result == null) {
            result = row;
            logger.warn("no internal row presentation found for row " + row, new Throwable());
        }

        return result;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   rownum  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public int insertRow(final int rownum) {
        final Integer internalRowNum = convertToInternalRowNumber(rownum);
        final int newRowNum = ++lastRowNumber;
        sheet.shiftRows(internalRowNum, sheet.getLastRowNum(), 1, true, false, true);
        sheet.createRow(internalRowNum);

        final Iterator<Integer> keys = addedRows.keySet().iterator();

        while (keys.hasNext()) {
            final Integer key = keys.next();
            final Integer value = addedRows.get(key);

            if (value >= internalRowNum) {
                addedRows.put(key, value + 1);
            }
        }


        addedRows.put(newRowNum, internalRowNum);

        return newRowNum;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  rowIndex  DOCUMENT ME!
     */
    public void removeRow(final int rowIndex) {
        final int internalRowNumber = convertToInternalRowNumber(rowIndex);
        final Iterator<Integer> keys = addedRows.keySet().iterator();

        final Row row = getRow(rowIndex);
        if (row != null) {
            sheet.removeRow(row);
        } else {
            logger.error("row " + rowIndex + " with internal number " + internalRowNumber
                        + "cannot be deleted, because it doesn't exist.");
        }

        while (keys.hasNext()) {
            final Integer key = keys.next();
            final Integer value = addedRows.get(key);

            if (value > internalRowNumber) {
                addedRows.put(key, value - 1);
            }
        }
        addedRows.remove(rowIndex);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   rowIndex  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public HSSFRow getRow(final int rowIndex) {
        return sheet.getRow(convertToInternalRowNumber(rowIndex));
    }

    /**
     * DOCUMENT ME!
     *
     * @param   rowIndex  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public HSSFRow getAbsRow(final int rowIndex) {
        return sheet.getRow(rowIndex);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public int getSheetIndex() {
        return sheet.getWorkbook().getSheetIndex(sheet);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   range  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public int addMergedRegion(final CellRangeAddress range) {
        return sheet.addMergedRegion(range);
    }

    /**
     * this allows the usage of foreach loops.
     *
     * @return  DOCUMENT ME!
     */
    @Override
    public Iterator<Row> iterator() {
        return sheet.iterator();
    }
}
