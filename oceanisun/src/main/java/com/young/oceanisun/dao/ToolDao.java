package com.young.oceanisun.dao;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface ToolDao {

    @Select("SELECT token FROM baidu_token")
    String selectBaiduToken();

    @Update("UPDATE baidu_token SET token = #{token}")
    void updateBaiduToken(String token);
}
