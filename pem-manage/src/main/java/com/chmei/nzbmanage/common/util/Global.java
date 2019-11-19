package com.chmei.nzbmanage.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

/**
 * 全局配置类
 *
 * @author ruoyi
 */
public class Global {
    private static final Logger log = LoggerFactory.getLogger(Global.class);

    private static String NAME = "app.yml";

    /**
     * 当前对象实例
     */
    private static Global global;

    /**
     * 保存全局属性值
     */
    private static Map<String, String> map = new HashMap<String, String>();

    private Global() {
    }

    /**
     * 静态工厂方法
     */
    public static synchronized Global getInstance() {
        if (global == null) {
            global = new Global();
        }
        return global;
    }

    /**
     * 获取配置
     */
    public static String getConfig(String key) {
        String value = map.get(key);
        if (value == null) {
            Map<?, ?> yamlMap = null;
            try {
                yamlMap = YamlUtil.loadYaml(NAME);
                value = String.valueOf(YamlUtil.getProperty(yamlMap, key));
                map.put(key, value != null ? value : StringUtils.EMPTY);
            } catch (FileNotFoundException e) {
                log.error("获取全局配置异常 {}", key);
            }
        }
        return value;
    }

    /**
     * 获取项目名称
     */
    public static String getName() {
        return StringUtils.nvl(getConfig("zhx.name"), "ZhongXiang");
    }

    /**
     * 获取项目版本
     */
    public static String getVersion() {
        return StringUtils.nvl(getConfig("zhx.version"), "1.1.0");
    }

    /**
     * 获取版权年份
     */
    public static String getCopyrightYear() {
        return StringUtils.nvl(getConfig("zhx.copyrightYear"), "2019");
    }

    /**
     * 获取ip地址开关
     */
    public static Boolean isAddressEnabled() {
        return Boolean.valueOf(getConfig("zhx.addressEnabled"));
    }

    /**
     * 获取文件上传路径
     */
    public static String getProfile() {
        return getConfig("zhx.profile");
    }

    /**
     * 获取头像上传路径
     */
    public static String getAvatarPath() {
        return getConfig("zhx.profile") + "avatar/";
    }

    /**
     * 获取下载路径
     */
    public static String getDownloadPath() {
        return getConfig("zhx.profile") + "download/";
    }

    /**
     * 获取上传路径
     */
    public static String getUploadPath() {
        return getConfig("zhx.profile") + "upload/";
    }

    /**
     * 获取视频上传路径
     */
    public static String getVideoPath(){
        return getConfig("zhx.FFmpegPath");
    }


    /**
     * 红包群组
     * redPacket: 82955057823745
     * @return
     */
    public static String getRedPacketId(){
        return getConfig("zhx.redPacket");
    }

    /**
     * 图文红包群组
     * redPacketImg: 82955116544002
     * @return
     */
    public static String getRedPacketImgId(){
        return getConfig("zhx.redPacketImg");
    }

    /**
     * 链接红包群组
     * redPacketLink: 82955171069953
     * @return
     */
    public static String getRedPacketLinkId(){
        return getConfig("zhx.redPacketLink");
    }

    /**
     * 视频红包群组
     * redPacketVideo: 82955213012993
     * @return
     */
    public static String getRedPacketVideoId(){
        return getConfig("zhx.redPacketVideo");
    }

    /**
     * 任务红包群组
     * redPacketTask: 82955242373121
     * @return
     */
    public static String getRedPacketTaskId(){
        return getConfig("zhx.redPacketTask");
    }

    /**
     * 获取sql路径
     */
    public static String getSqlPath(){
        return getConfig("zhx.sql");
    }
}

