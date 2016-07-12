package com.topweshare.autocode.service;


import com.topweshare.autocode.dao.BackUserDao;
import com.topweshare.autocode.entity.BackUser;
import org.durcframework.core.service.CrudService;
import org.springframework.stereotype.Service;

@Service
public class BackUserService extends CrudService<BackUser, BackUserDao> {

}
