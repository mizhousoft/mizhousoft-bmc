package com.mizhousoft.bmc.auditlog.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mizhousoft.bmc.auditlog.domain.OperationLog;
import com.mizhousoft.bmc.auditlog.request.AuditLogPageRequest;
import com.mizhousoft.commons.mapper.CrudMapper;

/**
 * 操作日志持久层Mapper
 *
 * @version
 */
public interface OperationLogMapper extends CrudMapper<OperationLog, Long>
{
	/**
	 * 统计操作日志记录数
	 * 
	 * @param request
	 * @return
	 */
	long countTotal(@Param("request") AuditLogPageRequest request);

	/**
	 * 查询操作日志
	 * 
	 * @param rowOffset
	 * @param request
	 * @return
	 */
	List<OperationLog> findPageData(@Param("rowOffset") long rowOffset, @Param("request") AuditLogPageRequest request);
}
