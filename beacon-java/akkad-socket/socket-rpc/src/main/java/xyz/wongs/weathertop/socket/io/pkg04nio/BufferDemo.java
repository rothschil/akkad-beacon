package xyz.wongs.weathertop.socket.io.pkg04nio;

import java.nio.IntBuffer;

public class BufferDemo {

    public static void main(String[] args) {
        IntBuffer intBuffer = IntBuffer.allocate(10);

        intBuffer.put(23);
        intBuffer.put(3);
        intBuffer.put(8);
        intBuffer.flip();
        System.out.println(intBuffer.toString());

        for (int i = 0; i < intBuffer.limit(); i++) {
            System.out.println(intBuffer.get());
        }

    }
}
