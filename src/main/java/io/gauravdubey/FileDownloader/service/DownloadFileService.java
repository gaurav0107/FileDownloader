package io.gauravdubey.FileDownloader.service;

import io.gauravdubey.FileDownloader.model.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DownloadFileService {


    DownloadResponse create(DownloadRequest downloadRequest);

    Optional<DownloadResponse> find(UUID id);

    List<DownloadResponse> findAll();

    DownloadFile update(DownloadFile downloadFile);

}
