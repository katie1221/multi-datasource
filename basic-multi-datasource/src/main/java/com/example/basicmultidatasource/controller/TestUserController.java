package com.example.basicmultidatasource.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.basicmultidatasource.entity.master.MasterTestUser;
import com.example.basicmultidatasource.entity.slave.SlaveTestUser;
import com.example.basicmultidatasource.mapper.master.MasterTestUserMapper;
import com.example.basicmultidatasource.mapper.slave.SlaveTestUserMapper;
import com.example.basicmultidatasource.vo.ResponseResult;
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
    private MasterTestUserMapper masterTestUserMapper;
    @Autowired
    private SlaveTestUserMapper slaveTestUserMapper;


    /**
     * 根据id获取用户信息
     * @param id
     * @return
     */
    @GetMapping("/find")
    public Object find(int id){
        QueryWrapper<SlaveTestUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",id);
        //根据id获取用户信息
        SlaveTestUser slaveTestUser = slaveTestUserMapper.selectOne(queryWrapper);
        if(slaveTestUser!=null){
            return ResponseResult.success(slaveTestUser);
        }else{
            return ResponseResult.error("没有找到该用户");
        }
    }

    /**
     * 根据全部
     * @return
     */
    @GetMapping("/listAll")
    public Object findListAll(){
        //自定义接口查看master数据库中的name不为空的用户
        QueryWrapper<MasterTestUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.isNotNull("name");
        List<MasterTestUser> resultData = masterTestUserMapper.selectAll(queryWrapper);
        //内置接口查看slave数据库中的所有用户
        List<SlaveTestUser> resultSlaveData = slaveTestUserMapper.selectList(null);

        Map<String,Object> result = new HashMap<>();
        result.put("master",resultData);
        result.put("slave",resultSlaveData);

        return ResponseResult.success(result);
    }

}
