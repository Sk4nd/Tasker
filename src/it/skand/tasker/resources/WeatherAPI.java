package it.skand.tasker.resources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

public class WeatherAPI extends RestClient {
	private static final String GET_WEATHER = "http://api.openweathermap.org/data/2.1/find/city?lat=";
	
	/* API calls */
	public static String getWeather(double lat, double lon) {
		
		InputStream is = null;
		
		try {
			is = new URL(WeatherAPI.GET_WEATHER + lat + "&lon=" + lon + "&cnt=1").openStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			String jsonText = readAll(rd);
			is.close();
			return jsonText;
			
		} catch(IOException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	private static String readAll(Reader rd) throws IOException {
			StringBuilder sb = new StringBuilder();
			int cp;
			while ((cp = rd.read())!= -1) {
				sb.append((char) cp);
			}
			return sb.toString();
	}
}
