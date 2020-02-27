package com.chmei.nzbmanage.common.util;

import com.chmei.nzbcommon.cmbean.OutputDTO;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpSession;
import java.io.*;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @Description TODO 聚合数据短信验证码
 * @Author zhangshixu
 * @Date 2019年11月6日 09点50分
 * @Version 1.0.0
 */
public class SmUtils {

    public static final String DEF_CHATSET = "UTF-8";
    public static final int DEF_CONN_TIMEOUT = 30000;
    public static final int DEF_READ_TIMEOUT = 30000;
    public static final String APP_KEY_MSG = "d3982c79040a8d66b8bb237a83da3239";
    public static final String APP_KEY_CARD = "4f8bc1dc4eb3f1d6ef3092a3f98d5e49";
    private static final Logger log = Logger.getLogger(SmUtils.class);
    public static String userAgent =  "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";

    @SuppressWarnings("all")
    public static OutputDTO mobileQuery(String phoneNumber, HttpSession session) {
        OutputDTO outputDTO = new OutputDTO();
        String result = null;
        String code = getNonce_str();
        String url = "http://v.juhe.cn/sms/send";//请求接口地址
        Map<String,String> params = new HashMap();//请求参数
        params.put("mobile", phoneNumber);//接受短信的用户手机号码
        params.put("tpl_id", "208392");//您申请的短信模板ID，根据实际情况修改11741
        params.put("tpl_value", "#code#=" + code);//您设置的模板变量，根据实际情况修改
        params.put("key", APP_KEY_MSG);//应用APPKEY(应用详细页查询)
        try {
            result = net(url, params, "GET");
            Map map = JSON.unmarshal(result, Map.class);
//            // TODO 需要IP白名单
//            Map map = new HashMap();
//            map.put("error_code", 0);
            if ((int) map.get("error_code") == 0) {
                log.info("短信发送成功,详细信息如下:" + result);
                // 短信发送成功,将验证码存储到session中
                session.setAttribute(phoneNumber,code);
                session.setAttribute("phone", phoneNumber);
                Map<String,Object> codeMap = new HashMap<>();
                codeMap.put("code",code);
                log.info(code);
                codeMap.put("sessionId", Md5Utils.hash(session.getId()));
                outputDTO.setCode("0");
                outputDTO.setMsg("发送短信成功!");
                outputDTO.setItem(codeMap);
                return outputDTO;
            }
            log.info("短信发送失败,详细信息如下:" + result);
            outputDTO.setCode("1");
            outputDTO.setMsg("发送短信失败!");
            return outputDTO;
        } catch (Exception e) {
            e.printStackTrace();
            log.info(e.getMessage());
            outputDTO.setCode("-1");
            outputDTO.setMsg("服务器错误(1,-1)");
            return outputDTO;
        }
    }

    public static OutputDTO authcIdCardReal(String idCardNo,String realName){
        OutputDTO outputDTO = new OutputDTO();
        String url = "http://op.juhe.cn/idcard/query";
        Map<String,String> params = new HashMap();//请求参数
        params.put("idcard",idCardNo);
        params.put("realname",realName);
        params.put("key",APP_KEY_CARD);
        try {
            String result = net(url, params, "GET");
            Map map = JSON.unmarshal(result, Map.class);
            if((int) map.get("error_code") == 0){
                Map obj = (Map) map.get("result");
                if ((int) obj.get("res") == 1) {
                    outputDTO.setCode("0");
                    outputDTO.setMsg("验证成功！");
                    outputDTO.setItem(obj);
                } else {
                    return new OutputDTO("-1", "身份证号与姓名不一致!");
                }
                return outputDTO;
            }
            log.info("身份验证失败,详细信息如下:" + result);
            return new OutputDTO("1", "验证失败!");
        } catch (Exception e) {
            e.printStackTrace();
            return new OutputDTO("500", "服务器错误(1,-1)!");
        }
    }

    /**
     *
     * @param strUrl 请求地址
     * @param params 请求参数
     * @param method 请求方法
     * @return  网络请求字符串
     * @throws Exception
     */
    public static String net(String strUrl, Map params, String method) throws Exception {
        HttpURLConnection conn = null;
        BufferedReader reader = null;
        String rs = null;
        try {
            StringBuffer sb = new StringBuffer();
            if(method==null || method.equals("GET")){
                strUrl = strUrl+"?"+urlencode(params);
            }
            URL url = new URL(strUrl);
            conn = (HttpURLConnection) url.openConnection();
            if(method==null || method.equals("GET")){
                conn.setRequestMethod("GET");
            }else{
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
            }
            conn.setRequestProperty("User-agent", userAgent);
            conn.setUseCaches(false);
            conn.setConnectTimeout(DEF_CONN_TIMEOUT);
            conn.setReadTimeout(DEF_READ_TIMEOUT);
            conn.setInstanceFollowRedirects(false);
            conn.connect();
            if (params!= null && method.equals("POST")) {
                try {
                    DataOutputStream out = new DataOutputStream(conn.getOutputStream());
                    log.info("--->" + urlencode(params));
                    out.writeBytes(urlencode(params));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            log.info("GET请求方式URL {} " + strUrl);
            InputStream is = conn.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, DEF_CHATSET));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sb.append(strRead);
            }
            rs = sb.toString();
            log.info("{}" + sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                reader.close();
            }
            if (conn != null) {
                conn.disconnect();
            }
        }
        return rs;
    }

    //将map型转为请求参数型
    public static String urlencode(Map<String,String> data) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry i : data.entrySet()) {
            try {
                sb.append(i.getKey()).append("=").append(URLEncoder.encode(i.getValue()+"","UTF-8")).append("&");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    public static String md5Password(String password){
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
            // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
            return new BigInteger(1, md.digest()).toString(16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            log.error("MD5加密出错 {}" + e.getMessage());
            throw new RuntimeException("MD5加密出现错误!");
        }
    }

    public static void main(String[] args) {

        /**
         *
         * idcard=411422199103285159&realname=%E6%97%B6%E6%9F%B3%E9%9D%92&key=4f8bc1dc4eb3f1d6ef3092a3f98d5e49
         *
         */

        System.out.println(authcIdCardReal("410526199312176979", "张士旭"));
    }

    /**
     * 手机号验证码随机数
     */
    private static final String SYMBOLS = "0123456789";
    private static final Random RANDOM = new SecureRandom();
    public static String getNonce_str() {
        // 如果需要4位，那 new char[4] 即可，其他位数同理可得
        char[] nonceChars = new char[6];
        for (int index = 0; index < nonceChars.length; ++index) {
            nonceChars[index] = SYMBOLS.charAt(RANDOM.nextInt(SYMBOLS.length()));
        }
        return new String(nonceChars);
    }

}
