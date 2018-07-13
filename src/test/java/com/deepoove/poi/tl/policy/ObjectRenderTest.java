package com.deepoove.poi.tl.policy;

import java.io.FileOutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.TextRenderData;
import com.deepoove.poi.tl.example.CustomObjData;

public class ObjectRenderTest {

    @SuppressWarnings("serial")
    @Test
    public void testTextRender() throws Exception {

        Map<String, Object> datas = new HashMap<String, Object>() {
            {
            	CustomObjData obj = new CustomObjData("王勃", "男", 24, "海内存知己,\n天涯若比邻。");
            	obj.setTest_Anno("test anno");
                put("obj", obj);
                put("text", new TextRenderData("FF0000", "red"));
            }
        };

       
        XWPFTemplate template = XWPFTemplate.compile("src/test/resources/objRender.docx")
                .render(datas);

//        FileOutputStream out = new FileOutputStream("d:/obj"+new Date().getTime()+".docx");
        FileOutputStream out = new FileOutputStream("d:/obj"+new Date().getTime()+".docx");
        template.write(out);
        out.flush();
        out.close();
        template.close();

    }

}
