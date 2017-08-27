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
package io.x4j.core.config;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import io.x4j.core.global.Constants;
import io.x4j.core.io.Resources;

/**
 * 通用配置加载器
 * 
 * @author Yoio
 *
 */
public class CommonConfiguration implements Config {
	private static final Log logger = LogFactory.getLog(CommonConfiguration.class);
	private static HashMap<String, Object> configurations = new HashMap<String, Object>();
	private static CommonConfiguration instance = new CommonConfiguration();

	public CommonConfiguration() {
	}

	public static CommonConfiguration getInstance() {
		return instance;
	}

	public Object c(String key) {

		return configurations.get(key);
	}

	public void c(String key, Object value) {
		configurations.put(key, value);
	}

	public void loadConfigurations() {
		Properties props = null;
		try {
			props = Resources.getResourceAsProperties(Constants.FRAMEWORK_CONFIGURATION_FILE);
			logger.debug("框架配置文件已加载：" + Constants.FRAMEWORK_CONFIGURATION_FILE);
			Enumeration<Object> keys = props.keys();
			while (keys.hasMoreElements()) {
				String key = (String) keys.nextElement();
				//去掉了properties文件中的空格，这样更方便开发者配置
				c(key, props.get(key).toString().trim());
			}
		} catch (IOException e) {
			logger.info("没有找到框架配置文件：" + Constants.FRAMEWORK_CONFIGURATION_FILE);
			e.printStackTrace();
		}
	}

}
