# 1장 Java Virtual Machine

## Java Architecture

Java는 **Write Once, Run Everywhere** 라는 철학을 실현하기 위해 다음 4가지 상호 연관된 기술을 엮어놓았다.

- The Java Programming Language
- The Java Class File Format
- The Java Application Programming Interface (Java API)
- The Java Virtual Machine (JVM)

에디터, IDE 등을 통해 Java Source Code를 작성하면 java 파일이 만들어 진다. 이것은 코드만 담고 있기 때문에 컴파일을 통해 Class 파일을 생성해줘야 한다. 이것은 JDK에 내장되어 있는 javac라는 컴파일러를 이용해서 수행된다. 

이 클래스 파일은 **수행이 가능한 형태의 파일**이다. 이것은 직접적으로 실행을 시도해도 실행이 되지 않고 JRE (Java Runtime Environment) 가 필요하다는 의미이다. java 프로그램을 통해 실행하게 되면 java프로그램은 JVM을 하나의 프로레스로 올리는 작업과 Class 파일의 로딩을 수행한다. 그 후 Class 파일을 분석하여 JRE 내에 있는 Java API와 같이 프로그램을 수행하도록 하는 것이다.

java 파일을 Class파일로 생성하는 시점을 Compile Time 이라고 하며, Class파일을 수행하는 시점을 Runtime 이라고 한다.

![](C:\pch\TIL\java\jvm\ch1\javaExecutionProcess.png)

즉 `Java Programming Language`로 작성된 파일을 `Class File Format` 으로 변경하고 `Java Virtual Machine`이 이것을 수행하는데 `Java Applicatoin Programming Interface`  와 동적으로 연결되어 실행되는 것이다.