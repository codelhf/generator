package com.example.generator.demo.dao;

import com.example.generator.demo.pojo.Result;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Title: ResultMapper
 * @Description: Result持久层
 * @Company: example
 * @Author: liuhf
 * @CreateTime: 2019-05-09 00:02:24
 */
@Mapper
public interface ResultMapper  {

    Result selectByPrimaryKey(String id);
    
    List<Result> selectPageList(Result example);
}