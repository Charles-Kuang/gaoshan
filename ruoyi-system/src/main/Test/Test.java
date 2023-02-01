
import cn.hutool.core.date.DateUtil;


import cn.hutool.core.util.IdUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import com.ruoyi.common.core.domain.entity.GsRegion;
import lombok.var;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

public class Test {


    private static final Logger log = LoggerFactory.getLogger(Test.class);

    private static AtomicInteger atomicInteger = new AtomicInteger(0);
    private static AtomicInteger atomicIntegerErrorNum = new AtomicInteger(0);

    public static void main(String[] args) {
        //获取所有的数据
        test(null, null);

        //获取指定的城市
//        test("贵州省", "贵阳市");
    }

    private static String getHtml(String url, GsRegion gsRegion) throws Exception {

        atomicInteger.incrementAndGet();
        
        log.info(url);
        String html = null;
        try {
            html = getHtml2(url, gsRegion);
        } catch (Exception e) {
            e.printStackTrace();
            atomicIntegerErrorNum.incrementAndGet();

            try {

                //重试1次
                html = getHtml2(url, gsRegion);
            } catch (Exception e2) {
                e2.printStackTrace();
                //重试2次
                html = getHtml2(url, gsRegion);

            }

        }
        log.info("请求结束时间 ==>" + DateUtil.formatDateTime(new Date()));
        return html;
//        }
    }

    private static String getHtml2(String url, GsRegion gsRegion) throws Exception {
        //链式构建请求

        return HttpRequest.get(url)
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/97.0.4692.99 Safari/537.36")//头信息，多个头信息多次调用此方法即可
                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")//头信息，多个头信息多次调用此方法即可
                .header("Cookie", "_trs_uv=" + IdUtil.simpleUUID() + "; SF_cookie_1=" + IdUtil.fastUUID())
                .header("If-None-Match", "f32-5d4bccaa05a80-gzip")
//                    .header("If-Modified-Since",new Date())
//                    .form(paramMap)//表单内容
                .timeout(15000)//超时，毫秒
                .execute().body();
    }

    public static String baseUrl = "http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2021/";

    public static String saveUrl = "http://127.0.0.1:8080/core/region";

    public static void test(String appendProvide, String appendCity) {


        var url = "http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2021/index.html";


        String html = HttpUtil.get(url);

        var htmlDoc = Jsoup.parse(html);
        var selectClasses = htmlDoc.getElementsByClass("provincetr");


        var startDateStr = DateUtil.formatDateTime(new Date());
        long startDate = System.currentTimeMillis();

//        ExecutorService executorService = new ThreadPoolExecutor(4, 5, 1L, TimeUnit.SECONDS, new ArrayBlockingQueue(4), Executors.defaultThreadFactory());

        for (int i = 0; i < selectClasses.size(); i++) {
            int finalI = i;
//            executorService.execute(() -> {
            try {
                //一个线程跑就行
                startProvide(selectClasses, finalI, baseUrl, appendProvide, appendCity);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


    /**
     * @param selectClasses
     * @param i
     * @param baseUrl
     * @param appendProvide 需要查询的省  ,null表示查询所有
     * @throws Exception
     */
    private static void startProvide(Elements selectClasses, Integer i, String baseUrl, String appendProvide, String appendCity) throws Exception {


        var provideCodes = selectClasses.get(i).children();

        //1.省份 provincetr
        for (int provideCodeIndex = 0; provideCodeIndex < provideCodes.size(); provideCodeIndex++) {
            var provideCodeUrl = provideCodes.get(provideCodeIndex).select("a").attr("href");
            var provideName = provideCodes.get(provideCodeIndex).select("a").text();

            String[] split = provideCodeUrl.split("/");
            String replace = split[split.length - 1].replace(".html", "");
            var len1=replace.length();
            var cityCode=replace;
            if (len1 < 6) {
                len1 = 6 - len1;
                for (int l = 0; l < len1; l++) {
                    cityCode += "0";
                }
            }
            String s = HttpUtil.get(saveUrl + "/" + provideName + "/" + cityCode + "/" + 0);
            log.info("s"+s);
            if (!StringUtils.isBlank(provideCodeUrl)) {
                if (StringUtils.isBlank(appendProvide)) {
                    provide(provideCodeUrl, provideName, null);
                } else {
                    if (appendProvide.equals(provideName)) {
                        provide(provideCodeUrl, provideName, appendCity);
                    }
                }
            }
        }
    }


    /**
     * 通过省，市获取下面的数据
     *
     * @param provideCodeUrl
     */
    public static void provide(String provideCodeUrl, String provideName, String appendCity) throws Exception {
        var provideCode = provideCodeUrl.split("\\.")[0];

        String gotoCityHtml = getHtml(baseUrl + provideCodeUrl, null);
        var cityHtmlDoc = Jsoup.parse(gotoCityHtml);
        Elements selectCityClass = cityHtmlDoc.select(".citytr");
        if (StringUtils.isBlank(appendCity)) {
            city(selectCityClass, provideName, provideCode, null);
        } else {
            city(selectCityClass, provideName, provideCode, appendCity);
        }

    }


    /**
     * 城市
     *
     * @param selectCityClass
     * @param provideName
     * @param provideCode
     * @throws Exception
     */
    private static void city(Elements selectCityClass, String provideName, String provideCode, String appendCity) throws Exception {

        //2.城市  citytr
        for (int cityIndex = 0; cityIndex < selectCityClass.size(); cityIndex++) {
            var gotoCountyUrl = selectCityClass.get(cityIndex).select("td").get(1).select("a").attr("href");
            var cityName = selectCityClass.get(cityIndex).select("td").get(1).select("a").text();

            var len = provideCode.length();
            var provideCode2 = provideCode;
            if (len < 6) {
                len = 6 - len;
                for (int l = 0; l < len; l++) {
                    provideCode2 += "0";
                }
            }
            String[] split = gotoCountyUrl.split("/");
            String replace = split[split.length - 1].replace(".html", "");
            var len1=replace.length();
            var cityCode=replace;
            if (len1 < 6) {
                len1 = 6 - len1;
                for (int l = 0; l < len1; l++) {
                    cityCode += "0";
                }
            }
            String s = HttpUtil.get(saveUrl + "/" + cityName + "/" + cityCode + "/" + provideCode2);
            log.info("s"+s);
            if (StringUtils.isBlank(gotoCountyUrl)) {
                continue;
            }

            if (StringUtils.isBlank(appendCity)) {
                appendCity(gotoCountyUrl, provideName, provideCode, cityName);
            } else {
                if (appendCity.equals(cityName)) {
                    appendCity(gotoCountyUrl, provideName, provideCode, cityName);
                }
            }
        }
    }


    private static void appendCity(String gotoCountyUrl, String provideName, String provideCode, String cityName) throws Exception {
        //                        String countytr = HttpUtil.get(baseUrl + gotoCountyUrl);

        boolean flag = true;
        String countytr = getHtml(baseUrl + gotoCountyUrl, null);
        var countytrDoc = Jsoup.parse(countytr);
        Elements countyClass = countytrDoc.select(".countytr");
        if (countyClass.size() == 0) {
            flag = false;
            countyClass = countytrDoc.select(".towntr");
        }

        var strCityUrl = gotoCountyUrl.split("/");
        var cityCode = strCityUrl[strCityUrl.length - 1].split("\\.")[0];

        if (cityName.equals("市辖区")) {
            cityName = provideName;
        }

        if (!flag) {
            town(countyClass,
                    provideName,
                    provideCode,
                    cityCode
                    , flag
            );
        } else {
            county(countyClass,
                    provideName,
                    provideCode);
        }

    }


    /**
     * 街道
     *
     * @param countyClass
     * @param provideCode
     */
    private static void county(Elements countyClass, String provideName, String provideCode) throws Exception {
        //3.县区  countytr
        for (int county = 0; county < countyClass.size(); county++) {
            var gotoTownUrl = countyClass.get(county).select("td").get(1).select("a").attr("href");
            var countyName = countyClass.get(county).select("td").get(1).select("a").text();

            //todo 保存到数据库
            String replace = gotoTownUrl.replace(".html", "");
            String[] split = replace.split("/");
            String s1 = split[split.length - 1];
            var len = provideCode.length();
            var provideCode2 = provideCode;
            if (len < 6) {
                len = 6 - len;
                for (int l = 0; l < len; l++) {
                    provideCode2 += "0";
                }
            }
            var len1 = s1.length();
            var sa = s1;
            if (len1 < 6) {
                len1 = 6 - len1;
                for (int l = 0; l < len1; l++) {
                    sa += "0";
                }
            }
            String s = HttpUtil.get(saveUrl + "/" + countyName + "/" + sa + "/" + provideCode2);
            log.info("s"+s+"县区县区县区县区县区县区县区县区县区县区" + countyName + "/" + sa + "/" + provideCode2);

            if (StringUtils.isBlank(gotoTownUrl)) {
                continue;
            }

            String towntr = getHtml(baseUrl + provideCode + "/" + gotoTownUrl, null);
            var townDoc = Jsoup.parse(towntr);
            Elements townClass = townDoc.select(".towntr");
            var gotoTownCode = gotoTownUrl.split("/")[0];
//                            Thread.sleep(3000);

            town(townClass,
                    provideName,
                    provideCode,
                    gotoTownCode, true);
        }
    }

    /**
     * @param townClass
     * @param provideName
     * @param provideCode
     * @param gotoTownCode
     * @param flag         是否地级市。true是，false否
     * @throws Exception
     */
    private static void town(Elements townClass, String provideName, String provideCode, String gotoTownCode, boolean flag) throws Exception {
        //4.街道。镇 towntr
        for (int town = 0; town < townClass.size(); town++) {
            //towntr
            var gotoVillageHref = townClass.get(town).select("td").get(1).select("a").attr("href");
            var townName = townClass.get(town).select("td").get(1).select("a").text();
            //todo 保存到数据库
            String villageStr = null;
            String[] split = gotoVillageHref.split("/");
            String aa = split[split.length-1].replace(".html","");
            String substring;
            if (flag) {
                villageStr = getHtml(baseUrl + provideCode + "/" + gotoTownCode + "/" + gotoVillageHref, null);
                substring = aa.substring(0,aa.length()-3);
            } else {
                villageStr = getHtml(baseUrl + provideCode + "/" + gotoVillageHref, null);
                substring = aa.substring(0,aa.length()-5);
            }
            String s = HttpUtil.get(saveUrl + "/" + townName + "/" + aa + "/" + substring);
            log.info("s"+s+"特别注意特别注意！！！！！！！" + townName + "/" + aa + "/" + substring);

            if (StringUtils.isBlank(gotoVillageHref)) {
                continue;
            }

            //居委会
//                                String villageStr = HttpUtil.get(baseUrl + provideCode + "/" + gotoTownCode + "/" + gotoVillageHref);

            var villageDoc = Jsoup.parse(villageStr);
            Elements villagetr = villageDoc.select(".villagetr");

            for (int villageIndex = 0; villageIndex < villagetr.size(); villageIndex++) {
                var tds = villagetr.get(villageIndex).select("td");//[0].text();
                var villageCode = tds.get(0).text();
                var villageType = tds.get(1).text();
                var villageName = tds.get(2).text();

                //todo 保存到数据库
                String s1 = HttpUtil.get(saveUrl + "/" + villageName + "/" + villageCode + "/" + aa+"/"+villageType);
                log.info("s1"+s1);

            }
        }
    }

}