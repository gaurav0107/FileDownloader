package io.gauravdubey.FileDownloader.validationTest;

import io.gauravdubey.FileDownloader.model.DownloadFile;
import io.gauravdubey.FileDownloader.model.DownloadFileRequest;
import io.gauravdubey.FileDownloader.model.DownloadRequestValidator;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.is;


public class DownloadFileRequestValidationTest {

    @InjectMocks
    DownloadRequestValidator downloadRequestValidator;


    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    public void supportsValidatingDownloadFileRequests() {
        assertThat(downloadRequestValidator.supports(DownloadFileRequest.class), is(true));
    }

    @Test
    public void failsGivenAPostRequestWithoutATitle() {
        DownloadFile target = new DownloadFile();
        target.setSource(null);

        Errors errors = new BeanPropertyBindingResult(target, "downloadFile");

        downloadRequestValidator.validate(target, errors);

        assertThat(errors.hasErrors(), is(true));
        assertThat(errors.getFieldError("source").getCode(), is("blank"));
    }

}
