package com.mizhousoft.bmc.auditlog.mapper;

import com.mizhousoft.bmc.auditlog.domain.OperationLog;
import com.mizhousoft.commons.mapper.PageableMapper;

/**
 * 操作日志持久层Mapper
 *
 * @version
 */
public interface OperationLogMapper extends PageableMapper<OperationLog, OperationLog, Long>
{

}
