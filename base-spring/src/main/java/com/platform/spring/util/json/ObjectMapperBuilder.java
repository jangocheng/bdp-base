package com.platform.spring.util.json;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * ObjectMapperBuilder
 */
public class ObjectMapperBuilder {
    
    public static TypeReference<Map<String, Object>> TYPE_MAP = new TypeReference<Map<String, Object>>() {};
    
    private ObjectMapper mapper = new ObjectMapper();
    
    private ObjectMapperBuilder() {
        // 设置输入时忽略JSON字符串中存在而Java对象实际没有的属性
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }
    
    /**
     * 取出Mapper做进一步的设置或使用其他序列化API.
     */
    public ObjectMapper getMapper() {
        return mapper;
    }

    /**
     * 创建输出全部属性到Json字符串的Binder.
     * @return
     */
    public static ObjectMapperBuilder defaultBuilder() {
        return new ObjectMapperBuilder();
    }
    
    /**
     * 创建只输出非空属性到Json字符串的Binder.
     */
    public static ObjectMapperBuilder nonNullBuilder() {
        ObjectMapperBuilder builder = new ObjectMapperBuilder();
        // 设置输出包含的属性
        builder.getMapper().setSerializationInclusion(Include.NON_NULL);
        // 设置map的值为空时不序列化输出
        builder.getMapper().configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
        
        return builder;
    }
    
    /**
     * 如果JSON字符串为null或者空串,返回null. 如果JSON字符串为"[]",返回空集合.
     * 
     * 如需读取集合如List/Map,且不是List<String>这种简单类型时使用如下语句: <br/>
     * <code>
     * List<MyBean> beanList = binder.getMapper().readValue(listString, new TypeReference<List<MyBean>>() {});
     * </code>
     * @throws IOException 
     * @throws JsonMappingException 
     * @throws JsonParseException 
     */
    public <T> T fromJson(String jsonString, Class<T> clazz) throws JsonParseException, JsonMappingException, IOException {
        if (StringUtils.isBlank(jsonString)) {
            return null;
        }
        return mapper.readValue(jsonString, clazz);
    }
    
    /**
     * 如果JSON字符串为null或者空串,返回null. 如果JSON字符串为"[]",返回空集合.
     * 
     * 如需读取集合如List/Map,且不是List<String>这种简单类型时使用如下语句: <br/>
     * <code>
     * List<MyBean> beanList = binder.getMapper().readValue(listString, new TypeReference<List<MyBean>>() {});
     * </code>
     * @throws IOException 
     * @throws JsonMappingException 
     * @throws JsonParseException 
     */
    public <T> T fromJson(String jsonString, TypeReference<T> typeReference) throws JsonParseException, JsonMappingException, IOException {
        if (StringUtils.isBlank(jsonString)) {
            return null;
        }
        return mapper.readValue(jsonString, typeReference);
    }
    
    /**
     * 将json字符串解析成map,如果JSON字符串为null或者空串,返回null
     * @throws IOException 
     * @throws JsonMappingException 
     * @throws JsonParseException 
     * 
     */
    public Map<String, Object> fromJsonToMap(String jsonString) throws JsonParseException, JsonMappingException, IOException {
        if (StringUtils.isBlank(jsonString)) {
            return null;
        }
        return mapper.readValue(jsonString, TYPE_MAP);
    }
    
    /**
     * 如果对象为null,返回null. 如果集合为空集合,返回"[]".
     * @throws JsonProcessingException 
     */
    public String toJson(Object object) throws JsonProcessingException {
        if (null == object) {
            return null;
        }
        return mapper.writeValueAsString(object);
    }
    
    public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {
        // 使用方法
        String jsonString = "{\"test\": \"我是string\",\"abc\":111}";
        Map<String, Object> result = ObjectMapperBuilder.defaultBuilder().fromJson(jsonString, ObjectMapperBuilder.TYPE_MAP);
        System.out.println(result.get("test"));
        System.out.println(result.get("testt"));
        
        String jsonResult = ObjectMapperBuilder.defaultBuilder().toJson(result);
        System.out.println(jsonResult);
    }
}