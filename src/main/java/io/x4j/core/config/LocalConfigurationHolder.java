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

import java.util.HashMap;
import java.util.Map;

/**
 * 本地化持有应用配置项信息
 * @author Yoio
 *
 */
public class LocalConfigurationHolder implements Config{
	/**
	 * 静态本地化持有属性配置
	 */
	private static Map<String,Object> configurationsStorage = new HashMap<String, Object>();

	public Object c(String key) {
		
		return configurationsStorage.get(key);
	}

	public void c(String key, Object value) {
		configurationsStorage.put(key, value);
	}

	public void loadConfigurations() {
	}
	
	
}
