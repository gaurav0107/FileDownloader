package io.gauravdubey.FileDownloader.workers;

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

    private static final Logger logger = LoggerFactory.getLogger(HttpDownloadTask.class);

    protected FtpDownloadTask(DownloadFile downloadFile){
        super(downloadFile);
    }



    public String getUserName(){
        return "demo-user";
    }

    public String getPassword(){
        return "demo-user";
    }

    private FTPClient createConnection(){
        try {
            ftp = new FTPClient();
            ftp.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
            int reply;
            ftp.connect(Utils.getHost(logger, mDownloadFile.getSource()));
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                throw new Exception("Exception in connecting to FTP Server");
            }
            ftp.login(getUserName(), getPassword());
            ftp.setFileType(FTP.BINARY_FILE_TYPE);
            ftp.enterLocalPassiveMode();
            return ftp;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public void downloadFile() {
        createConnection();
        try (FileOutputStream fos = new FileOutputStream(mDownloadFile.getDestination())) {
            this.ftp.retrieveFile(Utils.getPath(logger, mDownloadFile.getSource()), fos);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
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
        downloadFile();
    }
}
