package com.chmei.nzbcommon.bank;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import com.alibaba.fastjson.JSONObject;

public class BankUtils {
	public static String getCardDetail(String cardNo) {
		// 创建HttpClient实例     
		String url = "https://ccdcapi.alipay.com/validateAndCacheCardInfo.json?_input_charset=utf-8&cardNo=";
		url+=cardNo;
		url+="&cardBinCheck=true";
		StringBuilder sb = new StringBuilder();  
        try {  
            URL urlObject = new URL(url);  
            URLConnection uc = urlObject.openConnection();  
            BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));  
            String inputLine = null;  
            while ( (inputLine = in.readLine()) != null) {  
                sb.append(inputLine);  
            }  
            in.close();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        JSONObject jsonObject = JSONObject.parseObject(sb.toString());
        JSONObject bankJson = JSONObject.parseObject(strBank());
        jsonObject.put("bank", bankJson.get( jsonObject.get("bank").toString()));
        return sb.toString();
	}
	
	public static String strBank() {
		String str = "{\r\n" + 
				"  \"SRCB\": \"深圳农村商业银行\", \r\n" + 
				"  \"BGB\": \"广西北部湾银行\", \r\n" + 
				"  \"SHRCB\": \"上海农村商业银行\", \r\n" + 
				"  \"BJBANK\": \"北京银行\", \r\n" + 
				"  \"WHCCB\": \"威海市商业银行\", \r\n" + 
				"  \"BOZK\": \"周口银行\", \r\n" + 
				"  \"KORLABANK\": \"库尔勒市商业银行\", \r\n" + 
				"  \"SPABANK\": \"平安银行\", \r\n" + 
				"  \"SDEB\": \"顺德农商银行\", \r\n" + 
				"  \"HURCB\": \"湖北省农村信用社\", \r\n" + 
				"  \"WRCB\": \"无锡农村商业银行\", \r\n" + 
				"  \"BOCY\": \"朝阳银行\", \r\n" + 
				"  \"CZBANK\": \"浙商银行\", \r\n" + 
				"  \"HDBANK\": \"邯郸银行\", \r\n" + 
				"  \"BOC\": \"中国银行\", \r\n" + 
				"  \"BOD\": \"东莞银行\", \r\n" + 
				"  \"CCB\": \"中国建设银行\", \r\n" + 
				"  \"ZYCBANK\": \"遵义市商业银行\", \r\n" + 
				"  \"SXCB\": \"绍兴银行\", \r\n" + 
				"  \"GZRCU\": \"贵州省农村信用社\", \r\n" + 
				"  \"ZJKCCB\": \"张家口市商业银行\", \r\n" + 
				"  \"BOJZ\": \"锦州银行\", \r\n" + 
				"  \"BOP\": \"平顶山银行\", \r\n" + 
				"  \"HKB\": \"汉口银行\", \r\n" + 
				"  \"SPDB\": \"上海浦东发展银行\", \r\n" + 
				"  \"NXRCU\": \"宁夏黄河农村商业银行\", \r\n" + 
				"  \"NYNB\": \"广东南粤银行\", \r\n" + 
				"  \"GRCB\": \"广州农商银行\", \r\n" + 
				"  \"BOSZ\": \"苏州银行\", \r\n" + 
				"  \"HZCB\": \"杭州银行\", \r\n" + 
				"  \"HSBK\": \"衡水银行\", \r\n" + 
				"  \"HBC\": \"湖北银行\", \r\n" + 
				"  \"JXBANK\": \"嘉兴银行\", \r\n" + 
				"  \"HRXJB\": \"华融湘江银行\", \r\n" + 
				"  \"BODD\": \"丹东银行\", \r\n" + 
				"  \"AYCB\": \"安阳银行\", \r\n" + 
				"  \"EGBANK\": \"恒丰银行\", \r\n" + 
				"  \"CDB\": \"国家开发银行\", \r\n" + 
				"  \"TCRCB\": \"江苏太仓农村商业银行\", \r\n" + 
				"  \"NJCB\": \"南京银行\", \r\n" + 
				"  \"ZZBANK\": \"郑州银行\", \r\n" + 
				"  \"DYCB\": \"德阳商业银行\", \r\n" + 
				"  \"YBCCB\": \"宜宾市商业银行\", \r\n" + 
				"  \"SCRCU\": \"四川省农村信用\", \r\n" + 
				"  \"KLB\": \"昆仑银行\", \r\n" + 
				"  \"LSBANK\": \"莱商银行\", \r\n" + 
				"  \"YDRCB\": \"尧都农商行\", \r\n" + 
				"  \"CCQTGB\": \"重庆三峡银行\", \r\n" + 
				"  \"FDB\": \"富滇银行\", \r\n" + 
				"  \"JSRCU\": \"江苏省农村信用联合社\", \r\n" + 
				"  \"JNBANK\": \"济宁银行\", \r\n" + 
				"  \"CMB\": \"招商银行\", \r\n" + 
				"  \"JINCHB\": \"晋城银行JCBANK\", \r\n" + 
				"  \"FXCB\": \"阜新银行\", \r\n" + 
				"  \"WHRCB\": \"武汉农村商业银行\", \r\n" + 
				"  \"HBYCBANK\": \"湖北银行宜昌分行\", \r\n" + 
				"  \"TZCB\": \"台州银行\", \r\n" + 
				"  \"TACCB\": \"泰安市商业银行\", \r\n" + 
				"  \"XCYH\": \"许昌银行\", \r\n" + 
				"  \"CEB\": \"中国光大银行\", \r\n" + 
				"  \"NXBANK\": \"宁夏银行\", \r\n" + 
				"  \"HSBANK\": \"徽商银行\", \r\n" + 
				"  \"JJBANK\": \"九江银行\", \r\n" + 
				"  \"NHQS\": \"农信银清算中心\", \r\n" + 
				"  \"MTBANK\": \"浙江民泰商业银行\", \r\n" + 
				"  \"LANGFB\": \"廊坊银行\", \r\n" + 
				"  \"ASCB\": \"鞍山银行\", \r\n" + 
				"  \"KSRB\": \"昆山农村商业银行\", \r\n" + 
				"  \"YXCCB\": \"玉溪市商业银行\", \r\n" + 
				"  \"DLB\": \"大连银行\", \r\n" + 
				"  \"DRCBCL\": \"东莞农村商业银行\", \r\n" + 
				"  \"GCB\": \"广州银行\", \r\n" + 
				"  \"NBBANK\": \"宁波银行\", \r\n" + 
				"  \"BOYK\": \"营口银行\", \r\n" + 
				"  \"SXRCCU\": \"陕西信合\", \r\n" + 
				"  \"GLBANK\": \"桂林银行\", \r\n" + 
				"  \"BOQH\": \"青海银行\", \r\n" + 
				"  \"CDRCB\": \"成都农商银行\", \r\n" + 
				"  \"QDCCB\": \"青岛银行\", \r\n" + 
				"  \"HKBEA\": \"东亚银行\", \r\n" + 
				"  \"HBHSBANK\": \"湖北银行黄石分行\", \r\n" + 
				"  \"WZCB\": \"温州银行\", \r\n" + 
				"  \"TRCB\": \"天津农商银行\", \r\n" + 
				"  \"QLBANK\": \"齐鲁银行\", \r\n" + 
				"  \"GDRCC\": \"广东省农村信用社联合社\", \r\n" + 
				"  \"ZJTLCB\": \"浙江泰隆商业银行\", \r\n" + 
				"  \"GZB\": \"赣州银行\", \r\n" + 
				"  \"GYCB\": \"贵阳市商业银行\", \r\n" + 
				"  \"CQBANK\": \"重庆银行\", \r\n" + 
				"  \"DAQINGB\": \"龙江银行\", \r\n" + 
				"  \"CGNB\": \"南充市商业银行\", \r\n" + 
				"  \"SCCB\": \"三门峡银行\", \r\n" + 
				"  \"CSRCB\": \"常熟农村商业银行\", \r\n" + 
				"  \"SHBANK\": \"上海银行\", \r\n" + 
				"  \"JLBANK\": \"吉林银行\", \r\n" + 
				"  \"CZRCB\": \"常州农村信用联社\", \r\n" + 
				"  \"BANKWF\": \"潍坊银行\", \r\n" + 
				"  \"ZRCBANK\": \"张家港农村商业银行\", \r\n" + 
				"  \"FJHXBC\": \"福建海峡银行\", \r\n" + 
				"  \"ZJNX\": \"浙江省农村信用社联合社\", \r\n" + 
				"  \"LZYH\": \"兰州银行\", \r\n" + 
				"  \"JSB\": \"晋商银行\", \r\n" + 
				"  \"BOHAIB\": \"渤海银行\", \r\n" + 
				"  \"CZCB\": \"浙江稠州商业银行\", \r\n" + 
				"  \"YQCCB\": \"阳泉银行\", \r\n" + 
				"  \"SJBANK\": \"盛京银行\", \r\n" + 
				"  \"XABANK\": \"西安银行\", \r\n" + 
				"  \"BSB\": \"包商银行\", \r\n" + 
				"  \"JSBANK\": \"江苏银行\", \r\n" + 
				"  \"FSCB\": \"抚顺银行\", \r\n" + 
				"  \"HNRCU\": \"河南省农村信用\", \r\n" + 
				"  \"COMM\": \"交通银行\", \r\n" + 
				"  \"XTB\": \"邢台银行\", \r\n" + 
				"  \"CITIC\": \"中信银行\", \r\n" + 
				"  \"HXBANK\": \"华夏银行\", \r\n" + 
				"  \"HNRCC\": \"湖南省农村信用社\", \r\n" + 
				"  \"DYCCB\": \"东营市商业银行\", \r\n" + 
				"  \"ORBANK\": \"鄂尔多斯银行\", \r\n" + 
				"  \"BJRCB\": \"北京农村商业银行\", \r\n" + 
				"  \"XYBANK\": \"信阳银行\", \r\n" + 
				"  \"ZGCCB\": \"自贡市商业银行\", \r\n" + 
				"  \"CDCB\": \"成都银行\", \r\n" + 
				"  \"HANABANK\": \"韩亚银行\", \r\n" + 
				"  \"CMBC\": \"中国民生银行\", \r\n" + 
				"  \"LYBANK\": \"洛阳银行\", \r\n" + 
				"  \"GDB\": \"广东发展银行\", \r\n" + 
				"  \"ZBCB\": \"齐商银行\", \r\n" + 
				"  \"CBKF\": \"开封市商业银行\", \r\n" + 
				"  \"H3CB\": \"内蒙古银行\", \r\n" + 
				"  \"CIB\": \"兴业银行\", \r\n" + 
				"  \"CRCBANK\": \"重庆农村商业银行\", \r\n" + 
				"  \"SZSBK\": \"石嘴山银行\", \r\n" + 
				"  \"DZBANK\": \"德州银行\", \r\n" + 
				"  \"SRBANK\": \"上饶银行\", \r\n" + 
				"  \"LSCCB\": \"乐山市商业银行\", \r\n" + 
				"  \"JXRCU\": \"江西省农村信用\", \r\n" + 
				"  \"ICBC\": \"中国工商银行\", \r\n" + 
				"  \"JZBANK\": \"晋中市商业银行\", \r\n" + 
				"  \"HZCCB\": \"湖州市商业银行\", \r\n" + 
				"  \"NHB\": \"南海农村信用联社\", \r\n" + 
				"  \"XXBANK\": \"新乡银行\", \r\n" + 
				"  \"JRCB\": \"江苏江阴农村商业银行\", \r\n" + 
				"  \"YNRCC\": \"云南省农村信用社\", \r\n" + 
				"  \"ABC\": \"中国农业银行\", \r\n" + 
				"  \"GXRCU\": \"广西省农村信用\", \r\n" + 
				"  \"PSBC\": \"中国邮政储蓄银行\", \r\n" + 
				"  \"BZMD\": \"驻马店银行\", \r\n" + 
				"  \"ARCU\": \"安徽省农村信用社\", \r\n" + 
				"  \"GSRCU\": \"甘肃省农村信用\", \r\n" + 
				"  \"LYCB\": \"辽阳市商业银行\", \r\n" + 
				"  \"JLRCU\": \"吉林农信\", \r\n" + 
				"  \"URMQCCB\": \"乌鲁木齐市商业银行\", \r\n" + 
				"  \"XLBANK\": \"中山小榄村镇银行\", \r\n" + 
				"  \"CSCB\": \"长沙银行\", \r\n" + 
				"  \"JHBANK\": \"金华银行\", \r\n" + 
				"  \"BHB\": \"河北银行\", \r\n" + 
				"  \"NBYZ\": \"鄞州银行\", \r\n" + 
				"  \"LSBC\": \"临商银行\", \r\n" + 
				"  \"BOCD\": \"承德银行\", \r\n" + 
				"  \"SDRCU\": \"山东农信\", \r\n" + 
				"  \"NCB\": \"南昌银行\", \r\n" + 
				"  \"TCCB\": \"天津银行\", \r\n" + 
				"  \"WJRCB\": \"吴江农商银行\", \r\n" + 
				"  \"CBBQS\": \"城市商业银行资金清算中心\", \r\n" + 
				"  \"HBRCU\": \"河北省农村信用社\"\r\n" + 
				"}\r\n";
		return str;	
	}
}
