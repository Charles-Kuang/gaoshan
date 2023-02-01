package com.gaoshan.modules.wechat.init;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gaoshan.modules.wechat.util.cache.CacheServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public class WechatMsg {
    private static String active;
    private static String APPID;
    /**
     *  密钥
     */
    private static String SECRET;
    @Value("${spring.profiles.active}")
    public void setActive(String active) {
        WechatMsg.active = active;
    }
    @Value("${wx.appid}")
    public void setAPPID(String APPID) {
        WechatMsg.APPID = APPID;
    }
    @Value("${wx.secret}")
    public void setSECRET(String SECRET) {
        WechatMsg.SECRET = SECRET;
    }
    private static String ACCESS_TOKEN = "";
    private static String code2Session_PATH="https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code";

    public static void wxLoginCode(String code){
        if("local".equals(active)){
            System.err.println("本地不开启微信token授权");
            return;
        }
        code2Session_PATH=code2Session_PATH.replaceAll("APPID",APPID).replaceAll("SECRET",SECRET).replaceAll("JSCODE","JSCODE");
        String tokenJsonStr = doGetPost(code2Session_PATH, "GET", null);
        JSONObject json = JSONObject.parseObject(tokenJsonStr);
        if(0==json.getInteger("errcode")){
//            属性	类型	说明
//            openid	string	用户唯一标识
//            session_key	string	会话密钥
//            unionid	string	用户在开放平台的唯一标识符，若当前小程序已绑定到微信开放平台帐号下会返回，详见 UnionID 机制说明。
        }else{
            //失败 errmsg
        }
    }

    /**
     * 获取token
     *
     * @return token
     */
	@Scheduled(fixedDelay = 7100 * 1000L)
    public static String getToken() throws InterruptedException {
	    try{
//            if("local".equals(active)){
//                System.err.println("本地不开启微信token授权");
//                return "";
//            }
            System.err.println("执行寻找TOKEN");
            CacheServiceImpl cache = new CacheServiceImpl();
            if (cache.isContains("ACCESS_TOKEN")) {
                // 判断是否在缓存中
                if (cache.isTimeOut("ACCESS_TOKEN")) {
                    cache.clearByKey("ACCESS_TOKEN");
                    ACCESS_TOKEN="";
                    return setToken();
                }else{
                    return cache.getCacheDataByKey("ACCESS_TOKEN",String.class);
                }
            } else {
                return setToken();
            }
        }catch (Exception e){
	        long s=6000;
	        Thread.sleep(s);
            return getToken();
        }
    }

    public static String setToken() {
//        if("local".equals(active)){
//            System.err.println("本地不开启微信token授权");
//            return "";
//        }
        CacheServiceImpl cache = new CacheServiceImpl();
        // 授予形式
        String grant_type = "client_credential";
        // 接口地址拼接参数
        System.err.println(APPID);
        String getTokenApi = "https://api.weixin.qq.com/cgi-bin/token?grant_type=" + grant_type + "&appid=" + APPID
                + "&secret=" + SECRET;
        String tokenJsonStr = doGetPost(getTokenApi, "GET", null);
        JSONObject tokenJson = JSONObject.parseObject(tokenJsonStr);
        String token = tokenJson.getString("access_token");
        cache.putCache("ACCESS_TOKEN", token, tokenJson.getInteger("expires_in") * 1000L);
        ACCESS_TOKEN=token;
        System.err.println("微信ACCESS_TOKEN==="+ACCESS_TOKEN);
        return token;
    }
    public static JSONObject getPhoneNumber(String code) {
        // 获取token
        try {
            //获取phone
            String url = "https://api.weixin.qq.com/wxa/business/getuserphonenumber"
                    + "?access_token=" + ACCESS_TOKEN;
            Map paramMap=new HashMap();
            paramMap.put("code", code);
            String resultStr=doGetPost(url, "POST", paramMap);
            JSONObject resultJson = JSON.parseObject(resultStr);
            if(resultJson.getInteger("errcode")==42001||resultJson.getInteger("errcode")==40001){
                ACCESS_TOKEN=setToken();
                return getPhoneNumber(code);
            }
            return resultJson;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
/***************************************************************************************************/

    /**
     * 推送临时素材消息
     * @param cover 封面
     * @param title 标题
     * @param content 图文内容HTML
     * @param describe 描述
     * @return
     */
    public static boolean pushTemporaryContent(File cover,String title, String content, String describe){
        try {
            String media_id=uploadnews(cover,title,content,describe);
            Integer ends=GroupMessage(media_id);
            return true;
        } catch (Exception e) {
            // TODO: handle exception
            return false;
        }

    }
    /**
     * 推送永久素材消息
     * @param cover 封面
     * @param title 标题
     * @param content 图文内容HTML
     * @param describe 描述
     * @return
     */
    public static boolean pushPerpetualContent(File cover,String title, String content, String describe){
        try {
            String media_id=addNews(cover,title,content,describe);
            Integer ends=GroupMessage(media_id);
            return true;
        } catch (Exception e) {
            // TODO: handle exception
            return false;
        }

    }

    /**
     * 临时素材创建，只保存三天
     */
    public static String uploadnews(File cover,String title, String content, String describe) throws Exception {
        String uploadnews = "https://api.weixin.qq.com/cgi-bin/media/uploadnews?access_token=" + ACCESS_TOKEN;
        Map<String, Object> data = new HashMap<>();
        List<Map<String, Object>> articles = new ArrayList<Map<String, Object>>();
        Map<String, Object> article = new HashMap<>();
        String media_id = uploadFile(
                "https://api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE",cover, ACCESS_TOKEN, "image");
        article.put("thumb_media_id", media_id);
        article.put("title", "图文消息测试");// 标题
        article.put("content", content);// 内容
        article.put("digest", describe);// 描述
        articles.add(article);
        data.put("articles", articles);
        JSONObject json = new JSONObject(data);
        String result = doGetPost(uploadnews, "POST", json);
        JSONObject resultJson=JSONObject.parseObject(result);
        return resultJson.get("media_id").toString();
    }

    /**
     * @param content
     * @throws Exception
     *             永久素材
     */
    public static String addNews(File cover,String title, String content, String describe) throws Exception {
        String uploadnews = "https://api.weixin.qq.com/cgi-bin/material/add_news?access_token=" + ACCESS_TOKEN;
        Map<String, Object> data = new HashMap<>();
        List<Map<String, Object>> articles = new ArrayList<Map<String, Object>>();
        Map<String, Object> article = new HashMap<>();
        String media_id = uploadFile(
                "https://api.weixin.qq.com/cgi-bin/material/add_material?access_token=ACCESS_TOKEN&type=TYPE",cover, ACCESS_TOKEN, "image");
        article.put("thumb_media_id", media_id);
        article.put("title", title);// 标题
        article.put("content", content);// 内容
        article.put("digest", describe);// 描述
        articles.add(article);
        data.put("articles", articles);
        JSONObject json = new JSONObject(data);
        System.err.println(json);
        String result = doGetPost(uploadnews, "POST", json);
        System.err.println(result);
        JSONObject resultJson=JSONObject.parseObject(result);
        return resultJson.get("media_id").toString();
    }

    /***
     * 群发文本消息
     */
    public static Integer GroupMessage(String media_id ){
        // 接口地址
        String sendMsgApi = "https://api.weixin.qq.com/cgi-bin/message/mass/sendall?access_token=" + ACCESS_TOKEN;
        // 整体参数map
        Map<String, Object> paramMap = new HashMap<String, Object>();
        // 相关map
        Map<String, Object> dataMap1 = new HashMap<String, Object>();
//		Map<String, String> dataMap2 = new HashMap<String, String>();
        dataMap1.put("is_to_all", true);// 用于设定是否向全部用户发送，值为true或false，选择true该消息群发给所有用户，选择false可根据tag_id发送给指定群组的用户
//		dataMap1.put("tag_id", 100);// 群发到的标签的tag_id，参见用户管理中用户分组接口，若is_to_all值为true，可不填写tag_id
//		dataMap2.put("content", content);// 要推送的内容
        paramMap.put("filter", dataMap1);// 用于设定图文消息的接收者
        Map<String, String> news = new HashMap<String, String>();
        news.put("media_id", media_id);
        paramMap.put("mpnews", news);// 文本内容

        paramMap.put("send_ignore_reprint", 0);
        paramMap.put("msgtype", "mpnews");// 群发的消息类型，图文消息为mpnews，文本消息为text，语音为voice，音乐为music，图片为image，视频为video，卡券为wxcard
        String back = doGetPost(sendMsgApi, "POST", paramMap);
        System.out.println("群发返回:" + back);
        JSONObject jsonObject = JSONObject.parseObject(back);// String转JSONObject，
        Integer re = jsonObject.getInteger("errcode");
        return re;
    }

    /**
     * 调用接口 post
     *
     * @param apiPath
     */
    public static String doGetPost(String apiPath, String type, Map<String, Object> paramMap) {
        OutputStreamWriter out = null;
        InputStream is = null;
        String result = null;
        try {
            URL url = new URL(apiPath);// 创建连接
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestMethod(type); // 设置请求方式
            connection.setRequestProperty("Accept", "application/json"); // 设置接收数据的格式
            connection.setRequestProperty("Content-Type", "application/json"); // 设置发送数据的格式
            connection.connect();
            if (type.equals("POST")) {
                out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8"); // utf-8编码
                out.append(JSON.toJSONString(paramMap));
                out.flush();
                out.close();
            }
            // 读取响应
            is = connection.getInputStream();
            int length = (int) connection.getContentLength();// 获取长度
            if (length != -1) {
                byte[] data = new byte[length];
                byte[] temp = new byte[512];
                int readLen = 0;
                int destPos = 0;
                while ((readLen = is.read(temp)) > 0) {
                    System.arraycopy(temp, 0, data, destPos, readLen);
                    destPos += readLen;
                }
                result = new String(data, "UTF-8"); // utf-8编码
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 新增素材
     *
     * @param file
     *            文件路径
     * @param accessToken
     *            公众号接口唯一凭证
     * @param type
     *            消息类型
     * @return
     * @throws Exception
     */
    public static String uploadFile(String URL, File file, String accessToken, String type) throws Exception {
        if (!file.exists() || !file.isFile()) {
            throw new IOException("文件不存在！");
        }
        String url = URL.replace("ACCESS_TOKEN", accessToken).replace("TYPE", type);
        URL urlObj = new URL(url);

        // 创建Http连接
        HttpURLConnection conn = (HttpURLConnection) urlObj.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setUseCaches(false); // 使用post提交需要设置忽略缓存

        // 消息请求头信息
        conn.setRequestProperty("Connection", "Keep-Alive");
        conn.setRequestProperty("Charset", "UTF-8");

        // 设置边界
        String BOUNDARY = "----------" + System.currentTimeMillis();
        conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + BOUNDARY);

        StringBuilder sb = new StringBuilder();
        sb.append("--"); // 必须多两道线
        sb.append(BOUNDARY);
        sb.append("\r\n");
        sb.append("Content-Disposition:form-data;name=\"media\";filename=\"" + file.getName() + "\";filelength=\""
                + file.length() + "\"\r\n");
        sb.append("Content-Type:application/octet-stream\r\n\r\n");

        byte[] head = sb.toString().getBytes("utf-8");

        // 创建输出流
        OutputStream out = new DataOutputStream(conn.getOutputStream());
        // 获得输出流表头
        out.write(head);

        // 文件正文部分
        DataInputStream in = new DataInputStream(new FileInputStream(file));
        int bytes = 0;
        byte[] bufferOut = new byte[1024 * 1024 * 10]; // 10M
        while ((bytes = in.read(bufferOut)) != -1) {
            out.write(bufferOut, 0, bytes);
        }
        in.close();

        // 结尾
        byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");
        out.write(foot);
        out.flush();
        out.close();

        // 获取响应
        StringBuffer buffer = new StringBuffer();
        BufferedReader reader = null;
        String result = null;
        try {
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            if (result == null) {
                result = buffer.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            reader.close();
        }

        JSONObject json = JSONObject.parseObject(result);
        System.out.println(json);
        String mediaId = "";
        if (!type.equals("image")) {
            mediaId = json.getString(type + "_media_id");
        } else {
            mediaId = json.getString("media_id");
        }
        return mediaId;
    }
}
