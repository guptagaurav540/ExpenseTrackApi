package com.Gaurav.ExpenseTracker.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jdk.jfr.Description;
import lombok.Builder;
import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
@Builder
public class FilterDto {


    @Description("Transaction End Date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    Date StartDate;

    @Description("Transaction End Date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    Date EndDate;

    @Description("Transaction Type")
    List<String> Category;
    @Description("Transaction Type")
    String remark;
    @Description("Transaction Start Amount")
    double StartAmount;
    @Description("Transaction End Amount")
    double EndAmount;




}
