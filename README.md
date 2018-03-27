# To-Do 应用程序

## 任务管理用例

每个任务管理系统都是由一个有序的执行项目或者任务列表组成的。

*   每个任务都有一个标题。

*   可以在列表中添加和删除任务，以及将任务标记为活动或者完成状态。

*   可以修改任务标题。

*   当一个任务发生改变时应该自动地持久化到数据库中。

下图显示了用户界面的截图：一个基于 Web 的 To Do 应用的用户界面和执行项目。

![](doc/images/web-based-user-interface-of-to-do-application-and-its-actions.jpg)

## 实现

为 To Do 应用程序编写单元测试、集成测试和功能测试，并将它们集成到构建中。

为了在项目中划分单元测试、集成测试和功能测试，需要将三者的代码放置在不同的目录下。项目结构应该是这样的：

```
└── src
    ├── functTest
    │   └── java
    │   └── groovy
    │   └── resources 
    ├── integTest
    │   └── java
    │   └── groovy
    │   └── resources
    ├── main
    │   ├── java
    │   └── groovy
    │   └── resources
    └── test
	    └── java
	    └── groovy
	    └── resources
```

通常使用 `test` task 来执行单元测试。想要结合 JUnit、TestNG 和 Spock 等多个单元测试框架，你需要做两个事情：
	
*   默认的 `test` task 只会执行 JUnit 和 Spock 测试。需要添加了一个新的 `Test` 类型的 task，用于执行 TestNG 测试，并将其集成到测试生命周期中。

*   为了合并所有测试 task 的测试结果，需要添加了一个新的 `TestReport` 类型的 task 来生成综合报告，并将其集成到构建生命周期中。

为了在构建中支持集成测试和功能测试，你需要满足下面三个基本需求：

*   提供独立的 task 来分别执行单元测试、集成测试和功能测试。这意味着需要为集成测试和功能测试提供独立的 source set，并在各自的 task 中应用对应的 source set。

*   分离单元测试、集成测试和功能测试的运行结果和报告。

*   让集成测试和功能测试作为验证生命周期 task `check` 的一部分。

关于集成测试，这里引入了 [H2 数据库](http://www.h2database.com)来存储数据，以便验证持久层能否按预期工作。与数据库的交互是在新增的 `H2ToDoRepository` 类中实现的。

关于功能测试，这里使用 [Geb](http://www.gebish.org/) 来对添加任务的 web 功能进行了简单的测试。

## 版本说明

*   **0.1** 该版本实现由命令行控制的基本功能。程序将向用户展现一个命令菜单，用户通过菜单输入字符触发一个指定操作来管理 to-do 列表。

	为了保证第一轮迭代的解决方案尽可能的简单，我们不会引入数据库来存储数据，而是将它保存在内存里。
	
*   **0.2** 这里将上一版的应用程序转换为一个 Web 应用。

	为了尽可能简单，这里使用了标准的 Java 企业级 Web 组件 Servlet 和 JSP 来构建 Web 应用程序。另外，为了给用户一个流畅和舒适的体验，这里把 to-do 列表做成一个单页面应用。
	
*   **0.3** 为了更容易实现项目重构，基于弱耦合和强内聚的原则，这里将现有的 Do Do 应用程序项目结构重构成模块化架构，一共划分了三个模块：model、repository 和  web。