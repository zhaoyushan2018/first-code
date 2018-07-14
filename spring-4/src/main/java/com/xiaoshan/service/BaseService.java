package com.xiaoshan.service;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * @author YushanZhao
 * @Date:2018/7/14
 */
public class BaseService {

    private Integer id;
    private String name;
    private Double score;
    private List<String> stringList;
    private Set<Integer> numSets;
    private Map<String, String> maps;
    //键值对,获取properties配置文件的键值对
    //本质时map集合, 只能存放<String,String>
    private Properties properties;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public void setStringList(List<String> stringList) {
        this.stringList = stringList;
    }

    public void setNumSets(Set<Integer> numSets) {
        this.numSets = numSets;
    }

    public void setMaps(Map<String, String> maps) {
        this.maps = maps;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getScore() {
        return score;
    }

    public List<String> getStringList() {
        return stringList;
    }

    public Set<Integer> getNumSets() {
        return numSets;
    }

    public Map<String, String> getMaps() {
        return maps;
    }

    public Properties getProperties() {
        return properties;
    }
}
