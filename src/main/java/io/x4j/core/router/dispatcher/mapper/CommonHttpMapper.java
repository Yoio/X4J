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

import io.x4j.core.router.Invoke;
import io.x4j.core.router.RequestContext;
import io.x4j.core.router.Router;
import io.x4j.core.router.dispatcher.mapper.PathInfo.Biulder;
import io.x4j.core.router.invoker.CommonInvoke;

public class CommonHttpMapper implements MapperParser {
	private final static Log logger = LogFactory.getLog(CommonHttpMapper.class);

	public void parseMapper(Router router) {
		logger.info("通用路由模块解析URL");
		logger.info("当前请求URL：" + router.getRequest().getServletPath());
		String[] paths = router.getRequest().getServletPath().split("/");

		PathInfo pathInfo = new PathInfo();
		Biulder builder = pathInfo.new Biulder(paths);
		builder.build();

		// 将pathinfo装载到Context中
		RequestContext.getInstance().registerPathInfo(pathInfo);

		// 执行请求
		Invoke invoke = new CommonInvoke();
		invoke.doInvoke();
	}

}
