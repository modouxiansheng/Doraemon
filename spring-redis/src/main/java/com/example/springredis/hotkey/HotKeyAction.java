package com.example.springredis.hotkey;


public enum HotKeyAction {

    UPDATE(){
        @Override
        public void action(String key, String value) {
            HotKey.updateHotKey(key,value);
        }
    },
    REMOVE(){
        @Override
        public void action(String key, String value) {
            HotKey.removeHotKey(key);
        }
    };
    public abstract void action(String key,String value);
}
