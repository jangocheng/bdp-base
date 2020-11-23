package com.platform.repository;

import com.platform.entity.ModelFeature;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * author: wlhbdp
 * create: 2020-05-17 18:22
 */
@Mapper
public interface ModelFeatureRepo {

    @Select("select * from model_repo where id=#{id}")
    ModelFeature findById(@Param("id") Integer id);

    @Select("select * from model_repo where id in (select model_id from model_selection where status=0 and model_type=#{modelType})")
    List<ModelFeature> findByModelType(@Param("modelType") String modelType);
}
