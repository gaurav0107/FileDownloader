package io.gauravdubey.FileDownloader;

import io.gauravdubey.FileDownloader.model.*;
import io.gauravdubey.FileDownloader.service.DownloadFileServiceImpl;
import io.gauravdubey.FileDownloader.workers.DownloadManager;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.*;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.is;


public class DownloadFileServiceTest {

    @Mock
    DownloadRequestLogRepository downloadRequestLogRepository;

    @InjectMocks
    DownloadFileServiceImpl downloadFileService;

    @Captor
    ArgumentCaptor<DownloadRequestLog> downloadFileArgumentCaptor;


    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();


    @Mock
    DownloadManager downloadManager;


    @Test
    public void creatingADownloadRequestAndTellDownloadRequestLogRepositoryToSave() {
        String s1 ="http://sample.download.file";
        String s2 ="ftp://sample.download.file";
        UUID requestId = UUID.fromString("7b9b418f-3688-424f-acc3-5a3efc4163b5");
        Set<String> downloadFiles = new HashSet<>();
        downloadFiles.add(s1);
        downloadFiles.add(s2);
        DownloadRequest downloadRequest = new DownloadRequest();
        downloadRequest.setDownloadFiles(downloadFiles);

        DownloadRequestLog d1 = new DownloadRequestLog();
        Set<DownloadFile> downloadFilesSet = new HashSet<>();
        downloadFilesSet.add(new DownloadFile(d1, s1));
        downloadFilesSet.add(new DownloadFile(d1, s2));
        d1.setDownloadFiles(downloadFilesSet);
        d1.setRequestId(requestId);

        System.out.println(downloadRequest);
        DownloadRequestLog savedDownloadRequestLog = Mockito.when(Mockito.mock(DownloadRequestLog.class).getRequestId())
                .thenReturn(requestId)
                .getMock();
        Mockito.when(savedDownloadRequestLog.getDownloadFiles())
                .thenReturn(d1.getDownloadFiles());

        Mockito.when(downloadRequestLogRepository.save(downloadFileArgumentCaptor.capture()))
                .thenReturn(savedDownloadRequestLog);

        Mockito.when(downloadManager.createDownload(savedDownloadRequestLog)).thenReturn(true);

        DownloadResponse downloadResponse = downloadFileService.create(downloadRequest);

        assertThat(downloadResponse.getRequestId(), is(requestId));
        assertThat(downloadResponse.getDownloadFiles(), is(d1.getDownloadFiles()));
    }


    @Test
    public void findingAnExistingDownloadRequestById() {
        UUID requestId = UUID.fromString("7b9b418f-3688-424f-acc3-5a3efc4163b5");
        String s1 ="http://sample.download.file";
        String s2 ="ftp://sample.download.file";
        Set<String> downloadFiles = new HashSet<>();
        downloadFiles.add(s1);
        downloadFiles.add(s2);
        DownloadRequest downloadRequest = new DownloadRequest();
        downloadRequest.setDownloadFiles(downloadFiles);

        DownloadRequestLog d1 = new DownloadRequestLog();
        Set<DownloadFile> downloadFilesSet = new HashSet<>();
        downloadFilesSet.add(new DownloadFile(d1, s1));
        downloadFilesSet.add(new DownloadFile(d1, s2));
        d1.setDownloadFiles(downloadFilesSet);
        d1.setRequestId(requestId);

        DownloadRequestLog downloadRequestLog = Mockito.when(Mockito.mock(DownloadRequestLog.class).getRequestId())
                .thenReturn(requestId)
                .getMock();

        Mockito.when(downloadRequestLog.getDownloadFiles())
                .thenReturn(d1.getDownloadFiles());

        Mockito.when(downloadRequestLogRepository.findById(requestId))
                .thenReturn(java.util.Optional.ofNullable(downloadRequestLog));

        DownloadResponse downloadResponse = downloadFileService.find(requestId).get();

        assertThat(downloadResponse.getRequestId(), is(downloadRequestLog.getRequestId()));
        assertThat(downloadResponse.getDownloadFiles(), is(downloadRequestLog.getDownloadFiles()));
    }

}
