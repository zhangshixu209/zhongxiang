package com.chmei.nzbcommon.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import com.chmei.nzbcommon.cmbean.OutputDTO;

public class PublicUtil
{
	public static String create_nonce_str(int length)
	{
		String sl = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		StringBuffer bf = new StringBuffer();
		for (int i = 0; i < length; i++)
		{
			bf.append(sl.charAt(new Random().nextInt(sl.length() - 1)));
		}
		return bf.toString();
	}

	public static String creat_timestamp(String format)
	{
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		String timestamp = dateFormat.format(date);
		return timestamp;
	}

	public static String parseByte2HexStr(byte[] buf)
	{
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buf.length; i++)
		{
			String hex = Integer.toHexString(buf[i] & 0xFF);
			if (hex.length() == 1)
			{
				hex = '0' + hex;
			}
			sb.append(hex);
		}
		return sb.toString();
	}

	public static byte[] parseHexStr2Byte(String hexStr)
	{
		if (hexStr.length() < 1)
			return null;
		byte[] result = new byte[hexStr.length() / 2];
		for (int i = 0; i < hexStr.length() / 2; i++)
		{
			int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
			int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
			result[i] = ((byte) (high * 16 + low));
		}
		return result;
	}
	
	public static OutputDTO getErrorMessage(BindingResult result){
		OutputDTO out = new OutputDTO();
		if(result.hasErrors()){
        	List<ObjectError> allErrors = result.getAllErrors();
        	String defaultMessage = allErrors.get(0).getDefaultMessage();
        	out.setCode("-1");
        	out.setMsg(defaultMessage);
        }
		return out;
	}
}
