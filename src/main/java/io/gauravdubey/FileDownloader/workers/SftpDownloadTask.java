package io.gauravdubey.FileDownloader.workers;

import com.jcraft.jsch.*;
import io.gauravdubey.FileDownloader.model.DownloadFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.net.URISyntaxException;


public class SftpDownloadTask extends DownloadTask  {

    private static final Logger logger = LoggerFactory.getLogger(SftpDownloadTask.class);

    public SftpDownloadTask(DownloadFile downloadFile) {
        super(downloadFile);
    }

    private void downloadFile(){
        JSch jsch = new JSch();
        Session session;
        try {
            session = jsch.getSession(getUserName(), Utils.getHost(logger, mDownloadFile.getSource()), 22);
            session.setConfig("StrictHostKeyChecking", "no");
            session.setPassword(getPassword());
            session.connect();
            Channel channel = session.openChannel("sftp");
            channel.connect();
            ChannelSftp sftpChannel = (ChannelSftp) channel;
            sftpChannel.get(Utils.getPath(logger, mDownloadFile.getSource()), mDownloadFile.getDestination());
            sftpChannel.exit();
            session.disconnect();
            logger.info("Download Complete");
        } catch (JSchException e) {
            e.printStackTrace();
            logger.info("Download Failed");
        } catch (SftpException e) {
            e.printStackTrace();
            logger.info("Download Failed");
        } catch (URISyntaxException e) {
            logger.info("Download Failed due to invalid Url");
            e.printStackTrace();
        }
    }


    public String getUserName(){
        return "demo";
    }

    public String getPassword(){
        return "password";
    }

    @Override
    public void run() {
        downloadFile();
    }

}
