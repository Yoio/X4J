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
 * @author Yoio<Yoio@3cto.net>
 */
public class PersistenceException extends X4JException {

	private static final long serialVersionUID = -4138006535654189525L;
	public PersistenceException() {
		super();
	}

	public PersistenceException(String msg) {
		super(msg);
	}

	public PersistenceException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public PersistenceException(Throwable cause) {
		super(cause);
	}
}
