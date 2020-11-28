package com.vma.push.business.util;


import java.io.*;
import java.net.URLEncoder;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.nio.charset.Charset;

import java.security.Signature;
import java.util.*;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import com.alibaba.druid.support.json.JSONUtils;
import com.google.gson.Gson;
import com.tls.base64_url.base64_url;
import com.vma.push.business.dto.rsp.RspCircleDetail;
import com.vma.push.business.dto.rsp.RspIm;
import com.vma.push.business.service.ISuperEnterpriseService;
import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

@Service
public class TlsUtil {
    //Use pemfile keys to test

    @Autowired
    private ISuperEnterpriseService iSuperEnterpriseService;

    public RspIm getIm(String enterprise_id){

        return iSuperEnterpriseService.findIm(enterprise_id);
    }
//    private static final String privStr = "-----BEGIN PRIVATE KEY-----\r\n" +
//            "MIGHAgEAMBMGByqGSM49AgEGCCqGSM49AwEHBG0wawIBAQQgezJdcKGrWWnXA1HW\r\n" +
//            "q0XxCSqWhUvo98CWAWUYQYA90v2hRANCAAR14NA4/4CI8h/HHSbzpOIx/RRVCEC8\r\n" +
//            "Q5KBknF66TtrGuT2go+KJxGTUPAe2QUrbb6GFjBg4iTs0QoExUzffW1a\r\n" +
//            "-----END PRIVATE KEY-----\r\n";
//
//    //change public pem string to public string
//    private static final String pubStr = "-----BEGIN PUBLIC KEY-----\r\n" +
//            "MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAEdeDQOP+AiPIfxx0m86TiMf0UVQhA\r\n" +
//            "vEOSgZJxeuk7axrk9oKPiicRk1DwHtkFK22+hhYwYOIk7NEKBMVM331tWg==\r\n" +
//            "-----END PUBLIC KEY-----\r\n";
//
//    //app id
//    private static final int SdkAppId=1400088497;
//
//    private static final String managerid="hxqkk";

    public static class GenTLSSignatureResult {
        public String errMessage;
        public String urlSig;
        public int expireTime;
        public int initTime;

        public GenTLSSignatureResult() {
            errMessage = "";
            urlSig = "";
        }
    }

    public static class CheckTLSSignatureResult {
        public String errMessage;
        public boolean verifyResult;
        public int expireTime;
        public int initTime;

        public CheckTLSSignatureResult() {
            errMessage = "";
            verifyResult = false;
        }
    }

    public static void main(String[] args) {
/*        try {


            // generate signature
            GenTLSSignatureResult result = GenTLSSignatureEx(1400088497, "hxqkk", privStr);
            if (0 == result.urlSig.length()) {
                System.out.println("GenTLSSignatureEx failed: " + result.errMessage);
                return;
            }

            System.out.println("hxqkk sig:\n" + result.urlSig);

            // check signature
            CheckTLSSignatureResult checkResult = CheckTLSSignatureEx(result.urlSig, 1400088497, "test1", pubStr);
            if (checkResult.verifyResult == false) {
                System.out.println("CheckTLSSignature failed: " + result.errMessage);
                return;
            }

            System.out.println("\n---\ncheck sig ok -- expire time " + checkResult.expireTime + " -- init time " + checkResult.initTime + "\n---\n");
        } catch (Exception e) {
            e.printStackTrace();
        }*/
//        System.out.println("Math.random()=" + Math.random()*10000000);// 结果是个double类型的值，区间为[0.0,1.0）
//        System.out.println(getSig("test5",""));
//        System.out.println("test5");
    }

    /**
     * 获取用户的sig
     * @param id
     * @return
     */
    public  String getSig(String id,String enterprise_id){
        try {
            System.out.println("getsig");
            RspIm rspIm=getIm(enterprise_id);
            if (rspIm==null)
            {
                System.out.print("企业id------"+enterprise_id+"--------id--"+id);
                System.out.print("注册不了-------------------密钥什么的没配对sb------");
                return "";
            }else{
                //System.out.println("取到密钥 appid："+rspIm.getSkdAppId()+"---id:"+id+"---key:"+rspIm.getPrivateKey());
                GenTLSSignatureResult result = GenTLSSignatureEx(Long.valueOf(rspIm.getSkdAppId()), id, rspIm.getPrivateKey());
                //System.out.println("返回sig appid："+rspIm.getSkdAppId()+"---id:"+id+"---key:"+rspIm.getPrivateKey());
                return result.urlSig;
            }

        }catch (Exception e){
            e.printStackTrace();
            return "";
        }

    }

    public Integer addUser(String userid,String nick,String enterprise_id){

        String usersig=getSig("admin",enterprise_id);
        String managerid;
        String appsdk;
        if (getIm(enterprise_id)==null){
            managerid="";
            appsdk="";
        }else{
            managerid=getIm(enterprise_id).getManagerId();
            appsdk=getIm(enterprise_id).getSkdAppId();
        }
        String url="https://console.tim.qq.com/v4/im_open_login_svc/account_import?usersig="+usersig+"&identifier="+managerid+"&sdkappid="+appsdk+"&random=99999999&contenttype=json";
        Map param =  new HashMap();
        param.put("Identifier",userid);
        param.put("Nick",nick);
        param.put("FaceUrl","https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83erMpRNt5a76BkR3EwfiaXdtMCYCxNCMsM8G1y1eNEUsZet7iartkMMgDuYUqibvGR8FvWtPCyBWBsOOw/132");
        String result=HttpUtil.httpPostReq(url,param).getResponse_str();
        return new JSONObject(result).getInt("ErrorCode");
    }

    public Integer sendMsg(String staffId,String userId,String enterprise_id){

        String usersig=getSig("admin",enterprise_id);
        String managerid;
        String appsdk;
        if (getIm(enterprise_id)==null){
            managerid="";
            appsdk="";
        }else{
            managerid=getIm(enterprise_id).getManagerId();
            appsdk=getIm(enterprise_id).getSkdAppId();
        }
        String url="https://console.tim.qq.com/v4/openim/sendmsg?usersig="+usersig+"&identifier="+managerid+"&sdkappid="+appsdk+"&random=99999999&contenttype=json";
        Map param =  new HashMap();
        param.put("SyncOtherMachine",1);
        param.put("From_Account",staffId);
        param.put("To_Account",userId);
        param.put("MsgRandom",1287657);
        param.put("MsgTimeStamp",new Date().getTime()/1000);
        List<Map<String,Object>> msgBody = new ArrayList<Map<String,Object>>();
        Map<String,Object> msgContent = new HashMap<String,Object>();
        Map<String,Object> textContent = new HashMap<String,Object>();
        Map<String,Object> content = new HashMap<String,Object>();
        Map<String,Object> value = new HashMap<String,Object>();
        msgContent.put("MsgType","TIMTextElem");
        value.put("title","");
        content.put("type","welcome");
        content.put("value",value);
        textContent.put("Text", HtmlUtils.htmlEscapeHex("{\"type\":\"welcome\",\"value\":{\"title\":\"\"}}","UTF-8"));
        msgContent.put("MsgContent",textContent);
        msgBody.add(msgContent);
        param.put("MsgBody",msgBody);
        System.out.println(textContent.get("Text"));
        String result=HttpUtil.httpPostReq(url,param).getResponse_str();
        return new JSONObject(result).getInt("ErrorCode");
    }

    /**
     * 添加本地用户到腾讯im并添加销售人员为好友
     * @param userid 用户id
     * @param stafdid 销售人员id
     * @return
     */
    public  String regis(String userid,String stafdid,String enterprise_id){
        RspIm rspIm = getIm(enterprise_id);
        String sig=getSig(rspIm.getManagerId(),enterprise_id);
        String url="https://console.tim.qq.com/v4/im_open_login_svc/account_import?usersig=" +sig
                +"&identifier="+rspIm.getManagerId()+"&sdkappid="+rspIm.getSkdAppId()+"&random=99999999&contenttype=json";
        Map param = new HashMap();
        param.put("Identifier",userid);
       /*param.put("Nick","");//昵称
       param.put("FaceUrl","");//头像路径*/
        String result= HttpUtil.httpPostReq(url,param).getResponse_str();
        int errorCode=new JSONObject(result).getInt("ErrorCode");
        //注册成功后则添加好友
        if (errorCode==0){
            String url2="https://console.tim.qq.com/v4/sns/friend_add?usersig=" +sig
                    +"&identifier="+rspIm.getManagerId()+"&sdkappid="+rspIm.getSkdAppId()+"&random=99999999&contenttype=json";
            Map param2 = new HashMap();
            Map param3 = new HashMap();
            param2.put("From_Account",userid);
            param3.put("To_Account",stafdid);
            param3.put("AddSource","AddSource_Type_XXXXXXXX");
            List<Map> list=new ArrayList<>();
            list.add(param3);
            param2.put("AddFriendItem",list);
            String result2=HttpUtil.httpPostReq(url2,param2).getResponse_str();
            return  result2;

        }
        return "";
    }
    /**
     * @param expire      有效期，单位是秒，推荐一个月
     * @param strAppid3rd 填写与 sdkAppid 一致字符串形式的值
     * @param skdAppid    应用的 appid
     * @param identifier  用户 id
     * @param accountType 创建应用后在配置页面上展示的 acctype
     * @param privStr     生成 tls 票据使用的私钥内容
     * @return 如果出错，GenTLSSignatureResult 中的 urlSig为空，errMsg 为出错信息，成功返回有效的票据
     * @throws IOException
     * @brief 生成 tls 票据
     */
    @Deprecated
    public static GenTLSSignatureResult GenTLSSignature(long expire,
                                                        String strAppid3rd, long skdAppid,
                                                        String identifier, long accountType,
                                                        String privStr) throws IOException {

        GenTLSSignatureResult result = new GenTLSSignatureResult();

        Security.addProvider(new BouncyCastleProvider());
        Reader reader = new CharArrayReader(privStr.toCharArray());
        JcaPEMKeyConverter converter = new JcaPEMKeyConverter();
        PEMParser parser = new PEMParser(reader);
        Object obj = parser.readObject();
        parser.close();
        PrivateKey privKeyStruct = converter.getPrivateKey((PrivateKeyInfo) obj);

        //Create Json string and serialization String
        String jsonString = "{"
                + "\"TLS.account_type\":\"" + accountType + "\","
                + "\"TLS.identifier\":\"" + identifier + "\","
                + "\"TLS.appid_at_3rd\":\"" + strAppid3rd + "\","
                + "\"TLS.sdk_appid\":\"" + skdAppid + "\","
                + "\"TLS.expire_after\":\"" + expire + "\""
                + "}";
        //System.out.println("#jsonString : \n" + jsonString);

        String time = String.valueOf(System.currentTimeMillis() / 1000);
        String SerialString =
                "TLS.appid_at_3rd:" + strAppid3rd + "\n" +
                        "TLS.account_type:" + accountType + "\n" +
                        "TLS.identifier:" + identifier + "\n" +
                        "TLS.sdk_appid:" + skdAppid + "\n" +
                        "TLS.time:" + time + "\n" +
                        "TLS.expire_after:" + expire + "\n";


        //System.out.println("#SerialString : \n" + SerialString);
        //System.out.println("#SerialString Hex: \n" + Hex.encodeHexString(SerialString.getBytes()));

        try {
            //Create Signature by SerialString
            Signature signature = Signature.getInstance("SHA256withECDSA", "BC");
            signature.initSign(privKeyStruct);
            signature.update(SerialString.getBytes(Charset.forName("UTF-8")));
            byte[] signatureBytes = signature.sign();

            String sigTLS = Base64.encodeBase64String(signatureBytes);
            //System.out.println("#sigTLS : " + sigTLS);

            //Add TlsSig to jsonString
            JSONObject jsonObject = new JSONObject(jsonString);
            jsonObject.put("TLS.sig", (Object) sigTLS);
            jsonObject.put("TLS.time", (Object) time);
            jsonString = jsonObject.toString();

            // System.out.println("#jsonString : \n" + jsonString);

            //compression
            Deflater compresser = new Deflater();
            compresser.setInput(jsonString.getBytes(Charset.forName("UTF-8")));

            compresser.finish();
            byte[] compressBytes = new byte[512];
            int compressBytesLength = compresser.deflate(compressBytes);
            compresser.end();
            //System.out.println("#compressBytes "+ compressBytesLength+": " + Hex.encodeHexString(Arrays.copyOfRange(compressBytes,0,compressBytesLength)));

            //String userSig = Base64.encodeBase64URLSafeString(Arrays.copyOfRange(compressBytes,0,compressBytesLength));
            String userSig = new String(base64_url.base64EncodeUrl(Arrays.copyOfRange(compressBytes, 0, compressBytesLength)));

            result.urlSig = userSig;
            //System.out.println("urlSig: "+ userSig);
        } catch (Exception e) {
            e.printStackTrace();
            result.errMessage = "generate usersig failed";
        }

        return result;
    }

    /**
     * @param urlSig      返回 tls 票据
     * @param strAppid3rd 填写与 sdkAppid 一致的字符串形式的值
     * @param skdAppid    应的 appid
     * @param identifier  用户 id
     * @param accountType 创建应用后在配置页面上展示的 acctype
     * @param publicKey   用于校验 tls 票据的公钥内容，但是需要先将公钥文件转换为 java 原生 api 使用的格式，下面是推荐的命令
     *                    openssl pkcs8 -topk8 -in ec_key.pem -outform PEM -out p8_priv.pem -nocrypt
     * @return 如果出错 CheckTLSSignatureResult 中的 verifyResult 为 false，错误信息在 errMsg，校验成功为 true
     * @throws DataFormatException
     * @brief 校验 tls 票据
     */
    @Deprecated
    public static CheckTLSSignatureResult CheckTLSSignature(String urlSig,
                                                            String strAppid3rd, long skdAppid,
                                                            String identifier, long accountType,
                                                            String publicKey) throws DataFormatException {
        CheckTLSSignatureResult result = new CheckTLSSignatureResult();
        Security.addProvider(new BouncyCastleProvider());

        //DeBaseUrl64 urlSig to json
        Base64 decoder = new Base64();

        //byte [] compressBytes = decoder.decode(urlSig.getBytes());
        byte[] compressBytes = base64_url.base64DecodeUrl(urlSig.getBytes(Charset.forName("UTF-8")));

        //System.out.println("#compressBytes Passing in[" + compressBytes.length + "] " + Hex.encodeHexString(compressBytes));

        //Decompression
        Inflater decompression = new Inflater();
        decompression.setInput(compressBytes, 0, compressBytes.length);
        byte[] decompressBytes = new byte[1024];
        int decompressLength = decompression.inflate(decompressBytes);
        decompression.end();

        String jsonString = new String(Arrays.copyOfRange(decompressBytes, 0, decompressLength));

        //System.out.println("#Json String passing in : \n" + jsonString);

        //Get TLS.Sig from json
        JSONObject jsonObject = new JSONObject(jsonString);
        String sigTLS = jsonObject.getString("TLS.sig");

        //debase64 TLS.Sig to get serailString
        byte[] signatureBytes = decoder.decode(sigTLS.getBytes(Charset.forName("UTF-8")));

        try {

            String sigTime = jsonObject.getString("TLS.time");
            String sigExpire = jsonObject.getString("TLS.expire_after");

            //checkTime
            //System.out.println("#time check: "+ System.currentTimeMillis()/1000 + "-"
            //+ Long.parseLong(sigTime) + "-" + Long.parseLong(sigExpire));
            if (System.currentTimeMillis() / 1000 - Long.parseLong(sigTime) > Long.parseLong(sigExpire)) {
                result.errMessage = new String("TLS sig is out of date ");
                System.out.println("Timeout");
                return result;
            }

            //Get Serial String from json
            String SerialString =
                    "TLS.appid_at_3rd:" + strAppid3rd + "\n" +
                            "TLS.account_type:" + accountType + "\n" +
                            "TLS.identifier:" + identifier + "\n" +
                            "TLS.sdk_appid:" + skdAppid + "\n" +
                            "TLS.time:" + sigTime + "\n" +
                            "TLS.expire_after:" + sigExpire + "\n";

            //System.out.println("#SerialString : \n" + SerialString);

            Reader reader = new CharArrayReader(publicKey.toCharArray());
            PEMParser parser = new PEMParser(reader);
            JcaPEMKeyConverter converter = new JcaPEMKeyConverter();
            Object obj = parser.readObject();
            parser.close();
            PublicKey pubKeyStruct = converter.getPublicKey((SubjectPublicKeyInfo) obj);

            Signature signature = Signature.getInstance("SHA256withECDSA", "BC");
            signature.initVerify(pubKeyStruct);
            signature.update(SerialString.getBytes(Charset.forName("UTF-8")));
            boolean bool = signature.verify(signatureBytes);
            //System.out.println("#jdk ecdsa verify : " + bool);
            result.verifyResult = bool;
        } catch (Exception e) {
            e.printStackTrace();
            result.errMessage = "Failed in checking sig";
        }

        return result;
    }

    /**
     * @param skdAppid   应用的 sdkappid
     * @param identifier 用户 id
     * @param privStr    私钥文件内容
     * @return
     * @throws IOException
     * @brief 生成 tls 票据，精简参数列表，有效期默认为 180 天
     */
    public static GenTLSSignatureResult GenTLSSignatureEx(
            long skdAppid,
            String identifier,
            String privStr) throws IOException {
        return GenTLSSignatureEx(skdAppid, identifier, privStr, 3600 * 24 * 180);
    }

    /**
     * @param skdAppid   应用的 sdkappid
     * @param identifier 用户 id
     * @param privStr    私钥文件内容
     * @param expire     有效期，以秒为单位，推荐时长一个月
     * @return
     * @throws IOException
     * @brief 生成 tls 票据，精简参数列表
     */
    public static GenTLSSignatureResult GenTLSSignatureEx(
            long skdAppid,
            String identifier,
            String privStr,
            long expire) throws IOException {

        GenTLSSignatureResult result = new GenTLSSignatureResult();

        Security.addProvider(new BouncyCastleProvider());
        Reader reader = new CharArrayReader(privStr.toCharArray());
        JcaPEMKeyConverter converter = new JcaPEMKeyConverter();
        PEMParser parser = new PEMParser(reader);
        Object obj = parser.readObject();
        parser.close();
        PrivateKey privKeyStruct = converter.getPrivateKey((PrivateKeyInfo) obj);
        String jsonString = "{"
                + "\"TLS.account_type\":\"" + 0 + "\","
                + "\"TLS.identifier\":\"" + identifier + "\","
                + "\"TLS.appid_at_3rd\":\"" + 0 + "\","
                + "\"TLS.sdk_appid\":\"" + skdAppid + "\","
                + "\"TLS.expire_after\":\"" + expire + "\","
                + "\"TLS.version\": \"201512300000\""
                + "}";

        String time = String.valueOf(System.currentTimeMillis() / 1000);
        String SerialString =
                "TLS.appid_at_3rd:" + 0 + "\n" +
                        "TLS.account_type:" + 0 + "\n" +
                        "TLS.identifier:" + identifier + "\n" +
                        "TLS.sdk_appid:" + skdAppid + "\n" +
                        "TLS.time:" + time + "\n" +
                        "TLS.expire_after:" + expire + "\n";

        try {
            //Create Signature by SerialString
            Signature signature = Signature.getInstance("SHA256withECDSA", "BC");
            signature.initSign(privKeyStruct);
            signature.update(SerialString.getBytes(Charset.forName("UTF-8")));
            byte[] signatureBytes = signature.sign();
            String sigTLS = Base64.encodeBase64String(signatureBytes);


            //Add TlsSig to jsonString
            JSONObject jsonObject = new JSONObject(jsonString);
            jsonObject.put("TLS.sig", (Object) sigTLS);
            jsonObject.put("TLS.time", (Object) time);
            jsonString = jsonObject.toString();

            //compression
            Deflater compresser = new Deflater();
            compresser.setInput(jsonString.getBytes(Charset.forName("UTF-8")));

            compresser.finish();
            byte[] compressBytes = new byte[512];
            int compressBytesLength = compresser.deflate(compressBytes);
            compresser.end();

            System.out.println("hxq1-----begin");
            System.out.println("1"+compressBytes);
            System.out.println("2"+compressBytesLength);
            System.out.println("3"+Arrays.copyOfRange(compressBytes, 0, compressBytesLength));
            String userSig = new String(base64_url.base64EncodeUrl(Arrays.copyOfRange(compressBytes, 0, compressBytesLength)));

            System.out.println("hxq1-----end");
            result.urlSig = userSig;
        } catch (Exception e) {
            e.printStackTrace();
            result.errMessage = "generate usersig failed";
        }

        return result;
    }

    public static CheckTLSSignatureResult CheckTLSSignatureEx(
            String urlSig,
            long sdkAppid,
            String identifier,
            String publicKey) throws DataFormatException {

        CheckTLSSignatureResult result = new CheckTLSSignatureResult();
        Security.addProvider(new BouncyCastleProvider());

        //DeBaseUrl64 urlSig to json
        Base64 decoder = new Base64();

        byte[] compressBytes = base64_url.base64DecodeUrl(urlSig.getBytes(Charset.forName("UTF-8")));

        //Decompression
        Inflater decompression = new Inflater();
        decompression.setInput(compressBytes, 0, compressBytes.length);
        byte[] decompressBytes = new byte[1024];
        int decompressLength = decompression.inflate(decompressBytes);
        decompression.end();

        String jsonString = new String(Arrays.copyOfRange(decompressBytes, 0, decompressLength));

        //Get TLS.Sig from json
        JSONObject jsonObject = new JSONObject(jsonString);
        String sigTLS = jsonObject.getString("TLS.sig");

        //debase64 TLS.Sig to get serailString
        byte[] signatureBytes = decoder.decode(sigTLS.getBytes(Charset.forName("UTF-8")));

        try {
            String strSdkAppid = jsonObject.getString("TLS.sdk_appid");
            String sigTime = jsonObject.getString("TLS.time");
            String sigExpire = jsonObject.getString("TLS.expire_after");

            if (Integer.parseInt(strSdkAppid) != sdkAppid) {
                result.errMessage = new String("sdkappid "
                        + strSdkAppid
                        + " in tls sig not equal sdkappid "
                        + sdkAppid
                        + " in request");
                return result;
            }

            if (System.currentTimeMillis() / 1000 - Long.parseLong(sigTime) > Long.parseLong(sigExpire)) {
                result.errMessage = new String("TLS sig is out of date");
                return result;
            }

            //Get Serial String from json
            String SerialString =
                    "TLS.appid_at_3rd:" + 0 + "\n" +
                            "TLS.account_type:" + 0 + "\n" +
                            "TLS.identifier:" + identifier + "\n" +
                            "TLS.sdk_appid:" + sdkAppid + "\n" +
                            "TLS.time:" + sigTime + "\n" +
                            "TLS.expire_after:" + sigExpire + "\n";

            Reader reader = new CharArrayReader(publicKey.toCharArray());
            PEMParser parser = new PEMParser(reader);
            JcaPEMKeyConverter converter = new JcaPEMKeyConverter();
            Object obj = parser.readObject();
            parser.close();
            PublicKey pubKeyStruct = converter.getPublicKey((SubjectPublicKeyInfo) obj);

            Signature signature = Signature.getInstance("SHA256withECDSA", "BC");
            signature.initVerify(pubKeyStruct);
            signature.update(SerialString.getBytes(Charset.forName("UTF-8")));
            boolean bool = signature.verify(signatureBytes);
            result.expireTime = Integer.parseInt(sigExpire);
            result.initTime = Integer.parseInt(sigTime);
            result.verifyResult = bool;
        } catch (Exception e) {
            e.printStackTrace();
            result.errMessage = "Failed in checking sig";
        }

        return result;
    }

}
