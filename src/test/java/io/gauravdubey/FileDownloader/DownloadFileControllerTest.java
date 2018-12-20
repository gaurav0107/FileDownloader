package io.gauravdubey.FileDownloader;


import io.gauravdubey.FileDownloader.controller.DownloadFileController;
import io.gauravdubey.FileDownloader.model.*;
import io.gauravdubey.FileDownloader.service.DownloadFileService;
import org.hamcrest.Matchers;
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
import org.springframework.validation.Errors;
import java.util.*;
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
        Mockito.when(downloadRequestValidator.supports(DownloadRequest.class))
                .thenReturn(true);
    }


    @Test
    public void createTest() throws Exception {
        Set<DownloadFile> downloadFiles = new HashSet<>();
        DownloadRequestLog downloadRequestLog = new DownloadRequestLog();
        DownloadFile df1 = new DownloadFile(downloadRequestLog, "http://sample.download.file");
        DownloadFile df2 = new DownloadFile(downloadRequestLog, "sftp://sample.download.file");
        DownloadFile df3 = new DownloadFile(downloadRequestLog, "ftp://sample.download.file");
        downloadFiles.add(df1);downloadFiles.add(df2); downloadFiles.add(df3);
        DownloadResponse d1 = new DownloadResponse();
        d1.setDownloadFiles(downloadFiles);

        Mockito.when(downloadFileService.create(isA(DownloadRequest.class)))
                .thenReturn(d1);
        mvc.perform(MockMvcRequestBuilders.post("/api/v1/downloadRequest")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content("{\"downloadFiles\": [\"http://sample.download.file\", \"sftp://sample.download.file\", " +
                        "\"ftp://sample.download.file\"]}"))
                .andDo(print())
                .andExpect(status().isCreated());
    }


    @Test
    public void inValidCreateTest() throws Exception {
        doAnswer(invocation -> {
            Errors errors = invocation.getArgument(1);
            errors.rejectValue("downloadFiles", "blank");
            return null;
        }).when(downloadRequestValidator).validate(isA(DownloadRequest.class), isA(Errors.class));

        when(messageSource.getMessage("blank", null, null))
                .thenReturn("can't be blank");

        mvc.perform(MockMvcRequestBuilders.post("/api/v1/downloadRequest")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"downloadFiles\": \"\"}"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }



    @Test
    public void getTest() throws Exception {
        DownloadResponse downloadResponse = new DownloadResponse();
        when(downloadFileService.find(UUID.fromString("7b9b418f-3688-424f-acc3-5a3efc4163b5")))
                .thenReturn(Optional.of(downloadResponse));

        mvc.perform(MockMvcRequestBuilders.get("/api/v1/downloadRequest/{id}",
                UUID.fromString("7b9b418f-3688-424f-acc3-5a3efc4163b5"))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void inValidGetTest() throws Exception {
        when(downloadFileService.find(UUID.fromString("7b9b418f-3688-424f-acc3-5a3efc4163b5")))
                .thenReturn(Optional.empty());

        mvc.perform(MockMvcRequestBuilders.get("/api/v1/downloadFile/{id}",
                UUID.fromString("7b9b418f-3688-424f-acc3-5a3efc4163b5"))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void GetAllTest() throws Exception {
        when(downloadFileService.findAll())
                .thenReturn(Collections.emptyList());

        mvc.perform(MockMvcRequestBuilders.get("/api/v1/downloadRequest")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
}