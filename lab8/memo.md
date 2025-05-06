# lecture
- hashmap=散列表+链表
- 散列表的每个元素称为一个bucket（桶）
- 初始桶数量 (initialSize)：
  哈希表创建时的初始桶（buckets）数量，默认值为 16（与 Java 内置 HashMap 一致）。
- 负载因子 (loadFactor)：表示平均每个桶的元素数量。
  触发扩容的阈值默认值为 0.75，计算公式为：
  loadFactor = 元素数量(N) / 桶数量(M)
- 扩容条件：当 N/M > loadFactor 时，需要扩容（通常将桶数量翻倍）。
# from lecture
![hashmap](img/2.jpg)
- `Collection<Node>[]`=泛型数组，不能被直接创建
    ```java
  // 以下代码无法编译！
  Collection<Node>[] buckets = new Collection<Node>[10]; // 编译错误！
    ```
- 可以先创建原始类型数组，然后进行类型转换:
    ```java
  @SuppressWarnings("unchecked") //修改报警等级
  Collection<Node>[] buckets = (Collection<Node>[]) new Collection<?>[size];
    ```
- 或者使用List替代数组（更安全）
  ```
  //利用 List 的动态扩容特性:
  List<Collection<Node>> buckets = new ArrayList<>(size);
  //初始化(可以预先填充 null 或空集合：
  for (int i = 0; i < size; i++) {
    buckets.add(new LinkedList<>());
  }
  ```
- 类定义中调用构造方法使用`this(params)`，使用类名作为方法名会报错
# 进度
- factory method是啥来着
- 一头雾水，尤其是创建桶的部分，开抄（
- 抄完看懂了点，但是keySet和Iterator还是完全写不