package cn.pengj.tpcdemo1;

import com.xkeshi.utils.Encryptors;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/2/24.
 */
public class demoSms {


    /*public static void main(String[] args) {
        MessageSendApi mes = new MessageSendApi();
        mes.setServerAddress("http://push.test.xka.so:8082");
        mes.setPath("/api/message/sms/send");
        mes.setMessagePlatformClientId("1");
        mes.setMessagePlatformUsername("admin");
        mes.setMessagePlatformPassword("123456a");
        mes.setMessagePlatformSecret("45a94772b9704cfe91408d4a216d72d5");

        SmsSingleMessageRequest request = new SmsSingleMessageRequest();
        request.setUserName("admin");
        request.setMobile("18258181557");
        request.setCustomMsg("1111111111111111");
        request.setChannelName("xkeshi_yx");
        request.setContent("短信测试：营销模块系统功能说明");

        Result result = mes.smsSend(request);
        System.out.println(result);
    }*/


    public static void main(String[] args) throws Exception{
            System.out.println("start to post");
            CloseableHttpResponse response = null;
            String charset = "UTF-8";
            String contentType = "application/x-www-form-urlencoded";
            String url = "http://192.168.146.80:8080/api/message/sms/send";

            String sign = Encryptors.MD5.md5("/api/message/sms/send&password="
                    + Encryptors.MD5.md5("123456a")+ "&secret=45a94772b9704cfe91408d4a216d72d5");
            try {
                System.out.println("start to get httpclient");
                CloseableHttpClient client = HttpClients.createDefault();
                HttpPost httpPost = new HttpPost(url);
                httpPost.addHeader("Content-Type", contentType);
                httpPost.addHeader("charset", charset);
                httpPost.addHeader("signType", "MD5");
                httpPost.addHeader("sign", sign);



                Map<String, String> dtoMap = new HashMap<String, String>();
                dtoMap.put("userName", "xkeshi_test");
                dtoMap.put("clientId", "1");
                dtoMap.put("data","{\"userName\":\"xkeshi_test\",\"mobile\":\"18258181557\",\"customMsg\":\"c3ff646745478bf57e74\",\"content\":\"爱客仕运维测试短信 多谢配合\",\"channelName\":\"xkeshi_yx\",\"executeType\":\"IMMEDIATE\"}");
                dtoMap.put("type","SINGLE");
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                for (String key : dtoMap.keySet()) {
                    params.add(new BasicNameValuePair(key, dtoMap.get(key)));
                }
                UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(params, charset);
                httpPost.setEntity(urlEncodedFormEntity);

                System.out.println("start to execute");
                response = client.execute(httpPost);
                System.out.println("start to handle response");

                HttpEntity entity = response.getEntity();
                if (entity == null) {
                    String errMsg = String.format("HTTP请求无返回数据[url=%s,charset=%s]", url, charset);
                    throw new RuntimeException(errMsg);
                }

                if (HttpStatus.SC_OK != response.getStatusLine().getStatusCode()) {
                    String statusCode = String.valueOf(response.getStatusLine().getStatusCode());
                    InputStream in = entity.getContent();/*消耗 并关闭流*/
                    in.close();
                    String errMsg = String.format("HTTP请求返回异常[url=%s,charset=%s,statusCode=%s]", url, charset, statusCode);
                }

//                return callback.execute(response, charset);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            } finally {
                try {
                    if (response != null) {
                        HttpEntity entity = response.getEntity();
                        if (entity != null) {
                            entity.getContent().close();
                        }
                        response.close();
                    }
                } catch (IOException e) {
                    System.out.println("Close response failed");
                }
            }
        }




}
