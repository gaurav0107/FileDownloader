package io.gauravdubey.FileDownloader.model;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Set;


@Component
public class DownloadRequestValidator implements Validator {


    @Override
    public boolean supports(Class<?> aClass) {
        return DownloadRequest.class.equals(aClass);

    }

    @Override
    public void validate(Object o, Errors errors) {
        DownloadRequest downloadRequest = (DownloadRequest) o;
        if(downloadRequest.getDownloadFiles() == null){
            errors.rejectValue("downloadFiles", "downloadFile.mandatory");
        }else if(downloadRequest.getDownloadFiles().isEmpty()){
            errors.rejectValue("downloadFiles", "downloadFileList.empty");
        }else{
            for(String downloadFile: downloadRequest.getDownloadFiles()){
                try{
                    URI url = new URI(downloadFile);
                } catch (URISyntaxException e) {
                    errors.rejectValue("downloadFiles", "downloadFile.shouldBeUrl");
                }
            }
        }
    }
}

