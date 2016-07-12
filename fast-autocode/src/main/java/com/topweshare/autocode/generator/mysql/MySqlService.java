package com.topweshare.autocode.generator.mysql;


import com.topweshare.autocode.generator.ColumnSelector;
import com.topweshare.autocode.generator.DataBaseConfig;
import com.topweshare.autocode.generator.SQLService;
import com.topweshare.autocode.generator.TableSelector;

public class MySqlService implements SQLService {

	@Override
	public TableSelector getTableSelector(DataBaseConfig dataBaseConfig) {
		return new MySqlTableSelector(new MySqlColumnSelector(dataBaseConfig), dataBaseConfig);
	}

	@Override
	public ColumnSelector getColumnSelector(DataBaseConfig dataBaseConfig) {
		return new MySqlColumnSelector(dataBaseConfig);
	}


}
