package com.young.other.sql.bean;

import lombok.Data;
import org.apache.commons.codec.binary.StringUtils;

@Data
public class GeologicalDisastersPerson{
	

	private Integer id;
	/**
	 * 姓名
	 */
	private String name;
	/**
	 * 身份证
	 */
	private String idCard;
	/**
	 * 手机号
	 */
	private String phone;
	/**
	 * 村
	 */
	private String village;
	
}