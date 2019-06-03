package edu.nju.cinemasystem.dataservices;

import edu.nju.cinemasystem.data.po.Staff;

public interface StaffMapper {
    int deleteByPrimaryKey(Integer userId);

    int insert(Staff record);

    int insertSelective(Staff record);
}