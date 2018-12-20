package io.gauravdubey.FileDownloader.workers;

import io.gauravdubey.FileDownloader.Utils;
import io.gauravdubey.FileDownloader.config.Constants;
import io.gauravdubey.FileDownloader.model.DownloadFile;
import io.gauravdubey.FileDownloader.service.DownloadFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;



@Component
@Scope("prototype")
public abstract class DownloadTask implements Runnable {

    protected DownloadFile mDownloadFile;

    private static final Logger logger = LoggerFactory.getLogger(DownloadTask.class);


    @Autowired
    private DownloadFileService downloadFileService;


    public void initDownload(){
        this.mDownloadFile.setState(Constants.STATES[Constants.DOWNLOADING]);
        this.mDownloadFile.setDownloadStartTime(System.nanoTime());
        updateDatabase();
    }

    public void endDownload(){
        this.mDownloadFile.setDownloadEndTime(System.nanoTime());
        System.out.println("gaurav timediff" + (mDownloadFile.getDownloadEndTime() - mDownloadFile.getDownloadStartTime()));
        this.mDownloadFile.setDownloadSpeed((this.mDownloadFile.getFileSize()*1000000000*8)/
                (mDownloadFile.getDownloadEndTime() - mDownloadFile.getDownloadStartTime()));
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
            this.mDownloadFile.setState(Constants.STATES[Constants.COMPLETED]);
            this.mDownloadFile.setFileSize(Utils.getFileSize(this.mDownloadFile.getFileName()));
        }
        endDownload();
    }

    public void downloadFailed(String msg){
        if(!msg.isEmpty()){
            logger.info("download Failed due to:" + msg);
            mDownloadFile.setErrorMessage(msg);
        } else
            logger.info("download failed");

        this.mDownloadFile.setState(Constants.STATES[Constants.FAILED]);
        endDownload();
    }

    public void downloadAborted(){
        logger.info("download Aborted");
        this.mDownloadFile.setState(Constants.STATES[Constants.CANCELLED]);
        this.mDownloadFile.setDownloadEndTime(System.nanoTime());
        updateDatabase();

    }

    public void updateDatabase(){
        downloadFileService.update(this.mDownloadFile.getDownloadRequestLog());
    }

}
