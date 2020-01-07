package ga.freenas.newclient.util;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class NetworkUtil {
	public static InputStream getResource(String urlPath) throws IOException {
		URL url = new URL(urlPath);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		int responseCode = conn.getResponseCode();
		if (responseCode==200){
			return conn.getInputStream();
		}
		return null;
	}
}
