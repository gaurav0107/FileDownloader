package io.gauravdubey.FileDownloader.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.gauravdubey.FileDownloader.config.Config;
import org.hibernate.annotations.GenericGenerator;


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
    private String url;
    private String source;
    private String state;
    private String fileName;
    private String protocol;
    private Date creationTime;
    private Date modificationTime;
    private long fileSize;
    private String destination;
    private String errorMessage;
    private String params;
    private long downloadStartTime;
    private long downloadEndTime;
    private float downloadSpeed;

    @ManyToOne @JoinColumn(name = "request_id")
    @JsonBackReference
    private DownloadRequestLog downloadRequestLog;

    public DownloadFile() {
    }

    public DownloadFile(String source) {
        this.source = source;
        if(source.contains("?")){
            this.params = source.substring(source.indexOf("?")+1);
            this.url = source.substring(0, source.indexOf("?"));
        }else {
            this.params = "";
            this.url = source;
        }
        this.requestTime = new Date();
        this.state = Config.STATES[Config.PENDING];
        this.fileName = source.substring(source.lastIndexOf('/') + 1,
                source.contains("?")?source.indexOf("?"):source.length());
        this.fileSize = -1;
        this.protocol = source.substring(0, source.indexOf(':'));
        this.destination = Config.DEFAULT_STORAGE_LOCATION + "/" + this.fileName;
        this.errorMessage = "";
        this.downloadStartTime =0;
        this.downloadEndTime = 0;
        this.downloadSpeed = (float) 0.0;
    }

    public DownloadFile(DownloadRequestLog downloadRequestLog, String source){
        this(source);
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

    public String getState() {
        return state;
    }

    public String getFileName() {
        return fileName;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setState(String state) {
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

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getParams() {
        return params;
    }

    public String getUrl() {
        return url;
    }

    public DownloadRequestLog getDownloadRequestLog() {
        return downloadRequestLog;
    }

    public void setDownloadFileId(UUID downloadFileId) {
        this.downloadFileId = downloadFileId;
    }

    public long getDownloadStartTime() {
        return downloadStartTime;
    }

    public void setDownloadStartTime(long downloadStartTime) {
        this.downloadStartTime = downloadStartTime;
    }

    public long getDownloadEndTime() {
        return downloadEndTime;
    }

    public void setDownloadEndTime(long downloadEndTime) {
        this.downloadEndTime = downloadEndTime;
    }

    public float getDownloadSpeed() {
        return downloadSpeed;
    }

    public void setDownloadSpeed(float downloadSpeed) {
        this.downloadSpeed = downloadSpeed;
    }


    public void setDownloadRequestLog(DownloadRequestLog downloadRequestLog) {
        this.downloadRequestLog = downloadRequestLog;
    }
}



