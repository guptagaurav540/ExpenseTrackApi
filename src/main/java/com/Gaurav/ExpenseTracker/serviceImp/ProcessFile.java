package com.Gaurav.ExpenseTracker.serviceImp;


import com.Gaurav.ExpenseTracker.model.Expense;
import com.Gaurav.ExpenseTracker.model.FilterDto;
import com.Gaurav.ExpenseTracker.service.ExpenseReportService;
import lombok.Getter;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


public class ProcessFile {
    private static ProcessFile instance;

    // Process all files
    private List<Expense> allExpenses ;
    private ProcessFile()
    {    }


    public static synchronized ProcessFile getInstance()
    {
        if(instance == null)
        {
            instance = new ProcessFile();
        }
        return instance;
    }

    public List<Expense> getAllExpense()
    {
        return allExpenses;
    }

    public void processMultipleFiles(String directory, String prefix) {

        File folder = new File(directory);
        File[] files = folder.listFiles((dir, name) -> name.startsWith(prefix));

        if (files == null || files.length == 0) {
            System.out.println("No files found with prefix '" + prefix + "' in directory " + directory);
            return;
        }

        allExpenses  = new ArrayList<>();
        for (File file : files) {
            System.out.println("Processing file: " + file.getName());
            List<Expense> expenses = ExpenseReader.readExpensesFromFile(file.getAbsolutePath());
            allExpenses.addAll(expenses);
        }
    }
    public Date changeDateFormate(Date startDate) {
        if (startDate != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(startDate);
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            startDate = cal.getTime();
        } else {
            startDate = null;
        }
        return startDate;
    }

    public List<Expense> getAllExpenseBasedOnFiler(FilterDto filterDto)
    {
        Date startDate = changeDateFormate(filterDto.getStartDate());
        Date endDate = changeDateFormate(filterDto.getEndDate());
        List<String> categories = filterDto.getCategory();

        return allExpenses.stream()
                .filter(expense -> {
                    double startAmount = filterDto.getStartAmount();
                    double endAmount = filterDto.getEndAmount();
                    if(startAmount>0)
                    {
                        if(expense.getWithdrawalAmount()> 0 && startAmount > expense.getWithdrawalAmount())
                        {
                            return false;
                        }
                        if(expense.getDepositAmount()> 0 && startAmount > expense.getDepositAmount())
                        {
                            return false;
                        }
                    }
                    if(endAmount>0)
                    {
                        if(expense.getWithdrawalAmount() > 0 && expense.getWithdrawalAmount() > endAmount)
                        {
                            return false;
                        }

                        if(expense.getDepositAmount() > 0 && expense.getDepositAmount() > endAmount)
                        {
                            return false;
                        }
                    }
                    // if remarks is not null filter by remarks
                    if(filterDto.getRemark()!=null && !expense.getRemark().contains(filterDto.getRemark()))
                    {
                        return false;
                    }

                    // Case 1: If both startDate and endDate are null, filter only by category
                    if (startDate == null && endDate == null) {
                        return categories == null || categories.isEmpty() || categories.contains(expense.getCategory());
                    }
                    // Case 2: If startDate and endDate are provided, filter by date range and category
                    else {
                        boolean isWithinDateRange = (startDate == null || !expense.getValueDate().before(startDate)) &&
                                (endDate == null || !expense.getValueDate().after(endDate));
                        boolean matchesCategory = categories == null || categories.isEmpty() || categories.contains(expense.getCategory());
                        return isWithinDateRange && matchesCategory;
                    }
                })
                .collect(Collectors.toList());
    }

}
