//package com.huan.springboottest.common.util;
//
//
//import com.huan.springboottest.common.exception.BaseException;
//import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
//import org.apache.commons.httpclient.HttpClient;
//import org.apache.commons.httpclient.HttpException;
//import org.apache.commons.httpclient.HttpMethod;
//import org.apache.commons.httpclient.HttpStatus;
//import org.apache.commons.httpclient.NTCredentials;
//import org.apache.commons.httpclient.NameValuePair;
//import org.apache.commons.httpclient.URIException;
//import org.apache.commons.httpclient.methods.GetMethod;
//import org.apache.commons.httpclient.methods.PostMethod;
//import org.apache.commons.httpclient.params.HttpMethodParams;
//import org.apache.http.auth.AuthScope;
//import org.apache.http.auth.Credentials;
//import org.apache.http.client.CredentialsProvider;
//import org.apache.http.impl.client.BasicCredentialsProvider;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.impl.client.HttpClientBuilder;
//import org.apache.http.impl.client.HttpClients;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import javax.imageio.ImageIO;
//import java.awt.image.BufferedImage;
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.File;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.net.URI;
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// *
// * 旧版httpClient
// * @Description: ${Description}
// * @Author: Huan
// * @CreateTime: 2019-02-17 14:46
// */
//public class HttpUtil {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(HttpUtil.class);
//
//    /**
//     * @param httpMethod
//     * @param headMap    请求头
//     * @param paramMap   请求参数 GET和POST都适用，当然GET可以直接拼接参数
//     * @return 链接在使用后需要关闭
//     */
//    private static HttpMethod doHttp(HttpMethod httpMethod, Map<String, String> headMap, Map<String, String> paramMap) {
//        // 1.生成 HttpClinet 对象并设置参数
//        HttpClient httpClient = new HttpClient();
//        // 设置 HTTP 连接超时 5s
//        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
//        //httpMethod.setRequestHeader("User-Agent","Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt");
//        if (headMap != null) {
//            for (Map.Entry<String, String> entry : headMap.entrySet()) {
//                httpMethod.setRequestHeader(entry.getKey(), entry.getValue());
//            }
//        }
//        // 设置请求参数
//        if (httpMethod instanceof GetMethod) {
//            handleGetParam(httpMethod, paramMap);
//        } else if (httpMethod instanceof PostMethod) {
//            handlePostParam(httpMethod, paramMap);
//        }
//        // 设置 get 请求超时 5s
//        httpMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 5000);
//        // 设置请求重试处理
//        httpMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
//
////        NTCredentials ntCredentials = new NTCredentials("wb_xugz", "aq7drd", "", "");
////        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
////        CredentialsProvider credsProvider = new BasicCredentialsProvider();
////
////        NTCredentials creds = new NTCredentials("user", "password", "", "");
////        credsProvider.setCredentials();
////        credsProvider.setCredentials(new AuthScope("10.0.10.76", 81, null, "ntlm"), creds);
////        CloseableHttpClient httpclient = HttpClients.custom()
////                .setDefaultCredentialsProvider(credsProvider)
////                .build();
//
//        // 3.执行 HTTP GET 请求
//        try {
//            int statusCode = httpClient.executeMethod(httpMethod);
//            // 判断访问的状态码
//            if (statusCode != HttpStatus.SC_OK) {
//                LOGGER.warn("远程调用返回状态码：" + statusCode + "" + httpMethod.getStatusLine());
//            }
//            return httpMethod;
//        } catch (HttpException e) {
//            // 发生致命的异常，可能是协议不对或者返回的内容有问题
//            LOGGER.error("Please check your provided http address!" + e.getMessage());
//        } catch (IOException e) {
//            // 发生网络异常
//            LOGGER.error("网络异常" + e.getMessage());
//        } finally {
//        }
//        return null;
//    }
//
//    private static void handleGetParam(HttpMethod getMethod, Map<String, String> paramMap) {
//        if (paramMap == null || paramMap.isEmpty()) {
//            return;
//        }
//        String path = null;
//        try {
//            path = getMethod.getURI().toString();
//        } catch (URIException e) {
//            LOGGER.error("uri获取异常" + e.getMessage());
//            return;
//        }
//
//        if (StringUtil.isNull(path)) {
//            return;
//        }
//        System.err.println(path);
//        if (path.contains("?")) {
//            path += "&";
//        } else {
//            path += "?";
//        }
//        StringBuilder sb = new StringBuilder(path);
//        for (Map.Entry<String, String> entry : paramMap.entrySet()) {
//            sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
//        }
//        path = sb.subSequence(0, sb.length() - 1).toString();
//        getMethod.setPath(path);
//        System.err.println(getMethod.getPath());
//    }
//
//    private static void handlePostParam(HttpMethod httpMethod, Map<String, String> paramMap) {
//        if (paramMap == null || paramMap.isEmpty()) {
//            return;
//        }
//        List<NameValuePair> nameValuePairList = new ArrayList<>(paramMap.size());
//        for (Map.Entry<String, String> entry : paramMap.entrySet()) {
//            NameValuePair nameValuePair = new NameValuePair(entry.getKey(), entry.getValue());
//            nameValuePairList.add(nameValuePair);
//        }
//        httpMethod.setQueryString(nameValuePairList.toArray(new NameValuePair[paramMap.size()]));
//    }
//
//    /**
//     * 解析响应信息
//     * @return
//     */
//    private static String parseResponse(HttpMethod httpMethod) {
//        InputStreamReader isr = null;
//        try {
//            InputStream in = httpMethod.getResponseBodyAsStream();
//            // 4.处理 HTTP 响应内容
//            isr = new InputStreamReader(in, "utf-8");
//            char[] data = new char[100];
//            String result = "";
//            int i = -1;
//            while ((i = isr.read(data)) != -1) {
//                result += String.valueOf(data, 0, i);
//            }
//            return result;
//        } catch (IOException e) {
//            LOGGER.error("" + e.getMessage());
//        } finally {
//            // 释放连接
//            httpMethod.releaseConnection();
//            try {
//                if (isr != null) {
//                    isr.close();
//                }
//            } catch (IOException e) {
//                LOGGER.error("流关闭异常" + e.getMessage());
//            } finally {
//                isr = null;
//            }
//        }
//        return "";
//    }
//
//
//    public static String doGet(String url, Map<String, String> headMap, Map<String, String> paramMap) {
//        HttpMethod getMethod = doHttp(new GetMethod(url), headMap, paramMap);
//        return parseResponse(getMethod);
//    }
//
//
//    /**
//     * @param url     请求连接
//     * @param headMap 请求头
//     * @return
//     */
//    public static String doGet(String url, Map<String, String> headMap) {
//        return doGet(url, null, headMap);
//    }
//
//    public static byte[] doGetImage(String url) {
//        HttpMethod getMethod = doHttp(new GetMethod(url), null, null);
//        try {
//            return getMethod.getResponseBody();
//        } catch (IOException e) {
//            LOGGER.error("", e);
//            throw BaseException.of("返回结果解析异常");
//        }
//    }
//
//
//    public static String doGet(String url) {
//        return doGet(url, null);
//    }
//
//    public static String doPost(String url, Map<String, String> headMap, Map<String, String> paramMap) {
//        HttpMethod postMethod = doHttp(new PostMethod(url), headMap, paramMap);
//        return parseResponse(postMethod);
//    }
//
//    public static String doPost(String url, Map<String, String> headMap) {
//        return doPost(url, headMap, null);
//    }
//
//    public static String doPost(String url) {
//        return doPost(url, null);
//    }
//
//
//    /**
//     * 获得响应头的Set-Cookie TODO
//     * @param cookie
//     * @param url
//     * @return
//     */
//    public static byte[] getCookieByGet(String cookie, String url) {
//        if (StringUtil.isNull(cookie) || StringUtil.isNull(url)) {
//            throw new NullPointerException("cookie或者url为空");
//        }
//        Map<String, String> map = new HashMap<>();
//        map.put("CooKie", cookie);
//        HttpMethod getMethod = doHttp(new GetMethod(url), map, null);
//        try {
//            return getMethod.getResponseBody();
//        } catch (IOException e) {
//            LOGGER.error("", e);
//            throw BaseException.of("");
//        }
//    }
//
//    public static void main(String[] args) throws Exception {
//        //testURLImage();
//        //testURLOaImage();
////        byte[] data = getCookieByGet("JSESSIONID=aaa69K8D7nbNShQ0tg3Zw", "http://oa.klsz.com/weaver/weaver.file.FileDownload?fileid=106691");
//////        BufferedImage read = ImageIO.read(new ByteArrayInputStream(data));
//////        System.out.println(read.getHeight());
//        getProvision("b51d38172c9a486b8e49780d27023ca1");
//
//
//    }
//
//    public static void getProvision(String attachId) throws Exception {
//        String url = "http://basweb.klsz.com/WebSite/ProSite/Doc/GetAttach?AttachId=" + attachId;
//        System.out.println(url);
//        Map<String, String> map = new HashMap<>();
//        map.put("Cookie", "ASP.NET_SessionId=oy5vbgs44uhkf135o2gigg2d");
//        byte[] data = getCookieByGet("ASP.NET_SessionId=oy5vbgs44uhkf135o2gigg2d", url);
//    }
//
//    public static void testURLOaImage() throws Exception {
//        URL url = new URL("http://oa.klsz.com/weaver/weaver.file.FileDownload?fileid=106691");
//        BufferedImage image = ImageIO.read(url);
//        System.out.println(image.getHeight());
//        //doGetImage(url)
//    }
//
//    private static void testURLImage() throws Exception {
//        String url = "http://basweb.klsz.com/WebSite/PicUpload/6366683683478246944867205.jpg";
//        File file = new File(url);
//        URI uri = file.toURI();
//        URL url2 = uri.toURL();
//        url2.openStream();
//
//        //byte[] bytes = doGetImage(url);
//        BufferedImage image = ImageIO.read(new URL(url));
//        System.out.println(image.getHeight());
//        System.out.println(image.getType());
//        ByteArrayOutputStream os = new ByteArrayOutputStream();
//        ImageIO.write(image, "png", os);
//        InputStream input = new ByteArrayInputStream(os.toByteArray());
//    }
//
//
//}
