#!/bin/bash

java -cp libs/jflex.jar jflex.Main Lexico_Gramatica/Lexico.l

java -cp libs/cup.jar java_cup.Main -parser ConstructorAST -symbols ClaseLexica -nopositions Lexico_Gramatica/Gramatica.cup

mv Lexico_Gramatica/AnalizadorLexico.java src/alex
mv ConstructorAST.java src/ast
mv ClaseLexica.java src/ast

find "." -type f -name "*.java" -exec javac -cp "libs/cup.jar" {} +

read -rsp $'Press enter to continue...\n'
exit 0