package cn.nio.filechannel;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;

/**
 * Created by Administrator on 2017/5/3.
 */
public class FileChannel {

    public static void main(String[] args) {
        RandomAccessFile file = null;
        try {
            file = new RandomAccessFile("D://reciveFile.txt","rw");
            java.nio.channels.FileChannel fileChannel = file.getChannel();

            ByteBuffer bf = ByteBuffer.allocate(15);
            CharBuffer cf = CharBuffer.allocate(20);

            int read = fileChannel.read(bf);
            while (read != -1){
                System.out.println("\n Read " + read);
                bf.flip();

                while(bf.hasRemaining()){
                    System.out.print((char) bf.get());
                }

                bf.clear();
                read = fileChannel.read(bf);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                file.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
