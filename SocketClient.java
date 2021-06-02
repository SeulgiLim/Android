package com.company.design.singleton;
// 싱글턴 동작 기본 구조

public class SocketClient {

    private static SocketClient socketClient = null;

    private SocketClient(){

    }
    /*public SocketClient(){

    }*/

    // 메소드 생성
    public static SocketClient getInstance(){

        if (socketClient == null){
            socketClient = new SocketClient();

        }
        return socketClient;
    }

    // 접근 가능한 메소드 생성

    public void connect(){
        System.out.println("Connect");
    }

}
