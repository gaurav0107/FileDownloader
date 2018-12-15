package io.gauravdubey.FileDownloader;

import io.gauravdubey.FileDownloader.model.DownloadFile;
import io.gauravdubey.FileDownloader.model.DownloadFileRequest;
import io.gauravdubey.FileDownloader.model.DownloadFileResposne;
import io.gauravdubey.FileDownloader.model.DownloadRepository;
import io.gauravdubey.FileDownloader.service.DownloadFileServiceImpl;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.*;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.Optional;

import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.is;


public class DownloadServiceTest {

    @Mock
    DownloadRepository downloadRepository;

    @InjectMocks
    DownloadFileServiceImpl downloadFileService;

    @Captor
    ArgumentCaptor<DownloadFile> downloadFileArgumentCaptor;


    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();


    @Test
    public void creatingADownloadFileAndTellDownloadRepositoryToSave() {
        String source = "source";

        DownloadFileRequest downloadFileRequest = new DownloadFileRequest();
        downloadFileRequest.setSource(source);

        DownloadFile savedDownloadFile = Mockito.when(Mockito.mock(DownloadFile.class).getId())
                .thenReturn(1L)
                .getMock();
        Mockito.when(savedDownloadFile.getSource())
                .thenReturn(source);

        Mockito.when(downloadRepository.save(downloadFileArgumentCaptor.capture()))
                .thenReturn(savedDownloadFile);

        DownloadFileResposne downloadFileResposne = downloadFileService.create(downloadFileRequest);

        assertThat(downloadFileArgumentCaptor.getValue().getSource(), is(downloadFileRequest.getSource()));
        assertThat(downloadFileResposne.getId(), is(savedDownloadFile.getId()));
        assertThat(downloadFileRequest.getSource(), is(savedDownloadFile.getSource()));
    }


    @Test
    public void findingAnExistingDownloadFileByID() {
        DownloadFile existingDownloadFile = Mockito.when(Mockito.mock(DownloadFile.class).getId())
                .thenReturn(1L)
                .getMock();
        Mockito.when(existingDownloadFile.getSource())
                .thenReturn("source");
        Mockito.when(downloadRepository.getOne(1L))
                .thenReturn(existingDownloadFile);

        DownloadFileResposne downloadFileResponse = downloadFileService.find(1L).get();

        assertThat(downloadFileResponse.getId(), is(existingDownloadFile.getId()));
        assertThat(downloadFileResponse.getSource(), is(existingDownloadFile.getSource()));
    }

}
