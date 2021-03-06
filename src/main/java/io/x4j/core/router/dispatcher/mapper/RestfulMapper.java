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
package io.x4j.core.router.dispatcher.mapper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import io.x4j.core.router.Router;

public class RestfulMapper implements MapperParser {
	private final static Log logger = LogFactory.getLog(RestfulMapper.class);
	public void parseMapper(Router router) {
		logger.info("RESTful路由模块解析URL");
	}



}
