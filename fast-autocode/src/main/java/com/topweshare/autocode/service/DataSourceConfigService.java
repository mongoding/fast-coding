package com.topweshare.autocode.service;

import com.topweshare.autocode.dao.DataSourceConfigDao;
import com.topweshare.autocode.entity.DataSourceConfig;
import org.durcframework.core.service.CrudService;
import org.springframework.stereotype.Service;

@Service
public class DataSourceConfigService extends CrudService<DataSourceConfig, DataSourceConfigDao> {

}
