# Expressions

This project consists of 3 closely related homeworks
for the Programming course.


It provides classes for creating and evaluating expressions in the following way:
```java
Expression e = new Subtract(
        new Multiply(
                new Const(2),
                new Variable("x")
        ),
        new Const(3)
);
int a = e.evaluate(5);
```

Also, you can parse expressions:
```java
Parser parser = new ExpressionParser();
TripleExpression exp = parser.parse("2 ** (2 ** 3) + 123 / y * (z >> 3)");
```