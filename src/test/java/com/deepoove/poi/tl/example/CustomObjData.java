package com.deepoove.poi.tl.example;

import com.deepoove.poi.config.Name;

public class CustomObjData {
    
	private String name;
	private String sex;
	private Integer age;
	private String intro;
	
	@Name("testAnno")
	private String test_Anno;
	
	public CustomObjData() {
		super();
	}
	
	public CustomObjData(String name, String sex, Integer age, String intro) {
		super();
		this.name = name;
		this.sex = sex;
		this.age = age;
		this.intro = intro;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public String getIntro() {
		return intro;
	}
	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getTest_Anno() {
		return test_Anno;
	}

	public void setTest_Anno(String test_Anno) {
		this.test_Anno = test_Anno;
	}
	
}
