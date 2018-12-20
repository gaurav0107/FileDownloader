package io.gauravdubey.FileDownloader.config;

public class Constants {

    public static final int DEFAULT_CORE_POOL_SIZE = 4;
    public static final int DEFAULT_MAX_POOL_SIZE = 8;

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
    public static final String HTTPS = "https";
    public static final String FTP = "ftp";
    public static final String SFTP = "sftp";



    public static final int HTTP_CONN_TIMEOUT = 10000;
    public static final String DEFAULT_TEMP_LOCATION = "/tmp/";
    public static final String DEFAULT_STORAGE_LOCATION = "storage";
}
