package org.geoserver.wps.gs;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.geotools.process.factory.DescribeParameter;
import org.geotools.process.factory.DescribeProcess;
import org.geotools.process.factory.DescribeResult;
import org.geotools.process.gs.GSProcess;

@DescribeProcess(title = "Management-WPS", description = "Management WPS - Network-based approach (Pedestrian)")
public class Management_Pedestrian implements GSProcess {

	@DescribeResult(name = "Accessibility Score", description = "Accessibility score calculated based on Network-based approach (Pedestrian)")
	public String execute(
			@DescribeParameter(name = "StartPoint", description = "Walking start point") String start_point,
			@DescribeParameter(name = "WalkingTimePeriod", description = "Walking time period") String walking_time_period,
			@DescribeParameter(name = "WalkingSpeed", description = "Walking speed") String walking_speed,
			@DescribeParameter(name = "DistanceDecayFunction", description = "Distance Decay Function") String distance_decay_function) {
		return CallManagementService(start_point, walking_time_period,
				walking_speed, distance_decay_function);
	}

	public static String CallManagementService(String start_point,
			String walking_time_period, String walking_speed,
			String distance_decay_function) {
		URL url;
		String line;
		HttpURLConnection connection;
		StringBuilder sb = new StringBuilder();
		String url_string;

		try {

			url_string = "http://127.0.0.1:8363/management?start_point="
					+ start_point + "&walking_time_period="
					+ walking_time_period + "&walking_speed=" + walking_speed
					+ "&distance_decay_function=" + distance_decay_function;

			url = new URL(url_string);

			connection = (HttpURLConnection) url.openConnection();

			BufferedReader rd = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));

			while ((line = rd.readLine()) != null) {
				sb.append(line);
			}

			rd.close();

			connection.disconnect();

		} catch (Exception e) {
			System.out.println("Errors...");
		}

		return sb.toString();
	}
}
