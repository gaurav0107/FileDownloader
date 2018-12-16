package io.gauravdubey.FileDownloader.workers;

import io.gauravdubey.FileDownloader.config.Constants;
import io.gauravdubey.FileDownloader.controller.RestErrorHandler;
import io.gauravdubey.FileDownloader.model.DownloadFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpDownloader extends Downloader {

    private static final Logger logger = LoggerFactory.getLogger(RestErrorHandler.class);

    protected HttpDownloader(DownloadFile downloadFile) {
        super(downloadFile);
        download();
    }

    private void error() {
        logger.error("Download Failed");
        setState(Constants.FAILED);
    }

    @Override
    public void run() {
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection)new URL(this.mDownloadFile.getSource()).openConnection();
            conn.setConnectTimeout(Constants.HTTP_CONN_TIMEOUT);
            conn.connect();

            if (conn.getResponseCode() / 100 != 2) {
                error();
            }

            int contentLength = conn.getContentLength();
            if (contentLength < 1) {
                error();
            }

            logger.info("cLength"+contentLength);

            if (mDownloadFile.getFileSize() == -1) {
                mDownloadFile.setFileSize(contentLength);
                stateChanged();
                logger.info("File size: " + mDownloadFile.getFileSize());
            }

            /** If Download state is Pending start download **/

            if(mDownloadFile.getState() == Constants.PENDING){
                setState(Constants.DOWNLOADING);
                if(mListDownloadThread.size() == 0){
                    if (mDownloadFile.getFileSize() > Constants.MIN_DOWNLOAD_SIZE) {
                        int partSize = Math.round(((float)mDownloadFile.getFileSize() /
                                Constants.DEFAULT_NUM_CONN_PER_DOWNLOAD) / Constants.BLOCK_SIZE) * Constants.BLOCK_SIZE;
                        logger.info("Part Size: " + partSize);

                        int startByte = 0;
                        int endByte = partSize - 1;

                        HttpDownloadThread aThread = new HttpDownloadThread(1, mDownloadFile,
                                startByte, endByte);
                        mListDownloadThread.add(aThread);
                        int i = 2;
                        while (endByte < mDownloadFile.getFileSize()) {
                            startByte = endByte + 1;
                            endByte += partSize;
                            aThread = new HttpDownloadThread(i, mDownloadFile, startByte, endByte);
                            mListDownloadThread.add(aThread);
                            ++i;
                        }
                    }else {
                        HttpDownloadThread aThread = new HttpDownloadThread(1, mDownloadFile,
                                0, mDownloadFile.getFileSize());
                        mListDownloadThread.add(aThread);
                    }
                }else {
                    for (int i=0; i<mListDownloadThread.size(); ++i) {
                        if (!mListDownloadThread.get(i).isFinished())
                            mListDownloadThread.get(i).download();
                    }
                }
                // waiting for all threads to complete
                for (int i=0; i<mListDownloadThread.size(); ++i) {
                    mListDownloadThread.get(i).waitFinish();
                }

                // check the current state again
                if (mDownloadFile.getState() == Constants.DOWNLOADING) {
                    setState(Constants.COMPLETED);
                }
            }
        } catch (MalformedURLException e) {
            error();
            e.printStackTrace();
        } catch (IOException e) {
            error();
            e.printStackTrace();
        } catch (InterruptedException e) {
            error();
            e.printStackTrace();
        } finally {
            if (conn != null)
                conn.disconnect();
        }
    }


    private class HttpDownloadThread extends DownloadThread {

        /**
         * Constructor
         * @param threadID
         * @param downloadFile
         * @param startByte
         * @param endByte
         */
        public HttpDownloadThread(int threadID, DownloadFile downloadFile, long startByte, long endByte) {
            super(threadID, downloadFile, startByte, endByte);
        }

        @Override
        public void run() {
            BufferedInputStream in = null;
            RandomAccessFile raf = null;

            try {
                // open Http connection to URL
                HttpURLConnection conn = (HttpURLConnection) new URL(mDownloadFile.getSource()).openConnection();

                // set the range of byte to download
                String byteRange = mStartByte + "-" + mEndByte;
                conn.setRequestProperty("Range", "bytes=" + byteRange);
                logger.info("bytes=" + byteRange);

                // connect to server
                conn.connect();

                // Make sure the response code is in the 200 range.
                if (conn.getResponseCode() / 100 != 2) {
                    error();
                }

                // get the input stream
                in = new BufferedInputStream(conn.getInputStream());

                // open the output file and seek to the start location
                raf = new RandomAccessFile(Constants.DEFAULT_OUTPUT_FOLDER + mDownloadFile.getFileName(),
                        "rw");
                raf.seek(mStartByte);

                byte data[] = new byte[Constants.BUFFER_SIZE];
                int numRead;
                while((mDownloadFile.getState() == Constants.DOWNLOADING) &&
                        ((numRead = in.read(data,0, Constants.BUFFER_SIZE)) != -1)){

                    logger.info("current state:" +mDownloadFile.getState());
                    logger.info(""+ data.length);
                    raf.write(data,0, numRead);
                    mStartByte += numRead;
                    downloaded(numRead);
                }

                if (mDownloadFile.getState() == Constants.DOWNLOADING) {
                    mIsFinished = true;
                }
            } catch (IOException e) {
                error();
            } finally {
                if (raf != null) {
                    try {
                        raf.close();
                    } catch (IOException e) {}
                }

                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {}
                }
            }

            logger.info("End thread " + mThreadID);
        }
    }
}
