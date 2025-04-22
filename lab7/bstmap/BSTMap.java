package bstmap;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>,V> implements Map61B<K,V>{

    private class BSTNode{
        K key;
        V val;
        BSTNode left,right;
        BSTNode(K k,V v){
            key=k;
            val=v;
        }
    }
    private BSTNode root;
    private int size;
    public BSTMap(){
        root=null;
        size=0;
    }
    public BSTMap(BSTNode node){
        root=node;
        size=1;
    }
    /*
     * implement all of the methods given in Map61B except for `remove`, `iterator` and `keySet` */

    /*For these methods you should throw an `UnsupportedOperationException`. */
    @Override
    public void clear() {
        root=null;
        size=0;
    }

    @Override
    public boolean containsKey(K key) {
        return containsKey(root,key);
    }
    private boolean containsKey(BSTNode node,K key){
        if (node==null){
            return false;
        }
        if (node.key==key){
            return true;
        }
        return containsKey(root.left,key) || containsKey(root.right,key);
    }

    @Override
    public V get(K key) {
        return get(root,key);
    }
    private V get(BSTNode node,K key){
        if (node==null){
            return null;
        }
        if (node.key==key){
            return node.val;
        }
        if (key.compareTo (node.key)>0){
            return get(root.right,key);
        }
        else{
            return get(root.left,key);
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void put(K key, V value) {
        put(root,key,value);
    }
    private void put(BSTNode node,K key,V value){
        if (node==null){
            node=new BSTNode(key,value);
            size++;
        }
        if (key.compareTo (node.key)>0){
            put(node.right,key,value);
        }
        if (key.compareTo (node.key)<0) {
            put(node.left,key,value);
        }
        else {
            node.val=value;
        }
    }

    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
        //return Set.of();
    }

    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }

    public void printInOrder(){

        /* prints out your BSTMap in order of increasing Key. */
    }

}
