package com.code.codepractice.io.bio;

import org.junit.Test;

import java.io.OutputStream;
import java.net.Socket;

public class Client {

    @Test
    public void start() {
        try {
            Socket socket = new Socket("127.0.0.1", 8081);
            OutputStream out= socket.getOutputStream();
            String msg="hello everyone, this is " + System.currentTimeMillis();
            out.write(msg.getBytes());
            out.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void batchTest() {
        for (int i = 0; i < 10; i++) {
            Runnable runnable = () -> start();
            runnable.run();
        }
    }
}
