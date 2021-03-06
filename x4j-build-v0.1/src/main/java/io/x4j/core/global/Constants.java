/*
 * Copyright 2009-2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.x4j.core.global;

import java.util.Map;

/**
 * 框架常用常量
 * @author Yoio
 */
public class Constants {
	public static final String TRUE = "true";
	public static final String FRAMEWORK_CONFIGURATION_FILE = "x4j.properties";
	/**
	 * Http方法
	 * @author Yoio
	 *
	 */
	public static enum HTTP_METHOD{
		GET,POST,PUT,DELETE,HEAD,OPTIONS,TRACE
	}
	/**
	 * datasource type
	 * 
	 * @author Yoio
	 */
	public static interface DS_TYPE {
		public static final String JNDI = "jndi";
		public static final String C3P0 = "c3p0";
		public static final String POOLED = "pooled";
		public static final String UNPOOLED = "unpooled";
	}

	/**
	 * persistence result type
	 * 
	 * @author Yoio
	 */
	public static interface RESULTTYPE {
		public static final String COLUMN = "Column";
		public static final String LIST = "List";
	}

	/**
	 * database type
	 * 
	 * @author Yoio
	 */
	public static interface DB_TYPE {
		public static final String MYSQL = "mysql";
		public static final String ORACLE = "oracle";
		public static final String SQLSERVER = "sqlserver";
	}

	/**
	 * Operation Status
	 * 
	 * @author Yoio
	 */
	public static interface OPERATION {
		public static final String OP_SUCCESS = "1000";
		public static final String OP_ERROR = "1001";
	}

	/**
	 * system common icon
	 * 
	 * @author Yoio
	 */
	public static interface COMMON_ICON {
		// database sql field name splite
		public static final String DATABASE_SPLITE_ICON = ",";
		// comma
		public static final String COMMA = ",";
		// 空字符串
		public static final String STRINGNULL = "";
		// null
		public static final String NULL = "null";
		// PERIOD
		public static final String PERIOD = ".";
		// eqauls
		public static final String EQAULS = "=";
		// Enter space
		public static final String ENTER_SPACE = "\n";
		// space
		public static final String BLANK_SPACE = " ";
		// star
		public static final String STAR_SPACE = "*";
		// question
		public static final String QUESTION_SPACE = "?";
		// opening parentheses
		public static final String OPENING_PARENTHESES = "(";
		// closing paretheses
		public static final String CLOSING_PARETHESES = ")";
		// back quote
		public static final String BACK_QUOTE = "`";
		// slash
		public static final String SLASH_SPACE = "/";
		// back slash
		public static final String BACK_SLASH_SPACE = "\\";
	}

	// null
	public static interface NULL {
		// integer null
		public static final Integer INTEGER_NULL = null;
		// string null
		public static final String STRING_NULL = null;
		// object null
		public static final Object OBJECT_NULL = null;
		// map null
		@SuppressWarnings("rawtypes")
		public static final Map MAP_NULL = null;
	}

	/**
	 * Number
	 * 
	 * @author Yoio
	 */
	public static interface NUMBER {
		// ZERO
		public static final int ZERO = 0;
		// ONE
		public static final int ONE = 1;
		// TWO
		public static final int TWO = 2;
		// THREE
		public static final int THREE = 3;
		// FOUR
		public static final int FOUR = 4;
	}

	/**
	 * SQL_LANG
	 * 
	 * @author Yoio
	 */
	public static interface SQL_LANG {
		// SELECT
		public static final String SELECT = "SELECT";
		// UPDATE
		public static final String UPDATE = "UPDATE";
		// DELETE
		public static final String DELETE = "DELETE";
		// INSERT
		public static final String INSERT = "INSERT";
	}

	/**
	 * CACHE_TYPE
	 * 
	 * @author Yoio
	 */
	public static interface CACHE_TYPE {
		// Redis
		public static final String REDIS = "Redis";
		// Perpetual
		public static final String PERPETUAL = "Perpetual";
	}
}
