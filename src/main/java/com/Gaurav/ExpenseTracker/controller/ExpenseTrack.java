package com.Gaurav.ExpenseTracker.controller;

import com.Gaurav.ExpenseTracker.ApiResponse;
import com.Gaurav.ExpenseTracker.model.Expense;
import com.Gaurav.ExpenseTracker.model.FilterDto;
import com.Gaurav.ExpenseTracker.serviceImp.ProcessFile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ExpenseTrack {
    ProcessFile processFile = ProcessFile.getInstance();


    @GetMapping("/getAllExpenses")
    public ResponseEntity<ApiResponse<List<Expense>>> getAllExpense() {
        try {
            System.out.println("Inside getAllExpense");
            List<Expense> expenses = processFile.getAllExpense();
            ApiResponse<List<Expense>> response = new ApiResponse<>(
                    HttpStatus.OK.value(), // HTTP status code
                    "Expenses retrieved successfully", // Message
                    expenses // Data
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse<List<Expense>> response = new ApiResponse<>(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(), // HTTP status code
                    "Failed to retrieve expenses: " + e.getMessage(), // Error message
                    null // No data
            );
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/getAllExpensesBasedOnFilter")
    public ResponseEntity<ApiResponse<List<Expense>>> getAllExpenseBasedOnFilter(@RequestBody FilterDto filterDto) {
        try {
            System.out.println("Inside getAllExpense");
            List<Expense> expenses = processFile.getAllExpenseBasedOnFiler(filterDto);
            ApiResponse<List<Expense>> response = new ApiResponse<>(
                    HttpStatus.OK.value(), // HTTP status code
                    "Expenses retrieved successfully", // Message
                    expenses // Data
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse<List<Expense>> response = new ApiResponse<>(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(), // HTTP status code
                    "Failed to retrieve expenses: " + e.getMessage(), // Error message
                    null // No data
            );
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
