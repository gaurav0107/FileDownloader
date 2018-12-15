package io.gauravdubey.FileDownloader.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class DownloadFileRequest {

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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
}
