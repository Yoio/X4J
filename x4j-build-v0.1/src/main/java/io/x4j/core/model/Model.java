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
package io.x4j.core.model;

/**
 * Model层接口
 * 
 * @author Yoio
 *
 */
public interface Model {
	/**
	 * 设置属性
	 * 
	 * @param key
	 * @param value
	 */
	public void addAttribute(String key, Object value);

	/**
	 * 获取属性
	 * 
	 * @param key
	 * @return
	 */
	public Object getAttribute(String key);
}
