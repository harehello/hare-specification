package com.hare.jpa.example;

/**
 * ClassName: UserDO
 * Package: com.hare.jpa.example
 * Description:
 *
 * @Author: wang cheng
 * @Create: 2023/3/22
 * @Version: v1.0
 **/
public class UserDO {

    private String username;
    private String nickname;
    private Integer age;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
