package hashmap;

import java.util.*;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author ruttya
 */
public class MyHashMap<K, V> implements Map61B<K, V>,Iterable<K> {
    public static final int INITIAL_SIZE = 16; //初始桶数量
    public static final double LOAD_FACTOR = 0.75; //负载因子：元素数量(N) / 桶数量(M)达到此阈值时触发扩容
    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    /* Instance Variables */
    /* each 'Collection' of 'Node's represents a single bucket in the hash table. */
    private Collection<Node>[] buckets;
    private double load; //当前负载因子（每个桶的元素数）
    private int n; //元素数量
    private int m; //桶数量
    // You should probably define some more!

    /** Constructors */
    public MyHashMap() {
        this(INITIAL_SIZE);
    }

    public MyHashMap(int initialSize) {
        this(initialSize,LOAD_FACTOR);
    }

    /**
     * MyHashMap constructor that creates a backing array of initialSize.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialSize initial size of backing array
     * @param maxLoad maximum load factor
     */
    public MyHashMap(int initialSize, double maxLoad) {
        buckets =createTable(initialSize); //?
        m=initialSize;
        n=0;
        load=Math.min(LOAD_FACTOR,maxLoad);
    }

    /**
     * Returns a new node to be placed in a hash table bucket
     */
    private Node createNode(K key, V value) {
        return new Node(key,value);
    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucket type
     *
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        return new LinkedList<>();
    }

    /**
     * Returns a table to back our hash table. As per the comment
     * above, this table can be an array of Collection objects
     *
     * BE SURE TO CALL THIS FACTORY METHOD WHEN CREATING A TABLE SO
     * THAT ALL BUCKET TYPES ARE OF JAVA.UTIL.COLLECTION
     *
     * @param tableSize the size of the table to create
     */
    private Collection<Node>[] createTable(int tableSize) {
        Collection<Node>[] table=(Collection<Node>[]) new Collection[tableSize];
        for (int i=0;i<tableSize;i++){
            table[i]=createBucket();
        }
        return table;
    }

    // TODO: Implement the methods of the Map61B Interface below
    // Your code won't compile until you do so!
    @Override
    public void clear() {
        buckets=createTable(INITIAL_SIZE);
        m=INITIAL_SIZE;
        n=0;
    }

    //下方是借鉴的hash计算方法
    private int hash(K key) {
        return Math.floorMod((key.hashCode()), m);
    }

    @Override
    public boolean containsKey(K key) {
        if (key==null){
            return false;
        }
        int hash=hash(key);
        for (Node node:buckets[hash]){
            if (node.key.equals(key)){
                return true;
            }
        }
        return false;
    }

    @Override
    public V get(K key) {
        if (key==null){
            return null;
        }
        int hash=hash(key);
        for (Node node:buckets[hash]){
            if (node.key.equals(key)){
                return node.value;
            }
        }
        return null;
    }

    @Override
    public int size() {
        return n;
    }

    // You can assume null keys will never be inserted.
    @Override
    public void put(K key, V value) {
        int hash=hash(key);
        for (Node node:buckets[hash]){
            if (node.key.equals(key)){
                node.value=value;
                return;
            }
        }
        buckets[hash].add(createNode(key,value));
        n++;
        if ((double) n / m > load) {
            resize(m * 2);
        }
    }
    private void resize(int size){
        Collection<Node>[] table=buckets;
        buckets=createTable(size);
        m=size;
        for (Collection<Node> bucket:table){
            for (Node node:bucket){
                int hash=hash(node.key);
                buckets[hash].add(node);
            }
        }
    }

    @Override
    public Set<K> keySet() {
        Set<K> res=new HashSet<>();
        for (Collection<Node> bucket:buckets ){
            for (Node node:bucket){
                res.add(node.key);
            }
        }
        return res;
    }

    /* For remove, you should throw an UnsupportedOperationException */
    @Override
    public V remove(K key) {
        if (containsKey(key)) {
            int hash = hash(key);
            for (Node node : buckets[hash]) {
                if (node.key.equals(key)) {
                    V val = node.value;
                    buckets[hash].remove(node);
                    return val;
                }
            }
        }
        return null;
    }

    @Override
    public V remove(K key, V value) {
        if (containsKey(key) && get(key).equals(value)) {
            int hash = hash(key);
            for (Node node : buckets[hash]) {
                if (node.key.equals(key)) {
                    V val = node.value;
                    buckets[hash].remove(node);
                    return val;
                }
            }
        }
        return null;
    }

    @Override
    public Iterator<K> iterator() {
        return new hashmapIterator();
    }
    //以下全部借鉴。。。
    private class hashmapIterator implements Iterator<K>{
        private Iterator<Collection<Node>> bucketIter = Arrays.asList(buckets).iterator();
        private Iterator<Node> nodeIter = Collections.emptyIterator();

        @Override
        public boolean hasNext() {
            while (!nodeIter.hasNext() && bucketIter.hasNext()) {
                nodeIter = bucketIter.next().iterator();
            }
            return nodeIter.hasNext();
        }

        @Override
        public K next() {
            return nodeIter.next().key;
        }
    }


}
