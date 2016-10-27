#URAN

##Motivation
When I was using [Z3](https://github.com/Z3Prover/z3) SMT solver (for the first time) to solve some challenging problems (many years ago), I often found myself spending most of the time to program all kinds of formulas which significantly slow down my development.  So I decide to build my own set of classes that can help me quickly construct the formulas I want and interact with solvers. In short, uran is an engine that you could place in between solvers and your own specification language, and totally seperate your code from the language that you design and underlying solvers.

##Supported Features
* Boolean Circuit Gate.
* Quantifiers.
* Arithmetic Formula.
* Increment SMT Solving.
* Iterative SMT Solving. 

##Output Format
* dimacs (SAT Solvers)
* [SMT2](http://smtlib.cs.uiowa.edu/language.shtml) (SMT Solvers)

##Work in Progress
* Bit Vector Formula.
* Compact Sized Formula Generation.

##Supported SMT Solvers:
* [Z3](https://github.com/Z3Prover/z3)
* [CVC4](https://github.com/CVC4/CVC4) - work in progress.

##API Documentation (Partial)
You can find detailed structure of uran [here](http://htmlpreview.github.com?https://github.com/classicwuhao/uran/blob/master/docs/html/index.html).


##License
All source code is under MIT License which you can find [here](https://github.com/classicwuhao/uran/license).

##Author Information 
Hao Wu


#####Latest Update: October - 2016
