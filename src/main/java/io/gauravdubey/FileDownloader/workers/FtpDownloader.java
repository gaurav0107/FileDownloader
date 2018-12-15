package io.gauravdubey.FileDownloader.workers;

import io.gauravdubey.FileDownloader.model.DownloadFile;

public class FtpDownloader extends Downloader  {

    protected FtpDownloader(DownloadFile downloadFile) {
        super(downloadFile);
    }

    @Override
    public void run() {

    }
}
