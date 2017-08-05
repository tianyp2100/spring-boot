package me.loveshare.note5.data.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
    public static <T> boolean isEmptyList(List<T> list) {
        return list == null || list.isEmpty();
    }

    /**
     * 判断List集合不为空
     *
     * @param list list集合
     * @return true 集合不为空
     */
    public static <T> boolean isNotEmptyList(List<T> list) {
        return !isEmptyList(list);
    }

    /**
     * 通过索引范围获取list的值
     */
    public static final <T> List<T> subList(List<T> list, int fromId, int endId) {
        List<T> listRes = new ArrayList<T>();
        if (list.isEmpty()) return listRes;
        for (int i = fromId; i <= endId; i++) {
            listRes.add(list.get(i));
        }
        return listRes;
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
        if (a.size() != b.size()) return false;
        Collections.sort(a);
        Collections.sort(b);
        for (int i = 0; i < a.size(); i++) {
            if (!a.get(i).equals(b.get(i)))
                return false;
        }
        return true;
    }
}
