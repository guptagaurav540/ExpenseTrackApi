package com.Gaurav.ExpenseTracker.model;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@Builder
public class Expense {
    private Date valueDate;
    private String remark;
    private double withdrawalAmount;
    private double depositAmount;
    private String category;
    String transactionId;
    public Expense(Date valueDate, String remark, double withdrawalAmount, double depositAmount, String category,String transactionId) {
        this.valueDate = valueDate;
        this.remark = remark;
        this.withdrawalAmount = withdrawalAmount;
        this.depositAmount = depositAmount;
        this.category = category;
        this.transactionId = transactionId;
    }
    public String getMonth() {
        return new SimpleDateFormat("yyyy-MM").format(valueDate);
    }

    public Date getValueDate() {
        return valueDate;
    }

    public void setValueDate(Date valueDate) {
        this.valueDate = valueDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public double getWithdrawalAmount() {
        return withdrawalAmount;
    }

    public void setWithdrawalAmount(double withdrawalAmount) {
        this.withdrawalAmount = withdrawalAmount;
    }

    public double getDepositAmount() {
        return depositAmount;
    }

    public void setDepositAmount(double depositAmount) {
        this.depositAmount = depositAmount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
}
