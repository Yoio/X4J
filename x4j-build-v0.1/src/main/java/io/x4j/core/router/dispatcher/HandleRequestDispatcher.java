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
package io.x4j.core.router.dispatcher;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.x4j.core.config.Config;
import io.x4j.core.config.ConfigFactory;
import io.x4j.core.global.Constants;
import io.x4j.core.global.Constants.HTTP_METHOD;
import io.x4j.core.model.Model;
import io.x4j.core.model.ModelAndView;
import io.x4j.core.router.CommonHttpRouter;
import io.x4j.core.router.HoldRequestChain;
import io.x4j.core.router.RequestContext;
import io.x4j.core.router.RestfulRouter;
import io.x4j.core.router.Router;
import io.x4j.core.router.dispatcher.mapper.CommonHttpMapper;
import io.x4j.core.router.dispatcher.mapper.DispatherMapper;
import io.x4j.core.router.dispatcher.mapper.MapperParser;
import io.x4j.core.router.dispatcher.mapper.RestfulMapper;
import io.x4j.util.StringUtils;

/**
 * 统一请求处理
 * 
 * @author Yoio
 *
 */
public class HandleRequestDispatcher implements Dispatcher {
	private static Config config = ConfigFactory.getConfiguration();

	public void doDispatcher(HttpServletRequest req, HttpServletResponse resp, HTTP_METHOD method) {
		// 加载路由
		// 1. 如果配置中将RESTFUL_AUTO设置为true，则实例化RestfulRouter
		Router router = null;
		MapperParser mapperParser = null;

		if (Constants.TRUE.equals(config.c("RESTFUL_AUTO"))) {
			router = new RestfulRouter(req, resp, method);
			mapperParser = new RestfulMapper();
		} else {
			router = new CommonHttpRouter(req, resp);
			mapperParser = new CommonHttpMapper();
		}

		HoldRequestChain holdRequestChain = new HoldRequestChain(router);
		// 请求上下文 放到上下文环境中
		RequestContext context = RequestContext.getInstance();
		context.registerHoldRequestChain(holdRequestChain);

		loadParametersObjectsToContext(context, req);

		loadModelInstance(context);
		DispatherMapper dispatherMapper = new DispatherMapper(router, mapperParser);
		dispatherMapper.doDispather();

	}

	/**
	 * 加载Model实例到上下文环境
	 * 
	 * @param context
	 */
	private void loadModelInstance(RequestContext context) {
		Model model = new ModelAndView();
		context.registerContextObject(Model.class.getName(), model);
	}

	/**
	 * 加载所有参数对象到上下文对象中
	 * 
	 * @param context
	 * @param req
	 */
	private void loadParametersObjectsToContext(RequestContext context, HttpServletRequest req) {
		Map<String, String[]> params = req.getParameterMap();
		Set<String> keys = params.keySet();
		Iterator<String> keysIt = keys.iterator();
		while (keysIt.hasNext()) {
			String key = (String) keysIt.next();
			String[] value = params.get(key);
			if (Constants.NUMBER.ONE == value.length) {

				String paraValue = value[Constants.NUMBER.ZERO];
				// 兼容参数绑定方案，字符串null生成空对象
				if (Constants.COMMON_ICON.STRINGNULL.equals(paraValue)) {
					paraValue = Constants.COMMON_ICON.NULL;
				}

				context.getContextObjs().put(key, paraValue);
			} else {
				// 集合参数处理装载
				context.getContextObjs().put(key, StringUtils.stringArray2String(value));
			}
		}
	}

}
