package io.gauravdubey.FileDownloader.workers;

import io.gauravdubey.FileDownloader.config.Config;
import io.gauravdubey.FileDownloader.model.DownloadFile;
import io.gauravdubey.FileDownloader.model.DownloadRequestLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;


@Service
public class DownloadManager {


    private static DownloadManager sInstance = null;

    //ExecutorService downloadService = Executors.newFixedThreadPool(10);

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private TaskExecutor taskExecutor;

    public DownloadManager() {}

    public boolean createDownload(DownloadRequestLog downloadRequestLog){
        for(DownloadFile downloadFile: downloadRequestLog.getDownloadFiles()){
            DownloadTask downloadTask = null;
            switch (downloadFile.getProtocol()){
                case Config.HTTP:
                    downloadTask = applicationContext.getBean(HttpDownloadTask.class, downloadFile);
                    break;
                case Config.HTTPS:
                    downloadTask = applicationContext.getBean(HttpDownloadTask.class, downloadFile);
                    break;
                case Config.FTP:
                    downloadTask = applicationContext.getBean(FtpDownloadTask.class, downloadFile);
                    break;
                case Config.SFTP:
                    downloadTask = applicationContext.getBean(SftpDownloadTask.class, downloadFile);
                    break;
            }
            taskExecutor.execute(downloadTask);
        }
        return true;
    }

}











