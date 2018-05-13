package com.n26.main;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.Instant;
import java.util.stream.IntStream;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.n26.main.domain.Statistics;
import com.n26.main.domain.Transaction;

/**
 * Created by JuanM on 05/12/2018.
 * Integrated Test for both transaction saving and getting the
 * transaction statistics for last 60 seconds
 */

public class AsyncTransactionSequenceTest extends AbstractControllerTest {

    private final String transactionsURI = "/transactions";
    private final String statisticsURI = "/statistics";

    @Test
    public void getInitialTransactionStatistics() throws Exception {
        saveTransactionList();
    }

    private void saveTransactionList() throws Exception {

        IntStream.range(0, 100).forEach(i -> {
            try {
                saveTransaction(i + 1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        MvcResult mvcResult = this.mockMvc.perform(get(statisticsURI))
                .andExpect(request().asyncStarted())
                .andReturn();
        try {
            mvcResult.getAsyncResult();
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.mockMvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(content().string(toJson(new Statistics(5050,50.50,100.0,1.0,100))));

    }

    private void saveTransaction(double count) throws Exception {

        final MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post(transactionsURI).contentType(MediaType.APPLICATION_JSON).
                        content(toJson(new Transaction(count,Instant.now().getEpochSecond() * 1000 ))
                                .getBytes("UTF-8"));

        this.mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status()
                        .isCreated())
                .andExpect(content().string(""));

    }

}
