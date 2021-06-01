package com.mizhousoft.bmc.identity.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.mizhousoft.bmc.identity.domain.ObjectIdentity;
import com.mizhousoft.commons.mapper.CrudMapper;

/**
 * 对象ID标识持久层
 *
 * @version
 */
@Mapper
public interface ObjectIdentityMapper extends CrudMapper<ObjectIdentity, String>
{

}
