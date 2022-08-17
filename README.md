# MyPushbackInputStream
This is a program that mimics the functionality of Java's `PushbackInputStream`.

Unlike Java's `PushbackInputStream`, which uses an array to store the bytes that are read from a stream, `MyPushbackInputStream` uses `MyStack`, a stack data structure, to store
the data. A stack is more suitable than a regular array in the case of Java's `PushbackInputStream` because the `pop()` method fulfills the `PushbackInputStream`'s
core feature which allows user to unread data.
