package com.huan.springboottest.common.util;


import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: ${Description}
 * @Author: Huan
 * @CreateTime: 2019-02-17 14:46
 */
public class HttpUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpUtil.class);

    /**
     *
     * @param httpMethod
     * @param headMap 请求头
     * @param paramMap 请求参数 GET和POST都适用，当然GET可以直接拼接参数
     * @return 链接在使用后需要关闭
     */
    private static HttpMethod doHttp(HttpMethod httpMethod, Map<String, String> headMap,Map<String,String> paramMap){
        // 1.生成 HttpClinet 对象并设置参数
        HttpClient httpClient = new HttpClient();
        // 设置 HTTP 连接超时 5s
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
        httpMethod.setRequestHeader("User-Agent","Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt");
        if (headMap != null) {
            for (Map.Entry<String, String> entry : headMap.entrySet()) {
                httpMethod.setRequestHeader(entry.getKey(), entry.getValue());
            }
        }
        // 设置请求参数
        if(httpMethod instanceof GetMethod){
            handleGetParam(httpMethod,paramMap);
        }else if(httpMethod instanceof PostMethod){
            handlePostParam(httpMethod,paramMap);
        }
        // 设置 get 请求超时 5s
        httpMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 5000);
        // 设置请求重试处理
        httpMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
        // 3.执行 HTTP GET 请求
        try {
            int statusCode = httpClient.executeMethod(httpMethod);
            // 判断访问的状态码
            if (statusCode != HttpStatus.SC_OK) {
                LOGGER.warn("远程调用返回状态码："+statusCode+""+httpMethod.getStatusLine());
            }
            return httpMethod;
        } catch (HttpException e) {
            // 发生致命的异常，可能是协议不对或者返回的内容有问题
            LOGGER.error("Please check your provided http address!"+e.getMessage());
        } catch (IOException e) {
            // 发生网络异常
            LOGGER.error("网络异常"+e.getMessage());
        } finally {
        }
        return null;
    }

    private static void handleGetParam(HttpMethod getMethod, Map<String,String> paramMap){
        if(paramMap == null || paramMap.isEmpty()){
            return;
        }
        String path = null;
        try {
            path = getMethod.getURI().toString();
        } catch (URIException e) {
            LOGGER.error("uri获取异常"+e.getMessage());
            return;
        }

        if(StringUtil.isNull(path)){
            return;
        }
        System.err.println(path);
        if(path.contains("?")){
            path += "&";
        }else{
            path += "?";
        }
        StringBuilder sb = new StringBuilder(path);
        for(Map.Entry<String,String> entry : paramMap.entrySet()){
            sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        path = sb.subSequence(0,sb.length()-1).toString();
        getMethod.setPath(path);
        System.err.println(getMethod.getPath());
    }

    private static void handlePostParam(HttpMethod httpMethod, Map<String,String> paramMap){
        if(paramMap == null || paramMap.isEmpty()){
            return;
        }
        List<NameValuePair> nameValuePairList = new ArrayList<>(paramMap.size());
        for(Map.Entry<String,String> entry : paramMap.entrySet()){
            NameValuePair nameValuePair = new NameValuePair(entry.getKey(),entry.getValue());
            nameValuePairList.add(nameValuePair);
        }
        httpMethod.setQueryString(nameValuePairList.toArray(new NameValuePair[paramMap.size()]));
    }

    /**
     * 解析响应信息
     *
     * @return
     */
    private static String parseResponse(HttpMethod httpMethod){
        InputStreamReader isr = null;
        try {
            InputStream in = httpMethod.getResponseBodyAsStream();
            // 4.处理 HTTP 响应内容
            isr = new InputStreamReader(in,"utf-8");
            char[] data = new char[100];
            String result = "";
            int i = -1;
            while ((i = isr.read(data)) != -1){
                result += String.valueOf(data,0,i);
            }
            return result;
        } catch (IOException e){
            LOGGER.error(""+e.getMessage());
        } finally {
            // 释放连接
            httpMethod.releaseConnection();
            try {
                if(isr != null){
                    isr.close();
                }
            } catch (IOException e) {
                LOGGER.error("流关闭异常"+e.getMessage());
            } finally {
                isr = null;
            }
        }
        return "";
    }


    public static String doGet(String url,Map<String,String> headMap,Map<String,String> paramMap){
        HttpMethod getMethod = doHttp(new GetMethod(url),headMap,paramMap);
        return parseResponse(getMethod);
    }

    /**
     *
     * @param url 请求连接
     * @param headMap 请求头
     * @return
     */
    public static String doGet(String url, Map<String, String> headMap) {
        return doGet(url,null, headMap);
    }

    public static String doGet(String url){
        return doGet(url,null);
    }

    public static String doPost(String url, Map<String,String> headMap,Map<String,String> paramMap){
        HttpMethod postMethod = doHttp(new PostMethod(url),headMap,paramMap);
        return parseResponse(postMethod);
    }

    public static String doPost(String url, Map<String, String> headMap){
        return doPost(url,headMap,null);
    }

    public static String doPost(String url){
        return doPost(url,null);
    }


    /**
     * 获得响应头的Set-Cookie
     *
     * @param cookie
     * @param url
     * @return
     */
    public static String getCookieByGet(String cookie,String url){
        if(StringUtil.isNull(cookie) || StringUtil.isNull(url)){
            throw new NullPointerException("cookie或者url为空");
        }
        Map<String,String> map = new HashMap<>();
        map.put("CooKie","cck_lasttime="+System.currentTimeMillis()+";cck_count=1");
        map.put("Origin","http://kscx.hbee.edu.cn:9012");
        map.put("Referer","http://kscx.hbee.edu.cn:9012/zk/zkcj201704ap");
        HttpMethod getMethod = doHttp(new GetMethod(url),map,null);

        Header header = getMethod.getResponseHeader("Set-Cookie");
        HeaderElement[] headerElements = header.getElements();
        System.err.println(Arrays.toString(headerElements));
        for (int i = 0;i<headerElements.length;i++) {
            if(headerElements[i].getName().equals(cookie)){
                return headerElements[i].getValue();
            }
        }
        return "";
    }

    public static void main(String[] args) {
        String validateCode = getCookieByGet("ValidateCode", "http://kscx.hbee.edu.cn:9012/Validate/GetValidateCode");
        System.err.println(validateCode);

        Map<String,String> map = new HashMap<>();

        map.put("CooKie","validateCode="+validateCode+"; cck_lasttime="+System.currentTimeMillis()+";cck_count=1");
        map.put("Origin","http://kscx.hbee.edu.cn:9012");
        map.put("Referer","http://kscx.hbee.edu.cn:9012/zk/zkcj201704ap");
        Map<String,String> paramMap = new HashMap<>();
        paramMap.put("ksbh","1233");
        //String s = doPost("http://kscx.hbee.edu.cn:9012/zk/zkcj201704ap", map);
        String s = doPost("http://127.0.0.1:8080/service/card/post", map,paramMap);
        System.err.println(s);

    }

}
