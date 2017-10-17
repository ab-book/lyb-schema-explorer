package http.client;

import com.alibaba.fastjson.JSON;
import http.ApiResponse;
import http.HttpInvokeCallable;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.*;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author liyebing created on 16/12/4.
 * @version $Id$
 */
public class HttpClientInvoke {

    @Test
    public void test1() throws Exception {
        //构建HttpGet对象
        String uriGet = "http://localhost:8080/hello/sayHello.json?userName=liyebing";
        HttpGet httpGet = new HttpGet(uriGet);

        //构建HttpPost对象
        String uriPost = "http://localhost:8080/hello/sayHello.json";
        HttpPost httpPost = new HttpPost(uriPost);
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("userName", "liyebing"));
        httpPost.setEntity(new UrlEncodedFormEntity(nvps));

    }


    @Test
    public void testHttpGet() throws Exception {

        //构建请求的URL
        URI uri = new URIBuilder()
                .setScheme("http")
                .setHost("localhost")
                .setPort(8080)
                .setPath("/hello/sayHello.json")
                .setParameter("userName", "liyebing")
                .build();

        //构建httpGet
        HttpGet httpGet = new HttpGet(uri);

        //请求http接口,并获取结果
        CloseableHttpClient httpclient = HttpClients.createDefault();
        ApiResponse response = httpclient.execute(httpGet, new ResponseHandler<ApiResponse>() {
            public ApiResponse handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
                String result = EntityUtils.toString(response.getEntity(), Charset.forName("utf-8"));
                return JSON.parseObject(result, ApiResponse.class);
            }
        });
        //打印结果
        if (response.isSuccess()) {
            System.out.println(response.getData());
        }
    }

    @Test
    public void testFluentApi_1() throws IOException {

        String uri = "http://localhost:8080/hello/sayHello.json?userName=liyebing";
        String returnResult = Request.Get(uri)
                .execute().returnContent().asString(Charset.forName("utf-8"));

        ApiResponse response = JSON.parseObject(returnResult, ApiResponse.class);
        System.out.println(response.getData());
    }


    @Test
    public void testFluentApi_2() throws IOException {

        String uri = "http://localhost:8080/hello/sayHello.json?userName=liyebing";
        ApiResponse response = Request.Get(uri)
                .execute().handleResponse(new ResponseHandler<ApiResponse>() {
                    public ApiResponse handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
                        String result = EntityUtils.toString(response.getEntity(), Charset.forName("utf-8"));
                        return JSON.parseObject(result, ApiResponse.class);
                    }
                });
        System.out.println(response.getData());
    }

    @Test
    public void testHttpContext() throws Exception {
        HttpContext context = new BasicHttpContext();
        HttpClientContext clientContext = HttpClientContext.adapt(context);

        String userName = "liyebing";
        URI uri = new URIBuilder().setScheme("http").setHost("localhost")
                .setPort(8080).setPath("/hello/sayHello.json").setParameter("userName", userName).build();

        //第一次请求
        CloseableHttpClient httpclient1 = HttpClients.createDefault();
        ApiResponse response = httpclient1.execute(new HttpGet(uri), new ResponseHandler<ApiResponse>() {
            public ApiResponse handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
                String result = EntityUtils.toString(response.getEntity(), Charset.forName("utf-8"));
                return JSON.parseObject(result, ApiResponse.class);
            }
        }, clientContext);
        System.out.println(response.getData());


        //第二次请求
        CloseableHttpClient httpclient2 = HttpClients.createDefault();
        response = httpclient2.execute(new HttpGet(uri), new ResponseHandler<ApiResponse>() {
            public ApiResponse handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
                String result = EntityUtils.toString(response.getEntity(), Charset.forName("utf-8"));
                return JSON.parseObject(result, ApiResponse.class);
            }
        }, clientContext);
        System.out.println(response.getData());
    }


    @Test
    public void multiThreadHttpExecution() throws Exception {
        //连接管理,设置最大连接数
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(10);

        //获取httpClient对象
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm).build();

        //创建请求的URL列表
        List<String> urisToGet = new ArrayList<String>();
        for (int i = 0; i < 100; i++) {
            urisToGet.add("http://localhost:8080/hello/sayHello.json?userName=liyebing_" + i);
        }


        //多线程并发请求httpClient
        int uriSize = urisToGet.size();
        CountDownLatch countDownLatch = new CountDownLatch(uriSize);
        CompletionService<ApiResponse> completionService = new ExecutorCompletionService<ApiResponse>(Executors.newFixedThreadPool(10));
        for (String uri : urisToGet) {
            completionService.submit(new HttpInvokeCallable(httpClient, new HttpGet(uri), countDownLatch));
        }
        countDownLatch.await();

        //获取多线程请求的结果
        for (int i = 0; i < uriSize; i++) {
            ApiResponse response = completionService.take().get();
            System.out.println(response.getData());
        }
    }


    @Test
    public void testFutureRequest() throws Exception {
        HttpClient httpClient = HttpClientBuilder.create().setMaxConnPerRoute(2).build();
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        FutureRequestExecutionService futureRequestExecutionService = new FutureRequestExecutionService(httpClient, executorService);

        String url = "http://localhost:8080/hello/sayHello.json?userName=liyebing";
        HttpRequestFutureTask<ApiResponse> task = futureRequestExecutionService.execute(new HttpGet(url), HttpClientContext.create(), new ResponseHandler<ApiResponse>() {
            public ApiResponse handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
                String result = EntityUtils.toString(response.getEntity(), Charset.forName("utf-8"));
                return JSON.parseObject(result, ApiResponse.class);
            }
        });

        ApiResponse response = task.get();
        System.out.println(response.getData());

    }


    @Test
    public void testAsynHttpClient() throws Exception {
        CloseableHttpAsyncClient httpclient = HttpAsyncClients.createDefault();
        try {
            httpclient.start();
            String url = "http://localhost:8080/hello/sayHello.json?userName=liyebing";
            HttpGet request = new HttpGet(url);
            Future<HttpResponse> future = httpclient.execute(request, null);
            HttpResponse response = future.get();
            String result = EntityUtils.toString(response.getEntity(), Charset.forName("utf-8"));
            ApiResponse apiResponse = JSON.parseObject(result, ApiResponse.class);

            if (apiResponse.isSuccess()) {
                System.out.println(apiResponse.getData());
            }
        } finally {
            httpclient.close();
        }
    }
}
