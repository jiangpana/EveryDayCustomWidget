package com.jansir.widget;

import org.junit.Test;

import java.io.File;
import java.io.FileReader;
import java.util.HashMap;

/**
 * package: com.jansir.widget.
 * date: 2019/6/1.
 * 作者：张风捷特烈
 * 链接：https://juejin.im/post/5be51bc1f265da611179cf30
 * 来源：掘金
 * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
 * 自定义view 属性快捷生成 attname命名 z_ranview_xxx属性
 */
public class Attrs2Code {
    @Test
    public void main() {
        File file = new File("C:\\Users\\jansir\\Desktop\\attrs.xml");
        initAttr("z_", file);
    }

    public static void initAttr(String preFix, File file) {
        HashMap<String, String> format = format(preFix, file);
        String className = format.get("className");
        String result = format.get("result");
        StringBuilder sb = new StringBuilder();
        sb.append("TypedArray a = context.obtainStyledAttributes(attrs, R.styleable." + className + ");\r\n");
        format.forEach((s, s2) -> {
            String styleableName = className + "_" + preFix + s;
            if (s.contains("_")) {
                String[] partStrArray = s.split("_");
                s = "";
                for (String part : partStrArray) {
                    String partStr = upAChar(part);
                    s += partStr;
                }
            }
            if (s2.equals("dimension")) {
                // mPbBgHeight = (int) a.getDimension(R.styleable.TolyProgressBar_z_pb_bg_height, mPbBgHeight);
                sb.append("m" + s + " = (int) a.getDimension(R.styleable." + styleableName + ", m" + s + ");\r\n");
            }
            if (s2.equals("color")) {
                // mPbTxtColor = a.getColor(R.styleable.TolyProgressBar_z_pb_txt_color, mPbTxtColor);
                sb.append("m" + s + " =  a.getColor(R.styleable." + styleableName + ", m" + s + ");\r\n");
            }
            if (s2.equals("boolean")) {
                // mPbTxtColor = a.getColor(R.styleable.TolyProgressBar_z_pb_txt_color, mPbTxtColor);
                sb.append("m" + s + " =  a.getBoolean(R.styleable." + styleableName + ", m" + s + ");\r\n");
            }
            if (s2.equals("string")) {
                // mPbTxtColor = a.getColor(R.styleable.TolyProgressBar_z_pb_txt_color, mPbTxtColor);
                sb.append("m" + s + " =  a.getString(R.styleable." + styleableName + ");\r\n");
            }
        });
        sb.append("a.recycle();\r\n");
        System.out.println(result);
        System.out.println(sb.toString());
    }

    /**
     * 读取文件+解析
     *
     * @param preFix 前缀
     * @param file   文件路径
     */
    public static HashMap<String, String> format(String preFix, File file) {
        HashMap<String, String> container = new HashMap<>();
        if (!file.exists() && file.isDirectory()) {
            return null;
        }
        FileReader fr = null;
        try {
            fr = new FileReader(file);
            //字符数组循环读取
            char[] buf = new char[1024];
            int len = 0;
            StringBuilder sb = new StringBuilder();
            while ((len = fr.read(buf)) != -1) {
                sb.append(new String(buf, 0, len));
            }
            String className = sb.toString().split("<declare-styleable name=\"")[1];
            className = className.substring(0, className.indexOf("\">"));
            container.put("className", className);
            String[] split = sb.toString().split("<");
            String part1 = "private";
            String type = "";//类型
            String name = "";
            String result = "";
            String def = "";//默认值

            StringBuilder sb2 = new StringBuilder();
            for (String s : split) {
                if (s.contains(preFix)) {
                    result = s.split(preFix)[1];
                    name = result.substring(0, result.indexOf("\""));
                    type = result.split("format=\"")[1];
                    type = type.substring(0, type.indexOf("\""));
                    container.put(name, type);
                    if (type.contains("color") || type.contains("dimension") || type.contains("integer")) {
                        type = "int";
                        def = "0";
                    }
                    if (result.contains("fraction")) {
                        type = "float";
                        def = "0.f";
                    }
                    if (result.contains("string")) {
                        type = "String";
                        def = "\"toly\"";
                    }
                    if (result.contains("boolean")) {
                        type = "boolean";
                        def = "false";

                    }
                    if (name.contains("_")) {
                        String[] partStrArray = name.split("_");
                        name = "";
                        for (String part : partStrArray) {
                            String partStr = upAChar(part);
                            name += partStr;
                        }
                        sb2.append(part1 + " " + type + " m" + name + "= " + def + ";\r\n");
                    }
                    container.put("result", sb2.toString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fr != null) {
                    fr.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return container;
    }

    /**
     * 将字符串仅首字母大写
     *
     * @param str 待处理字符串
     * @return 将字符串仅首字母大写
     */
    public static String upAChar(String str) {
        String a = str.substring(0, 1);
        String tail = str.substring(1);
        return a.toUpperCase() + tail;
    }

}
