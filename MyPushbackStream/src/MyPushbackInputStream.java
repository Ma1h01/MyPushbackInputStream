import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;


// Bytes unread from an array follow first in first out
// Bytes unread from one byte by byte follow last in last out
// if there are mix of bytes(from an array and from one by one), must follow
// rules above
public class MyPushbackInputStream extends FilterInputStream {
    private MyStack buf;
    
    public MyPushbackInputStream(InputStream in) {
        super(in);
        this.buf = new MyStack();
    }
    
    public MyPushbackInputStream(InputStream in, int size) {
        // size = the initial size of the array, IT IS NOT A FIXED SIZE
        // unlike regular PushbackInputStream, the size will grow dynamically.
        super(in);
        this.buf = new MyStack(size);
    }

    @Override
    public int read() throws IOException {
        if (buf.isEmpty()) return super.read();
        return (int)buf.pop();
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        if (buf.isEmpty()) return super.read(b, off, len);
        int buf_len = buf.length();
        int count = 0;
        if (len <= buf_len) { //request NO more bytes than pushback buffer has
            // i = total number of times to loop through the unread buffer.
            // count = total number of bytes are read.
            for (int i = 0; i < len; i++, off++, count++) {
                b[off] = buf.pop();
            }
        }
        else {// first read the unread bytes from pushback buffer, then read bytes from the stream
            for (int j = 0; j < buf_len; j++, off++, count++) {
                b[off] = buf.pop();
            }
     
            int left_len = len - buf_len;
            for (int k = 0; k < left_len; k++, off++, count++) {
                b[off] = (byte)super.read();
            }
        }
        return count;
    }

    public void unread(int b) {
        buf.push((byte)b);
    }

    public void unread(byte[] b, int off, int len) {
        buf.push(b, off, len);
    }

    public void unread(byte[] b) {
        buf.push(b, 0, b.length);
    }

    public long skip(long n) throws IOException {
        if (n < 0) throw new IllegalArgumentException("Cannot be negative.");
        if (n == 0) return 0;
        if (buf.isEmpty()) return super.skip(n);
        
        int count = 0;
        int buf_len = buf.length();
        if (n <= buf_len) {
            while (n > 0) {
                buf.pop();
                count ++;
                n --;
            }
        }
        else {
            long left_len = n - buf_len;
            while (buf_len > 0) {
                buf.pop();
                count ++;
                buf_len --;
            }
            count += super.skip(left_len);
        }
        return count;
    }
}
