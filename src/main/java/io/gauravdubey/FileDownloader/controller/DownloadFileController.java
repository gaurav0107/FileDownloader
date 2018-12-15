package io.gauravdubey.FileDownloader.controller;



import io.gauravdubey.FileDownloader.errors.ErrorResponse;
import io.gauravdubey.FileDownloader.errors.InvalidResourceException;
import io.gauravdubey.FileDownloader.errors.ResourceNotFoundException;
import io.gauravdubey.FileDownloader.model.*;
import io.gauravdubey.FileDownloader.service.DownloadFileService;


import io.gauravdubey.FileDownloader.workers.DownloadManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;


import static java.util.stream.Collectors.toMap;
import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.fromMethodCall;
import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

@RestController
@RequestMapping("api/v1/")
public class DownloadFileController {

    @Autowired
    private DownloadRepository downloadRepository;

    @Autowired
    private DownloadFileService downloadFileService;

    @Autowired
    private DownloadRequestValidator downloadRequestValidator;

    @Autowired
    private MessageSource messageSource;


    @InitBinder
    protected void initBinder(WebDataBinder webDataBinder){
        webDataBinder.setValidator(downloadRequestValidator);
    }


    @GetMapping("downloadFile")
    public ResponseEntity<Object> retrieveAllFiles() {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("")
                .build().toUri();
        return ResponseEntity.created(uri).body(downloadFileService.findAll());
    }


    @GetMapping("downloadFile/{id}")
    public DownloadFileResposne retrieveDownloadFile(@PathVariable Long id){
        Optional<DownloadFileResposne> downloadFileResposne = downloadFileService.find(id);
        if(!downloadFileResposne.isPresent()){
            throw new ResourceNotFoundException();
        }
        return downloadFileResposne.get();

    }

    @DeleteMapping("/downloadFile/{id}")
    public void deleteDownloadFile(@PathVariable Long id) {
        downloadRepository.deleteById(id);
    }


    @PostMapping(value = "/downloadFile", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> createDownloadFile(@Validated @RequestBody DownloadFileRequest downloadFileRequest,
                                                     Errors errors){


        if (errors.hasErrors()) {
            throw new InvalidResourceException(errors);
        }

        DownloadFileResposne downloadFileResposne = downloadFileService.create(downloadFileRequest);

        URI uri = fromMethodCall(on(DownloadFileController.class).retrieveDownloadFile(downloadFileResposne.getId()))
                .build()
                .toUri();

        return ResponseEntity.created(uri).body(downloadFileResposne);
    }

    @ExceptionHandler(InvalidResourceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorResponse invalidResourceException(InvalidResourceException e) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrors(e.getErrors()
                .getFieldErrors()
                .stream()
                .collect(
                        toMap(FieldError::getField,
                                t -> messageSource.getMessage(t.getCode(), null, null))
                )
        );
        return errorResponse;
    }
}
