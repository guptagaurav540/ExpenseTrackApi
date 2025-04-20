package com.Gaurav.ExpenseTracker.serviceImp;


import com.Gaurav.ExpenseTracker.model.Expense;
import com.Gaurav.ExpenseTracker.service.ExpenseReportService;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class MonthlyExpenseReport implements ExpenseReportService {
    @Override
    public void ExpenseReport(List<Expense> allExpense, String outputDirectory) {
        // Group expenses by month and process
        Map<String, List<Expense>> expensesByMonth = allExpense.stream()
                .collect(Collectors.groupingBy(Expense::getMonth));

        for (Map.Entry<String, List<Expense>> entry1 : expensesByMonth.entrySet()) {
            String month = entry1.getKey();
            List<Expense> expenses = entry1.getValue();


            String monthFolder = outputDirectory + "/" + month;
            new File(monthFolder).mkdirs();

            // Group expenses by date
            Map<Date, List<Expense>> expensesByDate = expenses.stream()
                    .collect(Collectors.groupingBy(Expense::getValueDate));

            // Write expenses by date to a file
            String outputFile = monthFolder + "/expenses_by_date.csv";
            try (FileWriter writer = new FileWriter(outputFile)) {
                writer.write("Transaction Id,Date,Category,Withdrawal Amount,Deposit Amount\n");

                for (Map.Entry<Date, List<Expense>> entry : expensesByDate.entrySet()) {
                    Date date = entry.getKey();
                    List<Expense> dailyExpenses = entry.getValue();

                    for (Expense expense : dailyExpenses) {
                        writer.write(String.format("%s %s,%s,%.2f,%.2f\n",
                                expense.getTransactionId(),
                                new SimpleDateFormat("dd/MM/yyyy").format(date),
                                expense.getCategory(),
                                expense.getWithdrawalAmount(),
                                expense.getDepositAmount()));
                    }
                }

                log.info("Expense report for {} saved to {}", month, outputFile);
            } catch (IOException e) {
                log.error("Error writing to file: {}", outputFile);

            }
        }
    }
}
