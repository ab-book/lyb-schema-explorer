package http;

import com.alibaba.fastjson.JSON;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

/**
 * @author liyebing created on 16/12/22.
 * @version $Id$
 */
public class HttpInvokeCallable implements Callable<ApiResponse> {

    private final CloseableHttpClient httpClient;
    private final HttpGet httpGet;
    private final CountDownLatch countDownLatch;

    public HttpInvokeCallable(CloseableHttpClient httpClient, HttpGet httpGet, CountDownLatch countDownLatch) {
        this.httpClient = httpClient;
        this.httpGet = httpGet;
        this.countDownLatch = countDownLatch;
    }


    public ApiResponse call() throws Exception {
        try {
            return httpClient.execute(httpGet, new ResponseHandler<ApiResponse>() {
                public ApiResponse handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
                    String result = EntityUtils.toString(response.getEntity(), Charset.forName("utf-8"));
                    return JSON.parseObject(result, ApiResponse.class);
                }
            });
        } finally {
            countDownLatch.countDown();
        }
    }
}
