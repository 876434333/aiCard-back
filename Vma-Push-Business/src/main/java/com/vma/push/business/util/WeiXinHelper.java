package com.vma.push.business.util;

import com.vma.push.business.common.Constants;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.*;


/**
 * TODO:
 * Created by 王剑洪
 * on 2016/12/11 0011. 22:37
 */
public class WeiXinHelper {

    public final static Logger log = LoggerFactory.getLogger(WeiXinHelper.class);
    
    public static Map payInfo(String orderNumber, String body, String totalFee, String notify_url,String appId,String mchId,String tradeType,String payKey,String openId) throws Exception {
        String xmlParams=xmlParams(orderNumber,body,totalFee,notify_url,appId,mchId,tradeType,payKey,openId);
        String res = HttpUtil.httpPostReq("https://api.mch.weixin.qq.com/pay/unifiedorder", xmlParams);
        log.info("in WeiXinHelper.payInfo(), orderNumber={}, res={}",orderNumber,res);
        return doXMLParse(res);
    }

    /**
     * 退款
     * @return
     * @throws Exception
     */
    public static Map payReturn(String orderNumber,String totalFee,String key) throws Exception {
        HashMap<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("appid", Constants.WX_APP_ID); // appid
        paramMap.put("mch_id", Constants.WX_MCH_ID); // 商户号
        paramMap.put("nonce_str", getRandomString(32)); // 随机数
        paramMap.put("op_user_id", Constants.WX_MCH_ID); // 操作员帐号, 默认为商户号
        paramMap.put("out_trade_no", orderNumber); // 商户 后台的贸易单号
        paramMap.put("out_refund_no","1415757673"); // 商户系统内部的退款单号，商户系统内部唯一，同一退款单号多次请求只退一笔(暂时用随机数代替)
        paramMap.put("total_fee", "" + totalFee); // 金额必须为整数 单位为分
        paramMap.put("refund_fee", "" + totalFee); // 金额必须为整数 单位为分
        paramMap.put("sign", createSign(paramMap,key));// 根据微信签名规则，生成签名
        String xmlParams= mapToXml(paramMap);
        //String res = HtmlUtil.postData("https://www.baidu.com/", "123123");
        String res = HttpUtil.httpPostReq("https://api.mch.weixin.qq.com/secapi/pay/refund",xmlParams);
        return doXMLParse(res);
    }

    /**
     * 字符参数生成
     * @param orderNumber
     * @param body
     * @param totalFee
     * @param notify_url
     * @return
     */
    public static String xmlParams(String orderNumber, String body, String totalFee, String notify_url,String appId,String mchId,String tradeType,String payKey,String openId) {
        HashMap<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("appid", appId); // appid
        paramMap.put("mch_id", mchId); // 商户号
        paramMap.put("nonce_str", getRandomString(32)); // 随机数
        paramMap.put("body", body); // 商品描述
        paramMap.put("out_trade_no", orderNumber); // 商户 后台的贸易单号
        paramMap.put("total_fee", "" + totalFee); // 金额必须为整数 单位为分
        paramMap.put("spbill_create_ip", ""); // 本机的Ip // TODO: 2017/5/28
        paramMap.put("notify_url", notify_url); // 支付成功后，回调地址
        paramMap.put("trade_type", tradeType); // 交易类型，app
        paramMap.put("openid",openId);
        paramMap.put("sign", createSign(paramMap,payKey));// 根据微信签名规则，生成签名
        return mapToXml(paramMap);
    }


    /**
     * 随机字符串
     *
     * @param length
     * @return
     */
    public static String getRandomString(int length) {
        StringBuffer buffer = new StringBuffer("0123456789abcdefghijklmnopqrstuvwxyz");
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        int range = buffer.length();
        for (int i = 0; i < length; i++) {
            sb.append(buffer.charAt(random.nextInt(range)));
        }
        return sb.toString();
    }

    /**
     * 签名生成，使用默认MD5签名
     *
     * @param paramMap
     * @return
     */
    public static String createSign(HashMap<String, String> paramMap,String key) {
        SortedMap<Object, Object> parameters = new TreeMap<Object, Object>(paramMap);
        StringBuffer sb = new StringBuffer();
        Set es = parameters.entrySet();// 所有参与传参的参数按照accsii排序（升序）
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            Object v = entry.getValue();
            if (null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("key=" + key);
        String sign = MD5Helper.getMD5Str(sb.toString()).toUpperCase();
        return sign;
    }

    /**
     * map转XML
     *
     * @param map
     * @return
     */
    public static String mapToXml(HashMap<String, String> map) {
        String str = "<xml>";
        Set set = map.keySet();
        Iterator iter = set.iterator();
        while (iter.hasNext()) {
            String key = (String) iter.next();
            if (null != map.get(key) && !"".equals(map.get(key))) {
                str = str + "<" + key + ">" + map.get(key) + "</" + key + ">";
            }
        }
        return str + "</xml>";
    }

    /**
     * 字符串转map
     * @param strxml
     * @return
     * @throws Exception
     */
    public static Map doXMLParse(String strxml) throws Exception {
        strxml = strxml.replaceFirst("encoding=\".*\"", "encoding=\"UTF-8\"");
        if (null == strxml || "".equals(strxml)) {
            return null;
        }
        Map m = new HashMap();
        InputStream in = new ByteArrayInputStream(strxml.getBytes("UTF-8"));
        SAXBuilder builder = new SAXBuilder();
        Document doc = builder.build(in);
        Element root = doc.getRootElement();
        List list = root.getChildren();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            Element e = (Element) it.next();
            String k = e.getName();
            String v = "";
            List children = e.getChildren();
            if (children.isEmpty()) {
                v = e.getTextNormalize();
            } else {
                v = getChildrenText(children);
            }
            m.put(k, v);
        }
        // 关闭流
        in.close();
        return m;
    }

    /**
     * 获取子结点的xml
     *
     * @param children
     * @return String
     */
    public static String getChildrenText(List children) {
        StringBuffer sb = new StringBuffer();
        if (!children.isEmpty()) {
            Iterator it = children.iterator();
            while (it.hasNext()) {
                Element e = (Element) it.next();
                String name = e.getName();
                String value = e.getTextNormalize();
                List list = e.getChildren();
                sb.append("<" + name + ">");
                if (!list.isEmpty()) {
                    sb.append(getChildrenText(list));
                }
                sb.append(value);
                sb.append("</" + name + ">");
            }
        }

        return sb.toString();
    }

    /**
     * app支付参数
     * @param map
     * @return
     */
    public static HashMap<String,String> wxPay(HashMap<String,String> map,String payKey){
        if (!map.get("return_code").equals("SUCCESS")){
            log.error(map.toString());

            return new HashMap<String, String>();
        }
//        HashMap<String,String> m=new HashMap();
//        m.put("appid",map.get("appid").toString());
//        m.put("partnerid",map.get("mch_id").toString());
//        m.put("prepayid",map.get("prepay_id").toString());
//        m.put("package","Sign=WXPay");
//        m.put("noncestr",map.get("nonce_str").toString());
//        m.put("timestamp", (System.currentTimeMillis() / 1000+""));
//        m.put("sign",createSign(map,payKey));
//
//        HashMap<String,String> m1=new HashMap();
//        m1.put("appId",m.get("appid"));
//        m1.put("partnerId",m.get("partnerid"));
//        m1.put("prepayId",m.get("prepayid"));
//        m1.put("packageValue",m.get("package"));
//        m1.put("nonceStr",m.get("noncestr"));
//        m1.put("timeStamp",m.get("timestamp"));
//        m1.put("sign",m.get("sign"));
//        return m1;


        HashMap<String,String> m=new HashMap();
        m.put("appId",map.get("appid").toString());
        m.put("package","prepay_id="+map.get("prepay_id").toString());
        m.put("nonceStr",map.get("nonce_str").toString());
        m.put("signType","MD5");
        m.put("timeStamp", (System.currentTimeMillis() / 1000+""));
        m.put("sign",createSign(m,payKey));

        HashMap<String,String> m1=new HashMap();
        m1.put("appId",m.get("appId"));
        m1.put("package",m.get("package"));
        m1.put("nonceStr",m.get("nonceStr"));
        m1.put("timeStamp",m.get("timeStamp"));
        m1.put("paySign",m.get("sign"));
        m1.put("signType","MD5");
        return m1;
    }



}
