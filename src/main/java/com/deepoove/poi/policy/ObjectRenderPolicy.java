/*
 * Copyright 2014-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.deepoove.poi.policy;

import org.apache.poi.xwpf.usermodel.XWPFRun;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.ObjectRenderData;
import com.deepoove.poi.template.ElementTemplate;
import com.deepoove.poi.template.run.RunTemplate;
import com.deepoove.poi.util.StyleUtils;

/**
 * 对象渲染 - 类似freemarker
 * @author zhuby
 *
 */
public class ObjectRenderPolicy implements RenderPolicy {

	private static final String REGEX_LINE_CHARACTOR = "\\n";

	@Override
	public void render(ElementTemplate eleTemplate, Object renderData, XWPFTemplate template) {
		RunTemplate runTemplate = (RunTemplate) eleTemplate;
		XWPFRun run = runTemplate.getRun();
		if (null == renderData) {
			run.setText("", 0);
			return;
		}

		ObjectRenderData objRenderData = null;
		if (renderData instanceof ObjectRenderData) {
			objRenderData = (ObjectRenderData) renderData;
		} else {
			objRenderData = new ObjectRenderData(renderData);
		}
		
		String currTagName = eleTemplate.getTagName();
		String FieldData = objRenderData.getFieldVal(currTagName.substring(currTagName.indexOf('.')+1));
		StyleUtils.styleRun(run, objRenderData.getStyle());
		if (null == FieldData) FieldData = "";
		
		String[] split = FieldData.split(REGEX_LINE_CHARACTOR);
		if (null != split){
		    run.setText(split[0], 0); 
		    for (int i = 1; i < split.length; i++) {
                run.addBreak(); 
                run.setText(split[i]);
            }
		}
	}
}
