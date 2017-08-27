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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 通用路由
 * 
 * @author Yoio
 *
 */
public class CommonHttpRouter implements Router {
	private static final Log logger = LogFactory.getLog(CommonHttpRouter.class);
	private HttpServletRequest req;
	private HttpServletResponse resp;
	public CommonHttpRouter(HttpServletRequest req, HttpServletResponse resp) {
		logger.info("当前为通用路由模式。");
		this.req = req;
		this.resp = resp;
	}
	public HttpServletRequest getRequest() {
		return req;
	}
	public void setReq(HttpServletRequest req) {
		this.req = req;
	}
	public HttpServletResponse getResponse() {
		return resp;
	}
	public void setResp(HttpServletResponse resp) {
		this.resp = resp;
	}
	
	

}
