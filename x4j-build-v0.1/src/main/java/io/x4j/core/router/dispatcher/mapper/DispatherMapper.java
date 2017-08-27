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

import io.x4j.core.router.Router;

public class DispatherMapper {

	private Router router;
	private MapperParser mapperParser;

	public DispatherMapper(Router router, MapperParser mapperParser) {
		this.router = router;
		this.mapperParser = mapperParser;
	}
	
	public void doDispather() {
		mapperParser.parseMapper(router);
	}

}
