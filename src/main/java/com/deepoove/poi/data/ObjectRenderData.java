package com.deepoove.poi.data;

import java.lang.reflect.Field;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deepoove.poi.config.Name;
import com.deepoove.poi.data.style.Style;

public class ObjectRenderData implements RenderData {
	
	private static final Logger logger = LoggerFactory.getLogger(ObjectRenderData.class);
	
	private Style style;
	//对象数据
	private Object obj;

	public ObjectRenderData() {
	}

	public ObjectRenderData(Object obj) {
		super();
		this.obj = obj;
	}

	public ObjectRenderData(Object obj,Style style) {
		super();
		this.obj = obj;
		this.style = style;
	}

	public Style getStyle() {
		return style;
	}

	public void setStyle(Style style) {
		this.style = style;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	/**
	 * 当前对象 根据字段名获取字段值
	 * @param fieldName
	 * @return
	 */
	public String getFieldVal(String fieldName) {

		String fieldVal = null;

		if (this.obj != null) {
			Class<?> c = obj.getClass();
			try {

				Field f = c.getDeclaredField(fieldName);
				f.setAccessible(true);
				fieldVal = f.get(obj).toString();

			} catch (NoSuchFieldException e) {

				boolean fieldExists = false;
				for (Field f : c.getDeclaredFields()) {

					Name anno = f.getAnnotation(Name.class);

					if (anno != null && anno.value().equals(fieldName)) {
						fieldExists = true;
						f.setAccessible(true);
						try {
							fieldVal = f.get(obj).toString();
							break;
						} catch (Exception e1) {
							logger.error("get field val error: {}.{}",c.getName(),fieldName);
						}
					}
				}
				if (!fieldExists) {
					logger.error("no such field: {}.{}",c.getName(),fieldName);
				}
			} catch (Exception e) {
				logger.error("getFieldVal error: {}.{}",c.getName(),fieldName);
			}
		}

		return fieldVal;

	}
}
