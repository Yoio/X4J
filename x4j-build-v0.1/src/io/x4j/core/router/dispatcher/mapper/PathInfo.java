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

import java.util.ArrayList;
import java.util.List;

import io.x4j.core.config.Config;
import io.x4j.core.config.ConfigFactory;
import io.x4j.core.global.Constants;

/**
 * 请求信息
 * 
 * @author Yoio
 *
 */
public class PathInfo {
	private Config config = ConfigFactory.getConfiguration();
	/**
	 * list中path的存放顺序 0.Module 模块 1.Controller 控制器 2.Action 行为 下面几点 1.
	 * 使用list考虑到无序的，解析URL是通过顺序放到这里面 2. 这里不作为static是因为当前对象的生命周期应该是Request级别。
	 */
	private List<String> pathinfos = new ArrayList<String>();

	/**
	 * 这里比较重要，对于通用模式来说，项目分为一级模块，二级模块，行为，行为后面可能有参数 配置文件中进行模块配置，
	 * 通过获取配置的模块信息，和这边的size进行对比来判断是不是正常请求。
	 * 
	 * @return
	 */
	public int getSize() {
		return pathinfos.size();
	}
	
	public String getPath(int index) {
		return pathinfos.get(index);
	}

	public void add(String info) {
		pathinfos.add(info);
	}

	public class Biulder {
		private String[] paths;

		public Biulder(String[] paths) {
			super();
			this.paths = paths;
		}

		public void build() {
			// 首先判断是否是敏捷开发模式，是的话分模块
			if (Constants.TRUE.equals(config.c("IS_AGILE").toString().trim())) {
				
				loadModule();
				loadController();
				loadAction();

			}else{
				loadPathInfo();
			}
			
		}

		private void loadPathInfo() {
			if( paths.length >= Constants.NUMBER.TWO ){
				// 装载Controller
				PathInfo.this.add(paths[Constants.NUMBER.ONE]);
			}else{
				PathInfo.this.add(config.c("DEFAULT_CONTROLLER").toString());
			}
			
			if(paths.length >= Constants.NUMBER.THREE){
				// 装载Action
				PathInfo.this.add(paths[Constants.NUMBER.TWO]);
			}else{
				PathInfo.this.add(config.c("DEFAULT_ACTION").toString());
			}
		}

		private void loadAction() {
			if(Constants.NUMBER.FOUR == paths.length){
				// 装载Action
				PathInfo.this.add(paths[Constants.NUMBER.THREE]);
			}else{
				PathInfo.this.add(config.c("DEFAULT_ACTION").toString());
			}
		}

		private void loadController() {
			if(Constants.NUMBER.THREE == paths.length){
				// 装载Controller
				PathInfo.this.add(paths[Constants.NUMBER.TWO]);
			}else{
				PathInfo.this.add(config.c("DEFAULT_CONTROLLER").toString());
			}
		}

		private void loadModule() {
			String[] appmodules = config.c("APP_MODULE").toString().split(Constants.COMMON_ICON.COMMA);
			for (String module : appmodules) {
				if (paths.length > Constants.NUMBER.ONE) {
					if (module.equals(paths[Constants.NUMBER.ONE])) {
						// 装载Module
						PathInfo.this.add(paths[Constants.NUMBER.ONE]);
					}
				}
			}
			if(PathInfo.this.getSize() <= Constants.NUMBER.ZERO){
				throw new PathInfoLoadErrorException("请在项目配置文件中配置APP_MODULE项，模块"+paths[Constants.NUMBER.ONE]+"不存在。");
				
			}
		}

	}

}
