package io.gauravdubey.FileDownloader.workers;

import io.gauravdubey.FileDownloader.Utils;
import io.gauravdubey.FileDownloader.exceptions.UnableToCreateFtpConnection;
import io.gauravdubey.FileDownloader.model.DownloadFile;
import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;


public class FtpDownloadTask extends DownloadTask  {
    protected FTPClient ftp;

    private static final Logger logger = LoggerFactory.getLogger(FtpDownloadTask.class);

    protected FtpDownloadTask(DownloadFile downloadFile){
        super(downloadFile);
    }

    private void createConnection() throws URISyntaxException, IOException, UnableToCreateFtpConnection {
        ftp = new FTPClient();
        ftp.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
        int reply;
        ftp.connect(Utils.getHost(logger, mDownloadFile.getUrl()));
        reply = ftp.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            ftp.disconnect();
            throw new UnableToCreateFtpConnection("Exception in connecting to FTP Server");
        }
        ftp.login(Utils.getQuery(mDownloadFile.getSource(), "user"),
                Utils.getQuery(mDownloadFile.getSource(), "pass"));
        ftp.setFileType(FTP.BINARY_FILE_TYPE);
        ftp.enterLocalPassiveMode();
    }

    public void downloadFile() {
        try {
            createConnection();
            FileOutputStream fos = new FileOutputStream(mDownloadFile.getDestination());
            if(this.ftp.retrieveFile(Utils.getPath(logger, mDownloadFile.getUrl()), fos)){
                downloadSuccess();
            }else {
                downloadFailed("");
            }

        } catch (IOException e) {
            e.printStackTrace();
            downloadFailed(e.getMessage());
        } catch (URISyntaxException e) {
            e.printStackTrace();
            downloadFailed(e.getMessage());
        } catch (UnableToCreateFtpConnection e) {
            e.printStackTrace();
            downloadFailed(e.getMessage());
        } finally {
            disconnect();
        }
    }

    public void disconnect() {
        if (this.ftp.isConnected()) {
            try {
                this.ftp.logout();
                this.ftp.disconnect();
            } catch (IOException f) {
                f.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        initDownload();
        downloadFile();
    }
}
