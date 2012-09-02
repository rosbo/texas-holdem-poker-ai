package edu.ntnu.texasai.utils;

import java.util.*;

public class MapList<K, V> implements Iterable<List<V>> {
    private final Map<K, List<V>> mapList = new HashMap<K, List<V>>();

    @Override
    public Iterator<List<V>> iterator() {
        return mapList.values().iterator();
    }

    public V add(K key, V value){
        List<V> cards = mapList.get(key);

        if(cards == null){
            cards = new ArrayList<V>();
            mapList.put(key, cards);
        }

        cards.add(value);

        return value;
    }

    public Set<K> keySet(){
        return mapList.keySet();
    }

    public Set<Map.Entry<K, List<V>>> entrySet(){
        return mapList.entrySet();
    }

    public List<V> get(K key){
        return mapList.get(key);
    }
}
