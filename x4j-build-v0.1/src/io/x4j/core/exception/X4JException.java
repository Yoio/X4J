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
package io.x4j.core.exception;

/**
 * 异常基类
 * @author Yoio
 */
public class X4JException extends RuntimeException {

	private static final long serialVersionUID = 1246573576754371043L;

	public X4JException() {
		super();
	}

	public X4JException(String msg) {
		super(msg);
	}

	public X4JException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public X4JException(Throwable cause) {
		super(cause);
	}
}
