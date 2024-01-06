<div>
    <p><a href="https://github.com/philiprbrenan/Vanina"><img src="https://github.com/philiprbrenan/Vanina/workflows/Test/badge.svg"></a>
</div>

# Vanina

The minimum number of extra parentheses required to make a string of **(** and
**)** satisfy the grammar:

```
S -> (S)) | empty
```

Dynamically reduce the input string heuristically recording the number of
insertions required for each reduction and return the shortest such sequence
that leads to an empty string.

For example:
```
))((
())((           +1 == 1
   ((
   ())(         +2 == 3
      (
      ())       +2 == 5
```

No proof is offered that this is, in fact, the shortest path.

Problem suggested by [Vanina Godoy](https://github.com/vlambo3/My-Exercises/blob/main/src/main/java/com/exercises/my/exercises/MinInsertions.java)
