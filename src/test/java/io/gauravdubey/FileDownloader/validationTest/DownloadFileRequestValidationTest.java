package io.gauravdubey.FileDownloader.validationTest;

import io.gauravdubey.FileDownloader.model.DownloadFile;
import io.gauravdubey.FileDownloader.model.DownloadRequest;
import io.gauravdubey.FileDownloader.model.DownloadRequestLog;
import io.gauravdubey.FileDownloader.model.DownloadRequestValidator;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.is;


public class DownloadFileRequestValidationTest {

    @InjectMocks
    DownloadRequestValidator downloadRequestValidator;


    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    public void supportsValidatingDownloadFileRequests() {
        assertThat(downloadRequestValidator.supports(DownloadRequest.class), is(true));
    }

    @Test
    public void passGivenCorrectDataSet() {
        String s1 ="http://sample.download.file";
        String s2 ="ftp://sample.download.file";
        Set<String> downloadFilesSet = new HashSet<>();
        downloadFilesSet.add(s1);
        downloadFilesSet.add(s2);
        DownloadRequest dr = new DownloadRequest();
        dr.setDownloadFiles(downloadFilesSet);
        Errors errors = new BeanPropertyBindingResult(dr, "downloadFile");
        downloadRequestValidator.validate(dr, errors);
        assertThat(errors.hasErrors(), is(false));
    }

    @Test
    public void failsGivenAEmptyDownloadFile() {
        Set<String> downloadFilesSet = new HashSet<>();

        DownloadRequest dr = new DownloadRequest();
        dr.setDownloadFiles(downloadFilesSet);
        Errors errors = new BeanPropertyBindingResult(dr, "downloadFile");
        downloadRequestValidator.validate(dr, errors);
        assertThat(errors.hasErrors(), is(true));
    }

    @Test
    public void failsGivenInCorrectUrls() {
        String s1 ="http:";
        String s2 ="sample.download.file";
        Set<String> downloadFilesSet = new HashSet<>();
        downloadFilesSet.add(s1);
        downloadFilesSet.add(s2);
        DownloadRequest dr = new DownloadRequest();
        dr.setDownloadFiles(downloadFilesSet);
        Errors errors = new BeanPropertyBindingResult(dr, "downloadFile");
        downloadRequestValidator.validate(dr, errors);
        assertThat(errors.hasErrors(), is(true));
    }

}
