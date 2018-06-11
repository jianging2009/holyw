package com.holyw.holyw.timmer;

import com.holyw.holyw.common.HttpAPIService;
import com.holyw.holyw.common.SpringUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class MM131Task {


    private static MM131Task mM131Task;
    @Autowired
    private HttpAPIService httpAPIService;

    public static MM131Task getInstance() {
        if(Objects.isNull(mM131Task)) {
            mM131Task = new MM131Task();
        }
        return mM131Task;
    }

    public void  run (String keyword) {
        boolean flag = true;
        try {
            if(flag) {
                flag = false;

                HashMap<String, Object> params = new HashMap<>();
                params.put("keytext",keyword);
                httpAPIService = SpringUtil.getBean("httpAPIService",HttpAPIService.class);
                String result = httpAPIService.doGet("http://m.mm131.com/xinggan/",params,"UTF-8");
                if(StringUtils.isEmpty(result)) return;

                /*String content = result.substring(result.indexOf("<content "), result.indexOf("</content>")+10);
                System.out.println(content);*/

                //Document document2 = Jsoup.connect(result).get();
                Document doc = Jsoup.parse(result);
                Element content = doc.getElementById("content");
                Elements links = content.getElementsByTag("a");
                for (Element link : links) {
                    String linkHref = link.attr("href");
                    String linkText = link.text();

                    //System.out.println(linkHref+linkText);
                    if(StringUtils.isEmpty(linkText)) {
                        doc= Jsoup.connect(linkHref).get();
                        content = doc.getElementById("content");
                        links = content.getElementsByTag("img");

                        for (Element linkChild : links) {
                            linkHref = linkChild.attr("src");
                            //linkText = linkChild.text();
                            //System.out.println(linkHref+linkText);

                            Element masthead = doc.select("span.rw").first();
                            String text = masthead.text();
                            text = text.substring(text.indexOf("/")+1,text.indexOf("页"));
                            Integer totalPage = Integer.valueOf(text);
                            for (int i=1;i<=totalPage;i++) {
                                linkHref = linkHref.substring(0,linkHref.lastIndexOf("/")+1)+i+linkHref.substring(linkHref.lastIndexOf("."),linkHref.length());

                                //download(linkHref,i+linkHref.substring(linkHref.lastIndexOf("."),linkHref.length()),"D:\\ideaWorkspace\\holywe\\target");
                                //getImg(linkHref);
                            }
                        }
                    }
                }
/* <div class="paging">
   <span class="rw">1/48页</span>*/
                flag = true;
            }
        }catch (Exception e) {
            e.printStackTrace();
            flag = true;
        }
    }


    /**
     *
     * @Title: getImg
     * @Description: 通过一个url 去获取图片
     * @param @param url 图片的连接地址
     * @param @throws IOException
     * @throws
     */
    public static void getImg(String url) throws IOException {
        long startTime = System.currentTimeMillis();
        URL imgURL = new URL(url.trim());//转换URL
        HttpURLConnection urlConn = (HttpURLConnection) imgURL.openConnection();//构造连接
        urlConn.setRequestProperty("User-Agent", "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.79 Mobile Safari/537.36");
        urlConn.setRequestMethod("GET");
        urlConn.setRequestProperty("Upgrade-Insecure-Requests","1");
        urlConn.setRequestProperty("X-DevTools-Emulate-Network-Conditions-Client-Id", UUID.randomUUID().toString());
        urlConn.setConnectTimeout(60*1000);
        urlConn.setReadTimeout(60*1000);
        urlConn.setDoOutput(true);
        urlConn.setDoInput(true);
        urlConn.connect();
        System.out.println(MM131Task.class.toString()+":获取连接="+urlConn.getResponseMessage());
        if(urlConn.getResponseCode()==200){//返回的状态码是200 表示成功
            InputStream ins = urlConn.getInputStream(); //获取输入流,从网站读取数据到 内存中
            OutputStream out = new BufferedOutputStream(new FileOutputStream(new File("D:\\ideaWorkspace\\holywe\\target\\TEST.jpg")));
            int len=0;
            byte[] buff = new byte[1024*10];//10k缓冲流 视你内存大小而定咯

            while(-1!=(len=(new BufferedInputStream(ins)).read(buff))){//长度保存到len,内容放入到 buff
                out.write(buff, 0, len);//将图片数组内容写入到图片文件
//              System.out.println(CatchIMG.class.toString()+":"+len+"byte已经写入到文件中，内容:  "+new String(buff));
            }
            urlConn.disconnect();
            ins.close();
            out.close();
            System.out.println(MM131Task.class.toString()+"：获取图片完成,耗时="+((System.currentTimeMillis()-startTime)/1000)+"s");
        }
    }

}
