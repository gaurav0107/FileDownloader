package io.gauravdubey.FileDownloader.workers;

import io.gauravdubey.FileDownloader.model.DownloadFile;

public class HttpDownloader extends Downloader {

    protected HttpDownloader(DownloadFile downloadFile) {
        super(downloadFile);
    }

    @Override
    public void run() {

    }
}
