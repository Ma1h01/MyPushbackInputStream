import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PushbackInputStream;

public class App {
    public static void main(String[] args) throws Exception {
        FileInputStream fileIn1 = new FileInputStream("/Users/ma1h01/Desktop/projects/MyPushbackStream/MyPushbackStream/src/File01.txt");
        FileInputStream fileIn2 = new FileInputStream("/Users/ma1h01/Desktop/projects/MyPushbackStream/MyPushbackStream/src/File02.txt");
        PushbackInputStream in1 = new PushbackInputStream(fileIn1, 100);
        MyPushbackInputStream in2 = new MyPushbackInputStream(fileIn2);

        byte[] array1  = new byte [15];
        byte[] array2  = new byte [15];
        
        System.out.println("Reading 5 bytes from the files into arrays...");
        in1.read(array1, 0, 5);
        in2.read(array2, 0, 5);

        String msg1 = new String(array1);
        String msg2 = new String(array2);

        System.out.println("\nBytes read from two files:");
        System.out.println("Java PushbackInputStream: " + msg1);
        System.out.println("My MyPushbackInputStream: " + msg2);


        System.out.println("\nUnreading 2 bytes...");
        in1.unread(array1,0,2);
        in2.unread(array2, 0,2);


        System.out.println("\nReading 1 bytes from file...");
        in1.read(array1, 2, 1);
        in2.read(array2, 2, 1);
        msg1 = new String(array1);
        msg2 = new String(array2);
        System.out.println("\nBytes read from two files:");
        System.out.println("Java PushbackInputStream: " + msg1);
        System.out.println("My MyPushbackInputStream: " + msg2);
                  
        System.out.println("\nUnreading one byte, a byte array, and one byte...");
        in1.unread(71);
        in2.unread(71);
        byte[] temp = {72,73,74};
        in1.unread(temp);
        in2.unread(temp);
        in1.unread(75);
        in2.unread(75);

        System.out.println("\nReading all the just read bytes...");
        in1.read(array1, 0, array1.length);
        in2.read(array2, 0, array2.length);
        msg1 = new String(array1);
        msg2 = new String(array2);
        System.out.println("\nBytes read from two files:");
        System.out.println("Java PushbackInputStream: " + msg1);
        System.out.println("My MyPushbackInputStream: " + msg2);


        in1.close();
        in2.close();

        
    }
}
