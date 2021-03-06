package io.gauravdubey.FileDownloader.workers;

import com.jcraft.jsch.*;
import io.gauravdubey.FileDownloader.Utils;
import io.gauravdubey.FileDownloader.model.DownloadFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.net.URISyntaxException;


@Component
@Scope("prototype")
public class SftpDownloadTask extends DownloadTask  {

    private static final Logger logger = LoggerFactory.getLogger(SftpDownloadTask.class);

    public SftpDownloadTask(DownloadFile downloadFile) {
        super(downloadFile);
    }

    private void downloadFile(){

        JSch jsch = new JSch();
        Session session;
        try {
            session = jsch.getSession(Utils.getQuery(mDownloadFile.getSource(), "user"),
                    Utils.getHost(logger, mDownloadFile.getUrl()), 22);
            session.setConfig("StrictHostKeyChecking", "no");
            session.setPassword(Utils.getQuery(mDownloadFile.getSource(), "pass"));
            session.connect();
            Channel channel = session.openChannel("sftp");
            channel.connect();
            ChannelSftp sftpChannel = (ChannelSftp) channel;
            sftpChannel.get(Utils.getPath(logger, mDownloadFile.getUrl()),
                    Utils.getTempDownloadLocation(mDownloadFile.getFileName()));
            sftpChannel.exit();
            session.disconnect();
            downloadSuccess();
        } catch (JSchException e) {
            e.printStackTrace();
            downloadFailed(e.getMessage());
        } catch (SftpException e) {
            e.printStackTrace();
            downloadFailed(e.getMessage());
        } catch (URISyntaxException e) {
            e.printStackTrace();
            downloadFailed(e.getMessage());
        }
    }

    @Override
    public void run() {
        initDownload();
        downloadFile();
    }

}
