package com.chmei.nzbmanage.message.controller;

import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbcommon.cmutil.BeanUtil;
import com.chmei.nzbmanage.common.controller.BaseController;
import com.chmei.nzbmanage.common.util.Global;
import com.chmei.nzbmanage.configure.FileUploadPathConfig.UploadFilePathConfig;
import com.chmei.nzbmanage.message.bean.ZxMessageForm;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * 众享信息控制器
 * 
 * @author zhangshixu
 * @since 2019年11月11日 09点23分
 */
@RestController
@RequestMapping("/api/zxMessage")
public class ZxMessageController extends BaseController {
    /**
     * 日志对象
     */
    private static final Logger LOGGER = Logger.getLogger(ZxMessageController.class);

    /**
     * 上传下载文件
     */
    @Autowired
    private UploadFilePathConfig uploadFilePathConfig;
    
    /**
     * 众享信息新增
     *
     * @param zxMessageForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/saveZxMessageInfo")
    public OutputDTO saveZxMessageInfo(@ModelAttribute ZxMessageForm zxMessageForm) {
    	LOGGER.info("众享信息新增...ZxMessageController.saveZxMessageInfo()...");
        OutputDTO outputDTO;
        Map<String, Object> params = BeanUtil.convertBean2Map(zxMessageForm);
        outputDTO = getOutputDTO(params, "zxMessageService", "saveZxMessageInfo");
        return outputDTO;
    }

    /**
     * 众享信息上传图片
     * @param files 文件
     * @return
     */
    @RequestMapping(value = "/uploadImgsMessage")
    public OutputDTO uploadImgsMessage(@RequestParam("files") List<MultipartFile> files) {
        OutputDTO outputDTO = new OutputDTO();
        if (Optional.ofNullable(files).isPresent() && files.size() > 0) {
            // 上传图片到服务器
            ArrayList<String> list = new ArrayList<>();
            String path = uploadFilePathConfig.getUploadFolder();
            String PREFIX_IMG = uploadFilePathConfig.getFileUrlPath();

            for (MultipartFile file : files) {
                String originalFilename = file.getOriginalFilename();
                String lastFileName = getLastFileName(originalFilename);
                String randomFileName = getRandomFileName();
                // 最终的文件路径:
                String realPath = path + randomFileName + lastFileName;
                try {
                    FileUtils.writeByteArrayToFile(new File(realPath), file.getBytes());
                    list.add(PREFIX_IMG + randomFileName + lastFileName);
                } catch (IOException e) {
                    e.printStackTrace();
                    LOGGER.error("上传图片文件失败!" + e.getMessage());
                    return new OutputDTO("500", "服务器错误!");
                }
            }
            Map<String, Object> map = new HashMap<>();
            map.put("fileList", list); // 返回图片信息
            outputDTO.setCode("0");
            outputDTO.setMsg("上传成功!");
            outputDTO.setItem(map); // 存放图片路径
            return outputDTO;
        }
        return new OutputDTO("1", "上传失败!");
    }

    /**
     * 众享信息上传视频
     * @param file 文件
     * @return
     */
    @RequestMapping("/uploadMessageVideo")
    public OutputDTO uploadVideo(@RequestParam("file") MultipartFile file) {
        OutputDTO outputDTO = new OutputDTO();
        if (Optional.ofNullable(file).isPresent()) {
//            String video_url = Global.getUploadPath() + "app/message/video/";
            String video_url = uploadFilePathConfig.getUploadFolder();
            String originalFilename = file.getOriginalFilename();
            // 截取文件后缀名称
            String substring = originalFilename.substring(originalFilename.lastIndexOf("."));
            // 获取随机文件名称,不带.xxx后缀
            String extendFileName = this.getRandomFileName();
            String PREFIX_VIDEO = uploadFilePathConfig.getFileUrlPath();
            // 临时文件处理路径
            String url = video_url + extendFileName + substring;
            File path = new File(url);
            try {
                // 上传文件
                byte[] bytes = file.getBytes();
                FileUtils.writeByteArrayToFile(new File(url), bytes);
                Map<String, Object> map = new HashMap<>();
                outputDTO.setCode("0");
                outputDTO.setMsg("视频上传成功!");
                map.put("videoUrl", PREFIX_VIDEO + extendFileName + substring);
                outputDTO.setItem(map);
                return outputDTO;
            } catch (IOException e) {
                e.printStackTrace();
                return new OutputDTO("500", "视频上传失败!");
            }
        }
        return new OutputDTO("500", "参数不能为空!");
    }

    /**
     * 获取文件后缀名称
     */
    private String getLastFileName(String fileName) {
        // 截取文件后缀名称
        return fileName.substring(fileName.lastIndexOf("."));
    }

    /**
     * 文件名随机生成
     */
    private final String getRandomFileName() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 众享信息列表查询
     *
     * @param zxMessageForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/queryZxMessageList")
    public OutputDTO queryZxMessageList(@ModelAttribute ZxMessageForm zxMessageForm) {
        LOGGER.info("众享信息列表查询...ZxMessageController.queryZxMessageList()...");
        OutputDTO outputDTO;
        Map<String, Object> params = BeanUtil.convertBean2Map(zxMessageForm);
        outputDTO = getOutputDTO(params, "zxMessageService", "queryZxMessageList");
        return outputDTO;
    }

    /**
     * 众享信息点赞统计
     *
     * @param zxMessageForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/saveZxMessagePraiseCount")
    public OutputDTO saveZxMessagePraiseCount(@ModelAttribute ZxMessageForm zxMessageForm) {
        LOGGER.info("众享信息点赞统计...ZxMessageController.saveZxMessagePraiseCount()...");
        OutputDTO outputDTO;
        Map<String, Object> params = BeanUtil.convertBean2Map(zxMessageForm);
        outputDTO = getOutputDTO(params, "zxMessageService", "saveZxMessagePraiseCount");
        return outputDTO;
    }

    /**
     * 众享信息浏览统计
     *
     * @param zxMessageForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/saveZxMessageBrowseCount")
    public OutputDTO saveZxMessageBrowseCount(@ModelAttribute ZxMessageForm zxMessageForm) {
        LOGGER.info("众享信息浏览统计...ZxMessageController.saveZxMessageBrowseCount()...");
        OutputDTO outputDTO;
        Map<String, Object> params = BeanUtil.convertBean2Map(zxMessageForm);
        outputDTO = getOutputDTO(params, "zxMessageService", "saveZxMessageBrowseCount");
        return outputDTO;
    }

    /**
     * 众享信息收藏
     *
     * @param zxMessageForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/saveZxMessageCollection")
    public OutputDTO saveZxMessageCollection(@ModelAttribute ZxMessageForm zxMessageForm) {
        LOGGER.info("众享信息收藏...ZxMessageController.saveZxMessageCollection()...");
        OutputDTO outputDTO;
        Map<String, Object> params = BeanUtil.convertBean2Map(zxMessageForm);
        outputDTO = getOutputDTO(params, "zxMessageService", "saveZxMessageCollection");
        return outputDTO;
    }

    /**
     * 众享信息删除我的收藏
     *
     * @param zxMessageForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/delZxMessageCollection")
    public OutputDTO delZxMessageCollection(@ModelAttribute ZxMessageForm zxMessageForm) {
        LOGGER.info("众享信息删除我的收藏...ZxMessageController.delZxMessageCollection()...");
        OutputDTO outputDTO;
        Map<String, Object> params = BeanUtil.convertBean2Map(zxMessageForm);
        outputDTO = getOutputDTO(params, "zxMessageService", "delZxMessageCollection");
        return outputDTO;
    }

    /**
     * 众享信息删除我的发布
     *
     * @param zxMessageForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/delMyZxMessageInfo")
    public OutputDTO delMyZxMessageInfo(@ModelAttribute ZxMessageForm zxMessageForm) {
        LOGGER.info("众享信息删除我的发布...ZxMessageController.delMyZxMessageInfo()...");
        OutputDTO outputDTO;
        Map<String, Object> params = BeanUtil.convertBean2Map(zxMessageForm);
        outputDTO = getOutputDTO(params, "zxMessageService", "delMyZxMessageInfo");
        return outputDTO;
    }

    /**
     * 众享信息我的收藏列表
     *
     * @param zxMessageForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/queryMyZxMessageCollection")
    public OutputDTO queryMyZxMessageCollection(@ModelAttribute ZxMessageForm zxMessageForm) {
        LOGGER.info("众享信息我的收藏列表...ZxMessageController.queryMyZxMessageCollection()...");
        OutputDTO outputDTO;
        Map<String, Object> params = BeanUtil.convertBean2Map(zxMessageForm);
        outputDTO = getOutputDTO(params, "zxMessageService", "queryMyZxMessageCollection");
        return outputDTO;
    }

    /**
     * 众享信息详情查询
     *
     * @param zxMessageForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/queryZxMessageDetail")
    public OutputDTO queryZxMessageDetail(@ModelAttribute ZxMessageForm zxMessageForm) {
        LOGGER.info("众享信息详情查询...ZxMessageController.queryZxMessageDetail()...");
        OutputDTO outputDTO;
        Map<String, Object> params = BeanUtil.convertBean2Map(zxMessageForm);
        outputDTO = getOutputDTO(params, "zxMessageService", "queryZxMessageDetail");
        return outputDTO;
    }

    // TODO 点击头像查看用户信息

}
