package io.gauravdubey.FileDownloader.workers;

import io.gauravdubey.FileDownloader.Utils;
import io.gauravdubey.FileDownloader.config.Constants;
import io.gauravdubey.FileDownloader.model.DownloadFile;
import io.gauravdubey.FileDownloader.model.DownloadFileRepository;
import io.gauravdubey.FileDownloader.model.DownloadRequestLogRepository;
import io.gauravdubey.FileDownloader.service.DownloadFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class DownloadTask implements Runnable {

    protected DownloadFile mDownloadFile;

    private static final Logger logger = LoggerFactory.getLogger(DownloadTask.class);


    @Autowired
    private DownloadRequestLogRepository downloadRequestLogRepository;


    @Autowired
    private DownloadFileService downloadFileService;

    @Autowired
    private DownloadFileRepository downloadFileRepository;

    public void initDownload(){
        this.mDownloadFile.setState(Constants.DOWNLOADING);
        updateDatabase();
    }

    public DownloadTask(DownloadFile downloadFile) {
        this.mDownloadFile = downloadFile;
    }

    public void downloadSuccess(){
        if(!Utils.copyFileToPermanentLocation(Utils.getTempDownloadLocation(this.mDownloadFile.getFileName()),
                this.mDownloadFile.getFileName())){
            downloadFailed("Unable to move to Permanent Location");
        }else {
            logger.info("download Successful");
            this.mDownloadFile.setState(Constants.COMPLETED);
        }
        updateDatabase();
    }

    public void downloadFailed(String msg){
        if(!msg.isEmpty())
            logger.info("download Failed due to:" + msg);
        else
            logger.info("download failed");

        this.mDownloadFile.setState(Constants.FAILED);
        updateDatabase();
    }

    public void downloadAborted(){
        logger.info("download Aborted");
        this.mDownloadFile.setState(Constants.CANCELLED);
        updateDatabase();

    }

    public void updateDatabase(){
        downloadFileService.update(this.mDownloadFile);
    }

}
