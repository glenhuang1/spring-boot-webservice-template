package com.glenhuang.template.springboot.ws.mapper.oracle;

import com.glenhuang.template.springboot.ws.annotation.OracleDao;
import org.apache.ibatis.annotations.Mapper;

@OracleDao
@Mapper
public interface OracleDaoMapper {

    String getOracleVersion();

}
