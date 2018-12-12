package io.gauravdubey.FileDownloader;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class FileDownloadResource {

    @Autowired
    private DownloadRepository downloadRepository;


    @GetMapping("/downloadFile")
    public List<DownloadFile> retrieveAllStudents() {
        return downloadRepository.findAll();
    }


}
