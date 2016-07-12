package com.topweshare.autocode.entity;

import org.durcframework.core.SearchEntity;
import org.durcframework.core.expression.annotation.ValueField;

import java.util.Date;

public class BackUserSch extends SearchEntity{

    private String usernameSch;
    private String passwordSch;
    private Date addTimeSch;

    public void setUsernameSch(String usernameSch){
        this.usernameSch = usernameSch;
    }
    
    @ValueField(column = "username")
    public String getUsernameSch(){
        return this.usernameSch;
    }

    public void setPasswordSch(String passwordSch){
        this.passwordSch = passwordSch;
    }
    
    @ValueField(column = "password")
    public String getPasswordSch(){
        return this.passwordSch;
    }

    public void setAddTimeSch(Date addTimeSch){
        this.addTimeSch = addTimeSch;
    }
    
    @ValueField(column = "add_time")
    public Date getAddTimeSch(){
        return this.addTimeSch;
    }


}