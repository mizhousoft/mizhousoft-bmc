package com.mizhousoft.bmc.auditlog.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mizhousoft.bmc.auditlog.domain.ApiAuditLog;
import com.mizhousoft.bmc.auditlog.request.AuditLogPageRequest;
import com.mizhousoft.commons.mapper.CrudMapper;

/**
 * API日志持久层
 *
 * @version
 */
public interface ApiAuditLogMapper extends CrudMapper<ApiAuditLog, Long>
{
	/**
	 * 统计
	 * 
	 * @param request
	 * @return
	 */
	long countTotal(@Param("request") AuditLogPageRequest request);

	/**
	 * 查询
	 * 
	 * @param rowOffset
	 * @param request
	 * @return
	 */
	List<ApiAuditLog> findPageData(@Param("rowOffset") long rowOffset, @Param("request") AuditLogPageRequest request);
}
