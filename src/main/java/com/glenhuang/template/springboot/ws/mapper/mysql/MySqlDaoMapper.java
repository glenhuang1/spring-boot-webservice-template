package com.glenhuang.template.springboot.ws.mapper.mysql;

import com.glenhuang.template.springboot.ws.annotation.MySqlDao;
import org.apache.ibatis.annotations.Mapper;

@MySqlDao
@Mapper
public interface MySqlDaoMapper {

    String getMySqlVersion();

}
