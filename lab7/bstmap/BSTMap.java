package bstmap;

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
    /* deleted by gradescope api checker
    public BSTMap(BSTNode node){
        root=node;
        size=1;
    }
     */
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
        if (key==null){
            throw new UnsupportedOperationException();
        }
        BSTNode cur=root;
        while (cur!=null){
            int cmp=key.compareTo(cur.key);
            if (cmp==0){
                return true;
            }
            else if (cmp<0){
                cur=cur.left;
            }
            else {
                cur=cur.right;
            }
        }
        return false;
    }

    @Override
    public V get(K key) {
        if (root==null){
            return null;
        }
        else {
            BSTNode current=root;
            while (true){
                int cmp=key.compareTo(current.key);
                if (cmp>0){
                    if (current.right==null){
                        return null;
                    }
                    else{
                        current=current.right;
                    }
                }
                else if (cmp<0){
                    if (current.left==null){
                        return null;
                    }else {
                    current=current.left;}
                }
                else {
                    return current.val;
                }
            }
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void put(K key, V value) {
        //make sure put is working via containsKey and get
        if (root==null){
            root=new BSTNode(key,value);
            size=1;
        }
        else {
            BSTNode current=root;
            while (true){
                int cmp=key.compareTo(current.key);
                if (cmp>0){
                    if (current.right==null){
                        current.right=new BSTNode(key,value);
                        size++;
                        break;
                    }
                    current=current.right;
                }
                else if (cmp<0){
                    if (current.left==null){
                        current.left=new BSTNode(key,value);
                        size++;
                        break;
                    }
                    current=current.left;
                }
                else {
                    current.val=value;
                    break;
                }
            }
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
        printInOrder(root);
        /* prints out your BSTMap in order of increasing Key. */
    }
    private void printInOrder(BSTNode x) {
        if (x == null) return;
        printInOrder(x.left);
        System.out.println(x.key);
        printInOrder(x.right);
    }
}
