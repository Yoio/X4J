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

import io.x4j.core.exception.X4JException;

/**
 * 参数加载异常
 * @author Yoio
 */
public class ConfigurationLoadException extends X4JException {

	private static final long serialVersionUID = -41380054189525L;
	public ConfigurationLoadException() {
		super();
	}

	public ConfigurationLoadException(String msg) {
		super(msg);
	}

	public ConfigurationLoadException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public ConfigurationLoadException(Throwable cause) {
		super(cause);
	}
}
