package edu.nju.cinemasystem.dataservices;

import edu.nju.cinemasystem.data.po.Manager;

public interface ManagerMapper {
    int deleteByPrimaryKey(Integer userId);

    int insert(Manager record);

    int insertSelective(Manager record);

    Manager selectByPrimaryKey(Integer userId);

    int updateByPrimaryKeySelective(Manager record);

    int updateByPrimaryKey(Manager record);
}