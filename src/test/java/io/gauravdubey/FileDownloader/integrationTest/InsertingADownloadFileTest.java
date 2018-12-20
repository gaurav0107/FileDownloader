package io.gauravdubey.FileDownloader.integrationTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.gauravdubey.FileDownloader.FileDownloaderApplication;
import io.gauravdubey.FileDownloader.model.DownloadRequestLog;
import io.gauravdubey.FileDownloader.model.DownloadRequestLogRepository;
import io.gauravdubey.FileDownloader.model.DownloadResponse;
import io.gauravdubey.FileDownloader.service.DownloadFileService;
import org.hamcrest.Matchers;
import org.hamcrest.core.IsNull;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import static org.junit.Assert.assertThat;


@SpringBootTest(classes = FileDownloaderApplication.class)
@WebAppConfiguration
@ActiveProfiles("test")
public class InsertingADownloadFileTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    WebApplicationContext webApplicationContext;

    @Mock
    DownloadFileService downloadFileService;

    @Mock
    MockMvc mvc;


    @Mock
    DownloadRequestLogRepository downloadRequestLogRepository;

    @Before
    public void setUpMockMvc() {
        mvc = webAppContextSetup(webApplicationContext).build();
    }


    @Test
    public void savesThePostInTheDatabase() throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/api/v1/downloadRequest")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content("{\"downloadFiles\": [\"http://sample.download.file\"]}"))
                .andDo(print())
                .andExpect(status().isCreated()).andReturn();


        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = result.getResponse().getContentAsString();
        DownloadResponse obj = mapper.readValue(jsonInString, DownloadResponse.class);
        //Optional<DownloadRequestLog> fetchedResponseFromDb = downloadRequestLogRepository.findById(obj.getRequestId());
        //assertThat(fetchedResponseFromDb.isPresent(), is(false));
        //assertThat(fetchedResponseFromDb.get().getRequestId(), is(obj.getRequestId()));
        assertThat(obj.getRequestId(), IsNull.notNullValue());
        //TODO Compare and check with database for successful insertion
    }


    @Test
    public void failsWhenTryingToSubmitAnInvalidDownloadRequest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/api/v1/downloadRequest")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andDo(print())
                .andExpect(status().isBadRequest());
        //TODO Compare and check with database for any insertion if occurs when api throws error
    }
}
