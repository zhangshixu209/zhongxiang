/**
 * MD5Util.java
 *
 * Copyright 2011 Baidu, Inc.
 *
 * Baidu licenses this file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package com.chmei.nzbcommon.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5Util是一个用来产生MD5签名和校验MD5签名的工具类
 * 
 * 目前提供的功能如下：
 * 1. public static final String md5(String... plainTexts)
 * 对输入的字符串列表产生MD5签名
 * 
 * 2. public static final boolean md5Check(String md5, String... plainTexts)
 * 根据输入的MD5签名和字符串列表进行校验
 * 
 */
public class SMSMD5Util {
    private final static Logger LOG = LoggerFactory.getLogger(SMSMD5Util.class);
    /**
     * 对输入的字符串列表产生MD5签名
     * 
     * @param plainTexts 用来产生MD5签名的字符串列表
     * @return 输入字符串列表的MD5签名
     * @throws NoSuchAlgorithmException 当用户的JDK不支持MD5哈希算法时
     * @throws UnsupportedEncodingException 当输入的字符串不是UTF-8编码时
     */
    public static final String md5(String... plainTexts) {
        byte bytes[] = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            for (String plainText : plainTexts) {
                md.update(plainText.getBytes("UTF-8"));
            }
            bytes = md.digest();
        }catch (Exception e){
            LOG.error("生成MD5发生异常，生成字符串为:{}",plainTexts,e);
        }
        return Base16Util.byteToBase16(bytes);
    }

    /**
     * 根据输入的MD5签名和字符串列表进行校验
     *
     * @param md5 用来进行校验的MD5签名
     * @param plainTexts 用来进行校验的字符串列表
     * @return 如果通过返回true，失败返回false
     * @throws NoSuchAlgorithmException 当用户的JDK不支持MD5哈希算法时
     * @throws UnsupportedEncodingException 当输入的字符串不是UTF-8编码时
     */
    public static final boolean md5Check(String md5, String... plainTexts)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        for (String plainText : plainTexts) {
            md.update(plainText.getBytes("UTF-8"));
        }
        byte bytes[] = md.digest();

        return Base16Util.byteToBase16(bytes).equalsIgnoreCase(md5);
    }

   /* public static void main(String[] args) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        System.out.println(md5("e91e494b0df43a7a6af359c10c1407c0[{\"header\":{\"appId\":\"baaf3fb04a52515482a98bf7f2acf677\",\"appkey\":\"a5aaf32334e8399aa6cb7a1a4f402236\",\"startTime\":\"2018-09-25 15:46:32\"},\"body\":{\"templateId\":\"301638\",\"subcode\":\"\",\"sendtime\":\"\",\"phones\":\"18538046804\",\"templateArgs\":[\"575106\",\"5分钟\"]}}]e91e494b0df43a7a6af359c10c1407c0"));
    }*/
}
