package com.function.team;

import com.function.UrlIP.UrlIP;

public class HttpUtil {
	static UrlIP ip = new UrlIP();

	public static final String GETALERTPOINT_URL=ip.GetUtilIP()+"CityTreasureHunterServlet/servlet/GetAlertPointServlet";
	public static final String GETTEAMMEM_URL=ip.GetUtilIP()+"CityTreasureHunterServlet/servlet/GetteamServlet";
	public static final String USERSTRAIL_URL=ip.GetUtilIP()+"CityTreasureHunterServlet/servlet/UsersTrailServlet";
	public static final String GETUSERSTRAIL_URL=ip.GetUtilIP()+"CityTreasureHunterServlet/servlet/GetUsersTrailServlet";
	public static final String POINTSIGN_URL=ip.GetUtilIP()+"CityTreasureHunterServlet/servlet/PointSignServlet";
	public static final String GETPOINTSIGN_URL=ip.GetUtilIP()+"CityTreasureHunterServlet/servlet/GetPointSignServlet";
	public static final String GETORIGIN_URL=ip.GetUtilIP()+"CityTreasureHunterServlet/servlet/GetOriginServlet";
	public static final String STARTORENDGAME_URL=ip.GetUtilIP()+"CityTreasureHunterServlet/servlet/StartOrEndGameServlet";
	public static final String GETSTARTSIGN_URL=ip.GetUtilIP()+"CityTreasureHunterServlet/servlet/GetStartSignServlet";
	public static final String GETCHECKPOINTINFO_URL=ip.GetUtilIP()+"CityTreasureHunterServlet/servlet/GetCheckPointInfoServlet";
	public static final String JOINGROUP_URL=ip.GetUtilIP()+"CityTreasureHunterServlet/servlet/JoinGroupServlet";
	public static final String EXITACTIVITY_URL=ip.GetUtilIP()+"CityTreasureHunterServlet/servlet/ExitActivityServlet";
	public static final String GETEXITORCAP_URL=ip.GetUtilIP()+"CityTreasureHunterServlet/servlet/GetExitOrCapServlet";
	public static final String SINGLEORTEAMRECORD_URL=ip.GetUtilIP()+"CityTreasureHunterServlet/servlet/SingleOrTeamRecordServlet";

}
