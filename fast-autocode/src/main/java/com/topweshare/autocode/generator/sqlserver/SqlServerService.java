package com.topweshare.autocode.generator.sqlserver;


import com.topweshare.autocode.generator.ColumnSelector;
import com.topweshare.autocode.generator.DataBaseConfig;
import com.topweshare.autocode.generator.SQLService;
import com.topweshare.autocode.generator.TableSelector;

public class SqlServerService implements SQLService {

	@Override
	public TableSelector getTableSelector(DataBaseConfig dataBaseConfig) {
		return new SqlServerTableSelector(new SqlServerColumnSelector(dataBaseConfig), dataBaseConfig);
	}

	@Override
	public ColumnSelector getColumnSelector(DataBaseConfig dataBaseConfig) {
		return new SqlServerColumnSelector(dataBaseConfig);
	}

}
