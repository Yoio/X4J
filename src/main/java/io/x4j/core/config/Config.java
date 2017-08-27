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
/**
 * 动态配置接口
 * 1.基于上下文的动态配置
 * @author Yoio
 *
 */
public interface Config {
	/**
	 * 获取配置
	 * @param key
	 * @return
	 */
	public Object c(String key);
	/**
	 * 设置配置
	 * @param key
	 * @param value
	 */
	public void c(String key,Object value);
	

}
