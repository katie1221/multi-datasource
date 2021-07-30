package com.example.dynamicdatasource.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.dynamicdatasource.annotation.DS;
import com.example.dynamicdatasource.constants.DataSourceConstants;
import com.example.dynamicdatasource.entity.TestUser;
import com.example.dynamicdatasource.mapper.TestUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 服务类，数据源注解在方法上
 * @author qzz
 */
@Service
public class TestUserService {

    @Autowired
    private TestUserMapper testUserMapper;


    /**
     * 查询master库用户列表
     * @return
     */
    @DS(DataSourceConstants.DS_KEY_MASTER)
    public List<TestUser> getMasterUser(){
        QueryWrapper<TestUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.isNotNull("name");

        return testUserMapper.selectAll(queryWrapper);
    }

    /**
     * 查询slave库用户列表
     * @return
     */
    @DS(DataSourceConstants.DS_KEY_SLAVE)
    public List<TestUser> getSlaveUser(){
        return testUserMapper.selectList(null);
    }
}
