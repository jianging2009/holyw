package com.holyw.holyw.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.holyw.holyw.common.HttpAPIService;
import com.holyw.holyw.common.HttpMessage;
import com.holyw.holyw.common.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/Weather")
public class WeatherController {

    @Autowired
    private HttpAPIService httpAPIService;

    private static JSONObject resultChildData = new JSONObject();
    private static JSONObject resultChildDataOriginal = new JSONObject();


    @GetMapping
    public ResultEntity getWeather(String id,String type) {

        try {
            if ("yunjisoft".equals(id) && StringUtils.isEmpty(type)) {
                return ResultEntity.result(HttpMessage.SUCCESS, resultChildData, HttpStatus.OK);
            }
            if("yunjisoft".equals(id) && "Original".equals(type)){
                return ResultEntity.result(HttpMessage.SUCCESS, resultChildDataOriginal, HttpStatus.OK);
            }

            //TODO 其他用户进入，需要收费的处理



            return ResultEntity.result("please check you id...QQ:675424581",null, HttpStatus.INTERNAL_SERVER_ERROR);

        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.result("please check you id...QQ:675424581",null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @Scheduled(cron = "0/30 * * * * *")
    public void timer() {
        boolean flag = true;
        try {
            if(flag) {
                flag = false;
                JSONArray resultChildDataArr = new JSONArray();
                JSONArray resultChildDataArrOriginal = new JSONArray();

                String result =httpAPIService.doGet("http://product.weather.com.cn/alarm/grepalarm_cn.php?_=1528340356056","UTF-8");
                result = result.replace("var alarminfo=","");
                result = result.replace(";","");
                JSONObject resultJson = JSONObject.parseObject(result);
                JSONArray data = resultJson.getJSONArray("data");
                for (Object province:data) {
                    JSONArray provinceJson = JSONObject.parseArray(province.toString());
                    if(provinceJson.get(0).toString().indexOf("广东省") != -1) {
                        String html = provinceJson.get(1).toString();
                        String url = "http://product.weather.com.cn/alarm/webdata/"+html;
                        String cityData = httpAPIService.doGet(url,"UTF-8");
                        String cityStr = cityData.replace("var alarminfo=", "");
                        JSONObject cityJson = JSONObject.parseObject(cityStr);
                        JSONObject needJson = new JSONObject();
                        needJson.put("Describtion",cityJson.getString("ISSUECONTENT"));
                        needJson.put("Level",cityJson.getString("SIGNALLEVEL"));
                        needJson.put("PubishTime",cityJson.getString("ISSUETIME"));
                        needJson.put("Title",cityJson.getString("head"));
                        needJson.put("Type",cityJson.getString("SIGNALTYPE"));
                        needJson.put("regionName",cityJson.getString("CITY"));
                        resultChildDataArr.add(needJson);
                        resultChildDataArrOriginal.add(cityJson);
                    }
                }
                resultChildData.put("data",resultChildDataArr);
                resultChildDataOriginal.put("data",resultChildDataArrOriginal);
                flag = true;
            }
        }catch (Exception e) {
            flag = true;
        }
    }
}
