package com.chmei.nzbmanage.log.controller;

import java.util.Date;
import java.util.Map;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbcommon.cmutil.BeanUtil;
import com.chmei.nzbcommon.enums.OutPutEnum;
import com.chmei.nzbcommon.util.DateUtil;
import com.chmei.nzbmanage.common.controller.BaseController;
import com.chmei.nzbmanage.log.bean.OperationLogQueryDescribeForm;
import com.chmei.nzbmanage.log.bean.OperationLogQueryListForm;

/**
 * 操作日志
 * <p>Title: OperationLogController</p>  
 * <p>Description: </p>  
 * @author gaoxuemin  
 * @date 2018年9月6日
 */
@RestController
@RequestMapping("/api/log/operationLog")
public class OperationLogController extends BaseController {

	/**
	 * 分页查询
	 * @param operationLogQueryListForm
	 * @return
	 */
	@PostMapping("/queryList")
    public OutputDTO queryGoodsList(OperationLogQueryListForm operationLogQueryListForm){
		
		if(null != operationLogQueryListForm.getTimeBegin() && null != operationLogQueryListForm.getTimeEnd()) {
			Date timeBegin = DateUtil.string2Date(operationLogQueryListForm.getTimeBegin());
			Date timeEnd = DateUtil.string2Date(operationLogQueryListForm.getTimeEnd());
			String timeBeginStr = DateUtil.date2String(timeBegin, DateUtil.DATE_PATTERN.YYYYMM);
			String timeEndStr = DateUtil.date2String(timeEnd, DateUtil.DATE_PATTERN.YYYYMM);
			if(!timeBeginStr.equals(timeEndStr)) {
				return new OutputDTO(OutPutEnum.ServerError.getCode(), "搜索开始日期和结束日期必须是同一年月！");
			}
		}
		operationLogQueryListForm.setStart((operationLogQueryListForm.getPageNumber() - 1) * operationLogQueryListForm.getLimit());
        Map<String, Object> map = BeanUtil.convertBean2Map(operationLogQueryListForm);
        OutputDTO outputDTO = getOutputDTO(map, "operationLogService", "findList");
        return outputDTO;
    }
	
	/**
	 * 查看详情
	 * @param operationLogQueryDescribeForm
	 * @return
	 */
	@PostMapping("/queryDescribe")
    public OutputDTO queryDescribe(@Validated OperationLogQueryDescribeForm operationLogQueryDescribeForm){
        Map<String, Object> map = BeanUtil.convertBean2Map(operationLogQueryDescribeForm);
        OutputDTO outputDTO = getOutputDTO(map, "operationLogService", "findDescribe");
        return outputDTO;
    }
	
	/**
	 * 获取操作对象集合
	 * @return
	 */
	@PostMapping("/getOperationModelList")
	public OutputDTO getOperationModelList() {
		OutputDTO outputDTO = getOutputDTO((Map<String, Object>)null, "operationLogService", "getOperationModelList");
        return outputDTO;
	}
}
