package com.chmei.nzbcommon.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

public class UuidUtil {

	public static String get32UUID() {
		String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
		return uuid;
	}
	
	public static String get16(){
		SimpleDateFormat sdf=new SimpleDateFormat("yyMMddHHmmss");
		String str=sdf.format(new Date());
		String i=new Random().nextInt(10000)+"";
		while(i.length()<4){
			i="0"+i;
		}
		return str+i;
	}
	/*public static void main(String[] args) {
		System.out.println(get32UUID());
	}*/
}

