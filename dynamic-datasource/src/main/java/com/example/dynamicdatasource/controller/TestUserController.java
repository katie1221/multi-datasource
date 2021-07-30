package com.example.dynamicdatasource.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.dynamicdatasource.entity.TestUser;
import com.example.dynamicdatasource.mapper.TestUserMapper;
import com.example.dynamicdatasource.service.TestUserClassService;
import com.example.dynamicdatasource.service.TestUserService;
import com.example.dynamicdatasource.vo.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author qzz
 */
@RestController
@RequestMapping("/user")
public class TestUserController {

    @Autowired
    private TestUserMapper testUserMapper;

    @Autowired
    private TestUserService testUserService;

    @Autowired
    private TestUserClassService testUserClassService;

    /**
     * 根据id获取用户信息
     * @param id
     * @return
     */
    @GetMapping("/find")
    public Object find(int id){
        QueryWrapper<TestUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",id);
        //根据id获取用户信息
        TestUser testUser = testUserMapper.selectOne(queryWrapper);
        if(testUser!=null){
            return ResponseResult.success(testUser);
        }else{
            return ResponseResult.error("没有找到该用户");
        }
    }

    /**
     * 查看全部
     * @return
     */
    @GetMapping("/listAll")
    public Object findListAll(){

        Map<String,Object> result = new HashMap<>();
        //默认master数据源查询
        List<TestUser> masterUser = testUserService.getMasterUser();
        //从slave数据源查询
        List<TestUser> slaveUser = testUserService.getSlaveUser();

        result.put("master",masterUser);
        result.put("slave",slaveUser);
        //返回数据
        return ResponseResult.success(result);
    }
}
