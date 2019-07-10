package com.example.springredis.hotkey;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @program: springBootPractice
 * @description:
 * @author: hu_pf
 * @create: 2019-07-09 18:02
 **/
public class HotKey {

    private static Map<String,String> hotKeyMap = new ConcurrentHashMap<>();
    private static List<String> hotKeyList = new CopyOnWriteArrayList<>();

    static {
        setHotKey("hu1","1");
        setHotKey("hu2","2");
    }

    public static void setHotKey(String key,String value){
        hotKeyMap.put(key,value);
        hotKeyList.add(key);
    }

    public static void updateHotKey(String key,String value){
        hotKeyMap.put(key,value);
    }

    public static String getHotValue(String key){
        return hotKeyMap.get(key);
    }

    public static void removeHotKey(String key){
        hotKeyMap.remove(key);
    }

    public static boolean containKey(String key){
        return hotKeyList.contains(key);
    }
}
