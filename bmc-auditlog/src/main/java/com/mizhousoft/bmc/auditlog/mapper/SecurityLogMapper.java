package com.mizhousoft.bmc.auditlog.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mizhousoft.bmc.auditlog.domain.SecurityLog;
import com.mizhousoft.bmc.auditlog.request.AuditLogPageRequest;
import com.mizhousoft.commons.mapper.CrudMapper;

/**
 * 安全日志Mapper
 *
 * @version
 */
public interface SecurityLogMapper extends CrudMapper<SecurityLog, Long>
{
	/**
	 * 统计安全日志记录数
	 * 
	 * @param request
	 * @return
	 */
	long countTotal(@Param("request") AuditLogPageRequest request);

	/**
	 * 查询安全日志
	 * 
	 * @param rowOffset
	 * @param request
	 * @return
	 */
	List<SecurityLog> findPageData(@Param("rowOffset") long rowOffset, @Param("request") AuditLogPageRequest request);
}
