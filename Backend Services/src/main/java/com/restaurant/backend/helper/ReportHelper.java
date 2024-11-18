package com.restaurant.backend.helper;


import com.restaurant.backend.model.SaleDealReport;
import com.restaurant.backend.model.SaleItemReport;
import com.restaurant.backend.utils.Constants;
import lombok.NoArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@NoArgsConstructor
public class ReportHelper {

    public static ByteArrayInputStream dataToExcel(List<SaleItemReport> saleItemReports , List<SaleDealReport> saleDealReports, String reportType){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Workbook workbook = new XSSFWorkbook();

        try {

            Sheet sheetItem = workbook.createSheet(reportType + "_" + Constants.ITEM_REPORT_SHEET_NAME);
            Sheet sheetDeal = workbook.createSheet(reportType + "_" + Constants.DEAL_REPORT_SHEET_NAME);
            CellStyle headerStyle = headerStyle(workbook); // styling will apply to both sheet's headers

            Row rowItem = headers(sheetItem.createRow(0), Constants.HEADERS, headerStyle);
            Row rowDeal = headers(sheetDeal.createRow(0), Constants.HEADERS, headerStyle);


            // Creation of rows and setting up formula for Item
            for (int i = 0; i <saleItemReports.size() ; i++) {
                Row dataRow = contentSheet(saleItemReports.get(i), sheetItem, i);
            }
            FormulaEvaluator evaluatorItem = workbook.getCreationHelper().createFormulaEvaluator();
            evaluatorItem.evaluateFormulaCell(sumTheRevenue(sheetItem, workbook));



            // Creation of rows and setting up formula for Deal
            for (int i = 0; i <saleDealReports.size() ; i++) {
              Row dataRow = contentSheetDeal(saleDealReports.get(i), sheetDeal, i);
            }
            FormulaEvaluator evaluatorDeal = workbook.getCreationHelper().createFormulaEvaluator();
            evaluatorDeal.evaluateFormulaCell(sumTheRevenue(sheetDeal, workbook));



            for (int i = 0; i < Constants.HEADERS.length; i++) {
                sheetItem.autoSizeColumn(i);
                sheetDeal.autoSizeColumn(i);
            }


            workbook.write(outputStream);
            return new ByteArrayInputStream(outputStream.toByteArray());
        }
        catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }

    /** creation of the cell for Sum of Total Revenue and their styling. */
    public static Cell sumTheRevenue(Sheet sheet, Workbook workbook){
        int lastRowNum = sheet.getLastRowNum();
        Row sumRow = sheet.createRow(lastRowNum + 1);
        Cell sumCell = sumRow.createCell(4); // Column E

        String columnToSum = "E2:E" + lastRowNum;
        sumCell.setCellFormula("SUM(" + columnToSum + ")");

        CellStyle sumDataStyle = workbook.createCellStyle();
        sumDataStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
        sumDataStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        Font font = new XSSFFont();
        font.setBold(true);
        sumDataStyle.setFont(font);
        sumCell.setCellStyle(sumDataStyle);

        return sumCell;
    }


    /** for creation of Header's Row and Columns. */
    public static Row headers(Row row, String[] HEADERS, CellStyle headerStyle){
        for (int i = 0; i < HEADERS.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(HEADERS[i]);
            cell.setCellStyle(headerStyle);
        }
        return row;
    }


    /** for creation of Header Styles. */
    public static CellStyle headerStyle(Workbook workbook){
        // set font and their properties
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 14);
        headerFont.setColor(IndexedColors.BLACK.getIndex());

        // set font to the cell styles
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFont(headerFont);

        return headerStyle;
    }


    /** for creation of row and mapping data to them. */
    public static Row contentSheet(SaleItemReport saleReport, Sheet sheet, int index){
        Row dataRow = sheet.createRow(index + 1); // creating the row from 1st position
        dataRow.createCell(0).setCellValue(saleReport.getNumber());
        dataRow.createCell(1).setCellValue(saleReport.getId());
        dataRow.createCell(2).setCellValue(saleReport.getName());
        dataRow.createCell(3).setCellValue(saleReport.getTotalOrder());
        dataRow.createCell(4).setCellValue(saleReport.getPriceEach());
        dataRow.createCell(5).setCellValue(saleReport.getTotalRevenue());
        dataRow.createCell(6).setCellValue(saleReport.getTotalSale());
        dataRow.createCell(7).setCellValue(saleReport.getSaleDate());
        return dataRow;
    }

    public static Row contentSheetDeal(SaleDealReport saleReport, Sheet sheet, int index){
        Row dataRow = sheet.createRow(index + 1); // creating the row from 1st position
        dataRow.createCell(0).setCellValue(saleReport.getNumber());
        dataRow.createCell(1).setCellValue(saleReport.getId());
        dataRow.createCell(2).setCellValue(saleReport.getName());
        dataRow.createCell(3).setCellValue(saleReport.getTotalOrder());
        dataRow.createCell(4).setCellValue(saleReport.getPriceEach());
        dataRow.createCell(5).setCellValue(saleReport.getTotalRevenue());
        dataRow.createCell(6).setCellValue(saleReport.getTotalSale());
        dataRow.createCell(7).setCellValue(saleReport.getSaleDate());
        return dataRow;
    }
}
