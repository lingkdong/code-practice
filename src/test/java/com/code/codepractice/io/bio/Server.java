package com.code.codepractice.io.bio;

import org.junit.Test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Socket 链接 ServerSocket
 */
public class Server {

    @Test
    public void start() {
        try {
            ServerSocket serverSocket = new ServerSocket(8081);
            while (true) {
                System.out.println("waiting for connection");
                //Listens for a connection to be made to this socket and accepts it
                //The method blocks until a connection is made.
                final Socket socket = serverSocket.accept();//blocking 阻塞
                System.out.println("connection with client");
                //C10K 问题 来一个客户端开一个线程
                Runnable runnable = () -> handler(socket);
                runnable.run();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
   
    private void handler(Socket socket) {
        try {
            byte[] bytes = new byte[1024];
            System.out.println("read client data start");
            //Reads some number of bytes from the input stream and stores them into
            //the buffer array <code>b</code>. The number of bytes actually read is
            //returned as an integer.  This method blocks until input data is
            //available, end of file is detected, or an exception is thrown.
            int reads = socket.getInputStream().read(bytes);//blocking 阻塞
            System.out.println("read client data end");
            if(reads!=-1){
                System.out.println("client data : "+new String(bytes,0,reads));//(byte bytes[], int offset, int length)
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
