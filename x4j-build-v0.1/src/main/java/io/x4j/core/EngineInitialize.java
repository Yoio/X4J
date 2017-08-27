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
package io.x4j.core;

import io.x4j.core.config.CommonConfiguration;

/**
 * 引擎初始化
 * @author Yoio
 *
 */
public class EngineInitialize {
	private CommonConfiguration configuration;
	/**
	 * 加载应用状态
	 * @return
	 */
	public EngineInitialize initApplicationState() {
		configuration = new CommonConfiguration();
		return this;
	}
	
	/**
	 * 调用该方法引擎开始初始化
	 */
	public void init() {
		configuration.loadConfigurations();
	}
}
