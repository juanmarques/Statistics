package com.n26.main.controller;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.n26.main.domain.Statistics;
import com.n26.main.domain.Transaction;
import com.n26.main.service.TransactionCreationAsyncService;
import com.n26.main.service.TransactionStatisticsAsyncService;

/**
 * Created by JuanM on 05/12/2018.
 */

@RestController
public class TransactionAsyncController {

	@Autowired
	public TransactionStatisticsAsyncService trasactionAsyncService;

	@Autowired
	public TransactionCreationAsyncService transactionCreationAsyncService;

	/**
	 * Method will get the transaction from the user and add to the storage.
	 * 
	 * @return stats
	 */
	@RequestMapping(value = "/transactions", method = RequestMethod.POST)
	@ResponseBody
	public void getTransactions(@RequestBody Transaction transaction, HttpServletResponse response) {

		// Check if older than 60 seconds
		boolean validateStatus = transactionCreationAsyncService.validateTrans(transaction);

		if (validateStatus) {
			// Call method to save the transaction
			transactionCreationAsyncService.saveTransaction(transaction);

			// Setting 201 status
			response.setStatus(HttpStatus.CREATED.value());

		} else {

			// Setting 204 status
			response.setStatus(HttpStatus.NO_CONTENT.value());
		}

	}

	/**
	 * Method will return last 60 seconds transactions.
	 * 
	 * @return statistics
	 */
	@RequestMapping(value = "/statistics", method = RequestMethod.GET)
	public CompletableFuture<?> getTransactionsSummary() throws InterruptedException, ExecutionException {
		CompletableFuture<?> statisticsResponseEntity = trasactionAsyncService.getTransactionSummary()
				.thenApply(statistics -> new ResponseEntity<Statistics>(statistics, HttpStatus.OK));
		return statisticsResponseEntity;
	}

}
