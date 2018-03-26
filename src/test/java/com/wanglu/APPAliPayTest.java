package com.wanglu;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;

/**
 * Created by wanglu on 2017/10/9 0009.
 */
public class APPAliPayTest {

    //支付宝网关（固定）
    private static String URL = "https://openapi.alipay.com/gateway.do";

    //APPID即创建应用后生成
    private static String APP_ID = "********APP_ID*******";

    //开发者应用私钥，由开发者自己生成
    private static String APP_PRIVATE_KEY = "********APP_PRIVATE_KEY*******";

    //请求和签名使用的字符编码格式，支持GBK和UTF-8
    private static String CHARSET = "UTF-8";

    //支付宝公钥，由支付宝生成
    private static String ALIPAY_PUBLIC_KEY = "********ALIPAY_PUBLIC_KEY*******";

    //商户自己的订单编号
    private static String outtradeno = "S0123456231313132";

    public static void main(String[] args) {
        //实例化客户端
        AlipayClient alipayClient = new DefaultAlipayClient(URL, APP_ID, APP_PRIVATE_KEY, "json", CHARSET, ALIPAY_PUBLIC_KEY, "RSA2");
        //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        //SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setBody("我是测试数据");
        model.setSubject("App支付测试");
        model.setOutTradeNo(outtradeno);
        model.setTotalAmount("0.01");
        request.setBizModel(model);
        request.setNotifyUrl("***************************");
        try {
            //这里和普通的接口调用不同，使用的是sdkExecute
            AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
            System.out.println(response.getBody());//就是orderString 可以直接给客户端请求，无需再做处理。
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
    }
}
