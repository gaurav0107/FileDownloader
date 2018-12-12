package io.gauravdubey.FileDownloader;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

import javax.persistence.GeneratedValue;

@Entity
@Data
public class DownloadFile {
    private @Id
    @GeneratedValue
    Long id;
    private String url;
    private String fileName;


    public DownloadFile() {}

    public DownloadFile(String fileName, String url) {
        this.url = url;
        this.fileName = fileName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}

