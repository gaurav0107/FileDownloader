package io.gauravdubey.FileDownloader;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
public class MockMvcTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private DownloadRepository downloadRepository;


    @Test
    public void ApiGetTest() throws Exception {

        Mockito.when(downloadRepository.findAll()).thenReturn(
                Collections.emptyList()
        );

        MvcResult mvcResult = this.mvc.perform(MockMvcRequestBuilders.get("/downloadFile")
                .accept(MediaType.APPLICATION_JSON)).andReturn();
        System.out.println(mvcResult.getResponse());
        Mockito.verify(downloadRepository).findAll();

    }

}