package com.function.joinact;

import com.function.UrlIP.UrlIP;

public class HttpUtil {
	static UrlIP ip = new UrlIP();

	public static final String GETOFFLINEDPERSONAL_URL=ip.GetUtilIP()+"CityTreasureHunterServlet/servlet/GetOfflinedPersonalServlet";
	public static final String GETPRACTACTINFO_URL=ip.GetUtilIP()+"CityTreasureHunterServlet/servlet/GetPractActInfoServlet";
	public static final String GETACTTEAM_URL=ip.GetUtilIP()+"CityTreasureHunterServlet/servlet/GetActTeamServlet";
	public static final String GETTEAMMEMBERINFO_URL=ip.GetUtilIP()+"CityTreasureHunterServlet/servlet/GetTeamMemberInfoServlet";
	public static final String JOINGROUP_URL=ip.GetUtilIP()+"CityTreasureHunterServlet/servlet/JoinGroupServlet";
	public static final String JOINDOUBLE_URL=ip.GetUtilIP()+"CityTreasureHunterServlet/servlet/JoinDoubleServlet";
	public static final String GETTEAMID_URL=ip.GetUtilIP()+"CityTreasureHunterServlet/servlet/GetTeamInfoIdServlet";
	public static final String GETTEAMDESCRIBE_URL=ip.GetUtilIP()+"CityTreasureHunterServlet/servlet/GetTeamDescribeServlet";
	public static final String GETTEAMTOTAL_URL=ip.GetUtilIP()+"CityTreasureHunterServlet/servlet/GetTeamTotalServlet";
}
