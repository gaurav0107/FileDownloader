package io.gauravdubey.FileDownloader.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;



@Entity
@Table(name = "download_file")
public class DownloadFile extends AbstractPersistable<Long> {

    private Date requestTime;

    private String source;

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

    //
//
//    @Column(name = "creation_time", nullable = false)
//    private Date creationTime;
//
//    @Column(name = "modification_time", nullable = false)
//    private Date modificationTime;
//
//
//    public DownloadFile() {}
//
//    public DownloadFile(String fileName, String source, Date requestTime) {
//        this.source = source;
//        this.fileName = fileName;
//        this.requestTime = requestTime;
//    }
//
//    public Long  getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//
//    public String getFileName() {
//        return fileName;
//    }
//
//    public void setFileName(String fileName) {
//        this.fileName = fileName;
//    }
//
//    public Date getRequestTime() {
//        return requestTime;
//    }
//
//    public void setRequestTime(Date requestTime) {
//        this.requestTime = requestTime;
//    }
//
//    public String getSource() {
//        return source;
//    }
//
//    public void setSource(String source) {
//        this.source = source;
//    }
//
//    public String getDestination() {
//        return destination;
//    }
//
//    public void setDestination(String destination) {
//        this.destination = destination;
//    }
//
//    public String getDownloadStartTime() {
//        return downloadStartTime;
//    }
//
//    public void setDownloadStartTime(String downloadStartTime) {
//        this.downloadStartTime = downloadStartTime;
//    }
//
//    public String getDownloadEndTime() {
//        return downloadEndTime;
//    }
//
//    public void setDownloadEndTime(String downloadEndTime) {
//        this.downloadEndTime = downloadEndTime;
//    }
//
//    public String getProtocol() {
//        return protocol;
//    }
//
//    public void setProtocol(String protocol) {
//        this.protocol = protocol;
//    }
//
//    public String getFileSize() {
//        return fileSize;
//    }
//
//    public void setFileSize(String fileSize) {
//        this.fileSize = fileSize;
//    }
//
//    public String getDownloadSpeed() {
//        return downloadSpeed;
//    }
//
//    public void setDownloadSpeed(String downloadSpeed) {
//        this.downloadSpeed = downloadSpeed;
//    }
//
//
//    @PrePersist
//    public void prePersist() {
//        Date now = new Date();
//        creationTime = now;
//        modificationTime = now;
//    }
//
//    @PreUpdate
//    public void preUpdate() {
//        modificationTime = new Date();
//    }
//
//
//
//    public static class Builder {
//
//        private DownloadFile built;
//
//        public Builder(String source) {
//            built = new DownloadFile();
//            built.source = source;
//        }
//
//        public DownloadFile build() {
//            return built;
//        }
//
//    }
}

