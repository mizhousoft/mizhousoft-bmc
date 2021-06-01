package com.mizhousoft.bmc.auditlog.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mizhousoft.bmc.auditlog.domain.SystemLog;
import com.mizhousoft.bmc.auditlog.request.AuditLogPageRequest;
import com.mizhousoft.commons.mapper.CrudMapper;

/**
 * 系统日志持久层 Mapper
 *
 * @version
 */
public interface SystemLogMapper extends CrudMapper<SystemLog, Long>
{
	/**
	 * 统计系统日志记录数
	 * 
	 * @param request
	 * @return
	 */
	long countTotal(@Param("request") AuditLogPageRequest request);

	/**
	 * 查询系统日志
	 * 
	 * @param rowOffset
	 * @param request
	 * @return
	 */
	List<SystemLog> findPageData(@Param("rowOffset") long rowOffset, @Param("request") AuditLogPageRequest request);
}
