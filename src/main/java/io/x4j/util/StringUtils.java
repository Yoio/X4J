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
package io.x4j.util;

import io.x4j.core.global.Constants;

/**
 * 字符串工具类
 * 提供常用字符串处理功能
 * @author Yoio
 *
 */
public class StringUtils {
	/**
	 * 将字符串数组处理成以“,”分割的字符串
	 * 例如，
	 * 	String[] strings = {"1","2","3"};
	 * 处理成"1,2,3"
	 * 
	 * @param strings
	 * @return
	 */
	public static String stringArray2String(String[] strings) {
		StringBuilder buildString = new StringBuilder();
		for (int i = 0; i < strings.length; i++) {
			buildString.append(strings[i]);
			if(i == strings.length-1){break;}
			buildString.append(Constants.COMMON_ICON.COMMA);
			
		}
		return buildString.toString();
	}
}
