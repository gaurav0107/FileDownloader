package io.gauravdubey.FileDownloader.workers;

import io.gauravdubey.FileDownloader.controller.RestErrorHandler;
import io.gauravdubey.FileDownloader.model.DownloadFile;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpDownloadTask extends DownloadTask {

    private static final Logger logger = LoggerFactory.getLogger(HttpDownloadTask.class);

    public HttpDownloadTask(DownloadFile downloadFile) {
        super(downloadFile);
    }

    @Override
    public void run() {
        try {
            InputStream inputStream = new URL(mDownloadFile.getSource()).openStream();
            FileOutputStream fileOS = new FileOutputStream("/tmp/"+mDownloadFile.getFileName());
            long i = IOUtils.copyLarge(inputStream, fileOS);
            logger.info("size of file  downloaded: ", i);
            System.out.println("gaurav dubey:"+ i);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}