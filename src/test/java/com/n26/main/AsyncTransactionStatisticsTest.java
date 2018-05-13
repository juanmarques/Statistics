package com.n26.main;

import com.n26.main.domain.Statistics;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by JuanM on 05/12/2018.
 * Test for get statistics for given request - no transaction state or
 * no transactions with in 60 seconds
 */
@Ignore
public class AsyncTransactionStatisticsTest extends AbstractControllerTest {

    private final String URI = "/statistics";

    @Test
    public void getInitialTransactionStatistics() throws Exception {

        MvcResult mvcResult = this.mockMvc.perform(get(URI))
                .andExpect(request().asyncStarted())
                .andReturn();

        mvcResult.getAsyncResult();

        this.mockMvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(content().string(toJson(new Statistics(0,0,0,0,0))));
    }
}
