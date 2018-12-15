package io.gauravdubey.FileDownloader.workers;

import io.gauravdubey.FileDownloader.config.Constants;
import io.gauravdubey.FileDownloader.model.DownloadFile;

import java.util.ArrayList;

public class DownloadManager {


    private static DownloadManager sInstance = null;
    private int mNumConnPerDownload;
    private ArrayList<Downloader> mDownloadList;


    /** Private constructor,
     * to stop creation of multiple object of download Manager */
    private DownloadManager() {
        mNumConnPerDownload = Constants.DEFAULT_NUM_CONN_PER_DOWNLOAD;

        mDownloadList = new ArrayList<Downloader>();
    }

    public static DownloadManager getInstance() {
        if (sInstance == null)
            sInstance = new DownloadManager();

        return sInstance;
    }

    public int getNumConnPerDownload() {
        return mNumConnPerDownload;
    }

    public void SetNumConnPerDownload(int value) {
        mNumConnPerDownload = value;
    }

    public Downloader getDownload(int index) {
        return mDownloadList.get(index);
    }

    public void removeDownload(int index) {
        mDownloadList.remove(index);
    }

    public ArrayList<Downloader> getDownloadList() {
        return mDownloadList;
    }

    public Downloader createDownload(DownloadFile downloadFile) {
        Downloader fd = null;
        switch (downloadFile.getProtocol()){
            case Constants.HTTP:
                fd = new HttpDownloader(downloadFile);
                break;
            case Constants.FTP:
                fd = new FtpDownloader(downloadFile);
                break;
            case Constants.SFTP:
                fd = new SftpDownloader(downloadFile);
                break;
        }
        mDownloadList.add(fd);
        return fd;
    }
}
