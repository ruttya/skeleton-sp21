# lecture
## lab6
- ```javac xx.java```编译文件，生成```xx.class```文件
- 可以使用```javac *.java```
- ```java xx```执行`xx.class`文件，`xx`=类名
- 通常执行`Main`
- 执行包中的文件时，要在包的父目录下执行
    ```
  cd ..              #dir=lab6/
    java capers.Main
  ```
- ```System.getProperty("user.dir")``` 获取当前工作路径
- In IntelliJ, the CWD is given by the specified directory under Run > Edit Configurations > Working Directory
- ```cd ..```跳转父目录
- ```cd ../..```跳转爷目录
- `serialization`序列化

## proj2
- Real Git distinguishes several different kinds of objects. For our purposes, the important ones are

  - blobs: The saved contents of files. Since Gitlet saves many versions of files, a single file might correspond to multiple blobs: each being tracked in a different commit.
  - trees: Directory structures mapping names to references to blobs and other trees (subdirectories).
  - commits: Combinations of log messages, other metadata (commit date, author, etc.), a reference to a tree, and references to parent commits. The repository also maintains a mapping from branch heads to references to commits, so that certain important commits have symbolic names.
- Gitlet simplifies from Git still further by
  - Incorporating trees into commits and not dealing with subdirectories (so there will be one “flat” directory of plain files for each repository).
  - Limiting ourselves to merges that reference two parents (in real Git, there can be any number of parents.)
  Having our metadata consist only of a timestamp and log message. A commit, therefore, will consist of a log message, timestamp, a mapping of file names to blob references, a parent reference, and (for merges) a second parent reference.
- command
  - `init`
  - `add`
  - `commit`
  - `checkout -- [file name]`
  - `checkout [commit id] -- [file name]`
  - `log`
# from lecture
- 给的`.py`test也许有点问题，`lab6`并没有出现像样的界面
- git bash下`make`等于编译所有`.java`文件，文件注释中有中文会导致报错。。。
# 进度
- apr17，lab6花了一整个白天（晚睡摸鱼版，蚌
- 本质上我没彻底搞明白git本身的步骤（我觉得是英语不精，实现应该还好，没有边缘问题