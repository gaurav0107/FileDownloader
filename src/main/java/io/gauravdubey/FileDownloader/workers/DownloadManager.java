package io.gauravdubey.FileDownloader.workers;

import io.gauravdubey.FileDownloader.config.Constants;
import io.gauravdubey.FileDownloader.model.DownloadFile;
import io.gauravdubey.FileDownloader.model.DownloadRequest;
import io.gauravdubey.FileDownloader.model.DownloadRequestLog;
import org.apache.coyote.http11.HttpOutputBuffer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Service
public class DownloadManager {


    private static DownloadManager sInstance = null;

    //ExecutorService downloadService = Executors.newFixedThreadPool(10);

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private TaskExecutor taskExecutor;

    public DownloadManager() {}

    public void createDownload(DownloadRequestLog downloadRequestLog){
        for(DownloadFile downloadFile: downloadRequestLog.getDownloadFiles()){
            DownloadTask downloadTask = null;
            switch (downloadFile.getProtocol()){
                case Constants.HTTP:
                    downloadTask = applicationContext.getBean(HttpDownloadTask.class, downloadFile);
                    break;
                case Constants.HTTPS:
                    downloadTask = applicationContext.getBean(HttpDownloadTask.class, downloadFile);
                    break;
                case Constants.FTP:
                    downloadTask = applicationContext.getBean(FtpDownloadTask.class, downloadFile);
                    break;
                case Constants.SFTP:
                    downloadTask = applicationContext.getBean(SftpDownloadTask.class, downloadFile);
                    break;
            }
            taskExecutor.execute(downloadTask);
        }
    }

}











