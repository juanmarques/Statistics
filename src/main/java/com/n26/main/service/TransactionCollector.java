package com.n26.main.service;

import java.util.concurrent.ConcurrentSkipListMap;

import com.n26.main.domain.Statistics;
import com.n26.main.domain.Transaction;

/**
 * Created by JuanM on 05/12/2018. Store in main memory with concurrent requests
 */
public final class TransactionCollector {

	static volatile ConcurrentSkipListMap<Transaction, Statistics> transactions = new ConcurrentSkipListMap<>();

}
