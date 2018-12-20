package io.gauravdubey.FileDownloader.controller;



import io.gauravdubey.FileDownloader.errors.ErrorResponse;
import io.gauravdubey.FileDownloader.errors.InvalidResourceException;
import io.gauravdubey.FileDownloader.errors.ResourceNotFoundException;
import io.gauravdubey.FileDownloader.model.*;
import io.gauravdubey.FileDownloader.service.DownloadFileService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.MalformedURLException;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
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


    @InitBinder
    protected void initBinder(WebDataBinder webDataBinder){
        webDataBinder.setValidator(downloadRequestValidator);
    }


    @GetMapping("downloadRequest")
    public ResponseEntity<List<DownloadResponse>> retrieveAllRequests() {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("")
                .build().toUri();
        return ResponseEntity.ok().body(downloadFileService.findAll());
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

        DownloadResponse downloadResponse = downloadFileService.create(downloadRequest);

        URI uri = fromMethodCall(on(DownloadFileController.class).retrieveRequestById(downloadResponse.getRequestId()))
                .build()
                .toUri();

        return ResponseEntity.created(uri).body(downloadResponse);

    }

    @GetMapping("getFile/{fileName}")
    public ResponseEntity<Resource> getFile(@PathVariable String fileName){
        String contentType = null;
        Optional<Resource> resource = Optional.empty();
        Path fileStorageLocation = Paths.get("storage");
        Path filePath = fileStorageLocation.resolve(fileName).normalize();
        System.out.println("gaurav path: " + filePath.toString());
        Resource r = null;
        try {
            r = new UrlResource(filePath.toUri());
        } catch (MalformedURLException e) {
            System.out.println("not found 1");
        }
        if(r.exists()) {
                resource = Optional.ofNullable(r);
        } else {
                System.out.println("not found 2");
        }

        if(!resource.isPresent()){
            ResponseEntity.notFound().build();
        }

        contentType = "application/octet-stream";
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.get().getFilename() + "\"")
                .body(resource.get());
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
