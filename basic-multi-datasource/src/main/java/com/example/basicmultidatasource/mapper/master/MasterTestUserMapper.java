package com.example.basicmultidatasource.mapper.master;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.example.basicmultidatasource.entity.master.MasterTestUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author qzz
 */
public interface MasterTestUserMapper extends BaseMapper<MasterTestUser> {

    /**
     * 自定义查询
     * @param wrapper 条件构造器
     * @return
     */
    List<MasterTestUser> selectAll(@Param(Constants.WRAPPER) Wrapper<MasterTestUser> wrapper);
}
