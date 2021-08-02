package com.example.parametricdynamicdatasource.controller;

import com.example.parametricdynamicdatasource.context.DynamicDataSourceContextHolder;
import com.example.parametricdynamicdatasource.mapper.TableMapper;
import com.example.parametricdynamicdatasource.proxy.JdkParamDsMethodProxy;
import com.example.parametricdynamicdatasource.util.DataSourceUtil;
import com.example.parametricdynamicdatasource.vo.DbInfo;
import com.example.parametricdynamicdatasource.vo.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Map;

/**
 * @author qzz
 */
@RestController
@RequestMapping("/user")
public class TestUserController {

    @Autowired
    private TableMapper tableMapper;

    /**
     * 根据数据库连接信息获取表信息
     * @param dbInfo
     * @return
     */
    @GetMapping("table")
    public Object findWithDbInfo(DbInfo dbInfo){
        /**
         * 访问地址 http://localhost:8083/parametric/user/table?ip=localhost&port=3306&dbName=db_user&username=root&password=root
         */
        //数据源key
        String newDsKey = "slave";
//        //添加数据源
//        DataSourceUtil.addDataSourceToDynamic(newDsKey,dbInfo);
//        DynamicDataSourceContextHolder.setContextKey(newDsKey);
//        //查询表信息
//        List<Map<String,Object>> tables = tableMapper.selectTableList();
//        DynamicDataSourceContextHolder.removeContextKey();


        //使用代理切换数据源
        TableMapper tableMapperProxy = (TableMapper) JdkParamDsMethodProxy.createProxyInstance(tableMapper, newDsKey, dbInfo);
        //查询表信息
        List<Map<String,Object>> tables = tableMapperProxy.selectTableList();
        return ResponseResult.success(tables);
    }

}
