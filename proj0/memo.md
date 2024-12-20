* 先列后行循环，列号从左向右，行号从下向上，因此行号从大到小循环
* 此proj似乎对应链表，但是tile类中的next信息没看懂表示哪里
* 另外链表node和list为什么要分开定义来着，既然都有value和next，list实际上也只记录头节点，目前感觉区别在于方法，比如list里可以包含length方法之类，这样看起来更清晰？
* TestUpOnly：方向对了，但是没有一次性上移到非0位置
* 目前想到的方法是按列快慢指针，还是不够勤劳，多尝试几次应该不难
* tilt尚未解决，其他已完成
* 做完了，其实还是快慢指针，之前卡太久1是没认真看Google quiz，非常好的提示，2是不够专注
* 全靠官方内容完成的，开心，卡了将近3天，希望以后能越来越快
```
java.lang.AssertionError: Board incorrect. Before tilting towards NORTH, your board looked like:
[
|    |    |   2|    |
|    |    |    |    |
|    |    |   2|    |
|    |    |   2|    |
] 0 (max: 0) (game is not over)

After the call to tilt, we expected:
[
|    |    |   4|    |
|    |    |   2|    |
|    |    |    |    |
|    |    |    |    |
] 4 (max: 0) (game is not over)

But your board looks like:
[
|    |    |   2|    |
|    |    |    |    |
|    |    |   4|    |
|    |    |    |    |
] 4 (max: 0) (game is not over) 

java.lang.AssertionError: Board incorrect. Before tilting towards NORTH, your board looked like:
[
|    |    |    |    |
|    |    |   2|    |
|    |    |   2|    |
|    |    |    |    |
] 0 (max: 0) (game is not over)

After the call to tilt, we expected:
[
|    |    |   4|    |
|    |    |    |    |
|    |    |    |    |
|    |    |    |    |
] 4 (max: 0) (game is not over)

But your board looks like:
[
|    |    |    |    |
|    |    |   4|    |
|    |    |    |    |
|    |    |    |    |
] 4 (max: 0) (game is not over) 
```
质量确实高，testcase各种信息提示非常完善