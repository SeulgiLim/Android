package com.company.design.singleton;

public class AClazz {

    private SocketClient socketClient;

    // 기본생성자에서 초기화 진행
    public AClazz(){
        this.socketClient = SocketClient.getInstance();
        //this.socketClient = new SocketClient();

    }

    public SocketClient getSocketClient(){
        return this.socketClient;
    }
}
