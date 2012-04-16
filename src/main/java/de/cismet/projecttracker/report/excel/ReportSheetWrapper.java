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

/**
 *
 * @author therter
 */
public class ReportSheetWrapper implements Iterable<Row> {
    private static Logger logger = Logger.getLogger(ReportSheetWrapper.class);
    private HSSFSheet sheet;
    private HashMap<Integer, Integer> addedRows = new HashMap<Integer, Integer>();
    private int lastRowNumber = 0;


    /**
     *
     * @param sheet
     * @param useAsTemplate true, if a copy of the given sheet should be used
     */
    public ReportSheetWrapper(HSSFSheet sheet, boolean useAsTemplate) {
        if (useAsTemplate) {
            this.sheet = sheet.getWorkbook().cloneSheet( sheet.getWorkbook().getSheetIndex(sheet) );
        } else {
            this.sheet = sheet;
        }

        for (int i = 0; i <= sheet.getLastRowNum(); ++i) {
            addedRows.put(i, i);
        }

        lastRowNumber = sheet.getLastRowNum();
    }


    public int convertToInternalRowNumber(int row) {
        Integer result = addedRows.get(row);
        
        if (result == null) {
            result = row;
            logger.warn("no internal row presentation found for row " + row, new Throwable());
        }

        return result;
    }


    public int insertRow(int rownum) {
        Integer internalRowNum = convertToInternalRowNumber(rownum);
        int newRowNum = ++lastRowNumber;
        sheet.shiftRows( internalRowNum, sheet.getLastRowNum(), 1, true, false, true);
        sheet.createRow( internalRowNum );

        Iterator<Integer> keys = addedRows.keySet().iterator();

        while ( keys.hasNext() ) {
            Integer key = keys.next();
            Integer value = addedRows.get(key);

            if (value >= internalRowNum) {
                addedRows.put(key, value + 1);
            }
        }


        addedRows.put(newRowNum, internalRowNum);

        return newRowNum;
    }
    

    public void removeRow(int rowIndex) {
        int internalRowNumber = convertToInternalRowNumber(rowIndex);
        Iterator<Integer> keys = addedRows.keySet().iterator();

        Row row = getRow(rowIndex);
        if (row != null) {
            sheet.removeRow( row );
        } else {
           logger.error("row " + rowIndex + " with internal number " + internalRowNumber + "cannot be deleted, because it doesn't exist.");
        }

        while ( keys.hasNext() ) {
            Integer key = keys.next();
            Integer value = addedRows.get(key);

            if (value > internalRowNumber) {
                addedRows.put(key, value - 1);
            }
        }
        addedRows.remove(rowIndex);
    }

    
    public HSSFRow getRow(int rowIndex) {
        return sheet.getRow( convertToInternalRowNumber(rowIndex) );
    }

    public HSSFRow getAbsRow(int rowIndex) {
        return sheet.getRow( rowIndex );
    }


    public int getSheetIndex() {
        return sheet.getWorkbook().getSheetIndex(sheet);
    }

    public int addMergedRegion(CellRangeAddress range) {
        return sheet.addMergedRegion(range);
    }

    /**
     * this allows the usage of foreach loops
     * @return
     */
    @Override
    public Iterator<Row> iterator() {
        return sheet.iterator();
    }
}
