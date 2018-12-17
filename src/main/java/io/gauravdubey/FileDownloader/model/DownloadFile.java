package io.gauravdubey.FileDownloader.model;

import io.gauravdubey.FileDownloader.config.Constants;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.util.Date;



@Entity
@Table(name = "download_file")
public class DownloadFile extends AbstractPersistable<Long> {

    private Date requestTime;
    private String source;
    private int state;
    private String fileName;
    private String protocol;

    private Date creationTime;
    private Date modificationTime;
    private long fileSize;
    private String destination;

    public DownloadFile(){

    }

    public DownloadFile(String source){
        this.source = source;
        this.requestTime = new Date();
        this.state = Constants.PENDING;
        this.fileName = source.substring(source.lastIndexOf('/') + 1);
        this.fileSize = -1;
        this.protocol = source.substring(0, source.indexOf(':'));
        this.destination = "/tmp/"+ this.fileName;
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
}



