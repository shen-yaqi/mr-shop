package com.baidu.shop.config;

import java.io.FileWriter;
import java.io.IOException;

/**
 * @ClassName AlipayConfig
 * @Description: TODO
 * @Author shenyaqi
 * @Date 2020/10/22
 * @Version V1.0
 **/
public class AlipayConfig {

    //↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
    public static String app_id = "2016101600702852";

    // 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDVRHYcOZzQkMfCQzW5cbfIn5iLsK/zrq2KrIhOmnwG8amW0be4JTK0loqForZskcbhMEVP1NqD2yMw0TS+kL4wb+UsGlzI2gbP4tBKfRa6TrhRIIUuMGU+8Cw2pvyzY4W0nEw4XLGsy1Y4K5fO+E1cpq3uOTW485f4PPbpZsY10HPzZ9kt1NmqUUp8k/Q2e1E1qRDABUidOD6YdWGZiwjzYkEBrlW3BCHHjRuBc4p5DsGWcj8ZtS8tvKYtW0eD39322CIbvfFv4zha2x6BDo8O/2+lX8DKl5gmdiEeSdw0j1q6QBBGNG+8p/c4LmuNQxJDMU3NCssT2TUdi+6J8LY7AgMBAAECggEAbgAI15gTvTPgBN/7tvHjtHQ1D2AL8SmY010YVUqzTDWbnnH/S9jL5X98ZV8+rpMmPIJUTc/hAX+nEAfoJ56qqnscfnn+f46NUu6PNsx8S45M3XNjniZSIntooqvQzYo4raasXanZ7uFWUFST0aoa5P4H2kbh4cqrCNwREuPyulAffRJRijl9tASArYLnFQHQMc2as6cejf6RvWpKPhb9d/I72cKA8fHeAQ6GKf8mZbD7OZoA9FdRy5CmlahbGRHNxYvX1eHU6RcKTxvwxdujFQJ/7xC8sZ6vYIMb6I00u+ScskLoQfhLQ2XNFsnzbWOU2jZQIvteDD2jj9meFLmXKQKBgQD+yJ7cvXqnGCaNduK5ZIY4nmWs1Eww1d5sgRlp1Ju6BslA87F5Vk4tPx+AUwsEBi2LB5cx1+paHCdeH+cd5K5ZFhSyLAFlJ7M4vEeu+F7Xbjg478yaOHpvNZi0kisoNgusBWIKIVEwKNheydPLXtcHLgFXfFqg9Ynp/PHskjY4/wKBgQDWSRo6TQ2QXrKB0GrYvydZ1IKOKaTDvK3dDrfIDf4aLy09yst4VrkXR56Wq9W01CvD5suFtxXcILvhovdVd840QAQBFpj8QPKi/UcuXBWMWW7wmVoFAsOFwAVmdgM7jD0E6Bx2PMEowPpAid4S3mMuQyQN51zQCXxNk9APDT8mxQKBgCcp+5F+5s2snrB8J9nLo/4sCwEIGTYgifIv3hIwhkeQJ3t+ejdmltnJrDPzj+vsby/B2PHmeLWlQj/rs4Ea19oF1OSWcxhzg88i0foJX7rJzIakBdVm/Rr/KBwJo+yoch7dLPbVrD806B2Z9J58U51c6wPhr6SyWaLIKP/ucbtNAoGAapQpa13vpSK0fkR/IBbTJ6a+2J51yLULdKthVStAex2DzvwSf2QC40VH54DH5gyxR9p+157cTIQrbmpzwhq4gNxmIXRv2ucix1ZaFQDP5uKEfz6Gu39IaDm9ddy0duWlY89eeKB3IiH5Vig8P/5ELjpOsGumigJtxNgD37ii9FECgYEA3Zgn4ZkaBuYaHZEI1hROHsiFOQme0ySeedsq7vnATcDQmxQHg4HUinqxSOUc86vTLb0YbIknkK3yEYJGKWyxk13zC6WhyQHX+fWcDCIJtGUnYvWtZm/aG49q/d1jzH8ajlWdrJ8q+AFjXkVgXLkzJfbZBHZH94FxmFsAW49dO94=";

    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAkiy64E7hlFatIPF2GkxabPGV/ZQyKD6vs0n9SdYCKHk7wnEODEuKE6WFyevZuiTb98t9ZK+vuVmsaUlF95dpLeU/JwABSe9CF/x0d3OSVMMFvjzECTJXlZHzRKw8PA0M9RwgUYmBX82etMLK3QY//IEakxyzWoWES4RQypoPdgIzSTICTeN1XNazTuRBaIQLjSm6GpKRUd+0r0ei9K8hBK8b+9UFoPsdKyd/qKcI76jOEe/w+qimsT8vrCRv3KNTyrOc/wwYtZkJEVVcv/sOFGrFZweW9lpB5bMqR4h0dkhPxAkpYfRd8Brj52d8yLDVDM9Xvl4wXkdVZLcNTBm7cQIDAQAB";

    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "http://localhost:8900/pay/returnNotify";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，内网也可以正常访问
    public static String return_url = "http://localhost:8900/pay/returnURL";

    // 签名方式
    public static String sign_type = "RSA2";

    // 字符编码格式
    public static String charset = "utf-8";

    // 支付宝网关
    public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

    // 支付宝网关
    public static String log_path = "C:\\";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    /**
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
