package io.gauravdubey.FileDownloader.controller;



import io.gauravdubey.FileDownloader.errors.ErrorResponse;
import io.gauravdubey.FileDownloader.errors.InvalidResourceException;
import io.gauravdubey.FileDownloader.errors.ResourceNotFoundException;
import io.gauravdubey.FileDownloader.model.*;
import io.gauravdubey.FileDownloader.service.DownloadFileService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


import static java.util.stream.Collectors.toMap;
import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.fromMethodCall;
import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

@RestController
@RequestMapping("api/v1/")
public class DownloadFileController {

    @Autowired
    private DownloadFileService downloadFileService;

    @Autowired
    private DownloadRequestValidator downloadRequestValidator;

    @Autowired
    private MessageSource messageSource;

/*
    @InitBinder
    protected void initBinder(WebDataBinder webDataBinder){
        webDataBinder.setValidator(downloadRequestValidator);
    }
*/

    @GetMapping("downloadRequest")
    public ResponseEntity<List<DownloadResponse>> retrieveAllRequests() {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("")
                .build().toUri();
        return ResponseEntity.created(uri).body(downloadFileService.findAll());
    }



    @GetMapping("downloadRequest/{id}")
    public ResponseEntity<DownloadResponse> retrieveRequestById(@PathVariable UUID id){
        Optional<DownloadResponse> downloadResposne = downloadFileService.find(id);
        if(!downloadResposne.isPresent()){
            throw new ResourceNotFoundException();
        }
        return ResponseEntity.ok().body(downloadResposne.get());
    }


    @PostMapping(value = "downloadRequest", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> createDownloadFiles(@Validated @RequestBody DownloadRequest downloadRequest,
                                                     Errors errors){
        if (errors.hasErrors()) {
            throw new InvalidResourceException(errors);
        }

        DownloadResponse downloadResposne = downloadFileService.create(downloadRequest);

        URI uri = fromMethodCall(on(DownloadFileController.class).retrieveRequestById(downloadResposne.getRequestId()))
                .build()
                .toUri();

        return ResponseEntity.created(uri).body(downloadResposne);

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
