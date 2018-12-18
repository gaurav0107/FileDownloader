package io.gauravdubey.FileDownloader;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import io.gauravdubey.FileDownloader.config.Constants;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;


public class Utils {

    public static String getPath(Logger logger, String source) throws URISyntaxException {
        URI url = new URI(source);
        logger.info("host path is: " + url.getPath());
        return url.getPath();
    }

    public static String getHost(Logger logger, String source) throws URISyntaxException {
        URI url = new URI(source);
        return url.getHost();
    }

    public static String getProtocol(Logger logger, String source) throws MalformedURLException {
        URL url = new URL(source);
        return url.getProtocol();
    }


    public static String getQuery(String source, String key){
        try{
            MultiValueMap<String, String> parameters =
                    UriComponentsBuilder.fromUriString(source).build().getQueryParams();
            return String.join(",", parameters.get(key));
        }catch (Exception e){
            return "";
        }

    }

    public static String getTempDownloadLocation(String fileName){
        return Constants.DEFAULT_TEMP_LOCATION + fileName;
    }

    public static Boolean copyFileToPermanentLocation(String sourceFile, String fileName){
        File directory = new File(Constants.DEFAULT_STORAGE_LOCATION);
        if (! directory.exists()){
            directory.mkdir();
        }
        try {
            Files.copy(Paths.get(sourceFile), Paths.get(Constants.DEFAULT_STORAGE_LOCATION + "/" + fileName),
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}


