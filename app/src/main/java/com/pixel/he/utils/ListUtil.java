package com.pixel.he.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by pixel on 2017/3/10.
 * <p>
 * 实现列表分组
 */

public abstract class ListUtil {

    // 返回分组区分依据
    public interface OnGroupKeyInterface {
        Object getGroupKey();
    }

    /**
     * 列表分组
     *
     * @param list 要分组的列表
     * @param <V>  列表元素类型
     * @return 分组好的Map
     */
    public static <V extends Object> Map<Object, List<V>> doGroup(List<V> list) {
        return doGroup(list, false);
    }

    /**
     * 列表分组
     *
     * @param list 要分组的列表
     * @param sort 排序
     * @param <V>  列表元素类型
     * @return 分组好的Map
     */
    public static <V extends Object> Map<Object, List<V>> doGroup(List<V> list, boolean sort) {
        if (sort) {
            doSort(list);
        }
        if (list == null) {
            throw new NullPointerException("列表不能为空");
        }
        if (list.size() <= 0) {
            return new LinkedHashMap<Object, List<V>>();
        }
        if (!(list.get(0) instanceof OnGroupKeyInterface)) {
            throw new ClassCastException("类型异常.实体 应该实现 OnGroupKeyInterface 接口!");
        }
        Map<Object, List<V>> map = new LinkedHashMap<>();
        List<V> tempList = null;
        for (V v : list) {
            Object obj = ((OnGroupKeyInterface) v).getGroupKey();  // 有可能会出现类型不匹配
            tempList = map.get(obj);
            if (tempList == null) {
                tempList = new ArrayList<>();
                map.put(obj, tempList);
            }
            tempList.add(v);
        }
        return map;
    }

    // 返回排序依据依据(小的会排在前,大的会排在后.)
    public interface OnSortValueInterface {
        double getSortValue();
    }

    public static <V extends Object> void doSort(List<V> list) {
        if (list == null) {
            throw new NullPointerException("列表不能为空");
        }
        if (list.size() <= 0) {
            return;
        }
        if (!(list.get(0) instanceof OnSortValueInterface)) {
            throw new ClassCastException("类型异常.实体 应该实现 OnSortValueInterface 接口!");
        }
        Collections.sort(list, new Comparator<V>() {
            @Override
            public int compare(V lhs, V rhs) {
                if (((OnSortValueInterface) lhs).getSortValue() < ((OnSortValueInterface) rhs).getSortValue()) {
                    return -1;
                } else if (((OnSortValueInterface) lhs).getSortValue() > ((OnSortValueInterface) rhs).getSortValue()) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });
    }

}
