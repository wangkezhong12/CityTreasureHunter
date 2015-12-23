package com.function.doublefinder;

import com.function.UrlIP.UrlIP;

public class HttpUtil {
	static UrlIP ip = new UrlIP();

	public static final String JOINDOUBLE_URL=ip.GetUtilIP()+"CityTreasureHunterServlet/servlet/JoinDoubleServlet";
	public static final String GETDOUBLEINFO_URL=ip.GetUtilIP()+"CityTreasureHunterServlet/servlet/GetDoubleInfoServlet";
	public static final String GETSTARTDOUBLESIGN_URL=ip.GetUtilIP()+"CityTreasureHunterServlet/servlet/GetStartDoubleSignServlet";
	public static final String GETDOUBLEEXIT_URL=ip.GetUtilIP()+"CityTreasureHunterServlet/servlet/GetDoubleExitServlet";
	public static final String STARTORENDDOUBLEGAME_URL=ip.GetUtilIP()+"CityTreasureHunterServlet/servlet/StartOrEndDoubleGameServlet";
	public static final String USEPRIVILEGES_URL=ip.GetUtilIP()+"CityTreasureHunterServlet/servlet/UsePrivilegesServlet";
	public static final String GETUSEDPRIVILEGES_URL=ip.GetUtilIP()+"CityTreasureHunterServlet/servlet/GetUsedPrivilegesServlet";
	public static final String DOUBLETRAIL_URL=ip.GetUtilIP()+"CityTreasureHunterServlet/servlet/DoubleTrailServlet";
	public static final String GETDOUBLERECORD_URL=ip.GetUtilIP()+"CityTreasureHunterServlet/servlet/DoubleGameRecordServlet";
}
