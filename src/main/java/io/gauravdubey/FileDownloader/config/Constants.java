package io.gauravdubey.FileDownloader.config;

public class Constants {

    public static final String DEFAULT_OUTPUT_FOLDER = "/tmp";
    public static final int DEFAULT_NUM_CONN_PER_DOWNLOAD = 8;

    public static final String STATES[] = {"Pending", "Downloading",
            "Paused", "Complete", "Cancelled", "Failed"};

    public static final String PROTOCOLS[] = {"http", "ftp", "sftp"};


    public static final int PENDING = 0;
    public static final int DOWNLOADING = 1;
    public static final int PAUSED = 2;
    public static final int COMPLETED = 3;
    public static final int CANCELLED = 4;
    public static final int FAILED = 5;



    public static final String HTTP = "http";
    public static final String FTP = "ftp";
    public static final String SFTP = "sftp";


    // Contants for block and buffer size
    protected static final int BLOCK_SIZE = 4096;
    protected static final int BUFFER_SIZE = 4096;
    protected static final int MIN_DOWNLOAD_SIZE = BLOCK_SIZE * 100;
}
