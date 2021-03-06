package io.gauravdubey.FileDownloader.model;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "download_request_log")
public class DownloadRequestLog {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "request_id", columnDefinition = "uuid")
    private UUID requestId;
    private Date requestTime;
    private int totalFiles;



    @OneToMany(mappedBy = "downloadRequestLog", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<DownloadFile> downloadFiles;

    public DownloadRequestLog() {
        requestTime = new Date();

    }

    public Date getRequestTime() {
        return requestTime;
    }

    public Set<DownloadFile> getDownloadFiles() {
        return downloadFiles;
    }


    public void setDownloadFiles(Set<DownloadFile> downloadFiles) {
        this.downloadFiles = downloadFiles;
        this.totalFiles = downloadFiles.size();
    }

    public UUID getRequestId() {
        return requestId;
    }

    public void setRequestId(UUID requestId) {
        this.requestId = requestId;
    }
}

