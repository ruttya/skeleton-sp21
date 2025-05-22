# Gitlet Design Document
**Name**: ruttya

---

## 1. Classes and Data Structures
- Include here any class definitions. For each class list the instance variables and static variables (if any). Include a brief description of each variable and its purpose in the class. Your explanations in this section should be as concise as possible. Leave the full explanation to the following sections. You may cut this section short if you find your document is too wordy.
```java
/** directory construction: 
     *   .gitlet
     *      |--objects
     *      |     |--commit and blob
     *      |--refs 存储所有分支的末端信息，在heads文件夹内存有多个文件，每个文件的名字即为分支名字
     *      |    |--heads
     *      |         |--master 文件内容是末端Commit的40位ID
     *      |--HEAD 文件内容是当前指向的Commit的ID
     *      |--index 文件内容是当前stageArea<fileName,blobName>,blobName==null表示在移除区
     */
```
---

### Main
#### Fields
### Repository
- no Instance Variables to suit `Main.java`. All information saved in individual file.
#### Fields
- 提交后，暂存区域将被清除。
### Commit
#### Instance Variables
- Message - contains the message of a commit. 
- Author - author who submit this commit, equals branch name as I think. 
- Date - time at which a commit was created. Assigned by the constructor. 
- Parent - the parent commit of a commit object.
- Files - file list (as a HashMap Object) of this commit: Map<File file,String blobName>. 
#### Fields
- 构造方法
- get() and set()
- 获取现实时间并返回指定格式字符串形式
-
### Utils
#### Fields

---
## 2. Algorithms
- This is where you tell us how your code works. For each class, include a high-level description of the methods in that class. That is, do not include a line-by-line breakdown of your code, but something you would write in a javadoc comment above a method, including any edge cases you are accounting for. We have read the project spec too, so make sure you do not repeat or rephrase what is stated there. This should be a description of how your code accomplishes what is stated in the spec.

The length of this section depends on the complexity of the task and the complexity of your design. However, simple explanations are preferred. Here are some formatting tips:

For complex tasks, like determining merge conflicts, we recommend that you split the task into parts. Describe your algorithm for each part in a separate section. Start with the simplest component and build up your design, one piece at a time. For example, your algorithms section for Merge Conflicts could have sections for:

Checking if a merge is necessary.
Determining which files (if any) have a conflict.
Representing the conflict in the file.
Try to clearly mark titles or names of classes with white space or some other symbols.
---

## 3. Persistence
- Describe your strategy for ensuring that you don’t lose the state of your program across multiple runs. Here are some tips for writing this section:

This section should be structured as a list of all the times you will need to record the state of the program or files. For each case, you must prove that your design ensures correct behavior. For example, explain how you intend to make sure that after we call java gitlet.Main add wug.txt, on the next execution of java gitlet.Main commit -m “modify wug.txt”, the correct commit will be made.

A good strategy for reasoning about persistence is to identify which pieces of data are needed across multiple calls to Gitlet. Then, prove that the data remains consistent for all future calls.

This section should also include a description of your .gitlet directory and any files or subdirectories you intend on including there.
- example of Capers: https://sp21.datastructur.es/materials/proj/proj2/capers-example