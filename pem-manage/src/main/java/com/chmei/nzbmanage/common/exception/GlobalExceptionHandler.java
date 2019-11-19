package com.chmei.nzbmanage.common.exception;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ValidationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbcommon.cmutil.ControlConstants;

@Order(0)
@RestControllerAdvice
@RestController
public class GlobalExceptionHandler {
	private static final Logger logger = LoggerFactory
			.getLogger(GlobalExceptionHandler.class);

	@Bean
	public MethodValidationPostProcessor methodValidationPostProcessor() {
		return new MethodValidationPostProcessor();
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.OK)
	public OutputDTO handle(Exception ex,HttpServletRequest request) {
		if (ex instanceof ValidationException || ex instanceof BindException) {
			logger.error(ex.getMessage());
			OutputDTO outputDTO = new OutputDTO();
			outputDTO.setCode(ControlConstants.RETURN_CODE.SYSTEM_ERROR);
			outputDTO.setMsg("参数错误！");
			return outputDTO;
		} else {
			OutputDTO outputDTO = new OutputDTO();
			outputDTO.setCode(ControlConstants.RETURN_CODE.SYSTEM_ERROR);
			outputDTO.setMsg("系统异常！");
			ex.printStackTrace();
			return outputDTO;

		}
	}

}
