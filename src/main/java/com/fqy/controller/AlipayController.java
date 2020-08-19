package com.fqy.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.apache.catalina.manager.Constants.CHARSET;

@RestController
public class AlipayController {
    @RequestMapping("/pay")
    @ResponseBody
    public String pay(HttpServletRequest request , HttpServletResponse response){
        //获得初始化的AlipayClient
        AlipayClient alipayClient =  new DefaultAlipayClient(
                "https://openapi.alipaydev.com/gateway.do",
                "2021000118607266",
                "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCFWlpO1q0ucd/TIErwA3hzAXpNQBxPArHcYnK1XfFJ/dpTpJ165YxDMqjuyDnupzsUfwBLYOWNWTpToM7bkeYhjGIDY4AV/ZsyBiswnzAxH+oBFnlOciy1wxT8KpI8MmKx1fB7Fa4/NHd/FXb+jyYJbLUPtXwwe+PLsuRPcQUqPAx/mtEV5eXGTSc1q92zlNoxtqLln0lCcMBr5WSRmqKZmfOe1LK7O0WDrwcfTWbjr4Wypcu2jGQFz8HNXASC33NdvC/ngbF2GHWvSioNc3rFUw+U8TU+i9Sv2x2LIWtqBY66Sb3ndjjir5mq6VizP87AfCpXJ6zWcPGTpjHm9qNDAgMBAAECggEAOSUC1g4AoIqD08Fe24NhNkdH1ZrHhsQs27gEV9wt7fsud/Fu2p9Lf/BRnCGJUc+v7ZSO175qICxAZl3exxC6gV3bEoCs/r9ypfkmwOs2h5aZfGXZDdi3eBxk/bpweANPb82yA8zCoqslGfxkxakqL5e570ViNpSghUXfuHtCL1p0+zR4K82lvwQ8d2AA7/RDKT7pHLXSrQ5ZGaMyiYujtW+574W7rH/iHSNxIOM24LF6lBOfisq1CZmZu8BBeTq77A8hQZYh4LZc8lhmwwevbCDhtG8w288s1OAPglEa177xrlQEsx7B85DIRJUov1MISvhye/ZsFRmnjzGbMSdugQKBgQDM+zVQ6bcbXgGc29HJVqMldXbJijpTxGCZKkMlkzL54X9LMfaDtID1wZQsI+Lv/WeR+AGrAVL3FanPtX48CLXwCHupOH2lAOP677xFLsxtJhGMwrpklaQED6ioP/2L/9gOMojAShyZbPGeRJ6fEozLsbS212f3j6ZkmcfWL+2ykwKBgQCmizOgVGJCzIwxPcWVxGmZ7AhCaFkdSGGzA/lHhOS/t4ags8DNeE+i4eK4OxNSguHdb8O1Fh6zTpjqpITN+CxPYrcWs8Lurcq5dN4jCu5/F/qcsnvmG8wPvbklPNdmt7daH8j8F1IQw0WfUgreMdtOVWY65/xOv+b8LXOmCTJKkQKBgB6EY9qpAP7NDxZoT6JXCLEB/O5iau6ftPX4qciFUriLDuWGCbpv/Ccaa9HqgdXS90FvF+h6qPxqOD7ZOXL8pQGYcbiu1V3kdQyd9nyiTAMQANrg/tkMwj9siWKLLFdUfrkWYu+l2P4qjwpOEwOBwn5s9YenKC5eL5qYnnzP4+aLAoGAffaK9ZjdOOi8oAG4j64esOx00sVZoWw9eMhook9BND/681EBYma81NVERVNpKd8UNgdyXmR8k+G+hrooOCCcYu6my+TqatvHmL1rxdVezYG1aw8T5tbfYIh+oEuEH4yUgPXJbeG1kDghUH8c+TF8TaMJz0Pm8Sjz64f1EH+kKgECgYBxUJW4QYCQMFRXcVMFYlKOuj19GPsfl49m5W0p4rw5OsrifOvdIJ3ZKkcQf6TPoQmUXoWl4jEeggIyulKKyHHFcE8HG6VGlL4hCa9WmFrOk5R/0LL9tu7dJqvnv2wwWEM0Qus7eLOVgtYDa5Q4Q1PKzZrRic5sqDABYp6JnEEILQ==",
                "json",
                "UTF-8",
                "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA/UrT+MVgVZwu8b/W2Xr6+PFzWgeDPF1bJjzPJvNXgjSYzX1juYAPWsjuteoFxP6Lp35ObpTJBe4oChmIqTm7QDm08gWoXpXLMbW4JmFqSK32lregVQClL9SkW4eVMKRrt4EeocdayAlq1wGCiE5g3UM9/+mKBm8fe3mnIXbwlFdvfBBR61/IvNb5WqN+QU3lo8kz+DJluXFF8jiyb2avmFJOeuWV7/xhCXmJ3ZS7H/seOPyZdGO0/irDbcKinM/wfMASxNRlGMLslBbCfmnboLEex9ItKaR6zZNA6jYNFtWpic4opPnUsaJwWX4asqG2VnwMTK9MRZb+PTB60Pct2wIDAQAB",
                "RSA2");

        //创建API对应的request
        AlipayTradePagePayRequest aplirequest = new AlipayTradePagePayRequest();
        //支付宝的回调地址,支付结果异步通知
        aplirequest.setNotifyUrl( "http://domain.com/CallBack/notify_url.jsp" ); //在公共参数中设置回跳和通知地址
        aplirequest.setBizContent( "{"  +
                "    \"out_trade_no\":\"20150320010101001\","  +
                "    \"product_code\":\"FAST_INSTANT_TRADE_PAY\","  +
                "    \"total_amount\":88.88,"  +
                "    \"subject\":\"Iphone6 16G\","  +
                "    \"body\":\"Iphone6 16G\","  +
                "    \"passback_params\":\"merchantBizType%3d3C%26merchantBizNo%3d2016010101111\","  +
                "    \"extend_params\":{"  +
                "    \"sys_service_provider_id\":\"2088511833207846\""  +
                "    }" +
                "  }" ); //填充业务参数
        String form= "" ;
        try  {
            form = alipayClient.pageExecute(aplirequest).getBody();  //调用SDK生成表单
            response.setContentType( "text/html;charset="  + CHARSET);
            response.getWriter().write(form); //直接将完整的表单html输出到页面
            response.getWriter().flush();
            response.getWriter().close();
        }  catch  (Exception e) {
            e.printStackTrace();
        }

        return form;
    }
    @RequestMapping("/returlCallBack")
    public String returlCallBack(HttpServletRequest request , HttpServletResponse httpResp) throws  AlipayApiException {
        Map<String, String> paramsMap = convertRequestParamsToMap(request);
        boolean signVerified = AlipaySignature.rsaCheckV1(paramsMap, "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAol4SrTwjB6RqSYQKWGiGopysy9KoHluqrsasGqDZWBm8Q4WlDYXpRpV7C53fqbChz/p/VwdGpb+bDSZU9pLEaSCzZTX6JqEeFo2fQav4mRVIY1FPgqOJ5Mn4AN/dB7j45Qy9wrIKh6+nP3dvDiN4wrHiobj4cl8Xb4vxbI3bvbj2SFYeL6u8iO49Hi4oXHvNaNzoKw8tl2hkI8N5sWU4LZAdT8odCSdpWwoMRvAapN+Doa4ceRgBmSc9D6l2qfz8Y0DIjd9wrs2fAc0XUUxb+JG1p5wGnzDagaNxFO8YkfeKlnaLtQ2ELQ2cy25db6sdNihIvXs6J3RzaU9kB4lHZwIDAQAB", "utf-8", "RSA2"); //调用SDK验证签名
        if(signVerified){
            // TODO 验签成功后，按照支付结果异步通知中的描述，对支付结果中的业务内容进行二次校验，校验成功后在response中返回success并继续商户自身业务处理，校验失败返回failure
            System.out.println(paramsMap);
            return "success";
        }else{
            // TODO 验签失败则记录异常日志，并在response中返回failure.
            return "fail";
        }
    }

    // 将request中的参数转换成Map
    private static Map<String, String> convertRequestParamsToMap(HttpServletRequest request) {
        Map<String, String> retMap = new HashMap<String, String>();

        Set<Map.Entry<String, String[]>> entrySet = request.getParameterMap().entrySet();

        for (Map.Entry<String, String[]> entry : entrySet) {
            String name = entry.getKey();
            String[] values = entry.getValue();
            int valLen = values.length;

            if (valLen == 1) {
                retMap.put(name, values[0]);
            } else if (valLen > 1) {
                StringBuilder sb = new StringBuilder();
                for (String val : values) {
                    sb.append(",").append(val);
                }
                retMap.put(name, sb.toString().substring(1));
            } else {
                retMap.put(name, "");
            }
        }

        return retMap;
    }

}
