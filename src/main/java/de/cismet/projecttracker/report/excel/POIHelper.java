package de.cismet.projecttracker.report.excel;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;


/**
 * This class contains some static methods, which makes it easier to use the POI library.
 * @author therter
 */
public class POIHelper {
    private static Logger logger = Logger.getLogger(POIHelper.class);
    /**
     * this array contains all letters, which are required to generate a column name
     */
    private static char[] colDigit = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
                                        'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    /**
     * determines the name of an cell. <br />Example: the cell with the row 0 und column 0 has the name A1.
     * @param row the number of the row. The first row has the number 0.
     * @param col the number of the column. The first row has the number 0.
     * @return the name of the cell with the given row number and the given column number
     */
    public static String getCellName(int row, int col) {
        int firstDigit = col / colDigit.length;
        int secondDigit = col % colDigit.length;
        String colName = "";

        if (firstDigit > 0) {
            colName += colDigit[firstDigit - 1];
        }
        colName += colDigit[secondDigit];

        return colName + (row + 1);
    }


    public static HSSFCell getCell(ReportSheetWrapper sheet, int rowIndex, int colIndex) {
        HSSFRow row = sheet.getRow(rowIndex);
        if (logger.isDebugEnabled()) {
            logger.debug("find cell (" + rowIndex + ", " + colIndex + ")");
        }

        if (row == null) {
            logger.warn("row == null. cannot find the row with the index " + rowIndex, new Throwable());
        } else {
            HSSFCell cell = row.getCell(colIndex);

            if (cell == null) {
                logger.debug("cell == null. rowIndex: " + rowIndex + " colIndex: " + colIndex);
                cell = row.createCell(colIndex);
            }

            return cell;
        }

        return null;
    }


    /**
     * writes a double value into the given cell
     *
     * @param sheet the sheet of the cell
     * @param rowIndex the row of the cell
     * @param colIndex the col of the cell
     * @param value the new value of the cell
     */
    public static void writeCell(ReportSheetWrapper sheet, int rowIndex, int colIndex, double value) {
        HSSFCell cell = getCell(sheet, rowIndex, colIndex);

        if (cell != null) {
            cell.setCellValue( value );
        }
    }


    /**
     * writes a string value or a formula into the given cell
     *
     * @param sheet the sheet of the cell
     * @param rowIndex the row of the cell
     * @param colIndex the col of the cell
     * @param value the new value of the cell
     * @param isFormula true, if and only if the given cell should be of the type formula
     */
    public static void writeCell(ReportSheetWrapper sheet, int rowIndex, int colIndex, String value, boolean isFormula) {
        HSSFCell cell = getCell(sheet, rowIndex, colIndex);

        if (cell != null) {
            if (isFormula) {
                cell.setCellType(HSSFCell.CELL_TYPE_FORMULA);
                cell.setCellFormula(value);
            } else {
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                cell.setCellValue( new HSSFRichTextString(value) );
            }
        }
    }


    /**
     * set the style of the given cell on the given value
     *
     * @param sheet the sheet of the cell
     * @param rowIndex the row of the cell
     * @param colIndex the col of the cell
     * @param style the new style of the cell
     */
    public static void setCellStyle(ReportSheetWrapper sheet, int rowIndex, int colIndex, CellStyle style) {
        HSSFCell cell = getCell(sheet, rowIndex, colIndex);

        if (cell != null) {
            cell.setCellStyle(style);
        }
    }

    
    /**
     * copies the cell format from the cells of one row to the corrsponding cells of an other row.
     * 
     * @param startCol the column of the first cell, whose format should be copied
     * @param endCol the column of the last cell, whose format should be copied
     * @param sourceRow the row, the format should be copied from
     * @param destRow the row, the format should be copied to
     */
    public static void copyRowFormat(int startCol, int endCol, HSSFRow sourceRow, HSSFRow destRow) {
        for (int i = startCol; i <= endCol; ++i) {
            HSSFCell cell = destRow.getCell(i);
            if (cell == null) {
                cell = destRow.createCell(i);
            }
            if (sourceRow.getCell(i) != null && sourceRow.getCell(i).getCellStyle() != null) {
                cell.setCellStyle( sourceRow.getCell(i).getCellStyle() );
            } else {
                logger.warn("source cell or source cell style is null. Format cannot be copied.");

                if (sourceRow.getCell(i)==null) {
                    logger.debug("cell(" + i + ") is null in row " + sourceRow.getRowNum() );
                } else if (sourceRow.getCell(i).getCellStyle()==null) {
                    logger.debug("cell(" + i + ").getCellStyle() is null in row " + sourceRow.getRowNum());
                }
            }
        }
    }

    /**
     * copies the cell format from the cells of one row to the corrsponding cells of an other row.
     *
     * @param startCol the column of the first cell, whose format should be copied
     * @param endCol the column of the last cell, whose format should be copied
     * @param sourceRow the row, the format should be copied from
     * @param destRow the row, the format should be copied to
     */
    public static void copyColFormat(ReportSheetWrapper sheet, int startRow, int endRow, int sourceCol, int destCol) {
        for (int i = startRow; i <= endRow; ++i) {
            HSSFRow destRow = sheet.getRow(i);
            HSSFRow sourceRow = sheet.getRow(i);
            HSSFCell cell = destRow.getCell(destCol);
            if (cell == null) {
                cell = destRow.createCell(destCol);
            }
            if (sourceRow.getCell(sourceCol) != null && sourceRow.getCell(sourceCol).getCellStyle() != null) {
                cell.setCellStyle( sourceRow.getCell(sourceCol).getCellStyle() );
            } else {
                logger.warn("source cell or source cell style is null. Format cannot be copied.");

                if (sourceRow.getCell(sourceCol)==null) {
                    logger.debug("cell(" + sourceCol + ") is null in row " + sourceRow.getRowNum() );
                } else if (sourceRow.getCell(sourceCol).getCellStyle()==null) {
                    logger.debug("cell(" + sourceCol + ").getCellStyle() is null in row " + sourceRow.getRowNum());
                }
            }
        }
    }

    /**
     * merges all cells in the given row, which have the same content.
     * @param sheet
     * @param roẃ
     * @param firstCol
     */
    public static void checkRowForMerge(ReportSheetWrapper sheet, int roẃ, int firstCol) {
        HSSFRow currentRow = sheet.getRow(roẃ);
        HSSFCell cell = null;
        int col = firstCol;
        int startCol = col;
        boolean newContent = false;
        String lastContent = null;

        do {
            cell = currentRow.getCell(col);
            if ( cell != null ) {
                String tmp = null;

                if ( cell.getCellType() == HSSFCell.CELL_TYPE_STRING ) {
                    tmp = cell.getStringCellValue();
                } else if ( cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC ) {
                    tmp = "" + cell.getNumericCellValue();
                }

                if ( tmp == null || !tmp.equals(lastContent) ) {
                    lastContent = tmp;
                    newContent = true;
                }
            }

            if (cell == null || newContent) {
                if (col - startCol > 1) {
                    sheet.addMergedRegion(new CellRangeAddress(currentRow.getRowNum(), currentRow.getRowNum(), startCol, col - 1));
                }
                startCol = col;
                newContent = false;
            }

            ++col;
        } while (cell != null && col < 256);
    }

    
    public static void addComment(HSSFCell cell, String text, String author) {
//        HSSFPatriarch patriarch = cell.getSheet().getDrawingPatriarch();
//        System.out.println("patriarch: == null" + (patriarch == null) );
//        if (patriarch == null) {
//            patriarch = cell.getSheet().createDrawingPatriarch();
//        }
//        HSSFComment comment = patriarch.createComment( new HSSFClientAnchor(1,1,100,100, (short)cell.getColumnIndex(), cell.getRowIndex(), (short)(cell.getColumnIndex() + 5), cell.getRowIndex() + 5) );
//        comment.setShapeType( HSSFSimpleShape.OBJECT_TYPE_COMMENT );
//        comment.setAuthor(author);
//        comment.setString( new HSSFRichTextString(text) );
//        comment.setVisible( false );
//        cell.setCellComment(comment);
    }

    /**
     * evaluates all formulas of the gicen sheet
     * @param wb the workbook, the sheet is contained in
     * @param sheet the sheet, all formulas should be evaluated
     */
    public static void evaluateFormulas(ReportWorkbook wb, ReportSheetWrapper sheet) {
        FormulaEvaluator evaluator = wb.getCreationHelper().createFormulaEvaluator();
        for(Row r : sheet) {
            for(Cell c : r) {
                if(c.getCellType() == Cell.CELL_TYPE_FORMULA) {
                    evaluator.evaluateFormulaCell(c);
                }
            }
        }
    }
}
