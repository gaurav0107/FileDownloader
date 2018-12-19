package io.gauravdubey.FileDownloader.service;


import io.gauravdubey.FileDownloader.config.Constants;
import io.gauravdubey.FileDownloader.errors.ResourceNotFoundException;
import io.gauravdubey.FileDownloader.model.*;
import io.gauravdubey.FileDownloader.workers.DownloadManager;
import io.gauravdubey.FileDownloader.workers.HttpDownloadTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.*;

@Component
public class DownloadFileServiceImpl implements DownloadFileService {


    private static final Logger logger = LoggerFactory.getLogger(HttpDownloadTask.class);


    @Autowired
    private DownloadRequestLogRepository downloadRequestLogRepository;

    @Autowired DownloadFileRepository downloadFileRepository;

    @Autowired
    DownloadManager downloadManager;

    @Override
    public DownloadResponse create(DownloadRequest downloadRequest) {
        Set<String> downloadFileUrls = downloadRequest.getDownloadFiles();
        Iterator<String> iterator = downloadFileUrls.iterator();
        Set<DownloadFile> downloadFiles = new HashSet<>();
        DownloadRequestLog downloadRequestLog = new DownloadRequestLog();
        for(String downloadFileUrl : downloadFileUrls){
            DownloadFile downloadFile = new DownloadFile(downloadRequestLog, downloadFileUrl);
            downloadFiles.add(downloadFile);
        }
        downloadRequestLog.setDownloadFiles(downloadFiles);
        downloadRequestLog = downloadRequestLogRepository.save(downloadRequestLog);
        downloadManager.createDownload(downloadRequestLog);
        return createDownloadResposne(downloadRequestLog);
    }

    @Override
    public Optional<DownloadResponse> find(UUID id) {
        Optional<DownloadRequestLog> downloadRequestLog = downloadRequestLogRepository.findById(id);
        if(! downloadRequestLog.isPresent())
            return Optional.empty();
        else {
            return Optional.ofNullable(createDownloadResposne(downloadRequestLog.get()));
        }
    }

    @Override
    public List<DownloadResponse> findAll() {
        List<DownloadRequestLog> downloadRequestLogs = downloadRequestLogRepository.findAll();
        List<DownloadResponse> downloadResponses = new ArrayList<>();
        for (ListIterator<DownloadRequestLog> iter = downloadRequestLogs.listIterator(); iter.hasNext(); ) {
            downloadResponses.add(createDownloadResposne(iter.next()));
        }
        return downloadResponses;
    }


    @Override
    public DownloadRequestLog update(DownloadRequestLog downloadRequestLog) {
        System.out.println(downloadRequestLog.getRequestId());
        Optional<DownloadRequestLog> drl = downloadRequestLogRepository.findById(downloadRequestLog.getRequestId());
        if(!drl.isPresent()){
            throw new ResourceNotFoundException();
        }
        downloadRequestLog.setRequestId(drl.get().getRequestId());
        return downloadRequestLogRepository.save(downloadRequestLog);
    }

    public DownloadResponse createDownloadResposne(DownloadRequestLog downloadRequestLog){
        int success;
        int failed;
        DownloadResponse downloadResponse = new DownloadResponse();
        downloadResponse.setDownloadFiles(downloadRequestLog.getDownloadFiles());
        downloadResponse.setRequestTime(downloadRequestLog.getRequestTime());
        downloadResponse.setRequestId(downloadRequestLog.getRequestId());
        downloadResponse.setTotalFiles(downloadRequestLog.getDownloadFiles().size());
        success = failed = 0;
        for(DownloadFile df: downloadRequestLog.getDownloadFiles()){
            if(df.getState() == Constants.COMPLETED)
                success += 1;
            else if(df.getState() == Constants.CANCELLED || df.getState() == Constants.FAILED)
                failed +=1;
        }
        downloadResponse.setFailedDownloads(failed);
        downloadResponse.setSuccessfulDownloads(success);
        return downloadResponse;
    }
}
