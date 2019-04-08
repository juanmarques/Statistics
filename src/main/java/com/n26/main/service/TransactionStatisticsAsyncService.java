package com.n26.main.service;

import java.time.Instant;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import com.n26.main.domain.Statistics;
import com.n26.main.domain.Transaction;

/**
 * Created by JuanM on 05/12/2018. Get the last 60 seconds transaction summary
 */

@Service
public class TransactionStatisticsAsyncService {

	@Autowired
	private TaskExecutor transactionExecutor;

	
	/**
	 * Method will return last 60 seconds transactions.
	 * 
	 * @return statistics
	 */
	public CompletableFuture<Statistics> getTransactionSummary() {

		return CompletableFuture.supplyAsync(() -> {

			Transaction transaction = TransactionCollector.transactions.firstEntry().getKey();
			Statistics statistics = TransactionCollector.transactions.firstEntry().getValue();
			if (transaction.getTimestamp() < (Instant.now().getEpochSecond() * 1000) - 60000)
				return new Statistics(0, 0, 0, 0, 0);
			else
				return statistics;

		}, transactionExecutor);

	}

}
