package io.gauravdubey.FileDownloader.model;


import java.util.Date;

public class DownloadFileResposne {

    private Long id;
    private String souruce;
    private Date requestTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSource() {
        return souruce;
    }

    public void setSource(String souruce) {
        this.souruce = souruce;
    }

    public Date getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(Date requestTime) {
        this.requestTime = requestTime;
    }
}
