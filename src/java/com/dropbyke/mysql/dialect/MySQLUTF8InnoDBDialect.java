package com.dropbyke.mysql.dialect;

import org.hibernate.dialect.MySQL5InnoDBDialect;

public class MySQLUTF8InnoDBDialect extends MySQL5InnoDBDialect {

	@Override
	public String getTableTypeString() {
		return " ENGINE=InnoDB DEFAULT CHARSET=utf8";
	}

}
