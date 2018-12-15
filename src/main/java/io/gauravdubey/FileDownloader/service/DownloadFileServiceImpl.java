package io.gauravdubey.FileDownloader.service;


import io.gauravdubey.FileDownloader.model.DownloadFile;
import io.gauravdubey.FileDownloader.model.DownloadFileRequest;
import io.gauravdubey.FileDownloader.model.DownloadFileResposne;
import io.gauravdubey.FileDownloader.model.DownloadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.text.html.Option;
import java.util.*;

@Component
public class DownloadFileServiceImpl implements DownloadFileService {


    @Autowired
    private DownloadRepository downloadRepository;


    @Override
    public DownloadFileResposne create(DownloadFileRequest downloadFileRequest) {
        DownloadFile downloadFile = new DownloadFile();
        downloadFile.setSource(downloadFileRequest.getSource());
        downloadFile.setRequestTime(downloadFileRequest.getRequestTime());
        downloadFile = downloadRepository.save(downloadFile);

        DownloadFileResposne downloadFileResponse = new DownloadFileResposne();
        downloadFileResponse.setId(downloadFile.getId());
        downloadFileResponse.setSource(downloadFile.getSource());
        downloadFileResponse.setRequestTime(downloadFile.getRequestTime());
        return downloadFileResponse;
    }

    @Override
    public Optional<DownloadFileResposne> find(Long id) {
        Optional<DownloadFile> downloadFile = downloadRepository.findById(id);

        if(!downloadFile.isPresent()){
            return Optional.empty();
        }else {
            DownloadFileResposne downloadFileResposne = new DownloadFileResposne();
            downloadFileResposne.setId(downloadFile.get().getId());
            downloadFileResposne.setSource(downloadFile.get().getSource());
            downloadFileResposne.setRequestTime(downloadFile.get().getRequestTime());
            return Optional.ofNullable(downloadFileResposne);
        }

    }

    @Override
    public List<DownloadFileResposne> findAll() {
        List<DownloadFile> downloadFiles = downloadRepository.findAll();
        List<DownloadFileResposne> downloadFileResposnes = new ArrayList<>();
        for (ListIterator<DownloadFile> iter = downloadFiles.listIterator(); iter.hasNext(); ) {
            DownloadFile downloadFile = iter.next();
            DownloadFileResposne downloadFileResposne = new DownloadFileResposne();
            downloadFileResposne.setId(downloadFile.getId());
            downloadFileResposne.setSource(downloadFile.getSource());
            downloadFileResposne.setRequestTime(downloadFile.getRequestTime());
            downloadFileResposnes.add(downloadFileResposne);
        }
        return downloadFileResposnes;
    }
}
