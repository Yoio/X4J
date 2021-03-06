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
package com.yoio.app.controller;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.yoio.app.entity.User;

import io.x4j.core.model.Model;

public class IndexController {
	public void index(HttpServletResponse response, HttpSession session) {

	}

	public String get(int[] id,Model model) {
		model.addAttribute("ids", Arrays.toString(id));
		return "/WEB-INF/a.jsp";
	}

	public void login(HttpServletResponse response, HttpSession session, User user) throws IOException {

	}
}
