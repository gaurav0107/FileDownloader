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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.validation.Errors;


import java.util.Date;
import java.util.Optional;

import static org.hamcrest.Matchers.hasEntry;
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
/*
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
                */
    }


    @Test
    public void inValidCreateTest() throws Exception {
        doAnswer(invocation -> {
            Errors errors = invocation.getArgument(1);
            errors.rejectValue("source", "blank");
            return null;
        }).when(downloadRequestValidator).validate(isA(DownloadFileRequest.class), isA(Errors.class));

        when(messageSource.getMessage("blank", null, null))
                .thenReturn("can't be blank");

        mvc.perform(MockMvcRequestBuilders.post("/api/v1/downloadFile")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors", Matchers.hasEntry("source", "can't be blank")));
    }


    @Test
    public void getTest() throws Exception {
        /*
        DownloadFileResposne downloadFileResposne = new DownloadFileResposne();
        when(downloadFileService.find(1L))
                .thenReturn(Optional.of(downloadFileResposne));

        mvc.perform(MockMvcRequestBuilders.get("/api/v1/downloadFile/{id}", 1L)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
                */
    }

    @Test
    public void inValidGetTest() throws Exception {
        /*when(downloadFileService.find(-1L))
                .thenReturn(Optional.empty());

        mvc.perform(MockMvcRequestBuilders.get("/api/v1/downloadFile/{id}", "-1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
                */
    }
}