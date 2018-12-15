package io.gauravdubey.FileDownloader.service;

import io.gauravdubey.FileDownloader.model.DownloadFileRequest;
import io.gauravdubey.FileDownloader.model.DownloadFileResposne;

import java.util.List;
import java.util.Optional;

public interface DownloadFileService {

    DownloadFileResposne create(DownloadFileRequest downloadFile);

    Optional<DownloadFileResposne> find(Long id);

    List<DownloadFileResposne> findAll();

}
