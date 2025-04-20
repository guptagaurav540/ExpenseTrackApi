package com.Gaurav.ExpenseTracker.service;


import com.Gaurav.ExpenseTracker.model.Expense;

import java.util.List;

public interface ExpenseReportService {
    void ExpenseReport(List<Expense> expenses, String outputDirectory);
}
