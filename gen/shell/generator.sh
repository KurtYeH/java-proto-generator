#!/usr/bin/env bash

echo "start decompiler"

java -cp ../lib/java-decompiler.jar org.jetbrains.java.decompiler.main.decompiler.ConsoleDecompiler -dgs=true ../result/PersonConverter.class ../result

sed -i '' '1i\
// 源码由KURT从字节码生成（由FernFlower反编译器驱动）
' ../result/PersonConverter.java

echo "end decompiler"
