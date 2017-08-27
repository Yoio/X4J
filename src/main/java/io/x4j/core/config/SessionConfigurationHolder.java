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

import javax.servlet.http.HttpSession;

/**
 * Session级别配置项
 * 
 * @author Yoio
 *
 */
public class SessionConfigurationHolder implements Config {
	/**
	 * 初始化方式不会获取到该对象（每次用户请求才会创建该对象）。每次请求单独持有该对象。每次请求加载该对象对系统资源是一种浪费。
	 * 但是分布式部署的话，容器都有session共享策略。分布式部署情况下，不通过session共享应用配置，那是用什么方式比较好？
	 */
	private HttpSession session;

	public Object c(String key) {

		return session.getAttribute(key);
	}

	public void c(String key, Object value) {
		session.setAttribute(key, value);
	}

	public void loadConfigurations() {
	}

}
