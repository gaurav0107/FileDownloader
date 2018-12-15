package io.gauravdubey.FileDownloader.workers;

import io.gauravdubey.FileDownloader.config.Constants;
import io.gauravdubey.FileDownloader.model.DownloadFile;
import java.util.ArrayList;
import java.util.Observable;


public abstract class Downloader extends Observable implements Runnable{

    protected DownloadFile mDownloadFile;

    /** downloaded size of the file (in bytes) */
    protected int mDownloaded;


    /** List of download threads */
    protected ArrayList<DownloadThread> mListDownloadThread;

    protected Downloader(DownloadFile downloadFile){
        this.mDownloadFile = downloadFile;
        this.mDownloaded = 0;
        mListDownloadThread = new ArrayList<DownloadThread>();
    }

    protected void download() {
        Thread t = new Thread(this);
        t.start();
    }

    /**
     * Increase the downloaded size
     */
    protected synchronized void downloaded(int value) {
        mDownloaded += value;
        stateChanged();
    }

    public void pause() {
        setState(Constants.PAUSED);
    }


    public void cancel() {
        setState(Constants.CANCELLED);
    }


    public String getURL() {
        return mDownloadFile.getSource();
    }

    public int getFileSize() {
        return mDownloadFile.getFileSize();
    }


    public float getProgress() {
        return ((float)mDownloaded / mDownloadFile.getFileSize()) * 100;
    }

    public int getState() {
        return mDownloadFile.getState();
    }


    /**
     * Set the state has changed and notify the observers
     */
    protected void stateChanged() {
        setChanged();
        notifyObservers();
    }


    protected void setState(int value) {
        mDownloadFile.setState(value);
        stateChanged();
    }

    /**
     * Thread to download part of a file
     */
    protected abstract class DownloadThread implements Runnable {
        protected int mThreadID;
        protected DownloadFile mDownloadFile;
        protected int mStartByte;
        protected int mEndByte;
        protected boolean mIsFinished;
        protected Thread mThread;

        public DownloadThread(int threadID, DownloadFile downloadFile, int startByte, int endByte) {
            mThreadID = threadID;
            mDownloadFile = downloadFile;
            mStartByte = startByte;
            mEndByte = endByte;
            mIsFinished = false;
            download();
        }

        /**
         * Get whether the thread is finished download the part of file
         */
        public boolean isFinished() {
            return mIsFinished;
        }

        /**
         * Start or resume the download
         */
        public void download() {
            mThread = new Thread(this);
            mThread.start();
        }

        /**
         * Waiting for the thread to finish
         * @throws InterruptedException
         */
        public void waitFinish() throws InterruptedException {
            mThread.join();
        }

    }

}
