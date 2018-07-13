package com.deepoove.poi.tl.renderObjTest;

import java.io.FileOutputStream;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.render.RenderAPI;


/**
 * 		***************************************************
 * 		*****************  for test ***********************
 * 		***************************************************

 * poi-tl 渲染对象
 * @author zhuby
 * 
 * 1、
 * @see com.deepoove.poi.resolver.TemplateResolver
 * TAG_PATTERN 字段 原匹配方式：    "{0}{1}\\w+{2}"  ，修改为 ：   "{0}{1}\\w+\\.?\\w+{2}" ，
 * 即可匹配模板  aa.bbb （类似el表达式对象取值），同时支持原生匹配模式。
 * 
 * 2、
 * dataMap转化
 * dataMap<"dataMapKey",valObj> 转化为 dataMap<"dataMapKey.valObjFieldName",valObjFieldValStr> 即可 （反射）
 *
 */
public class CustomRenderAPI{
	
	/**
	 * 
	 * @param template
	 * @param dataMap <K,V>  	V-可为 自定义对象
	 * @throws Exception
	 */
	public static void renderObj(XWPFTemplate template, Map<String, Object> dataMap) throws Exception {
		
		RenderAPI.render(template, transformMap(dataMap));
		
	}
	
	/**
	 * 数据源适配（poi-tl无法直接渲染对象数据）
	 * @param sourceMap
	 * @return
	 * @throws Exception
	 */
	private static Map<String, Object> transformMap(Map<String, Object> sourceMap) throws Exception {

		Map<String, Object> destMap = new HashMap<String, Object>();
		
		String key = null;
		Object val = null;
		String getter = null;
		String acutalKey = null;
		String acutalVal = null;
		
		for (Map.Entry<String, Object> entry : sourceMap.entrySet()) {
			
			key = entry.getKey();
			val = entry.getValue();
				
			if (val != null && val instanceof RenderObj) {
				Class<?> c = val.getClass();
				for (Method m : c.getDeclaredMethods()) {
					getter = m.getName();
					if (getter.startsWith("get")) {
						m.setAccessible(true);
						acutalKey = key + "." + getter.substring(3, 4).toLowerCase() + getter.substring(4);
						acutalVal = m.invoke(val)==null?"":m.invoke(val).toString();
						destMap.put(acutalKey, acutalVal);
					}
				}
			}else{//支持直接渲染
				destMap.put(key, val);
			}
		}
		return destMap;
	}
	
	
	public static void main(String[] args) {
		Map<String, Object> datas = new HashMap<String, Object>();
		
		datas.put("obj", new DataModel("data model"));
		datas.put("name", "张三");
		
		XWPFTemplate doc = XWPFTemplate.compile(CustomRenderAPI.class.getResourceAsStream("objfield.docx"));
		
		try {
			
			renderObj(doc, datas);
			FileOutputStream out = new FileOutputStream("d:/obj"+new Date().getTime()+".docx");
			doc.write(out);
			out.flush();
			out.close();
			doc.close();
			System.out.println("success");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

}
