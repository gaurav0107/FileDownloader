package io.gauravdubey.FileDownloader;


import io.gauravdubey.FileDownloader.controller.DownloadFileController;
import io.gauravdubey.FileDownloader.model.*;
import io.gauravdubey.FileDownloader.service.DownloadFileService;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import java.util.Date;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import static org.mockito.Mockito.*;



public class DownloadFileControllerTest {

    @Mock
    private DownloadFileService downloadFileService;

    @Mock
    DownloadRequestValidator downloadRequestValidator;

    @Mock
    MessageSource messageSource;

    @InjectMocks
    DownloadFileController downloadFileController;


    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    private MockMvc mvc;


    @Before
    public void setUpMockMvc() {
        mvc = standaloneSetup(downloadFileController).build();
    }

    @Before
    public void setUpPostRequestValidator() {
        Mockito.when(downloadRequestValidator.supports(DownloadFileRequest.class))
                .thenReturn(true);
    }


    @Test
    public void createTest() throws Exception {

        DownloadFileResposne d1 = new DownloadFileResposne();
        d1.setSource("testing");
        Mockito.when(downloadFileService.create(isA(DownloadFileRequest.class)))
                .thenReturn(d1);
        mvc.perform(MockMvcRequestBuilders.post("/api/v1/downloadFile")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content("{\"source\": \"testing\"}"))
                .andDo(print())
                .andExpect(status().isCreated());
    }
}