package io.gauravdubey.FileDownloader.controller;

import io.gauravdubey.FileDownloader.exceptions.DownloadFileNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class RestErrorHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestErrorHandler.class);

    @ExceptionHandler(DownloadFileNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleDownloadFileNotFound(DownloadFileNotFoundException ex) {
        LOGGER.debug("handling 404 error on entry");
    }
}