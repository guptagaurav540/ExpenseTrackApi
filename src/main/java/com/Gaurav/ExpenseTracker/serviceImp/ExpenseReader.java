package com.Gaurav.ExpenseTracker.serviceImp;

import com.Gaurav.ExpenseTracker.model.Expense;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
public class ExpenseReader {
    private static final Logger log = LoggerFactory.getLogger(ExpenseReader.class);

    public static List<Expense> readExpensesFromFile(String filePath) {
        int countSkipped = 0;
        List<Expense> expenses = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        try (FileInputStream file = new FileInputStream(filePath)) {
            // Create a workbook instance for the Excel file
            Workbook workbook = new XSSFWorkbook(file);

            // Get the sheet with the name "transactionHistory"
            Sheet sheet = workbook.getSheet("OpTransactionHistory");
            if (sheet == null) {
                log.error("Sheet 'OpTransactionHistory' not found in the file.");
                return expenses;
            }
            log.info("Total Rows : {}", sheet.getLastRowNum());
            // Iterate through rows (skip the header row)
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {

                try {
                    Row row = sheet.getRow(i);
                    if (row == null) continue; // Skip empty rows

                    // Read cell values
                    Cell valueDateCell = row.getCell(2);
                    Cell remarkCell = row.getCell(4);
                    Cell withdrawalCell = row.getCell(5);
                    Cell depositCell = row.getCell(6);

                    // Skip rows with missing or invalid data
                    if (valueDateCell == null || remarkCell == null || withdrawalCell == null || depositCell == null) {
                        countSkipped++;
                        continue;
                    }

                    // Parse cell values
                    Date valueDate = dateFormat.parse(valueDateCell.getStringCellValue());
                    String remark = remarkCell.getStringCellValue();
                    double withdrawal = parseCellValueAsDouble(withdrawalCell);
                    double deposit = parseCellValueAsDouble(depositCell);
                    String category = categorizeExpense(remark);
                    String[] parts = remark.split("/");
                    String transactionId =parts[parts.length-1];
                    // Create an Expense object and add it to the list
                    expenses.add(new Expense(valueDate, remark, withdrawal, deposit,category,transactionId));

                }
                catch (Exception e)
                {
                      countSkipped++;
                }
            }
            log.info("Sheet {} skipped {} no of rows.", filePath, countSkipped);
            workbook.close();
        } catch (IOException e) {
            log.error("Error reading file: {}", filePath);
        }
        return expenses;
    }
    private static double parseCellValueAsDouble(Cell cell) {
        if (cell.getCellType() == CellType.NUMERIC) {
            // If the cell is numeric, return its value directly
            return cell.getNumericCellValue();
        } else if (cell.getCellType() == CellType.STRING) {
            // If the cell is a string, parse it as a double
            try {
                return Double.parseDouble(cell.getStringCellValue().trim());
            } catch (NumberFormatException e) {
                log.error("Error parsing numeric value from string: {}", cell.getStringCellValue());
                return 0.0; // Default value if parsing fails
            }
        } else {
            return 0.0; // Default value for unsupported cell types
        }
    }
    private static String categorizeExpense(String remark) {
        if (remark == null || remark.trim().isEmpty()) {
            return "Other";
        }

        remark = remark.toUpperCase(); // Case-insensitive matching

        // Define categorization rules
        if (remark.contains("GROCERY") || remark.contains("ZEPTO") || remark.contains("BLINKIT")) {
            return "Grocery Delivery";
        } else if (remark.contains("SWIGGY") || remark.contains("ZOMATO")) {
            return "Food Delivery";
        } else if (remark.contains("AMAZON") || remark.contains("FLIPKART") || remark.contains("MYNTRA")) {
            return "Online Shopping";
        } else if (remark.contains("SALARY") || remark.contains("BNY MELLON")) {
            return "Salary Credited";
        } else if ( (remark.contains("SURESH") || remark.contains("7370068620") || remark.contains("rauniyar2002") || remark.contains("964952116142")
        || remark.contains("8271223619") || remark.contains("9430526508") || remark.contains("9117145816") || remark.contains("8651237791")|| remark.contains("6202107241")
        )) {
            return "Home Expense";
        }else if (remark.contains("ZERODHA") || remark.contains("ZERODHABROKING") || remark.contains("UPSTOX") || remark.contains("INDIAN CLEARING CORP"))  {
            return "Stocks";
        }
        else if (remark.contains("BANK CREDIT CA") || remark.contains("CRED.CLUB@AXISB")) {
            return "Credit Card";
        }
        else if (remark.contains("ACH")) {
            return "Bank Transfer";
        } else if (remark.contains("UPI")) {
            return "Online Payment";
        } else {
            return "Other";
        }
    }
}
