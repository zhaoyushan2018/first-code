package com.xiaoshan.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author YushanZhao
 * @Date:2018/7/10
 */
public class Movie implements Serializable {

    private Integer id;
    private String movieName;
    private String directorName;
    private Integer userId;

    private User user;

    private List<Type> typeList;

    public List<Type> getTypeList() {
        return typeList;
    }

    public void setTypeList(List<Type> typeList) {
        this.typeList = typeList;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getDirectorName() {
        return directorName;
    }

    public void setDirectorName(String directorName) {
        this.directorName = directorName;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

 /*   @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", movieName='" + movieName + '\'' +
                ", directorName='" + directorName + '\'' +
                ", userId=" + userId +
                ", user=" + user +
                '}';
    }*/

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", movieName='" + movieName + '\'' +
                ", directorName='" + directorName + '\'' +
                ", userId=" + userId +
                ", user=" + user +
                ", typeList=" + typeList +
                '}';
    }
}
