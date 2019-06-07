package edu.nju.cinemasystem.dataservices.user;

import edu.nju.cinemasystem.data.po.Manager;
import edu.nju.cinemasystem.data.po.Staff;
import edu.nju.cinemasystem.data.po.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User selectByName(String name);

    List<Staff> selectAllStaff();

    List<Manager> selectAllManager();

    Staff selectStaffByName(String name);

    Manager selectManagerByName(String name);
}