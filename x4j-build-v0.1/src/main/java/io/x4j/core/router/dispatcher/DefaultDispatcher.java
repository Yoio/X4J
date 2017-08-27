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

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import io.x4j.core.EngineInitialize;
import io.x4j.core.global.Constants.HTTP_METHOD;

/**
 * web请求都会通过该统一入口进行分发
 * 
 * @author Yoio
 *
 */
public final class DefaultDispatcher extends HttpServlet implements Dispatcher {
	private static final long serialVersionUID = 1L;
	private static Log logger = LogFactory.getLog(DefaultDispatcher.class);

	@Override
	public void init(ServletConfig config) throws ServletException {
		EngineInitialize engineInitialize = new EngineInitialize();
		engineInitialize.initApplicationState().init();
		super.init(config);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) {

		this.doDispatcher(req, resp, HTTP_METHOD.GET);
	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
		this.doDispatcher(req, resp, HTTP_METHOD.DELETE);
	}

	@Override
	protected void doHead(HttpServletRequest arg0, HttpServletResponse arg1) {

		this.doDispatcher(arg0, arg1, HTTP_METHOD.HEAD);
	}

	@Override
	protected void doOptions(HttpServletRequest arg0, HttpServletResponse arg1) {

		this.doDispatcher(arg0, arg1, HTTP_METHOD.TRACE);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) {

		this.doDispatcher(req, resp, HTTP_METHOD.POST);
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) {

		this.doDispatcher(req, resp, HTTP_METHOD.PUT);
	}

	@Override
	protected void doTrace(HttpServletRequest arg0, HttpServletResponse arg1) {

		this.doDispatcher(arg0, arg1, HTTP_METHOD.TRACE);
	}

	public void doDispatcher(HttpServletRequest req, HttpServletResponse resp, HTTP_METHOD method) {
		HandleRequestDispatcher handleRequestDispatcher = new HandleRequestDispatcher();
		handleRequestDispatcher.doDispatcher(req, resp, method);
		logger.debug("x4j framework is ready.");
	}

}
