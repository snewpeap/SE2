package edu.nju.cinemasystem.dataservices.user;

import edu.nju.cinemasystem.data.po.Role;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface RoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Integer id);

    Role selectRoleByName(String role_name);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);
}