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
package io.x4j.core.router.invoker;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Collection;
import java.util.Iterator;

import io.x4j.core.config.Config;
import io.x4j.core.config.ConfigFactory;
import io.x4j.core.global.Constants;
import io.x4j.core.router.Invoke;
import io.x4j.core.router.RequestContext;
import io.x4j.core.router.dispatcher.mapper.PathInfo;
import io.x4j.paranamer.BytecodeReadingParanamer;
import io.x4j.paranamer.Paranamer;
import io.x4j.typeparser.TypeParser;
import io.x4j.util.ClassUtils;

public class CommonInvoke implements Invoke {
	private Config config = ConfigFactory.getConfiguration();
	// 控制器在pathinfo中的索引位置
	private int controllerIndex = Constants.NUMBER.ZERO;
	// 行为在pathinfo中的索引位置
	private int actionIndex = Constants.NUMBER.ONE;
	private int parameterCount;
	private Class<?> targetClass;
	private Method invokeMethod;
	private Object invokeResult;

	public void doInvoke() {
		PathInfo pathInfo = RequestContext.getInstance().getPathInfo();
		targetClass = buildClassWithPathInfo(pathInfo);
		Method[] methods = getMethodsByClass(targetClass);
		executionMethodInvoke(pathInfo, methods);

	}

	private void executionMethodInvoke(PathInfo pathInfo, Method[] methods) {
		for (Method method : methods) {
			if (pathInfo.getPath(actionIndex).equals(method.getName())) {
				invokeMethod = method;
				break;
			}
		}
		exec(invokeMethod);
	}

	/**
	 * 执行调用
	 * 
	 * @param method
	 */
	private void exec(Method method) {
		// Class<?> returnType = method.getReturnType();
		parameterCount = method.getParameterCount();
		Class<?>[] parameterTypes = method.getParameterTypes();
		Parameter[] parameters = method.getParameters();

		Object[] parameterObjs = loadParameterWithMethod(parameterTypes, parameters);
		try {
			invokeResult = method.invoke(targetClass.newInstance(), parameterObjs);
		} catch (IllegalAccessException e) {

			e.printStackTrace();
		} catch (IllegalArgumentException e) {

			e.printStackTrace();
		} catch (InvocationTargetException e) {

			e.printStackTrace();
		} catch (InstantiationException e) {

			e.printStackTrace();
		}
		ResolveInvokedResultStrategy strategy = null;
		if (invokeResult instanceof String) {
			strategy = new ResolveInvokedResultStrategyToStringType(invokeResult);
		}
		strategy.resoleWithStrategy();
	}

	/**
	 * 方法的参数加载对象 可优化：Parameter包含类型和名称
	 * 
	 * @param parameterTypes
	 * @param parameters
	 * @return
	 */
	private Object[] loadParameterWithMethod(Class[] parameterTypes, Parameter[] parameters) {
		Object[] paramObjs = new Object[parameterCount];

		// 厉害了，我的哥。
		// 今天找了一整天，JDK/ASM都不行，Paranamer稳！
		Paranamer paranamer = new BytecodeReadingParanamer();
		String[] parametersName = paranamer.lookupParameterNames(invokeMethod);

		int i = Constants.NUMBER.ZERO;
		for (Class<?> type : parameterTypes) {
			if (RequestContext.getInstance().isInContextObjs(type)) {
				paramObjs[i] = RequestContext.getInstance().searchObjByType(type);
			} else {
				TypeParser parser = TypeParser.newBuilder().build();
				Object searchedObject = RequestContext.getInstance().searchObjByName(parametersName[i]);
				paramObjs[i] = parser.parseType(searchedObject.toString(), type);
			}
			i++;
		}
		// 通过类型加载
		// searchAndBindByType(paramObjs, parameterTypes);
		// TODO 通过参数名加载
		return paramObjs;
	}

	/**
	 * 根据类型加载参数对象
	 * 
	 * @param paramObjs
	 * @param parameterTypes
	 */
	private void searchAndBindByType(Object[] paramObjs, Class[] parameterTypes) {
		Collection<Object> objs = RequestContext.getInstance().getContextObjs().values();
		int i = Constants.NUMBER.ZERO;
		for (Class<?> type : parameterTypes) {
			Iterator<Object> it = objs.iterator();
			while (it.hasNext()) {
				Object object = (Object) it.next();
				if (object.getClass().getName().equals(type.getName())) {
					paramObjs[i] = object;
					i++;
				}

			}
		}
	}

	/**
	 * 获取所有方法
	 * 
	 * @param clz
	 * @return
	 */
	private Method[] getMethodsByClass(Class<?> clz) {
		return clz.getDeclaredMethods();
	}

	/**
	 * 通过PathInfo构造Class
	 * 
	 * @param pathInfo
	 * @return
	 */
	private Class<?> buildClassWithPathInfo(PathInfo pathInfo) {
		StringBuilder clzName = new StringBuilder();
		clzName.append(config.c("DEFAULT_PACKAGE_PREFIX").toString().trim());
		clzName.append(Constants.COMMON_ICON.PERIOD);
		clzName.append(config.c("DEFAULT_PACKAGE_CONTROLLER").toString().trim());
		clzName.append(Constants.COMMON_ICON.PERIOD);
		if (Constants.TRUE.equals(config.c("IS_AGILE").toString().trim())) {
			clzName.append(pathInfo.getPath(Constants.NUMBER.ZERO).toLowerCase());
			clzName.append(Constants.COMMON_ICON.PERIOD);
			controllerIndex = Constants.NUMBER.ONE;
			actionIndex = Constants.NUMBER.TWO;
		}
		clzName.append(pathInfo.getPath(controllerIndex));
		clzName.append(config.c("DEFAULT_CONTROLLERCLASS_SUFFIX").toString().trim());

		try {
			return ClassUtils.forName(clzName.toString(), ClassUtils.getDefaultClassLoader());

		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		}
		return null;
	}

}
