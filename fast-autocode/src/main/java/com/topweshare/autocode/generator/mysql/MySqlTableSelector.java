package com.topweshare.autocode.generator.mysql;


import com.topweshare.autocode.generator.ColumnSelector;
import com.topweshare.autocode.generator.DataBaseConfig;
import com.topweshare.autocode.generator.TableDefinition;
import com.topweshare.autocode.generator.TableSelector;

import java.util.Map;

/**
 * 查询mysql数据库表
 */
public class MySqlTableSelector extends TableSelector {

	public MySqlTableSelector(ColumnSelector columnSelector,
			DataBaseConfig dataBaseConfig) {
		super(columnSelector, dataBaseConfig);
	}

	@Override
	protected String getShowTablesSQL(String dbName) {
		String sql = "SHOW TABLE STATUS FROM " + dbName;
		if(this.getSchTableNames() != null && this.getSchTableNames().size() > 0) {
			StringBuilder tables = new StringBuilder();
			for (String table : this.getSchTableNames()) {
				tables.append(",'").append(table).append("'");
			}
			sql += " WHERE NAME IN (" + tables.substring(1) + ")";
		}
		return sql;
	}

	@Override
	protected TableDefinition buildTableDefinition(Map<String, Object> tableMap) {
		TableDefinition tableDefinition = new TableDefinition();
		tableDefinition.setTableName((String)tableMap.get("NAME"));
		tableDefinition.setComment((String)tableMap.get("COMMENT"));
		return tableDefinition;
	}

}
