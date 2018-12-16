package io.gauravdubey.FileDownloader.workers;

import io.gauravdubey.FileDownloader.model.DownloadFile;

public abstract class DownloadTask implements Runnable {

    protected DownloadFile mDownloadFile;

    public DownloadTask(DownloadFile downloadFile) {
        this.mDownloadFile = downloadFile;
    }
}
