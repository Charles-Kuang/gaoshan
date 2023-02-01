package com.ruoyi.applet.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import com.ruoyi.applet.mapper.GsRegionMapper;
import com.ruoyi.applet.service.IChinaService;
import com.ruoyi.common.core.domain.entity.GsRegion;
import com.ruoyi.common.utils.AMapUtils;
import com.ruoyi.common.utils.ChineseCharacterUtil;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author XIAOCAO
 */
@Slf4j
@Service
public class ChinasServiceImpl implements IChinaService {


    @Autowired
    GsRegionMapper gsRegionMapper;

    public void addChina(GsRegion gsRegion) {
        if (gsRegion.getParentCode() != 0) {
            gsRegion.setFullName(getFullName(gsRegion));
            GsRegion ParentRegion = gsRegionMapper.selectGsRegionByCode(gsRegion.getParentCode());
            if (ParentRegion != null) {
                gsRegion.setParentName(ParentRegion.getName());
                if (ParentRegion.getHasChild() == null || !ParentRegion.getHasChild()) {
                    ParentRegion.setHasChild(true);
                }
                gsRegionMapper.updateGsRegionOne(ParentRegion);
            }
        }
        double[] doubles = AMapUtils.addressToGPS(gsRegion.getName());
        if (doubles != null) {
            gsRegion.setLng(String.valueOf(doubles[0]));
            gsRegion.setLat(String.valueOf(doubles[1]));
        }
        assert gsRegion.getName() != null;
        gsRegion.setFirstLetter(ChineseCharacterUtil.getSpells(gsRegion.getName()));
        GsRegion info = gsRegionMapper.selectGsRegionByCode(gsRegion.getParentCode());
        if (info != null) {
            gsRegion.setForefathers(info.getForefathers() + "," + info.getCode());
        } else {
            if (gsRegion.getParentCode() == null) gsRegion.setParentCode(0L);
            gsRegion.setForefathers(gsRegion.getParentCode().toString());
        }
        if (gsRegionMapper.selectGsRegionByCode(gsRegion.getCode()) != null) {
            gsRegion.setLastUpdateTime(DateUtils.getNowDate());
            gsRegion.setLastUpdateUser(SecurityUtils.getUsername());
            int i = gsRegionMapper.updateGsRegion(gsRegion);
            log.info("更新成功" + i + gsRegion.getName());
        } else {
            gsRegion.setCreateTime(DateUtils.getNowDate());
            gsRegion.setCreateUser(SecurityUtils.getUsername());
            int i = gsRegionMapper.insertGsRegion(gsRegion);
            log.info("插入成功" + i + gsRegion.getName());
        }
    }

    public String getFullName(GsRegion region) {
        GsRegion ParentRegion = gsRegionMapper.selectGsRegionByCode(region.getParentCode());
        if (ParentRegion != null && region.getParentCode() != 0) {
            region.setFullName(getFullName(ParentRegion) + region.getName());
        } else {
            region.setFullName(region.getName());
        }
        return region.getFullName();
    }

    private static AtomicInteger atomicInteger = new AtomicInteger(0);
    private static AtomicInteger atomicIntegerErrorNum = new AtomicInteger(0);

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

    @Override
    public void test(GsRegion gsRegion) {
        try {
            if (gsRegion == null) {
                var url = "http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2021/index.html";
                String html = HttpUtil.get(url);
                var htmlDoc = Jsoup.parse(html);
                var selectClasses = htmlDoc.getElementsByClass("provincetr");
                for (int i = 0; i < selectClasses.size(); i++) {
                    int finalI = i;
                    try {
                        //一个线程跑就行
                        startProvide(selectClasses, finalI, null);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else {
                startProvide(null, null, gsRegion);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     */
    private void startProvide(Elements selectClasses, Integer i, GsRegion region) throws Exception {


            if (region != null && region.getLevel() >= 0) {
                provide(null, region.getName(), region);
            } else {
                var provideCodes = selectClasses.get(i).children();

            //1.省份 provincetr
            for (int provideCodeIndex = 0; provideCodeIndex < provideCodes.size(); provideCodeIndex++) {
                var provideCodeUrl = provideCodes.get(provideCodeIndex).select("a").attr("href");
                var provideName = provideCodes.get(provideCodeIndex).select("a").text();

                if (!StringUtils.isBlank(provideCodeUrl)) {
                    String[] split = provideCodeUrl.split("/");
                    String replace = split[split.length - 1].replace(".html", "");
                    var len1 = replace.length();
                    var cityCode = replace;
                    if (len1 < 6) {
                        len1 = 6 - len1;
                        for (int l = 0; l < len1; l++) {
                            cityCode += "0";
                        }
                    }
                    GsRegion gsRegion = new GsRegion();
                    gsRegion.setName(provideName);
                    gsRegion.setCode(Long.valueOf(cityCode));
                    gsRegion.setParentCode(0L);
                    gsRegion.setLevel(0);
                    addChina(gsRegion);
                    provide(provideCodeUrl, provideName, null);
                }
            }
        }
    }


    /**
     * 通过省，市获取下面的数据
     *
     * @param provideCodeUrl
     */
    public void provide(String provideCodeUrl, String provideName, GsRegion region) throws Exception {
        if (provideCodeUrl == null) {
            String substring = region.getCode().toString().substring(0, 2);
            provideCodeUrl = substring + ".html";
        }
        var provideCode = provideCodeUrl.split("\\.")[0];
        String gotoCityHtml = getHtml(baseUrl + provideCodeUrl, null);
        var cityHtmlDoc = Jsoup.parse(gotoCityHtml);
        Elements selectCityClass = cityHtmlDoc.select(".citytr");
        city(selectCityClass, provideName, provideCode, region);

    }


    /**
     * 城市
     *
     * @param selectCityClass
     * @param provideName
     * @param provideCode
     * @throws Exception
     */
    private void city(Elements selectCityClass, String provideName, String provideCode, GsRegion gsRegion) throws Exception {

        //2.城市  citytr
        for (org.jsoup.nodes.Element cityClass : selectCityClass) {
            var gotoCountyUrl = cityClass.select("td").get(1).select("a").attr("href");
            var cityName = cityClass.select("td").get(1).select("a").text();
            if (StringUtils.isBlank(gotoCountyUrl)) {
                continue;
            }
            if (gsRegion != null && gsRegion.getLevel() > 1){
                String cityNameOne= gsRegionMapper.selectGsRegionByCode(Long.valueOf(gsRegion.getCode().toString().substring(0,4)+"00")).getName();
                if (Objects.equals(cityNameOne, cityName)) {
                    String[] split = gotoCountyUrl.split("/");
                    String replace = split[split.length - 1].replace(".html", "");
                    var len1 = replace.length();
                    var cityCode = replace;
                    if (len1 < 6) {
                        len1 = 6 - len1;
                        for (int l = 0; l < len1; l++) {
                            cityCode += "0";
                        }
                    }
                    appendCity(gotoCountyUrl, provideName, provideCode, cityName, gsRegion);
                }
            }else if(gsRegion != null && gsRegion.getLevel() == 1) {

                if (Objects.equals(gsRegion.getName(), cityName)) {
                    String[] split = gotoCountyUrl.split("/");
                    String replace = split[split.length - 1].replace(".html", "");
                    var len1 = replace.length();
                    var cityCode = replace;
                    if (len1 < 6) {
                        len1 = 6 - len1;
                        for (int l = 0; l < len1; l++) {
                            cityCode += "0";
                        }
                    }
                    appendCity(gotoCountyUrl, provideName, provideCode, cityName, gsRegion);
                }
            } else {
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
                var len1 = replace.length();
                var cityCode = replace;
                if (len1 < 6) {
                    len1 = 6 - len1;
                    for (int l = 0; l < len1; l++) {
                        cityCode += "0";
                    }
                }
                GsRegion gsRegionOne = new GsRegion();
                gsRegionOne.setName(cityName);
                gsRegionOne.setCode(Long.valueOf(cityCode));
                gsRegionOne.setParentCode(Long.valueOf(provideCode2));
                gsRegionOne.setLevel(1);
                addChina(gsRegionOne);
                appendCity(gotoCountyUrl, provideName, provideCode, cityName, null);
            }
        }
    }


    private void appendCity(String gotoCountyUrl, String provideName, String provideCode, String cityName, GsRegion gsRegion) throws Exception {

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
                    , flag, gsRegion
            );
        } else {
            county(countyClass,
                    provideName,
                    provideCode, cityCode, gsRegion);
        }

    }


    /**
     * 街道
     *
     * @param countyClass
     * @param provideCode
     */
    private void county(Elements countyClass, String provideName, String provideCode, String cityCode, GsRegion region) throws Exception {
        System.out.println(region);
        //3.县区  countytr
        for (int county = 0; county < countyClass.size(); county++) {
            var gotoTownUrl = countyClass.get(county).select("td").get(1).select("a").attr("href");
            var countyName = countyClass.get(county).select("td").get(1).select("a").text();
            String towntr = getHtml(baseUrl + provideCode + "/" + gotoTownUrl, null);
            var townDoc = Jsoup.parse(towntr);
            Elements townClass = townDoc.select(".towntr");
            var gotoTownCode = gotoTownUrl.split("/")[0];
            if (StringUtils.isBlank(gotoTownUrl)) {
                continue;
            }
            if (region != null && region.getLevel() > 2){
                String cityNameOne= gsRegionMapper.selectGsRegionByCode(Long.valueOf(region.getCode().toString().substring(0,6))).getName();
                if (Objects.equals(cityNameOne, countyName)) {
                    town(townClass,
                            provideName,
                            provideCode,
                            gotoTownCode, true, region);
                }
            }else if (region != null && region.getLevel() == 2){
                System.out.println(region);
                if (Objects.equals(region.getName(), countyName)) {
                    town(townClass,
                            provideName,
                            provideCode,
                            gotoTownCode, true, region);
                }
            } else {
                //todo 保存到数据库
                String replace = gotoTownUrl.replace(".html", "");
                String[] split = replace.split("/");
                String s1 = split[split.length - 1];
                var len = cityCode.length();
                var provideCode2 = cityCode;
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
                GsRegion gsRegion = new GsRegion();
                gsRegion.setName(countyName);
                gsRegion.setCode(Long.valueOf(sa));
                gsRegion.setParentCode(Long.valueOf(provideCode2));
                gsRegion.setLevel(2);
                addChina(gsRegion);
                town(townClass,
                        provideName,
                        provideCode,
                        gotoTownCode, true, null);
            }
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
    private void town(Elements townClass, String provideName, String provideCode, String gotoTownCode, boolean flag, GsRegion region) throws Exception {
        //4.街道。镇 towntr
        for (int town = 0; town < townClass.size(); town++) {
            //towntr
            var gotoVillageHref = townClass.get(town).select("td").get(1).select("a").attr("href");
            var townName = townClass.get(town).select("td").get(1).select("a").text();
            if (StringUtils.isBlank(gotoVillageHref)) {
                continue;
            }
            String villageStr = null;
            String[] split = gotoVillageHref.split("/");
            String aa = split[split.length - 1].replace(".html", "");
            String substring;
            if (flag) {
                villageStr = getHtml(baseUrl + provideCode + "/" + gotoTownCode + "/" + gotoVillageHref, null);
                substring = aa.substring(0, aa.length() - 3);
            } else {
                villageStr = getHtml(baseUrl + provideCode + "/" + gotoVillageHref, null);
                substring = aa.substring(0, aa.length() - 5);
            }
            //todo 保存到数据库
            if (region != null && region.getLevel() == 3) {
                if (Objects.equals(region.getName(), townName)) {
                    var villageDoc = Jsoup.parse(villageStr);
                    Elements villagetr = villageDoc.select(".villagetr");

                    for (int villageIndex = 0; villageIndex < villagetr.size(); villageIndex++) {
                        var tds = villagetr.get(villageIndex).select("td");//[0].text();
                        var villageCode = tds.get(0).text();
                        var villageType = tds.get(1).text();
                        var villageName = tds.get(2).text();
                        //todo 保存到数据库
                        GsRegion gsRegionOne = new GsRegion();
                        gsRegionOne.setName(villageName);
                        gsRegionOne.setCode(Long.valueOf(villageCode));
                        gsRegionOne.setParentCode(Long.valueOf(aa));
                        gsRegionOne.setType(villageType);
                        gsRegionOne.setLevel(4);
                        addChina(gsRegionOne);
                    }
                }
            } else {
                GsRegion gsRegion = new GsRegion();
                gsRegion.setName(townName);
                gsRegion.setCode(Long.valueOf(aa));
                gsRegion.setParentCode(Long.valueOf(substring));
                gsRegion.setLevel(3);
                addChina(gsRegion);

                var villageDoc = Jsoup.parse(villageStr);
                Elements villagetr = villageDoc.select(".villagetr");

                for (int villageIndex = 0; villageIndex < villagetr.size(); villageIndex++) {
                    var tds = villagetr.get(villageIndex).select("td");//[0].text();
                    var villageCode = tds.get(0).text();
                    var villageType = tds.get(1).text();
                    var villageName = tds.get(2).text();
                    //todo 保存到数据库
                    GsRegion gsRegionOne = new GsRegion();
                    gsRegionOne.setName(villageName);
                    gsRegionOne.setCode(Long.valueOf(villageCode));
                    gsRegionOne.setParentCode(Long.valueOf(aa));
                    gsRegionOne.setType(villageType);
                    gsRegionOne.setLevel(4);
                    addChina(gsRegionOne);

                }
            }
        }
    }
}

