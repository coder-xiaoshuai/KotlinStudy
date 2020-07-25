package com.example.common.utils;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 集合相关的工具类
 *
 * @author zhangshuai
 */
public class CollectionUtils {

    private CollectionUtils() {}

    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isNotEmpty(List<?> list) {
        return list != null && list.size() > 0 && !TextUtils.isEmpty(list.get(0).toString());
    }

    public static boolean isNotEmpty(Map<?, ?> map) {
        return map != null && map.size() > 0;
    }

    public static <T> boolean isEmpty(T[] array) {
        return array == null || array.length == 0;
    }

    /**
     * 合并两个 list
     */
    public static <T> List<T> merge(List<T> list1, List<T> list2) {
        if (isEmpty(list1) && isEmpty(list2)) {
            return null;
        }
        if (isEmpty(list1)) {
            return list2;
        }
        if (isEmpty(list2)) {
            return list1;
        }
        List<T> ret = new ArrayList<>();
        ret.addAll(list1);
        ret.addAll(list2);
        return ret;
    }

    /**
     * Sub the list without exception
     */
    public static <T> List<T> safeSubList(List<T> list, int start, int end) {
        if (start >= list.size()) {
            return Collections.emptyList();
        }
        return list.subList(start, Math.min(end, list.size()));
    }

    /**
     * If list is null, change to empty list
     */
    public static <T> List<T> notNull(List<T> list) {
        if (list == null) {
            return Collections.emptyList();
        }
        return list;
    }

    /**
     * Get last element of the list
     */
    public static <T> T getLast(@NonNull List<T> list) {
        return list.get(list.size() - 1);
    }

    public static <T> T getFirst(@NonNull List<T> list) {
        return getSafeFirst(list);
    }

    public static <T> T getSafeFirst(@NonNull List<T> list) {
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list.get(0);
    }


    public static <T> T removeFirst(@NonNull List<T> list) {
        return list.remove(0);
    }
    public static <T> T removeLast(@NonNull List<T> list) {
        return list.remove(list.size() - 1);
    }

    /**
     * Pick n elements from list randomly
     */
    public static <T> List<T> pickNRandom(List<T> list, int n) {
        List<T> copy = new ArrayList<>(list);
        Collections.shuffle(copy);
        return safeSubList(copy, 0, n);
    }

    /**
     * Convert a list of type T to a list of type Object
     */
    public static <T> List<Object> convertToObjectList(List<T> list) {
        List<Object> result = new ArrayList<>();
        result.addAll(list);
        return result;
    }

    /**
     * 扩展 list 的大小
     */
    public static <T> void ensureSize(List<T> list, int size) {
        while (list.size() < size) {
            list.add(null);
        }
    }

    /**
     * 对 list 做平滑
     * 每个数字等于其左右和自己共 5 个数，加权平均
     */
    public static List<Integer> smoothList(List<Integer> list) {
        List<Integer> smoothedList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            int average;
            if (i == 0) {
                average = (list.get(i) * 2 + list.get(i + 1)) / 3;
            } else if (i == 1 || i == list.size() - 2) {
                average = (list.get(i) * 2 + list.get(i - 1) + list.get(i + 1)) / 4;
            } else if (i == list.size() - 1) {
                average = (list.get(i) * 2 + list.get(i - 1)) / 3;
            } else {
                average =
                    (list.get(i) * 3 + list.get(i - 1) * 2 + list.get(i + 1) * 2 + list.get(i - 2) + list.get(i + 2))
                        / 9;
            }
            smoothedList.add(average);
        }
        return smoothedList;
    }

    public static boolean isOutOfBounders(Collection<?> collection, int index) {
        return collection == null || index < 0 || index >= collection.size();
    }

    /**
     * 在 index 不在 list 范围内时，直接将 item 添加至尾部
     */
    public static <T> void insert(List<T> list, int index, T item) {
        if (isOutOfBounders(list, index)) {
            list.add(item);
        } else {
            list.add(index, item);
        }
    }

    /**
     * 将sourceList按blockSize大小,进行倍数切分
     *
     * - 如:list的大小为23,blockSize为10。那么切分的段数为:23 / 10 + (23 % 10)==0 ? 0 : 1 = 3
     * 切分后的list为:[0, 10], [0, 20], [0,22]
     */
    public static <T> List<List<T>> multipleSubList(List<T> sourceList, int blockSize) {
        if (isEmpty(sourceList) || blockSize <= 0) {
            return Collections.emptyList();
        }
        List<List<T>> list = new ArrayList<>();
        int listSize = sourceList.size();
        if (listSize <= blockSize) {
            for (int i = 1; i < sourceList.size(); i++) {
                list.add(sourceList.subList(0, i));
            }
            return list;
        }
        int batchSize = listSize / blockSize;
        int remain = listSize % blockSize;
        for (int i = 0; i < blockSize; i++) {
            int toIndex = i * batchSize + batchSize;
            list.add(sourceList.subList(0, toIndex));
        }
        if (remain > 0) {
            list.add(sourceList.subList(0, listSize));
        }
        return list;
    }

    /**
     * 将sourceList按blockSize大小等分
     */
    public static <T> List<List<T>> subList(List<T> sourceList, int blockSize) {
        if (isEmpty(sourceList) || blockSize <= 0) {
            return Collections.emptyList();
        }
        List<List<T>> list = new ArrayList<>();
        int listSize = sourceList.size();
        if (listSize <= blockSize) {
            list.add(sourceList);
            return list;
        }
        int batchSize = listSize / blockSize;
        int remain = listSize % blockSize;
        for (int i = 0; i < batchSize; i++) {
            int fromIndex = i * blockSize;
            int toIndex = fromIndex + blockSize;
            list.add(sourceList.subList(fromIndex, toIndex));
        }
        if (remain > 0) {
            list.add(sourceList.subList(listSize - remain, listSize));
        }
        return list;
    }

    @NonNull
    public static List<Long> averageAssignMillis(int blockSize, long source, long startRangeTime) {
        List<Long> datalist = new ArrayList<>();
        long batchSize = source / blockSize;
        for (int i = 0; i < blockSize; i++) {
            long result = i * batchSize;
            datalist.add(startRangeTime + result);
        }
        datalist.add(startRangeTime + source - 1);
        return datalist;
    }

    @NonNull
    public static List<Long> averageAssign(int blockSize, long source, long startRangeTime) {
        List<Long> datalist = new ArrayList<>();
        long batchSize = source / blockSize;
        for (int i = 0; i < blockSize; i++) {
            long result = i * batchSize;
            datalist.add(startRangeTime + result);
        }
        datalist.add(startRangeTime + source - 100000);
        return datalist;
    }

    public static <T> List<T> singletonList(T o) {
        ArrayList<T> objects = new ArrayList<>();
        objects.add(o);
        return objects;
    }
}
