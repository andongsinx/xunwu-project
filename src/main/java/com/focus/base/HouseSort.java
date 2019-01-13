package com.focus.base;

import com.google.common.collect.Sets;
import org.springframework.data.domain.Sort;

import java.util.Set;

/**
 * @Description： 排序生成器
 * @Author: shadow
 * @Date: create in 19:03 2019/1/13
 */
public class HouseSort {

    public static final String DEFAULT_SORT_KEY = "lastUpdateTime";

    public static final String DISTANCE_TO_SUBWAY_KEY = "distanceToSubway";

    public static final Set<String> SORT_KEYS = Sets.newHashSet(
            DEFAULT_SORT_KEY,
            DISTANCE_TO_SUBWAY_KEY,
            "price",
            "area",
            "createTime"
    );

    public static Sort generateSort(String key, String directionKey) {
        key = getSortKey(key);
        Sort.Direction direction = Sort.Direction.fromString(directionKey);
        if (direction == null) {
            direction = Sort.Direction.DESC;
        }
        return new Sort(direction, key);
    }

    public static String getSortKey(String key) {
        if (!SORT_KEYS.contains(key)) {
            key = DEFAULT_SORT_KEY;
        }

        return key;

    }


}
