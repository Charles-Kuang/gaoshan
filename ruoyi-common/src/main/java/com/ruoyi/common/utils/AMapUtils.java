package com.ruoyi.common.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class AMapUtils {


    // 高德地图key
    public static final String KEY = "6dc239658cd94e2007b4f3c933106693";
    private static Pattern pattern = Pattern.compile("\"location\":\"(\\d+\\.\\d+),(\\d+\\.\\d+)\"");

    /**
     * 通过具体位置，获取对应地图上的坐标: 经度、纬度
     *
     * @param address
     * @return
     */
    public static double[] addressToGPS(String address) {
        try {
            String url = String .format("http://restapi.amap.com/v3/geocode/geo?&s=rsv3&address=%s&key=%s", address, KEY);
            URL myURL = null;
            URLConnection httpsConn = null;
            try {
                myURL = new URL(url);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            InputStreamReader insr = null;
            BufferedReader br = null;
            httpsConn = (URLConnection) myURL.openConnection();// 不使用代理
            if (httpsConn != null) {
                insr = new InputStreamReader( httpsConn.getInputStream(), "UTF-8");
                br = new BufferedReader(insr);
                String data = "";
                String line = null;
                while((line= br.readLine())!=null){
                    data+=line;
                }
                Matcher matcher = pattern.matcher(data);
                if (matcher.find() && matcher.groupCount() == 2) {
                    double[] gps = new double[2];
                    gps[0] = Double.valueOf(matcher.group(1));
                    gps[1] = Double.valueOf(matcher.group(2));
                    return gps;
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }
}