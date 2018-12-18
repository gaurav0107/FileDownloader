package io.gauravdubey.FileDownloader.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.gauravdubey.FileDownloader.config.Constants;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;


@Entity
@Table(name = "download_file")
public class DownloadFile{

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", columnDefinition = "uuid")
    private UUID downloadFileId;

    private Date requestTime;
    private String source;
    private int state;
    private String fileName;
    private String protocol;
    private Date creationTime;
    private Date modificationTime;
    private long fileSize;
    private String destination;

    @ManyToOne @JoinColumn(name = "request_id")
    @JsonBackReference
    private DownloadRequestLog downloadRequestLog;


    public DownloadFile() {
    }

    public DownloadFile(DownloadRequestLog downloadRequestLog, String source){
        this.source = source;
        this.requestTime = new Date();
        this.state = Constants.PENDING;
        this.fileName = source.substring(source.lastIndexOf('/') + 1);
        this.fileSize = -1;
        this.protocol = source.substring(0, source.indexOf(':'));
        this.destination = "/tmp/"+ this.fileName;
        this.downloadRequestLog = downloadRequestLog;
    }

    public Date getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(Date requestTime) {
        this.requestTime = requestTime;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @PrePersist
    public void prePersist() {
        Date now = new Date();
        creationTime = now;
        modificationTime = now;
    }
    @PreUpdate
    public void preUpdate() {
        modificationTime = new Date();
    }

    public int getState() {
        return state;
    }

    public String getFileName() {
        return fileName;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getProtocol() {
        return protocol;
    }

    public String getDestination() {
        return destination;
    }


    public UUID getDownloadFileId() {
        return downloadFileId;
    }

}



