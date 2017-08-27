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
package com.yoio.app.entity;

public class User {
	private int id;
	private String name;
	private double height;
	private float salary;
	private boolean isMarry;
	
	public User() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public float getSalary() {
		return salary;
	}

	public void setSalary(float salary) {
		this.salary = salary;
	}

	public boolean isMarry() {
		return isMarry;
	}

	public void setMarry(boolean isMarry) {
		this.isMarry = isMarry;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", height=" + height + ", salary=" + salary + ", isMarry="
				+ isMarry + "]";
	}
	
}
