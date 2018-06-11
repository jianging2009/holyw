package com.holyw.holyw.common;


import org.springframework.util.CollectionUtils;

import javax.net.ssl.*;
import java.io.*;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;

/**
 * 公用的调用https接口类，支持有域名、无域名的Get Post请求
 */
public class HttpsApiService {

    private static final class DefaultTrustManager implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    }
    /**
     * @Description GET
     * @author Denchy Chiang
     * @date 12:33 2018/5/29
     * @param uri
     * @return
     */
    public static String doGet(String uri) throws IOException {
        HttpsURLConnection httpsConn = getHttpsURLConnection(uri, "GET");
        byte[] bytesFromStream = getBytesFromStream(httpsConn.getInputStream());
        String result = new String(bytesFromStream, "UTF-8");
        httpsConn.disconnect();
        return result;
    }
    /**
     * @Description GET
     * @author Denchy Chiang
     * @date 12:33 2018/5/29
     * @param uri
     * @return
     */
    public static String doGet(String uri,Map<String,Object> params) throws IOException {
        uri +="?";
        if(!CollectionUtils.isEmpty(params)) {
            for (Map.Entry<String,Object> entry:params.entrySet()) {
                uri += (entry.getKey()+"="+entry.getValue().toString()+"&");
            }
        }
        return doGet(uri);
    }
    /**
     * @Description POST
     * @author Denchy Chiang
     * @date 12:33 2018/5/29
     * @param uri
     * @param header
     * @param params
     * @param data
     * @return
     */
    public static String doPost(String uri,Map<String,String> header, Map<String,Object> params,String data) throws Exception {
        String queryParams = "";
        if(!CollectionUtils.isEmpty(params)) {
            queryParams = "?";
            for (Map.Entry<String,Object> entry:params.entrySet()) {
                queryParams += (entry.getKey()+"="+entry.getValue()+"&");
            }
        }

        HttpsURLConnection httpsConn = getHttpsURLConnection(uri+queryParams, "POST");
        if(!CollectionUtils.isEmpty(header)) {
            for (Map.Entry<String,String> entry:header.entrySet()) {
                httpsConn.setRequestProperty(entry.getKey(),entry.getValue());
            }
        }

        /*Origin: chrome-extension://odnlkfnjocohjompflgjjkejgcbhoikd
        app_id: peedap.jy
        vuid: _vuid_
        times: 1527647728
        User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.181 Safari/537.36
        sign: 2f0c56d2bac30851a7cbfbe6543fa68f
        Content-Type: application/json
        Accept-Encoding: gzip, deflate, br
        Accept-Language: zh-CN,zh;q=0.9
        Cookie: BIGipServerPOOL_PACLOUD_STGR2017111508728=3653468375.22815.0000*/

        httpsConn.setRequestProperty("Origin", "chrome-extension://odnlkfnjocohjompflgjjkejgcbhoikd");
        httpsConn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.181 Safari/537.36");
        httpsConn.setRequestProperty("Content-Type", "application/json");
        httpsConn.setRequestProperty("Accept-Encoding", "gzip, deflate, br");
        httpsConn.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.9");
        httpsConn.setRequestProperty("Accept", "*/*");
        //httpsConn.setRequestProperty("Charset", "UTF-8");
        httpsConn.connect();

        /*String requestMethod = httpsConn.getRequestMethod();
        System.out.println(requestMethod);*/
        OutputStream outputStream = httpsConn.getOutputStream();
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, "UTF-8");
        outputStreamWriter.write(data);
        outputStreamWriter.flush();
        outputStreamWriter.close();
        //setBytesToStream(httpsConn.getOutputStream(), null == data ? "".getBytes() : data.getBytes());
        byte[] bytesFromStream = getBytesFromStream(httpsConn.getInputStream());
        httpsConn.disconnect();
        return new String(bytesFromStream);//utf-8
    }

    private static HttpsURLConnection getHttpsURLConnection(String uri, String method) throws IOException {
        SSLContext ctx = null;
        try {
            ctx = SSLContext.getInstance("TLS","SunJSSE");
            ctx.init(new KeyManager[0], new TrustManager[] { new DefaultTrustManager() }, new SecureRandom());
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }
        SSLSocketFactory ssf = ctx.getSocketFactory();

        URL url = new URL(uri);
        HttpsURLConnection httpsConn = (HttpsURLConnection) url.openConnection();
        httpsConn.setSSLSocketFactory(ssf);
        httpsConn.setHostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String arg0, SSLSession arg1) {
                return true;
            }
        });
        httpsConn.setRequestMethod(method);
        httpsConn.setDoInput(true);
        httpsConn.setDoOutput(true);
        return httpsConn;
    }

    private static byte[] getBytesFromStream(InputStream is) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] kb = new byte[1024];
        int len;
        while ((len = is.read(kb)) != -1) {
            baos.write(kb, 0, len);
        }
        byte[] bytes = baos.toByteArray();
        baos.close();
        is.close();
        return bytes;
    }

    private static void setBytesToStream(OutputStream os, byte[] bytes) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        byte[] kb = new byte[1024];
        int len;
        while ((len = bais.read(kb)) != -1) {
            os.write(kb, 0, len);
        }
        os.flush();
        os.close();
        bais.close();
    }
}
