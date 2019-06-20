package edu.nju.cinemasystem.data.vo;

import edu.nju.cinemasystem.data.po.User;

/**
 * 用户信息 包括：
 * ID 用户序号
 * name 用户名
 */
public class UserVO {
    private int ID;
    private String name;

    UserVO(int id, String name) {
        this.ID = id;
        this.name = name;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static UserVO assembleUserVO(User user) {
        return new UserVO(
                user.getId(),
                user.getName()
        );
    }
}
