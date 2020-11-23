package com.platform.repository;

import com.platform.entity.ModelSelection;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * author: wlhbdp
 * create: 2020-05-19 12:35
 */
@Mapper
public interface ModelSelectionRepo {

    @Select("select * from model_selection where model_name=#{modelName} and status=0")
    ModelSelection findByModelName(@Param("modelName") String modelName);

    @Select("select * from model_selection where model_type=#{modelType}")
    List<ModelSelection> findAllByModelType(@Param("modelType")String modelType);

    @Update("update model_selection set model_id=#{modelId} where model_name=#{modelName}")
    Integer updateModelByName(@Param("modelName") String modelName, @Param("modelId")int modelId);

    @Update("update model_selection set status=1 where model_name=#{modelName}")
    Integer deleteModelByName(@Param("modelName") String modelName);
}
