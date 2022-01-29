# simple-grammar-parser-java

This repo contains a few classes defining a tokenizer and parser for the following grammar:

assign = id , ’=’ , expr , ’;’   
expr = term , [ ( ’+’ | ’-’ ) , expr ]  
term = factor , [ ( ’*’ | ’/’) , term]  
factor = int | ’(’ , expr , ’)’   

It was written as part of an assignment, and I have only included the classes which I wrote myself, which means some of the classes referred to in the code, like the Scanner, can not be found in this repo.
