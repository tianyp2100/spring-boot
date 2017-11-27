package me.loveshare.data.util;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Tony on 2017/3/28.
 */
public class CollectionUtils {

    /**
     * 判断List集合为空
     *
     * @param list list集合
     * @return true 集合为空
     */
    public static <T> boolean isEmptyList(Collection<T> list) {
        return list == null || list.isEmpty();
    }

    /**
     * 判断List集合不为空
     *
     * @param list list集合
     * @return true 集合不为空
     */
    public static <T> boolean isNotEmptyList(Collection<T> list) {
        return !isEmptyList(list);
    }

    /**
     * 判断map集合为空
     *
     * @param map map集合
     * @return true 集合为空
     */
    public static boolean isEmptyMap(Map map) {
        return map == null || map.isEmpty();
    }

    /**
     * 判断map集合不为空
     *
     * @param map map集合
     * @return true 集合为空
     */
    public static boolean isNotEmptyMap(Map map) {
        return !isEmptyMap(map);
    }


    /**
     * 获取集合的长度，空则返回0
     *
     * @param list list集合
     * @return true 集合不为空
     */
    public static <T> int size(Collection<T> list) {
        if (CollectionUtils.isEmptyList(list)) {
            return 0;
        }
        return list.size();
    }

    /**
     * 去重添加到列表集合
     */
    public static <T> void uniqAddList(List<T> list, T t) {
        if (list == null) {
            return;
        }
        if (!list.contains(t)) {
            list.add(t);
        }
    }

    /**
     * 页面传入多个或一个附件id.<br>
     *
     * @param ids      页面传入用逗号分隔的附件id.<br>
     *                 警告：如果返回true，说明，附件的id列表，不合法。
     * @param isMust   是否必须
     * @param size     最大大小
     * @param itemTips 当前操作对象名  比如：“用户姓名” + 不能为空,即提示信息拼接
     * @return List:<br>
     * 第一个元素("flag")：类型：boolean，true：列表错误；false：列表正确<br>
     * 第二个元素("message")：说明错误信息
     * 第三个元素("result")：类型：List<Integer>，flag=true，result则不存在，flag=false，第三个元素result为附件id，去除空id的Integer的附件id列表<br>
     * eg:原数据：,123,456,1,9999999,,88<br>
     * 处理后数据：[123, 456, 1, 9999999, 88]
     * 调用举例eg：
     * //safeRightDocsId：案件id获取，多个用逗号隔开，必须上传
     * List<Object> res= BusinessUnit.checkAndFilterIds(safeRightIds);
     * //附件id的处理结果Integer列表：
     * <p><blockquote><pre>
     * 	List<Integer> docsi = null;
     * 	if((Boolean)res.get(0)){
     * 		json.setCode(JsonResult.CODE_WARMTIPS);
     * 		json.setMessage((String)res.get(1));
     * 		return json;
     * 	}else{
     * 		ids = (List<Integer>) res.get(2);
     * 	}
     * </pre></blockquote><p>
     * 注：使用可参考：com.hxwisec.common.unit.BusinessUnitTest#testCheckAndFilterIds()
     */
    public static final List<Object> checkAndFilterIds(String ids, boolean isMust, int size, String itemTips) {
        List<Object> resultList = new ArrayList<Object>();
        //必须--存在
        if (StringUtils.isBlank(ids)) {
            if (isMust) {
                resultList.add(true);
                resultList.add(itemTips + "不能为空");
                return resultList;
            } else {
                resultList.add(false);
                resultList.add("empty");
                resultList.add(null);
                return resultList;
            }
        }
        //中间变量
        boolean hasIdFlag = false;
        List<String> idss = null;
        List<Integer> idsi = new ArrayList<Integer>();
        //多个id
        if (ids.contains(",")) {
            //附件id数组
            idss = new ArrayList<String>(Arrays.asList(ids.split(",")));
            List<Integer> removeId = new ArrayList<Integer>();
            for (int i = 0, attachLen = idss.size(); i < attachLen; i++) {
                String id = idss.get(i);
                //元素为空，移除
                if (id == null || "".equals(id)) {
                    removeId.add(i);
                    continue;
                }
                //前后空格去除
                id = id.trim();
                //保证id为Integer，不能有eg：a2n3
                if (!StringUtils.isNumeric(id)) {
                    hasIdFlag = true;
                    break;
                }
                //正确的都添加列表中
                idsi.add(Integer.parseInt(id));
            }
            //移除空
            if (removeId != null && !removeId.isEmpty()) {
                for (Integer i : removeId) {
                    idsi.remove(i);
                }
            }
            if (idsi != null && idsi.size() > size) {
                resultList.add(true);
                resultList.add(itemTips + "长度最大为" + size);
                return resultList;
            }
            //1个id
        } else {
            //保证id为Integer
            if (StringUtils.isNumeric(ids)) {
                idsi.add(Integer.parseInt(ids));
            }
        }
        //附件id验证不通过，或者为空
        if (hasIdFlag || idsi == null || idsi.isEmpty()) {
            resultList.add(true);
            resultList.add(itemTips + "格式错误或为空");
            return resultList;
        }
        //添加ok的附件id并返回
        resultList.add(false);
        resultList.add("ok");
        resultList.add(idsi);
        return resultList;
    }

    /**
     * 过滤两个列表list中不同的值（数据库中附件列表，删除和新上传的附件过滤）<br>
     * listdb ： 数据库中附件id的list列表 <br>
     * listhtml：页面附件id参数的list列表 ，需要使用 BusinessUnit#checkAndFilterAttachId() <br>
     * 过滤：<br>
     * key=upload：为新上传附件列表 <br>
     * key=delete：为删除的附件列表 <br>
     */
    public static final Map<String, Object> compareAttachList(List<Integer> listdb, List<Integer> listhtml) {
        List<Integer> listupload = new ArrayList<Integer>();
        List<Integer> listdelete = new ArrayList<Integer>();
        List<Integer> listtemp = new ArrayList<Integer>();
        Map<String, Object> map = new HashMap<String, Object>();
        //页面全部删除
        if (listhtml == null || listhtml.isEmpty()) {
            map.put("delete", listdb);
            return map;
        }
        //数据库没有数据，第一次上传
        if (listdb == null || listdb.isEmpty()) {
            map.put("upload", listhtml);
            return map;
        }
        //新上传附件过滤
        for (Integer html : listhtml) {
            //DB存在的id，页面的附件未删除
            if (listdb.contains(html)) {
                listtemp.add(html);
            } else {
                //不在DB，页面新上传的附件id
                listupload.add(html);
            }
        }
        //需删除的附件
        for (Integer temp : listtemp) {
            listdb.remove(temp);
        }
        listdelete = listdb;
        map.put("delete", listdelete);
        map.put("upload", listupload);
        return map;
    }

    /**
     * 页面传入多个或一个附件id.<br>
     *
     * @param attachIdStrArray 页面传入用逗号分隔的附件id.<br>
     *                         警告：如果返回true，说明，附件的id列表，不合法。
     * @return List:<br>
     * 第一个元素("flag")：类型：boolean，true：附件列表错误；false：附件列表正确<br>
     * 第二个元素("result")：类型：List<Integer>，flag=true，result则不存在，flag=false，第二个元素result为附件id，去除空id的Integer的附件id列表<br>
     * eg:原数据：,123,456,1,9999999,,88<br>
     * 处理后数据：[123, 456, 1, 9999999, 88]
     * 调用举例eg：
     * //safeRightDocsId：案件id获取，多个用逗号隔开，必须上传
     * List<Object> res= BusinessUnit.checkAndFilterAttachId(safeRightDocsId);
     * //附件id的处理结果Integer列表：
     * List<Integer> docsi = null;
     * if((Boolean)res.get(0)){
     * json.setCode(JsonResult.CODE_ERROR);
     * json.setMessage("请求参数：上传附件不合法");
     * return json;
     * }else{
     * docsi = (List<Integer>) res.get(1);
     * }
     */
    public static final List<Object> checkAndFilterAttachId(String attachIdStrArray) {
        List<Object> resultList = new ArrayList<Object>();
        //必须上传
        if (StringUtils.isBlank(attachIdStrArray)) {
            resultList.add(true);
            return resultList;
        }
        //中间变量
        boolean hasAttachFlag = false;
        List<String> attachs = null;
        List<Integer> attachsi = new ArrayList<Integer>();
        //多个id
        if (attachIdStrArray.contains(",")) {
            //附件id数组
            attachs = new ArrayList<String>(Arrays.asList(attachIdStrArray.split(",")));
            List<Integer> removeId = new ArrayList<Integer>();
            for (int i = 0, attachLen = attachs.size(); i < attachLen; i++) {
                String attachId = attachs.get(i);
                //元素为空，移除
                if (attachId == null || "".equals(attachId)) {
                    removeId.add(i);
                    continue;
                }
                //保证id为Integer，不能有eg：a2n3
                if (!StringUtils.isNumeric(attachId)) {
                    hasAttachFlag = true;
                    break;
                }
                //前后空格去除
                attachId = attachId.trim();
                //正确的都添加列表中
                attachsi.add(Integer.parseInt(attachId));
            }
            //移除空
            if (removeId != null && !removeId.isEmpty()) {
                for (Integer i : removeId) {
                    attachsi.remove(i);
                }
            }
            //1个id
        } else {
            //保证id为Integer
            if (StringUtils.isNumeric(attachIdStrArray)) {
                attachsi.add(Integer.parseInt(attachIdStrArray));
            }
        }
        //附件id验证不通过，或者为空
        if (hasAttachFlag || attachsi == null || attachsi.isEmpty()) {
            resultList.add(true);
            return resultList;
        }
        //添加ok的附件id并返回
        resultList.add(false);
        resultList.add(attachsi);
        return resultList;
    }

    /**
     * 过滤两个列表list中不同的值（数据库中附件列表，页面操作后的id列表）<br>
     * listdbArgs ： 数据库中id的list列表 .eg:[1, 2, 3, 4, 5]<br>
     * listhtmlArgs：页面操作后id参数的list列表 .eg:[2, 3, 6]   过滤辅助方法：#{@link #checkAndFilterIds}}<br>
     * 过滤：<br>
     * key=upload：为新加入的id列表.eg:[6] <br>
     * key=delete：已删除的id列表 .eg:[1, 4, 5]<br>
     */
    public static final Map<String, Object> compareIds(List<Integer> listdbArgs, List<Integer> listhtmlArgs) {
        List<Integer> listdb = null;
        List<Integer> listhtml = null;
        if (listdbArgs != null) {
            listdb = new ArrayList<Integer>(listdbArgs);
        } else {
            listdb = new ArrayList<Integer>();
        }
        if (listhtmlArgs != null) {
            listhtml = new ArrayList<Integer>(listhtmlArgs);
        } else {
            listhtml = new ArrayList<Integer>();
        }
        List<Integer> listupload = new ArrayList<Integer>();
        List<Integer> listdelete = new ArrayList<Integer>();
        List<Integer> listtemp = new ArrayList<Integer>();
        Map<String, Object> map = new HashMap<String, Object>();
        //页面全部删除
        if (listhtml == null || listhtml.isEmpty()) {
            map.put("delete", listdb);
            return map;
        }
        //数据库没有数据，第一次上传
        if (listdb == null || listdb.isEmpty()) {
            map.put("upload", listhtml);
            return map;
        }
        //新上传附件过滤
        for (Integer html : listhtml) {
            //DB存在的id，页面的附件未删除
            if (listdb.contains(html)) {
                listtemp.add(html);
            } else {
                //不在DB，页面新上传的附件id
                listupload.add(html);
            }
        }
        //需删除的附件
        for (Integer temp : listtemp) {
            listdb.remove(temp);
        }
        listdelete = listdb;
        map.put("delete", listdelete);
        map.put("upload", listupload);
        return map;
    }

    /**
     * 通过索引范围获取list的值
     */
    public static final <T> List<T> subList(List<T> list, int fromId, int endId) {
        List<T> listRes = new ArrayList<T>();
        if (list.isEmpty()) {
            return listRes;
        }
        for (int i = fromId; i <= endId; i++) {
            listRes.add(list.get(i));
        }
        return listRes;
    }

    /**
     * 字符串list转字符串去除中括号
     */
    public static final String list2Str(List<String> strList) {
        String s = "";
        if (strList != null && !strList.isEmpty()) {
            s = strList.toString();
            s = s.replace(" ", "");
            s = s.substring(1, s.length() - 1);
        }
        return s;
    }

    /**
     * integr list转字符串去除中括号
     */
    public static final String listI2Str(List<Integer> strList) {
        String s = "";
        if (strList != null && !strList.isEmpty()) {
            s = strList.toString();
            s = s.replace(" ", "");
            s = s.substring(1, s.length() - 1);
        }
        return s;
    }

    /**
     * integr list转字符串去除中括号
     */
    public static final String setI2Str(Set<Integer> strList) {
        String s = "";
        if (strList != null && !strList.isEmpty()) {
            s = strList.toString();
            s = s.replace(" ", "");
            s = s.substring(1, s.length() - 1);
        }
        return s;
    }

    /**
     * must numric str change to integer list
     */
    public static final List<Integer> str2list(String strlist) {
        if (StringUtils.isEmpty(strlist)) return null;
        List<Integer> intlists = new ArrayList<Integer>();
        String[] arry = strlist.split(",");
        for (String string : arry) {
            if (StringUtils.isNumeric(string)) {
                intlists.add(Integer.parseInt(string));
            }
        }
        return intlists;
    }

    /**
     * must numric str change to integer list
     */
    public static final Set<Integer> str2set(String strlist) {
        if (StringUtils.isEmpty(strlist)) return null;
        Set<Integer> intsets = new HashSet<Integer>();
        String[] arry = strlist.split(",");
        for (String string : arry) {
            if (StringUtils.isNumeric(string)) {
                intsets.add(Integer.parseInt(string));
            }
        }
        return intsets;
    }

    public static final List<String> str2lists(String strlist) {
        if (StringUtils.isEmpty(strlist)) {
            return null;
        }
        return Arrays.asList(strlist.split(","));
    }

    public static final Set<String> str2sets(String strlist) {
        if (StringUtils.isEmpty(strlist)) return null;
        return new HashSet<String>(Arrays.asList(strlist.split(",")));
    }

    public static final Set<String> list2set(List<String> strlist) {
        if (strlist == null) return null;
        return new HashSet<String>(strlist);
    }

    /**
     * 替换id  --删除id
     */
    public static final String replace(String ids, String id) {
        if (StringUtils.isEmpty(ids) || StringUtils.isEmpty(id)) return null;
        List<String> reslist = new ArrayList<String>();
        List<String> list = Arrays.asList(ids.split(","));
        reslist.addAll(list);
        while (reslist.contains(id)) {
            reslist.remove(id);
        }
        String s = reslist.toString();
        s = s.replace(" ", "");
        s = s.substring(1, s.length() - 1);
        return s;
    }

    /**
     * 替换id int list
     */
    public static final List<Integer> removelisti(List<Integer> ids, List<Integer> id) {
        if (ids != null && !ids.isEmpty() && id != null && !id.isEmpty()) {
            List<Integer> intlist = new ArrayList<Integer>();
            intlist.addAll(ids);
            for (Integer idi : id) {
                while (intlist.contains(idi)) {
                    intlist.remove(idi);
                }
            }
            return intlist;
        }
        return null;
    }

    /**
     * 替换id int set
     */
    public static final Set<Integer> removeseti(Set<Integer> ids, Set<Integer> id) {
        if (ids != null && !ids.isEmpty() && id != null && !id.isEmpty()) {
            Set<Integer> intset = new HashSet<Integer>();
            intset.addAll(ids);
            for (Integer idi : id) {
                intset.remove(idi);
            }
            return intset;
        }
        return null;
    }

    /**
     * list string 转 list int
     */
    public static final List<Integer> liststr2listint(List<String> strlist) {
        if (strlist != null && !strlist.isEmpty()) {
            List<Integer> intlist = new ArrayList<Integer>();
            for (String str : strlist) {
                if (StringUtils.isNumeric(str)) {
                    intlist.add(Integer.parseInt(str));
                }
            }
            return intlist;
        }
        return null;
    }

    /**
     * list int 转 list string
     */
    public static final List<String> listint2liststr(List<Integer> intlist) {
        if (intlist != null && !intlist.isEmpty()) {
            List<String> strlist = new ArrayList<String>();
            for (Integer intr : intlist) {
                strlist.add(String.valueOf(intr));
            }
            return strlist;
        }
        return null;
    }

    /**
     * 排序   -默认升序
     */
    public static final List<String> orderby(List<String> keys, String orderbyType) {
        List<String> reslist = null;
        if (keys != null && !keys.isEmpty()) {
            reslist = new ArrayList<String>();
            reslist.addAll(keys);
            //default sort : asc
            Collections.sort(reslist);
            if ("DESC".equalsIgnoreCase(orderbyType)) {
                Collections.reverse(reslist);
            }
        }
        return reslist;
    }

    public static final int pageindex = 0;
    public static final int pageindex_1 = 1;
    public static final int pagesize = 5;
    public static final int pagesize_10 = 10;

    /**
     * 分页和排序   -默认升序 默认：页码0，大小5
     */
    public static final <T> List<T> limitList(List<T> tList, Integer pageIndex, Integer pageSize) {
        List<T> reslist = null;
        if (isNotEmptyList(tList)) {
            reslist = new ArrayList<T>();
            reslist.addAll(tList);
            pageIndex = pageIndex == null ? pageindex : pageIndex;
            pageSize = pageSize == null ? pagesize : pageSize;
            int len = reslist.size();
            int begin = pageIndex * pageSize;
            int end = pageIndex * pageSize + pageSize;
            if (begin > len) {
                begin = len - pagesize;
            }
            if (end > len) {
                end = len;
            }
            reslist = reslist.subList(begin, end);
        }
        return reslist;
    }

    /**
     * list去重 --添加
     */
    public static final <T> void distinctList(List<T> tList, T t) {
        if (isNotEmptyList(tList) || tList.contains(t)) {
            return;
        }
        tList.add(t);
    }

    /**
     * 分页和排序   -默认升序 默认：页码0，大小5
     */
    public static final List<String> orderbyandlimit(List<String> keys, String orderbyType, Integer pageIndex, Integer pageSize) {
        List<String> reslist = null;
        if (keys != null && !keys.isEmpty()) {
            reslist = new ArrayList<String>();
            reslist.addAll(keys);
            //default sort : asc
            Collections.sort(reslist);
            if ("DESC".equalsIgnoreCase(orderbyType)) {
                Collections.reverse(reslist);
            }
            pageIndex = pageIndex == null ? pageindex : pageIndex;
            pageSize = pageSize == null ? pagesize : pageSize;
            int len = reslist.size();
            int begin = pageIndex * pageSize;
            int end = pageIndex * pageSize + pageSize;
            if (begin > len) {
                begin = len - pagesize;
            }
            if (end > len) {
                end = len;
            }
            reslist = reslist.subList(begin, end);
        }
        return reslist;
    }

    /**
     * 分页和排序   -默认升序 默认：页码0，大小5 只升序
     */
    public static final List<String> orderbyandlimitset(Set<String> keys, String orderbyType, Integer pageIndex, Integer pageSize) {
        List<String> reslist = null;
        if (keys != null && !keys.isEmpty()) {
            reslist = new ArrayList<String>(keys);
            //default sort : asc
            Collections.sort(reslist);
            if ("DESC".equalsIgnoreCase(orderbyType)) {
                Collections.reverse(reslist);
            }
            pageIndex = pageIndex == null ? pageindex : pageIndex;
            pageSize = pageSize == null ? pagesize : pageSize;
            int len = reslist.size();
            int begin = pageIndex * pageSize;
            int end = pageIndex * pageSize + pageSize;
            if (begin > len) {
                begin = len - pagesize;
            }
            if (end > len) {
                end = len;
            }
            reslist = reslist.subList(begin, end);
        }
        return reslist;
    }

    /**
     * 文件大小：8M
     */
    public static final long SIZE_8M = 8 * 1024 * 1024l;

    /**
     * Dubbo传输文件最大限制 -- 8388608 ->8 M  (<strong>直接调用dubbo协议服务必须验证</strong>)<br>
     * 问题：com.alibaba.dubbo.remoting.transport.AbstractCodec.checkPayload() ERROR
     * Data length too large: 11557050, max payload: 8388608
     * java.io.IOException: Data length too large: 11557050, max payload: 838860
     * Dubbo限制大数据传输的解决方案
     * 解决方案如下：
     * 1、在项目中集成MongoDB；
     * 2、在service层把大容量数据存放到MongoDB中；
     * 3、在web层从MongoDB中取出大容量数据。
     * 本解决方案：将service代码，直接拿到消费者层  ==移动文件上传功能
     *
     * @return true 则不能进行正常服务调用---服务调用异常
     */
    public static final boolean checkDubboMaxFileSize(long filesize) {
        if (filesize > SIZE_8M) {
            return true;
        }
        return false;
    }

    public static String digitalConversion(Integer number) {
        boolean isWan = false;
        String[] str = {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
        String ss[] = new String[]{"", "十", "百", "千", "万", "十", "百", "千", "亿"};
        String s = String.valueOf(number);
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
            String index = String.valueOf(s.charAt(i));
            sb = sb.append(str[Integer.parseInt(index)]);

            int wz = s.length() - 1 - i;
            if (wz > 4 && wz < 8 && !"0".equals(index)) {
                isWan = true;
            }

            if ("0".equals(index)) {
                if (wz == 4 && isWan) {
                    sb.append("万");
                } else {
                    sb.append("零");
                }
            } else {
                sb.append(ss[wz]);
            }
        }

        String res = sb.toString();

        while (res.indexOf("零零") != -1) {
            res = res.replace("零零", "零");
        }

        if (res.indexOf("零万") != -1) {
            res = res.replace("零万", "万");
        }

        if (res.indexOf("一十") == 0) {
            res = res.substring(1);
        }

        if (res.indexOf("零") == (res.length() - 1)) {
            res = res.substring(0, res.length() - 1);
        }

        return res;
    }

    /**
     * 字符串转list<string>
     */
    public static final List<String> string2lists(String str) {
        if (StringUtils.isEmpty(str)) return null;
        List<String> slists = new ArrayList<String>();
        String[] strary = str.split("");
        for (String string : strary) {
            if (string != null && string.length() > 0 && !string.equals(" ")) {
                slists.add(string);
            }
        }
        return slists;
    }

    /**
     * 合并2个list集合
     *
     * @param args1
     * @param args2
     * @return
     */
    public static List<Map<String, Object>> mergeList(List<Map<String, Object>> args1, List<Map<String, Object>> args2) {

        if (args1 == null) {
            return args2;
        }

        if (args2 == null) {
            return args1;
        }

        for (Map<String, Object> map : args2) {
            args1.add(map);
        }

        return args1;
    }

    public static Pattern p1 = Pattern.compile("\\s*|\t|\r|\n");

    /**
     * 过滤字符串，空格、制表符、回车符
     */
    public static final String filterSpecialSymbol(String str) {
        if (StringUtils.isEmpty(str)) {
            return null;
        }
        Matcher m = p1.matcher(str);
        return m.replaceAll("");
    }

    public static String suspendTimstep(Integer secondNumber) {
        StringBuilder sb = new StringBuilder();

        Integer hourTime = secondNumber / (60 * 60);
        if (hourTime < 10) {
            sb.append("0");
        }
        sb.append(hourTime);
        sb.append(":");

        Integer minLong = secondNumber % (60 * 60);
        Integer minTime = minLong / (60);
        if (minTime < 10) {
            sb.append("0");
        }
        sb.append(minTime);
        sb.append(":");

        Integer secondTime = minLong % (60);
        if (secondTime < 10) {
            sb.append("0");
        }
        sb.append(secondTime);

        return sb.toString();
    }

    /**
     * Map按键排序
     */
    public static Map<String, String> sortMapByKey(Map<String, String> oriMap) {
        if (oriMap == null || oriMap.isEmpty()) {
            return null;
        }
        Map<String, String> sortedMap = new TreeMap<String, String>(new Comparator<String>() {
            public int compare(String key1, String key2) {
                int intKey1 = 0, intKey2 = 0;
                try {
                    intKey1 = getInt(key1);
                    intKey2 = getInt(key2);
                } catch (Exception e) {
                    intKey1 = 0;
                    intKey2 = 0;
                }
                return intKey1 - intKey2;
            }
        });
        sortedMap.putAll(oriMap);
        return sortedMap;
    }

    private static final Pattern p = Pattern.compile("^\\d+");

    private static int getInt(String str) {
        int i = 0;
        try {
            Matcher m = p.matcher(str);
            if (m.find()) {
                i = Integer.valueOf(m.group());
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return i;
    }

    /**
     * Map按值排序
     */
    public static Map<String, String> sortMapByValue(Map<String, String> oriMap) {
        Map<String, String> sortedMap = new LinkedHashMap<String, String>();
        if (oriMap != null && !oriMap.isEmpty()) {
            List<Map.Entry<String, String>> entryList = new ArrayList<Map.Entry<String, String>>(oriMap.entrySet());
            Collections.sort(entryList, (Map.Entry<String, String> entry1,
                                         Map.Entry<String, String> entry2) -> {
                int value1 = 0, value2 = 0;
                try {
                    value1 = getInt(entry1.getValue());
                    value2 = getInt(entry2.getValue());
                } catch (NumberFormatException e) {
                    value1 = 0;
                    value2 = 0;
                }
                return value2 - value1;
            });
            Iterator<Map.Entry<String, String>> iter = entryList.iterator();
            Map.Entry<String, String> tmpEntry = null;
            while (iter.hasNext()) {
                tmpEntry = iter.next();
                sortedMap.put(tmpEntry.getKey(), tmpEntry.getValue());
            }
        }
        return sortedMap;
    }

    public static Map<String, Integer> sortMapByValueInt(Map<String, Integer> oriMap) {
        Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
        if (oriMap != null && !oriMap.isEmpty()) {
            List<Map.Entry<String, Integer>> entryList = new ArrayList<Map.Entry<String, Integer>>(oriMap.entrySet());
            Collections.sort(entryList, (Map.Entry<String, Integer> entry1,
                                         Map.Entry<String, Integer> entry2) -> {
                int value1 = 0, value2 = 0;
                try {
                    value1 = entry1.getValue();
                    value2 = entry2.getValue();
                } catch (NumberFormatException e) {
                    value1 = 0;
                    value2 = 0;
                }
                return value2 - value1;
            });
            Iterator<Map.Entry<String, Integer>> iter = entryList.iterator();
            Map.Entry<String, Integer> tmpEntry = null;
            while (iter.hasNext()) {
                tmpEntry = iter.next();
                sortedMap.put(tmpEntry.getKey(), tmpEntry.getValue());
            }
        }
        return sortedMap;
    }


    /**
     * 集合队列比较
     *
     * @param <T> 泛型标识
     * @param a   第一个队列
     * @param b   第二个队列
     * @return 相同返回true
     */
    public static <T extends Comparable<T>> boolean compare2List(List<T> a, List<T> b) {
        if (a.size() != b.size()) {
            return false;
        }
        Collections.sort(a);
        Collections.sort(b);
        for (int i = 0; i < a.size(); i++) {
            if (!a.get(i).equals(b.get(i))) {
                return false;
            }
        }
        return true;
    }

    /***
     * 获取int数组的最大、小值
     * @param values 数组值
     *               @param switchFlag 切换标识, true最大
     */
    public static final int getIntArrayCriticalValue(int[] values, boolean switchFlag) {
        if (values == null || values.length == 0) {
            return 0;
        }
        int tmp1 = Integer.MIN_VALUE;
        int tmp2 = tmp1;
        if (null != values) {
            tmp1 = values[0];
            for (int i = 0; i < values.length; i++) {
                if (tmp1 < values[i]) {
                    tmp1 = values[i];
                } else {
                    tmp2 = values[i];
                }
            }
        }
        return switchFlag ? tmp1 : tmp2;
    }
}
