package io.gauravdubey.FileDownloader.integrationTest;

import io.gauravdubey.FileDownloader.FileDownloaderApplication;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;


import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;


@SpringBootTest(classes = FileDownloaderApplication.class)
@WebAppConfiguration
@ActiveProfiles("test")
public class InsertingADownloadFileTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    WebApplicationContext webApplicationContext;

    MockMvc mvc;

    @Before
    public void setUpMockMvc() {
        mvc = webAppContextSetup(webApplicationContext).build();
    }


    @Test
    public void savesThePostInTheDatabase() throws Exception {
        int count = countRowsInTable("download_file");

        mvc.perform(MockMvcRequestBuilders.post("/api/v1/downloadFile")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content("{\"source\": \"testing\"}"))
                .andDo(print())
                .andExpect(status().isCreated());

        count = countRowsInTable("download_file");
        System.out.println("new count is " + count);
        assertThat(countRowsInTable("download_file"), is(count + 1));
    }

    /*
    @Test
    public void failsWhenTryingToSubmitAnInvalidPost() throws Exception {
        int count = countRowsInTable("download_file");

        mvc.perform(MockMvcRequestBuilders.post("/api/v1/downloadFile")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andDo(print())
                .andExpect(status().isBadRequest());

        assertThat(countRowsInTable("download_file"), is(count));
    }
*/
}
