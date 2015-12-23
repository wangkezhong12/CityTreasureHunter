package com.function.singlepractice;

import com.function.UrlIP.UrlIP;

public class HttpUtil {
	static UrlIP ip = new UrlIP();

	public static final String GETORIGIN_URL=ip.GetUtilIP()+"CityTreasureHunterServlet/servlet/GetOriginServlet";
	public static final String STARTORENDGAME_URL=ip.GetUtilIP()+"CityTreasureHunterServlet/servlet/StartOrEndGameServlet";
	public static final String GETALERTPOINT_URL=ip.GetUtilIP()+"CityTreasureHunterServlet/servlet/GetAlertPointServlet";
	public static final String GETCHECKPOINTINFO_URL=ip.GetUtilIP()+"CityTreasureHunterServlet/servlet/GetCheckPointInfoServlet";
}
