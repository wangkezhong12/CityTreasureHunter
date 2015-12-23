package com.function.personal;

import com.function.UrlIP.UrlIP;

public class HttpUtil {
	static UrlIP ip = new UrlIP();

	public static final String CREATPRO_URL=ip.GetUtilIP()+"CityTreasureHunterServlet/servlet/CreatProtectionServlet";
	public static final String PERCREATACTS_URL=ip.GetUtilIP()+"CityTreasureHunterServlet/servlet/PerCreatActsServlet";

}
