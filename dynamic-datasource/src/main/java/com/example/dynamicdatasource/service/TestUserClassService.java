package com.example.dynamicdatasource.service;

import com.example.dynamicdatasource.annotation.DS;
import com.example.dynamicdatasource.constants.DataSourceConstants;
import com.example.dynamicdatasource.entity.TestUser;
import com.example.dynamicdatasource.mapper.TestUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 服务类，数据源注解在类上
 * @author qzz
 */
@Service
@DS(DataSourceConstants.DS_KEY_SLAVE)
public class TestUserClassService {

    @Autowired
    private TestUserMapper testUserMapper;


    /**
     * 查询slave库用户列表
     * @return
     */
    public List<TestUser> getSlaveUser(){
        return testUserMapper.selectList(null);
    }
}
