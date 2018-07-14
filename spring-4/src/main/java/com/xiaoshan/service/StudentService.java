package com.xiaoshan.service;

import com.xiaoshan.dao.StudentDao;

/**
 * @author YushanZhao
 * @Date:2018/7/14
 */
public class StudentService {

    private StudentDao studentDao;

    /*public StudentService(StudentDao studentDao){
        this.studentDao = studentDao;
    }*/

    public void save(){
        studentDao.save();
    }

    public void setStudentDao(StudentDao studentDao) {
        this.studentDao = studentDao;
    }
}

/*
2
public class StudentService {

    private StudentDao studentDao;

    public StudentService(StudentDao studentDao){
        this.studentDao = studentDao;
    }

    public void save(){
        studentDao.save();
    }

}*/

/*
1
public class StudentService {

    private StudentDao studentDao;

    public void save(){
        studentDao.save();
    }

    public void setStudentDao(StudentDao studentDao) {
        this.studentDao = studentDao;
    }
}*/
