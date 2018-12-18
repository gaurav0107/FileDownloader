package io.gauravdubey.FileDownloader.workers;

import io.gauravdubey.FileDownloader.Utils;
import io.gauravdubey.FileDownloader.config.Constants;
import io.gauravdubey.FileDownloader.model.DownloadFile;
import io.gauravdubey.FileDownloader.service.DownloadFileService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;


@Component
@Scope("prototype")
public class HttpDownloadTask extends DownloadTask {

    private static final Logger logger = LoggerFactory.getLogger(HttpDownloadTask.class);

    public HttpDownloadTask(DownloadFile downloadFile) {
        super(downloadFile);
    }



    @Override
    public void run() {
        initDownload();
        try {
            InputStream inputStream = new URL(mDownloadFile.getUrl()).openStream();
            FileOutputStream fileOS = new FileOutputStream(Utils.getTempDownloadLocation(mDownloadFile.getFileName()));
            long i = IOUtils.copyLarge(inputStream, fileOS);
            mDownloadFile.setFileSize(i);
            downloadSuccess();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            downloadFailed(e.getMessage());
        } catch (MalformedURLException e) {
            e.printStackTrace();
            downloadFailed(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            downloadFailed(e.getMessage());
        }
        updateDatabase();
    }
}
