package io.gauravdubey.FileDownloader.workers;

import io.gauravdubey.FileDownloader.model.DownloadFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class DownloadTask implements Runnable {

    protected DownloadFile mDownloadFile;

    private static final Logger logger = LoggerFactory.getLogger(DownloadTask.class);

    public DownloadTask(DownloadFile downloadFile) {
        this.mDownloadFile = downloadFile;
    }

    public void downloadSuccess(){
        logger.info("download Successful");
    }

    public void downloadFailed(String msg){
        if(!msg.isEmpty())
            logger.info("download Failed due to:" + msg);
        else
            logger.info("download failed");

    }

    public void downloadAborted(){
        logger.info("download Aborted");

    }

    public void downloadTimedOut(){
        logger.info("download TimedOut");
    }

}
