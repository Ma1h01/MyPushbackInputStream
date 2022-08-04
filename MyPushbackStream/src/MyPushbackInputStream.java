import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class MyPushbackInputStream extends FilterInputStream {
    private MyStack buf;
    // private InputStream in;
    
    public MyPushbackInputStream(InputStream in) {
        super(in);
        this.buf = new MyStack();
    }
    public MyPushbackInputStream(InputStream in, int size) {
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
        for (int i = 0; i < buf_len && count < len; i++, off++, count++) { //'count < len' = 'buf' has more bytes than the requested 'len' 
            b[off] = buf.pop();
        }
        if (count < len) {
            int left_len = len - buf_len;
            for (int j = 0; j < left_len; j++, off++, count++) {
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
