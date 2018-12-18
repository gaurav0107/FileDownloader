package io.gauravdubey.FileDownloader.model;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class DownloadResponse {

    private UUID requestId;
    private Set<DownloadFile> downloadFiles;
    private Date requestTime;

    public UUID getRequestId() {
        return requestId;
    }

    public void setRequestId(UUID requestId) {
        this.requestId = requestId;
    }

    public void setDownloadFiles(Set<DownloadFile> downloadFiles) {
        this.downloadFiles = downloadFiles;
    }

    public void setRequestTime(Date requestTime) {
        this.requestTime = requestTime;
    }

    public Set<DownloadFile> getDownloadFiles() {
        return downloadFiles;
    }

    public Date getRequestTime() {
        return requestTime;
    }
}
