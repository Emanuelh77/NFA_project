# Project 2: Non-Deterministic Finite Automata

* Author: Emanuel Hernandez & Ron Lowies
* Class: CS361 Section 3
* Semester: Spring 2019

## Overview

This program models a Non-Deterministic Finite Automata that determines whether a
set of strings are in the language based off the input file.

## Compiling and Using

To compile, execute the following command in the main project directory:
```
$ javac fa/nfa/NFADriver.java
```

To run the compiled class, user input is required, like this:
```
$ java fa/nfa/NFADriver ./tests/p2tc0.txt
```
In this case, the user provides a simple .txt file that provides the alphabet, language and strings
to test.

## Discussion

Starting the project was fairly straightforward until we started working on getDFA().
We tried to implement some pseudocode we wrote on a whiteboard in the lab. After this didn't work we tried following the slides
but still ran into issues. To try an fix them we went to office hours with Dr. Sherman and she gave us an outline for the getDFA() method.
After hours of debugging we managed to get the program to an acceptable point. With most testcases producing the correct output. 
However there are a few warnings for some cases. 
## Testing

We used all the test files provided.
