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
package io.x4j.core.router;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import io.x4j.core.Context;
import io.x4j.core.router.dispatcher.mapper.PathInfo;

/**
 * 请求上下文
 * 
 * @author Yoio
 *
 */
public final class RequestContext implements Context {
	private static RequestContext instance = new RequestContext();
	private HoldRequestChain holdRequestChain;
	private PathInfo pathInfo;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private HttpSession session;
	private Map<String, Object> contextObjs = new HashMap<String, Object>();

	private RequestContext() {
	}

	public static RequestContext getInstance() {
		return instance;
	}

	public void registerContextObject(String key, Object value) {
		contextObjs.put(key, value);
	}

	public HttpSession getSession() {
		return session;
	}

	public void setSession(HttpSession session) {
		this.session = session;
	}

	public Map<String, Object> getContextObjs() {
		return contextObjs;
	}

	public void setContextObjs(Map<String, Object> contextObjs) {
		this.contextObjs = contextObjs;
	}

	public void registerHoldRequestChain(HoldRequestChain holdRequestChain) {
		this.holdRequestChain = holdRequestChain;
		// 注册request和response
		registerRequest(holdRequestChain.getRouter().getRequest());
		registerResponse(holdRequestChain.getRouter().getResponse());
		// 注册上下文对象
		registerContextObject(request.getClass().getName(), request);
		registerContextObject(response.getClass().getName(), response);
		// 注册Session对象到上下文对象
		registerSessionObject();
	}

	private void registerSessionObject() {
		this.session = request.getSession(true);
		registerContextObject(session.getClass().getName(), session);
	}

	public HoldRequestChain getHoldRequestChain() {
		return holdRequestChain;
	}

	public void registerPathInfo(PathInfo pi) {
		pathInfo = pi;
	}

	public PathInfo getPathInfo() {
		return pathInfo;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void registerRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void registerResponse(HttpServletResponse response) {
		this.response = response;
	}

	// 通过类名返回对象
	public Object searchObjByType(Class<?> type) {
		Collection<Object> objs = contextObjs.values();
		Iterator<Object> it = objs.iterator();
		Object obj = null;
		while (it.hasNext()) {
			Object object = (Object) it.next();
			if (type.isInstance(object)) {
				obj = object;
				break;
			}

		}
		return obj;
	}

	public boolean isInContextObjs(Class<?> type) {
		boolean flag = false;
		Collection<Object> objs = contextObjs.values();
		Iterator<Object> it = objs.iterator();
		while (it.hasNext()) {
			Object object = (Object) it.next();
			if (type.isInstance(object)) {
				flag = true;
				break;
			}

		}
		return flag;
	}

	/**
	 * 通过参数名返回对象
	 * 
	 * @param string
	 * @return
	 */
	public Object searchObjByName(String paramName) {

		return contextObjs.get(paramName);
	}

}
