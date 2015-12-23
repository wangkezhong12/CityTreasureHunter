package com.function.buildact;

import com.function.UrlIP.UrlIP;

public class HttpUtil {
	static UrlIP ip = new UrlIP();

	public static final String CREATGAME_URL=ip.GetUtilIP()+"CityTreasureHunterServlet/servlet/CreatgameServlet";
	public static final String CREATCHECKPOINT_URL=ip.GetUtilIP()+"CityTreasureHunterServlet/servlet/CreatcheckpointServlet";

}
