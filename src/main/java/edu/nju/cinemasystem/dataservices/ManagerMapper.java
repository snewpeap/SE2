package edu.nju.cinemasystem.dataservices;

import edu.nju.cinemasystem.data.po.Manager;
import edu.nju.cinemasystem.data.po.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ManagerMapper extends UserMapper {
    int deleteByPrimaryKey(Integer userId);

    int insert(Manager record);

    int insertSelective(Manager record);

    Manager selectManagerByPrimaryKey(Integer userId);

    int updateByPrimaryKeySelective(Manager record);

    int updateByPrimaryKey(Manager record);

    List<User> selectAll();

    User selectByName(String name);
}