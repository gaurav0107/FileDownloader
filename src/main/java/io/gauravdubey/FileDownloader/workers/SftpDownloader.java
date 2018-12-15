package io.gauravdubey.FileDownloader.workers;

import io.gauravdubey.FileDownloader.model.DownloadFile;

public class SftpDownloader extends Downloader {

    protected SftpDownloader(DownloadFile downloadFile) {
        super(downloadFile);
    }

    @Override
    public void run() {

    }
}
