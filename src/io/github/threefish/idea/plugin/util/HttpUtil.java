package io.github.threefish.idea.plugin.util;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;

/**
 * @author 黄川 huchuc@vip.qq.com
 * date: 2018/9/3
 */
public class HttpUtil {

    private static final String HEADER_VALUE = "NutzCodeInsight";

    private static final String HEADER_NAME = "maker_origin";

    public static String post(String url, BasicHttpEntity entity) throws IOException {
        HttpClient httpclient = HttpClients.createDefault();
        HttpPost post = new HttpPost(url);
        post.addHeader(HEADER_NAME, HEADER_VALUE);
        post.setEntity(entity);
        HttpResponse response = httpclient.execute(post);
        return FileUtil.ioToString(response.getEntity().getContent(), "UTF-8");
    }

    public static HttpResponse getResponse(String url) throws IOException {
        HttpClient httpclient = HttpClients.createDefault();
        HttpGet get = new HttpGet(url);
        get.addHeader(HEADER_NAME, HEADER_VALUE);
        return httpclient.execute(get);
    }

    public static String get(String url) throws IOException {
        HttpResponse response = getResponse(url);
        return FileUtil.ioToString(response.getEntity().getContent(), "UTF-8");
    }


}
