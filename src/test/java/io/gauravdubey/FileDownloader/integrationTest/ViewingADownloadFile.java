package io.gauravdubey.FileDownloader.integrationTest;

import io.gauravdubey.FileDownloader.FileDownloaderApplication;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@SpringBootTest(classes = FileDownloaderApplication.class)
@WebAppConfiguration
@ActiveProfiles("test")
public class ViewingADownloadFile extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    WebApplicationContext webApplicationContext;

    MockMvc mvc;

    @Before
    public void setUpMockMvc() {
        mvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    @Sql(statements = "insert into download_file (id, source) values(1, 'testing')")
    public void returnsTheDownloadFileAsJson() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/v1/downloadFile/{id}", "1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.source", is("testing")));
    }

    @Test
    public void failsWhenTryingToViewAnUnknownDownloadFile() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/v1/downloadFile/{id}", "-1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}
