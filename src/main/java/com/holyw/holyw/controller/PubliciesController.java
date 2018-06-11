package com.holyw.holyw.controller;


import com.alibaba.fastjson.JSONObject;
import com.holyw.holyw.common.HttpAPIService;
import com.holyw.holyw.common.HttpMessage;
import com.holyw.holyw.common.ResultEntity;
import com.holyw.holyw.timmer.ToutiaoTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping(value = "/publicies")
public class PubliciesController {


    @Autowired
    private HttpAPIService httpAPIService;

    private static JSONObject resultData = new JSONObject();






    @GetMapping
    public ResultEntity getPublicies(String id,String keyword,String type) {
        try {

            if(Objects.nonNull(resultData)) {
                return ResultEntity.result(HttpMessage.SUCCESS, ToutiaoTask.resultData, HttpStatus.OK);
            }else {
                return ResultEntity.result("please check you id...QQ:675424581",null, HttpStatus.INTERNAL_SERVER_ERROR);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.result("please check you id...QQ:675424581",null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /*@PostMapping
    public ResultEntity setPubliciesKeyword(String id,String keyword,String type) {
        try {
            HashMap<String, Object> params = new HashMap<>();
            params.put("offset",0);
            params.put("format","json");
            params.put("keyword","广东高考");
            params.put("autoload",true);
            params.put("count",20);
            params.put("cur_tab",1);
            params.put("from","search_tab");

            String result = HttpsApiService.doGet("https://www.toutiao.com/search_content/",params);
            if(StringUtils.isEmpty(result)) return null;
            JSONObject resultJson = JSONObject.parseObject(result);
            return ResultEntity.result(HttpMessage.SUCCESS,null, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.result("please check you id...QQ:675424581",null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }*/

    /*@Scheduled(cron = "0/10 * * * * *")
    public void publiciesTimer() {
        boolean flag = true;
        try {
            if(flag) {
                flag = false;

                HashMap<String, Object> params = new HashMap<>();
                params.put("offset",0);
                params.put("format","json");
                params.put("keyword","广东高考");
                params.put("autoload",true);
                params.put("count",20);
                params.put("cur_tab",1);
                params.put("from","search_tab");

                String result = HttpsApiService.doGet("https://www.toutiao.com/search_content/",params);
                if(StringUtils.isEmpty(result)) return;
                JSONObject resultJson = JSONObject.parseObject(result);
                JSONArray resultDataArr = new JSONArray();
                if(Objects.nonNull(resultJson)) {
                    JSONArray data = resultJson.getJSONArray("data");
                    //System.out.println(data);
                    if(!CollectionUtils.isEmpty(data)) {
                        for (Object news: data) {
                            JSONObject newsJson = JSONObject.parseObject(news.toString());
                            String source = newsJson.getString("source");
                            if("专题".equals(source)) continue;
                            String title = newsJson.getString("title");
                            String item_id = newsJson.getString("item_id");
                            if(StringUtils.isEmpty(item_id)) continue;
                            String abstracts = newsJson.getString("abstract");
                            String image_url = newsJson.getString("image_url");

                            String news_url = "https://www.toutiao.com/a"+item_id;
                            JSONObject resultNewsJson = new JSONObject();
                            resultNewsJson.put("title",title);
                            resultNewsJson.put("news_url",news_url);
                            resultNewsJson.put("abstract",abstracts);
                            if(!StringUtils.isEmpty(image_url)) {
                                resultNewsJson.put("image_url",image_url.replace("//",""));//
                                String large_image_url = "http://p9.pstatp.com/large/pgc-image/"+image_url.substring(image_url.lastIndexOf("/"),image_url.length());
                                resultNewsJson.put("large_image_url",large_image_url);
                            }

                            resultDataArr.add(resultNewsJson);
                        }
                    }
                }
                resultData.put("data",resultDataArr);
                //System.out.println(resultData);
                flag = true;
            }
        }catch (Exception e) {
            e.printStackTrace();
            flag = true;
        }
    }*/
}
