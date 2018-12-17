package io.gauravdubey.FileDownloader.workers;

import java.net.URI;
import java.net.URISyntaxException;
import org.slf4j.Logger;

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
}
