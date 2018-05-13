package com.n26.main;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * Created by JuanM on 05/12/2018.
 */

@SpringBootApplication
@EnableAsync
public class Application extends AsyncConfigurerSupport {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Value("${batch.core.pool.size}")
	private int corePollSize;

	@Value("${batch.max.pool.size}")
	private int maxPoolSize;

	@Value("${batch.queue.capacity}")
	private int queueCapacity;

	@Override
	@Bean(name = "transactionExecutor")
	public TaskExecutor getAsyncExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(corePollSize);
		executor.setMaxPoolSize(maxPoolSize);
		executor.setQueueCapacity(queueCapacity);
		executor.setWaitForTasksToCompleteOnShutdown(true);
		executor.initialize();
		return executor;
	}

}
