package com.Gaurav.ExpenseTracker;

import com.Gaurav.ExpenseTracker.serviceImp.ProcessFile;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ExpenseTrackerApplication {

	public static void main(String[] args) {
		ProcessFile processFile = ProcessFile.getInstance();
		String directory = "src/main/resources/transactionHistory";
		String prefix = "transaction";
		processFile.processMultipleFiles(directory, prefix);
		SpringApplication.run(ExpenseTrackerApplication.class, args);
	}

}
