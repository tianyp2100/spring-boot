package me.loveshare.note1.data.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Tony on 2017/7/31.
 */
@Slf4j
public class SplicingUtils {

    /**
     * 文本空格分隔行数据，转换打印map
     */
    public static void readLine2Map(String filePathAndName, String mapName, boolean direction) {
        try {
            BufferedReader br = getBufferedReader(filePathAndName);
            String str = null;
            System.out.println("Map<String,String> " + mapName + " = new HashMap<String,String>();");
            while ((str = br.readLine()) != null) {
                str = str.trim();
                String[] strings = str.split(" ");
                if (direction) {
                    System.out.println(mapName + ".put(\"" + strings[1].trim() + "\",\"" + strings[0].trim() + "\");");
                } else {
                    System.out.println(mapName + ".put(\"" + strings[0].trim() + "\",\"" + strings[1].trim() + "\");");
                }

            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 行数据转换DB的enum
     */
    public static void readLine2DBenum(String filePathAndName) {
        try {
            BufferedReader br = getBufferedReader(filePathAndName);
            String str = null;
            StringBuilder su = new StringBuilder("enum(");
            while ((str = br.readLine()) != null) {
                str = str.trim();
                su.append("'").append(str).append("',");
            }
            String resStr = su.toString();
            resStr = resStr.substring(0, resStr.length() - 1) + ")";
            System.out.println(resStr);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 行数据转换字符数组
     */
    public static void readLine2StringArray(String filePathAndName) {
        try {
            BufferedReader br = getBufferedReader(filePathAndName);
            String str = null;
            String tempStr = "";
            while ((str = br.readLine()) != null) {
                str = str.trim();
                str = str.replaceAll("\\s*", "");
                tempStr += str;
            }
            char[] tempStrArray = tempStr.toCharArray();
            StringBuilder su = new StringBuilder("{");
            for (char c : tempStrArray) {
                String cStr = String.valueOf(c);
                cStr = cStr.trim();
                if (StringUtils.isNotBlank(cStr)) su.append("\"").append(cStr).append("\",");
            }
            String resStr = su.toString();
            resStr = resStr.substring(0, resStr.length() - 1) + "};";
            System.out.println(resStr);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 行数据转换字符数组，自定义分隔符
     */
    public static void readLine2StringArray(String filePathAndName, String splitSymbol) {
        try {
            BufferedReader br = getBufferedReader(filePathAndName);
            String str = null;
            List<String> tempList = new ArrayList<String>();
            while ((str = br.readLine()) != null) {
                str = str.trim();
                String[] tempArray = str.split(splitSymbol);
                tempList.addAll(Arrays.asList(tempArray));
            }
            StringBuilder su = new StringBuilder("{");
            for (String cStr : tempList) {
                cStr = cStr.trim();
                if (StringUtils.isNotBlank(cStr)) su.append("\"").append(cStr).append("\",");
            }
            String resStr = su.toString();
            resStr = resStr.substring(0, resStr.length() - 1) + "};";
            System.out.println(resStr);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 行数据转换字符数组
     */
    public static void readLine2StringArray1(String filePathAndName) {
        try {
            BufferedReader br = getBufferedReader(filePathAndName);
            String str = null;
            List<String> tempList = new ArrayList<String>();
            while ((str = br.readLine()) != null) {
                str = str.trim();
                tempList.add(str);
            }
            StringBuilder su = new StringBuilder("{");
            for (String cStr : tempList) {
                cStr = cStr.trim();
                if (StringUtils.isNotBlank(cStr)) su.append("\"").append(cStr).append("\",");
            }
            String resStr = su.toString();
            resStr = resStr.substring(0, resStr.length() - 1) + "};";
            System.out.println(resStr);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    public static BufferedReader getBufferedReader(String filePathAndName) throws FileNotFoundException {
        FileReader reader = new FileReader(filePathAndName);
        BufferedReader br = new BufferedReader(reader);
        return br;
    }
}
