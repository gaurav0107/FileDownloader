package io.gauravdubey.FileDownloader.service;

import io.gauravdubey.FileDownloader.model.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DownloadFileService {

    //DownloadFileResposne create(DownloadFileRequest downloadFile);

    DownloadResponse create(DownloadRequest downloadRequest);

    Optional<DownloadResponse> find(UUID id);

    //Optional<DownloadFileResposne> find(Long id);

    List<DownloadResponse> findAll();

}
