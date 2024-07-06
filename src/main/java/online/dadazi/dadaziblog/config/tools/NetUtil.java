package online.dadazi.dadaziblog.config.tools;

/**
 * @author lqk
 */
public class NetUtil {
    public static final String HTTP_HEAD = "http://";
    public static final String HTTPS_HEAD = "https://";

    public static boolean isHttpUrl(String url) {
        return url.startsWith(HTTP_HEAD) || url.startsWith(HTTPS_HEAD);
    }


}
