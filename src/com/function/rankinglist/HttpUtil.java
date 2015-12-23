package com.function.rankinglist;

import com.function.UrlIP.UrlIP;

public class HttpUtil {
	static UrlIP ip = new UrlIP();

	public static final String GETACTIVITY_URL=ip.GetUtilIP()+"CityTreasureHunterServlet/servlet/GetActivityServlet";
	//获得团体赛信息
	public static final String GETPERSONAL_URL=ip.GetUtilIP()+"CityTreasureHunterServlet/servlet/GetpersonalServlet";
    //获得单人赛信息
	public static final String GETRECORD_URL=ip.GetUtilIP()+"CityTreasureHunterServlet/servlet/GetrecordServlet";
	public static final String IFONLINE_URL=ip.GetUtilIP()+"CityTreasureHunterServlet/servlet/IfOnlineServlet";
}
