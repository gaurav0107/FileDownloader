package io.gauravdubey.FileDownloader.workers;

import io.gauravdubey.FileDownloader.config.Constants;
import io.gauravdubey.FileDownloader.model.DownloadFile;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DownloadManager {


    private static DownloadManager sInstance = null;

    ExecutorService downloadService = Executors.newFixedThreadPool(10);


    /** Private constructor,
     * to stop creation of multiple object of download Manager */
    private DownloadManager() {}

    public static DownloadManager getInstance() {
        if (sInstance == null)
            sInstance = new DownloadManager();

        return sInstance;
    }

    public void createDownload(DownloadFile downloadFile){
        DownloadTask downloadTask = null;
        switch (downloadFile.getProtocol()){
            case Constants.HTTP:
                downloadTask = new HttpDownloadTask(downloadFile);
                break;
            case Constants.HTTPS:
                downloadTask = new HttpDownloadTask(downloadFile);
                break;
            case Constants.FTP:
                downloadTask = new FtpDownloadTask(downloadFile);
                break;
            case Constants.SFTP:
                downloadTask = new SftpDownloadTask(downloadFile);
                break;
        }
        downloadService.execute(downloadTask);
    }
}











