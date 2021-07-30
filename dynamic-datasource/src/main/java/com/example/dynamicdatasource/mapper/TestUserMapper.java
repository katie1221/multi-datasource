package com.example.dynamicdatasource.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.example.dynamicdatasource.entity.TestUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * @author qzz
 */
@Repository
public interface TestUserMapper extends BaseMapper<TestUser> {

    /**
     * 自定义查询
     * @param wrapper
     * @return
     */
    List<TestUser> selectAll(@Param(Constants.WRAPPER)Wrapper<TestUser> wrapper);
}
