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
    private static String APP_ID = "2017091908816030";

    //开发者应用私钥，由开发者自己生成
    private static String APP_PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQC8+i3aDikQpjC6ddlICVntngcw4jMYUfIstDWF8vaZtywarzKT9bqolZ++4LiwORQikvYClsvSTHt+k9zuTlC8+AzctMN2w8kDLSConGdL+/3H5rDo+U5eeGJEPAA57LdRzFhTeAj6D59GSOk9ewFZWO6zoZltGFFpIQ9eUR+YOwVVj5fU7tyku3+DBEcTgVAfSfOdCnVMGh9DGnfg3bv4JEVaYZvzLR49pjqw3jTDTM7Wj+c2q598RkY0O7XoKrbjmDuaKm69rXI4LCGU864zg2Gl0G9trGXc0aoSj6KEqmj2pVduXSkPsopfVovIAHsmIxz/7eXm4i0yWcx+i1dPAgMBAAECggEAcqJkbBEI8hOOHZ3dd2hMx+qDvDCLBCa5fEq46Q9wbeCCfZFnYf+/AgL5i2VwQ13si+iQGRP2ghRpGWiCrYCOU4M48i0pIQM1DZszY3S06I/s63VSPZD17DKkkXXAQWMJ9TG7Jq766w3M9WrHKcdTEyryab8OffIxeE9YrkMRolqoCPSdoEhFqZ/qJHZ/mlfG7X7R13uYTQtnI37dbtYggQz1nVJJN7/q8/kE1TVwRWQWZkkDaY9lP0OvRixd64Vx2VZJzBbKjW9sTNcdlcFpJidR4kS1MqjbD8M7oLCTLM8VMtUoaD7y8Qe7adsW2jQJmG9PUcOZW02OQgCzCYq6MQKBgQDmTUiakci/tJfibfD5lM5xiiqH+ngyAKNuPoykieW9BoFnYL5fQOYl+CEgFt4UOh4CG5AR8xWW2/RNjQ8y5rJTv4oyNS/Rwf6sgqWED5NmdFQhHACnU+Y7XdqWaYXYsxErgxaBNK2Ye4eppp5/xsYeHR0+IAznHqPoMcHaOKQbJwKBgQDSEG6P92BqhWbS1HU6FL3favpsdrqTPiYWenDa3o3TnJtZFSQhxdotZ3MUx2JJbxGNmisdT6yUVQY1MpPEgISh+MzbFgPFvKWs0aaQqYo/KM7GsaEcrIdTReCqP0Tj8pEery2u9Hw6c43HQZer171HT90kiTK2Ar1cdsvUJdYbmQKBgHrMoLcSnwlhgU9SMhjSuNWfx5fj/S0kXACPx6yAfwiwrFV0J25hC5cZXIhgyR/d8VxMWCDKDiv3sqebXIxWqKWxTz4AUDQ3jdVsgFcPexg5VRameM7AFNZ47/Id6CmpKbGa32gbr6jLzYWve3pEgiVSfuI+csLV+q33whba9t4nAoGBAMKcjpG6MQz+wiAYzPIePTo7Nf4+kOPQ9CBnFx7uiJ6/u/xv5aWUU2k1KhzaC2jVCYISjlb9dXmGu9WVIeku17HneoKOhrgu6Z6OLK5hI78X41mZPxX9kPHJDzcqnnxAQcoXqU8pGd5dJ8CuOAHwQV9L0ikDIoQVTbHYLUZCN3YhAoGAfvPvBtDj69Ne5XK2Qu0PPN1FLo3N70gLzsV/K36LuWaLW4vXE0wINc+Rcm87DmFlcpBlBNiyJwUj33ZZbub7D6n2Eu0yPIhGfYBrME775Q8t98mxq6XH0+PSx7xCl7dVQPQYNgiwy1HjM7toTHjOZfmRgENHrlFPhKRghvtI0Pg=";

    //请求和签名使用的字符编码格式，支持GBK和UTF-8
    private static String CHARSET = "UTF-8";

    //支付宝公钥，由支付宝生成
    private static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAr5yRuDh0ZOVFlHR2hMGfDbxHwa7a3EHqhxOK8APkmXNRu7eldltl4WwHMy/MQuAcSqBNpme7pZJHLhHSBSBAAgwoHjHylctGOyXcQGREQGXHZRBidx+Sgrb5Y1VnrRVz8eb9copR9aOlBzJvv8qYQR2A3UzPBndU9YpsMikJMcnAuKblfb6Qty5JR+aPmRc0wrNk2MyoMtOTx3OMSd863+CsAQCIGE33SMpR0lCpGGNFiaOUt/yL1DQB+Pa7vxcofQCCAiRgR9r8wT82jq0n/9+Aa1XvOUE4VrpertpStBsSM+xA5eEpSZYsKf9ZkGTG9lzOyHM4mTO75jTQTFzM/wIDAQAB";

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
        request.setNotifyUrl("http://hhk.drdss.com/macro/app/ali/notify");
        try {
            //这里和普通的接口调用不同，使用的是sdkExecute
            AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
            System.out.println(response.getBody());//就是orderString 可以直接给客户端请求，无需再做处理。
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
    }
}
