public class MyStack {
    private byte[] arr;
    private int size;
    private int len;
    private int top;

    public MyStack(int s) {
        this.size = s;
        this.len = 0;
        this.top = -1;
        this.arr = new byte[this.size];
    }
    public MyStack() {
        this(5);
    }

    boolean isEmpty() {
        return len == 0;
    }
    int length() {
        return len;
    }
    void expand(int s) {
        int new_size = this.size + s;
        byte[] temp = new byte[new_size];
        for (int i = 0; i < this.len; i++) {
            temp[i] = arr[i];
        }
        arr = temp;
        this.size = new_size;
    }
    void expand() {
        expand(5);
    }

    void push(byte b) {
        if (this.len + 1 >= size) expand();
        top ++;
        arr[top] = b;
        len ++;
    }

    void push(byte[] b, int off, int len) {
        if (this.len + len >= size) expand(len);// use '>=' becuase 'len' is indexed, out of bound when len == size
        for (int i = off; i < off + len; i++) { // 'off + len' = the index position of the FIRST data
            push(b[i]);                         // that should not be pushed
        }
    }

    byte pop() {
        if (isEmpty()) throw new IndexOutOfBoundsException("Cannot pop from an empty array.");
        byte temp = arr[top];
        top --;
        len --;
        return temp;
    }
}