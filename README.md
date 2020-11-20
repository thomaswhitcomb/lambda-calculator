# lambda-calculator

A lambda calculator in clojure.

## Grammar

```
  <exp>::= <var>
       | /<var> . <exp>
       | ( <exp> <exp> )
```

This BNF grammar tells us that expressions in the lambda calculus come in one of three flavors:

A variable (the first production above): we will use a single letter to denote a variable. So, x,y are examples of variables.

A function abstraction (the second production above): this type of / expressions, also called a lambda abstraction, corresponds to a function definition, which contains two components: the formal parameter of the function (there must be exactly one parameter, namely the <var> non-terminal in the second production above) and the body of the function (namely the <exp> non-terminal in the same production). So, for example, /x.y is the function whose formal parameter is x and whose body is y. Note that the non-terminal <var> after the / terminal is not the name of the function. In fact, all functions in the lambda calculus are anonymous.

An application (the third production above): this type of / expressions corresponds to a function call (or application, or invocation), which contains two components: the function being called, followed by the argument that is passed into the function. So, for example, (f x) is the application of the variable f (which must stand for a function, since functions are the only values in the lambda calculus) to the argument x, which must also stand for a function.

Note that in the lambda calculus, the parentheses surround both the function and its argument, while in many modern programming languages (and in mathematical notation), the function would come first and be followed by the argument in parentheses, like this: f(x). In the lambda calculus, the parentheses are not optional around function calls. Furthermore, the grammar above makes it clear that they cannot be used anywhere else.

The grammar above is quite concise, since it contains only two non-terminals. Yet it generates an infinite set of expressions that represent all computable functions! Recall that the expressive power of BNF grammars comes from recursion, which is present in both the second and third productions in the grammar above.
