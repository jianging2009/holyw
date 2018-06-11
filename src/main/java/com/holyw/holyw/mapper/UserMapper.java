package com.holyw.holyw.mapper;

import com.holyw.holyw.model.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UserMapper {

    int deleteByPrimaryKey(Integer userId);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer userId);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    List<User> selectAllUser();

    @Select("SELECT MAX(ID) FROM SM_USER")
    Integer maxId() throws Exception;

    @Select("SELECT * FROM SM_USER where id = #{id}")
    User get(@Param("id") Integer id) throws Exception;

    @Select("SELECT * FROM SM_USER where userName = #{userName}")
    User findByUserName(@Param("userName") String userName) throws Exception;

    @Select("INSERT INTO SM_USER(ID,USERNAME,PASSWORD,CREATETIME,UPDATETIME,MOBILE,EMAIL,ADDRESS,GENDER,PERSONNAME}) " +
            "VALUES " +
            "(#{id},#{userName},#{password},#{createTime},#{updateTime},#{mobile},#{email},#{address},#{gender},#{personName})")
    void save(@Param("id")Integer id);




}
