package com.huan.springboottest.common.util;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.NTCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * @Author: wb_xugz
 * @CreateTime: 2020-06-02 14:24
 */
public class HttpUtil {

    public static void main(String[] args) {

        String file = "http://basweb.klsz.com/WebSite/ProSite/Doc/GetAttach?AttachId=b51d38172c9a486b8e49780d27023ca1";
        //get();
        //get2();
        get3(file);
        System.out.println("OK");
    }

    private static void get() {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        // 创建Get请求
        HttpGet httpGet = new HttpGet("https://www.bejson.com/");

        // 响应模型
        CloseableHttpResponse response = null;
        try {
            // 由客户端执行(发送)Get请求
            response = httpClient.execute(httpGet);
            // 从响应模型中获取响应实体
            HttpEntity responseEntity = response.getEntity();
            System.out.println("响应状态为:" + response.getStatusLine());
            if (responseEntity != null) {
                System.out.println("响应内容长度为:" + responseEntity.getContentLength());
                System.out.println(responseEntity.getContentEncoding());
                System.out.println("响应内容为:" + EntityUtils.toString(responseEntity, "utf-8"));
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // 释放资源
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void get2() {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        // 创建Get请求
        HttpGet httpGet = new HttpGet("http://basweb.klsz.com/WebSite/PicUpload/6366683683478246944867205.jpg");

        // 响应模型
        CloseableHttpResponse response = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            // 由客户端执行(发送)Get请求
            response = httpClient.execute(httpGet);
            // 从响应模型中获取响应实体
            HttpEntity responseEntity = response.getEntity();
            System.out.println("响应状态为:" + response.getStatusLine());
            if (responseEntity != null) {
                InputStream content = responseEntity.getContent();

                byte[] data = new byte[1024];
                int i = -1;
                while ((i = content.read(data)) != -1) {
                    baos.write(data, 0, i);
                }
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // 释放资源
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(Arrays.toString(baos.toByteArray()));
        }
    }

    private static void get3(String url) {
        NTCredentials creds = new NTCredentials("wb_xugz", "aq7drd",
                "", "");
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, creds);

        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setConnectionTimeToLive(4000L, TimeUnit.MILLISECONDS)
                .setDefaultCredentialsProvider(credentialsProvider)
                .build();
        // 创建Get请求
        HttpGet httpGet = new HttpGet(url);

        // 响应模型
        CloseableHttpResponse response = null;
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream("附则.docx");
            // 由客户端执行(发送)Get请求
            response = httpClient.execute(httpGet);
            // 从响应模型中获取响应实体
            HttpEntity responseEntity = response.getEntity();
            System.out.println("响应状态为:" + response.getStatusLine());
            if (responseEntity != null) {
                InputStream content = responseEntity.getContent();
                byte[] data = new byte[1024 * 1024];
                int i = -1;
                while ((i = content.read(data))!= -1){
                   fos.write(data, 0 ,i);
                }
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            httpGet.releaseConnection();
            try {
                // 释放资源
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
                if(fos != null){
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
