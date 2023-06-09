package com.zhaolq.mars.common.core.util;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

/**
 * Map相关工具类
 *
 * @author zhaolq
 * @date 2023/6/8 23:32
 */
public class MapUtil {
    /**
     * 默认初始大小
     */
    public static final int DEFAULT_INITIAL_CAPACITY = 16;
    /**
     * 默认增长因子，当Map的size达到 容量*增长因子时，开始扩充Map
     */
    public static final float DEFAULT_LOAD_FACTOR = 0.75f;

    /**
     * Map是否为空
     *
     * @param map 集合
     * @return 是否为空
     */
    public static boolean isEmpty(Map<?, ?> map) {
        return null == map || map.isEmpty();
    }

    /**
     * Map是否为非空
     *
     * @param map 集合
     * @return 是否为非空
     */
    public static boolean isNotEmpty(Map<?, ?> map) {
        return null != map && false == map.isEmpty();
    }

    /**
     * 如果提供的集合为{@code null}，返回一个不可变的默认空集合，否则返回原集合<br>
     * 空集合使用{@link Collections#emptyMap()}
     *
     * @param <K> 键类型
     * @param <V> 值类型
     * @param set 提供的集合，可能为null
     * @return 原集合，若为null返回空集合
     */
    public static <K, V> Map<K, V> emptyIfNull(Map<K, V> set) {
        return (null == set) ? Collections.emptyMap() : set;
    }

    /**
     * 如果给定Map为空，返回默认Map
     *
     * @param <T> 集合类型
     * @param <K> 键类型
     * @param <V> 值类型
     * @param map Map
     * @param defaultMap 默认Map
     * @return 非空（empty）的原Map或默认Map
     */
    public static <T extends Map<K, V>, K, V> T defaultIfEmpty(T map, T defaultMap) {
        return isEmpty(map) ? defaultMap : map;
    }

    // ----------------------------------------------------------------------------------------------- new HashMap

    /**
     * 新建一个HashMap
     *
     * @param <K> Key类型
     * @param <V> Value类型
     * @return HashMap对象
     */
    public static <K, V> HashMap<K, V> newHashMap() {
        return new HashMap<>();
    }

    /**
     * 新建一个HashMap
     *
     * @param <K> Key类型
     * @param <V> Value类型
     * @param size 初始大小，由于默认负载因子0.75，传入的size会实际初始大小为size / 0.75 + 1
     * @param isLinked Map的Key是否有序，有序返回 {@link LinkedHashMap}，否则返回 {@link HashMap}
     * @return HashMap对象
     */
    public static <K, V> HashMap<K, V> newHashMap(int size, boolean isLinked) {
        int initialCapacity = (int) (size / DEFAULT_LOAD_FACTOR) + 1;
        return isLinked ? new LinkedHashMap<>(initialCapacity) : new HashMap<>(initialCapacity);
    }

    /**
     * 新建一个HashMap
     *
     * @param <K> Key类型
     * @param <V> Value类型
     * @param size 初始大小，由于默认负载因子0.75，传入的size会实际初始大小为size / 0.75 + 1
     * @return HashMap对象
     */
    public static <K, V> HashMap<K, V> newHashMap(int size) {
        return newHashMap(size, false);
    }

    /**
     * 新建一个HashMap
     *
     * @param <K> Key类型
     * @param <V> Value类型
     * @param isLinked Map的Key是否有序，有序返回 {@link LinkedHashMap}，否则返回 {@link HashMap}
     * @return HashMap对象
     */
    public static <K, V> HashMap<K, V> newHashMap(boolean isLinked) {
        return newHashMap(DEFAULT_INITIAL_CAPACITY, isLinked);
    }

    /**
     * 新建TreeMap，Key有序的Map
     *
     * @param <K> key的类型
     * @param <V> value的类型
     * @param comparator Key比较器
     * @return TreeMap
     */
    public static <K, V> TreeMap<K, V> newTreeMap(Comparator<? super K> comparator) {
        return new TreeMap<>(comparator);
    }

    /**
     * 新建TreeMap，Key有序的Map
     *
     * @param <K> key的类型
     * @param <V> value的类型
     * @param map Map
     * @param comparator Key比较器
     * @return TreeMap
     */
    public static <K, V> TreeMap<K, V> newTreeMap(Map<K, V> map, Comparator<? super K> comparator) {
        final TreeMap<K, V> treeMap = new TreeMap<>(comparator);
        if (false == isEmpty(map)) {
            treeMap.putAll(map);
        }
        return treeMap;
    }

    /**
     * 创建键不重复Map
     *
     * @param <K> key的类型
     * @param <V> value的类型
     * @param size 初始容量
     * @return {@link IdentityHashMap}
     */
    public static <K, V> Map<K, V> newIdentityMap(int size) {
        return new IdentityHashMap<>(size);
    }

    /**
     * 新建一个初始容量为{@link MapUtil#DEFAULT_INITIAL_CAPACITY} 的ConcurrentHashMap
     *
     * @param <K> key的类型
     * @param <V> value的类型
     * @return ConcurrentHashMap
     */
    public static <K, V> ConcurrentHashMap<K, V> newConcurrentHashMap() {
        return new ConcurrentHashMap<>(DEFAULT_INITIAL_CAPACITY);
    }

    /**
     * 新建一个ConcurrentHashMap
     *
     * @param size 初始容量，当传入的容量小于等于0时，容量为{@link MapUtil#DEFAULT_INITIAL_CAPACITY}
     * @param <K> key的类型
     * @param <V> value的类型
     * @return ConcurrentHashMap
     */
    public static <K, V> ConcurrentHashMap<K, V> newConcurrentHashMap(int size) {
        final int initCapacity = size <= 0 ? DEFAULT_INITIAL_CAPACITY : size;
        return new ConcurrentHashMap<>(initCapacity);
    }

    /**
     * 传入一个Map将其转化为ConcurrentHashMap类型
     *
     * @param map map
     * @param <K> key的类型
     * @param <V> value的类型
     * @return ConcurrentHashMap
     */
    public static <K, V> ConcurrentHashMap<K, V> newConcurrentHashMap(Map<K, V> map) {
        if (isEmpty(map)) {
            return new ConcurrentHashMap<>(DEFAULT_INITIAL_CAPACITY);
        }
        return new ConcurrentHashMap<>(map);
    }

    // ----------------------------------------------------------------------------------------------- value of

    /**
     * 将单一键值对转换为Map
     *
     * @param <K> 键类型
     * @param <V> 值类型
     * @param key 键
     * @param value 值
     * @return {@link HashMap}
     */
    public static <K, V> HashMap<K, V> of(K key, V value) {
        return of(key, value, false);
    }

    /**
     * 将单一键值对转换为Map
     *
     * @param <K> 键类型
     * @param <V> 值类型
     * @param key 键
     * @param value 值
     * @param isOrder 是否有序
     * @return {@link HashMap}
     */
    public static <K, V> HashMap<K, V> of(K key, V value, boolean isOrder) {
        final HashMap<K, V> map = newHashMap(isOrder);
        map.put(key, value);
        return map;
    }

    /**
     * 根据给定的Pair数组创建Map对象
     *
     * @param <K> 键类型
     * @param <V> 值类型
     * @param entries 键值对
     * @return Map
     * @see #entry(Object, Object)
     */
    @SafeVarargs
    public static <K, V> Map<K, V> ofEntries(Map.Entry<K, V>... entries) {
        final Map<K, V> map = new HashMap<>();
        for (Map.Entry<K, V> pair : entries) {
            map.put(pair.getKey(), pair.getValue());
        }
        return map;
    }

    /**
     * 将数组转换为Map（HashMap），支持数组元素类型为：
     *
     * <pre>
     * Map.Entry
     * 长度大于1的数组（取前两个值），如果不满足跳过此元素
     * Iterable 长度也必须大于1（取前两个值），如果不满足跳过此元素
     * Iterator 长度也必须大于1（取前两个值），如果不满足跳过此元素
     * </pre>
     *
     * <pre>
     * Map&lt;Object, Object&gt; colorMap = MapUtil.of(new String[][] {
     *    { "RED", "#FF0000" },
     *    { "GREEN", "#00FF00" },
     *    { "BLUE", "#0000FF" }
     * });
     * </pre>
     * <p>
     * 参考：commons-lang
     *
     * @param array 数组。元素类型为Map.Entry、数组、Iterable、Iterator
     * @return {@link HashMap}
     */
    @SuppressWarnings("rawtypes")
    public static HashMap<Object, Object> of(Object[] array) {
        if (array == null) {
            return null;
        }
        final HashMap<Object, Object> map = new HashMap<>((int) (array.length * 1.5));
        for (int i = 0; i < array.length; i++) {
            final Object object = array[i];
            if (object instanceof Map.Entry) {
                Map.Entry entry = (Map.Entry) object;
                map.put(entry.getKey(), entry.getValue());
            } else if (object instanceof Object[]) {
                final Object[] entry = (Object[]) object;
                if (entry.length > 1) {
                    map.put(entry[0], entry[1]);
                }
            } else if (object instanceof Iterable) {
                final Iterator iter = ((Iterable) object).iterator();
                if (iter.hasNext()) {
                    final Object key = iter.next();
                    if (iter.hasNext()) {
                        final Object value = iter.next();
                        map.put(key, value);
                    }
                }
            } else if (object instanceof Iterator) {
                final Iterator iter = ((Iterator) object);
                if (iter.hasNext()) {
                    final Object key = iter.next();
                    if (iter.hasNext()) {
                        final Object value = iter.next();
                        map.put(key, value);
                    }
                }
            } else {
                String msg = String.format("Array element %d, '%s', is not type of Map.Entry or Array or Iterable or Iterator", i, object);
                throw new IllegalArgumentException(msg);
            }
        }
        return map;
    }

    /**
     * 将键值对转换为二维数组，第一维是key，第二纬是value
     *
     * @param map map
     * @return 数组
     */
    public static Object[][] toObjectArray(Map<?, ?> map) {
        if (map == null) {
            return null;
        }
        final Object[][] result = new Object[map.size()][2];
        if (map.isEmpty()) {
            return result;
        }
        int index = 0;
        for (Entry<?, ?> entry : map.entrySet()) {
            result[index][0] = entry.getKey();
            result[index][1] = entry.getValue();
            index++;
        }
        return result;
    }

    // ----------------------------------------------------------------------------------------------- filter

    /**
     * 通过biFunction自定义一个规则，此规则将原Map中的元素转换成新的元素，生成新的Map返回<br>
     * 变更过程通过传入的 {@link BiFunction} 实现来返回一个值可以为不同类型的 {@link Map}
     *
     * @param map 原有的map
     * @param biFunction {@code lambda}，参数包含{@code key},{@code value}，返回值会作为新的{@code value}
     * @param <K> {@code key}的类型
     * @param <V> {@code value}的类型
     * @param <R> 新的，修改后的{@code value}的类型
     * @return 值可以为不同类型的 {@link Map}
     */
    public static <K, V, R> Map<K, R> map(Map<K, V> map, BiFunction<K, V, R> biFunction) {
        if (null == map || null == biFunction) {
            return MapUtil.newHashMap();
        }
        return map.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, m -> biFunction.apply(m.getKey(), m.getValue())));
    }

    /**
     * 排序已有Map，Key有序的Map，使用默认Key排序方式（字母顺序）
     *
     * @param <K> key的类型
     * @param <V> value的类型
     * @param map Map
     * @return TreeMap
     * @see #newTreeMap(Map, Comparator)
     */
    public static <K, V> TreeMap<K, V> sort(Map<K, V> map) {
        return sort(map, null);
    }

    /**
     * 排序已有Map，Key有序的Map
     *
     * @param <K> key的类型
     * @param <V> value的类型
     * @param map Map，为null返回null
     * @param comparator Key比较器
     * @return TreeMap，map为null返回null
     * @see #newTreeMap(Map, Comparator)
     */
    public static <K, V> TreeMap<K, V> sort(Map<K, V> map, Comparator<? super K> comparator) {
        if (null == map) {
            return null;
        }

        if (map instanceof TreeMap) {
            // 已经是可排序Map，此时只有比较器一致才返回原map
            TreeMap<K, V> result = (TreeMap<K, V>) map;
            if (null == comparator || comparator.equals(result.comparator())) {
                return result;
            }
        }

        return newTreeMap(map, comparator);
    }

    /**
     * 按照值排序，可选是否倒序
     *
     * @param map 需要对值排序的map
     * @param <K> 键类型
     * @param <V> 值类型
     * @param isDesc 是否倒序
     * @return 排序后新的Map
     */
    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map, boolean isDesc) {
        Map<K, V> result = new LinkedHashMap<>();
        Comparator<Entry<K, V>> entryComparator = Entry.comparingByValue();
        if (isDesc) {
            entryComparator = entryComparator.reversed();
        }
        map.entrySet().stream().sorted(entryComparator).forEachOrdered(e -> result.put(e.getKey(), e.getValue()));
        return result;
    }

    /**
     * 将对应Map转换为不可修改的Map
     *
     * @param map Map
     * @param <K> 键类型
     * @param <V> 值类型
     * @return 不修改Map
     */
    public static <K, V> Map<K, V> unmodifiable(Map<K, V> map) {
        return Collections.unmodifiableMap(map);
    }

    /**
     * 去掉Map中指定key的键值对，修改原Map
     *
     * @param <K> Key类型
     * @param <V> Value类型
     * @param map Map
     * @param keys 键列表
     * @return 修改后的key
     */
    @SuppressWarnings("unchecked")
    public static <K, V> Map<K, V> removeAny(Map<K, V> map, final K... keys) {
        for (K key : keys) {
            map.remove(key);
        }
        return map;
    }

    /**
     * 重命名键<br>
     * 实现方式为一处然后重新put，当旧的key不存在直接返回<br>
     * 当新的key存在，抛出{@link IllegalArgumentException} 异常
     *
     * @param <K> key的类型
     * @param <V> value的类型
     * @param map Map
     * @param oldKey 原键
     * @param newKey 新键
     * @return map
     * @throws IllegalArgumentException 新key存在抛出此异常
     */
    public static <K, V> Map<K, V> renameKey(Map<K, V> map, K oldKey, K newKey) {
        if (isNotEmpty(map) && map.containsKey(oldKey)) {
            if (map.containsKey(newKey)) {
                throw new IllegalArgumentException(String.format("The key '%s' exist !", newKey));
            }
            map.put(newKey, map.remove(oldKey));
        }
        return map;
    }

    /**
     * 去除Map中值为{@code null}的键值对<br>
     * 注意：此方法在传入的Map上直接修改。
     *
     * @param <K> key的类型
     * @param <V> value的类型
     * @param map Map
     * @return map
     */
    public static <K, V> Map<K, V> removeNullValue(Map<K, V> map) {
        if (isEmpty(map)) {
            return map;
        }

        final Iterator<Entry<K, V>> iter = map.entrySet().iterator();
        Entry<K, V> entry;
        while (iter.hasNext()) {
            entry = iter.next();
            if (null == entry.getValue()) {
                iter.remove();
            }
        }

        return map;
    }

    /**
     * 返回一个空Map
     *
     * @param <K> 键类型
     * @param <V> 值类型
     * @return 空Map
     * @see Collections#emptyMap()
     */
    public static <K, V> Map<K, V> empty() {
        return Collections.emptyMap();
    }

    /**
     * 根据传入的Map类型不同，返回对应类型的空Map，支持类型包括：
     *
     * <pre>
     *     1. NavigableMap
     *     2. SortedMap
     *     3. Map
     * </pre>
     *
     * @param <K> 键类型
     * @param <V> 值类型
     * @param <T> Map类型
     * @param mapClass Map类型，null返回默认的Map
     * @return 空Map
     */
    @SuppressWarnings("unchecked")
    public static <K, V, T extends Map<K, V>> T empty(Class<?> mapClass) {
        if (null == mapClass) {
            return (T) Collections.emptyMap();
        }
        if (NavigableMap.class == mapClass) {
            return (T) Collections.emptyNavigableMap();
        } else if (SortedMap.class == mapClass) {
            return (T) Collections.emptySortedMap();
        } else if (Map.class == mapClass) {
            return (T) Collections.emptyMap();
        }

        // 不支持空集合的集合类型
        throw new IllegalArgumentException(String.format("[%s] is not support to get empty!", mapClass));
    }

    /**
     * 清除一个或多个Map集合内的元素，每个Map调用clear()方法
     *
     * @param maps 一个或多个Map
     */
    public static void clear(Map<?, ?>... maps) {
        for (Map<?, ?> map : maps) {
            if (isNotEmpty(map)) {
                map.clear();
            }
        }
    }

    /**
     * 从Map中获取指定键列表对应的值列表<br>
     * 如果key在map中不存在或key对应值为null，则返回值列表对应位置的值也为null
     *
     * @param <K> 键类型
     * @param <V> 值类型
     * @param map {@link Map}
     * @param keys 键列表
     * @return 值列表
     */
    public static <K, V> ArrayList<V> valuesOfKeys(Map<K, V> map, Iterator<K> keys) {
        final ArrayList<V> values = new ArrayList<>();
        while (keys.hasNext()) {
            values.add(map.get(keys.next()));
        }
        return values;
    }

    /**
     * 将键和值转换为{@link AbstractMap.SimpleImmutableEntry}<br>
     * 返回的Entry不可变
     *
     * @param key 键
     * @param value 值
     * @param <K> 键类型
     * @param <V> 值类型
     * @return {@link AbstractMap.SimpleImmutableEntry}
     */
    public static <K, V> Map.Entry<K, V> entry(K key, V value) {
        return entry(key, value, true);
    }

    /**
     * 将键和值转换为{@link AbstractMap.SimpleEntry} 或者 {@link AbstractMap.SimpleImmutableEntry}
     *
     * @param key 键
     * @param value 值
     * @param <K> 键类型
     * @param <V> 值类型
     * @param isImmutable 是否不可变Entry
     * @return {@link AbstractMap.SimpleEntry} 或者 {@link AbstractMap.SimpleImmutableEntry}
     */
    public static <K, V> Map.Entry<K, V> entry(K key, V value, boolean isImmutable) {
        return isImmutable ? new AbstractMap.SimpleEntry<>(key, value) : new AbstractMap.SimpleImmutableEntry<>(key, value);
    }
}
