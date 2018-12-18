package io.gauravdubey.FileDownloader.model;

import java.util.Set;

public class DownloadRequest {
    private Set<String> downloadFiles;

    public Set<String> getDownloadFiles() {
        return downloadFiles;
    }

    public void setDownloadFiles(Set<String> downloadFiles) {
        this.downloadFiles = downloadFiles;
    }
}
