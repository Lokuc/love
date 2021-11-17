package com.severgames.client;


import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ClientWindow {
    // адрес сервера
    private static final String SERVER_HOST = "localhost";
    // порт
    private static final int SERVER_PORT = 3443;
    // клиентский сокет
    private Socket clientSocket;
    // входящее сообщение
    private Scanner inMessage;
    // исходящее сообщение
    private PrintWriter outMessage;
    private String clientName = "lol";
    // получаем имя клиента
    public String getClientName() {
        return this.clientName;
    }

    // конструктор
    public ClientWindow() {
        try {
            // подключаемся к серверу
            clientSocket = new Socket(SERVER_HOST, SERVER_PORT);
            inMessage = new Scanner(clientSocket.getInputStream());
            outMessage = new PrintWriter(clientSocket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        // в отдельном потоке начинаем работу с сервером
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // бесконечный цикл
                    while (true) {
                        // если есть входящее сообщение
                        if (inMessage.hasNext()) {
                            // считываем его
                            String inMes = inMessage.nextLine();
                            System.out.println(inMes);
                            sendMsg(clientName+" "+inMes);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        // добавляем обработчик события закрытия окна клиентского приложения

    }

    // отправка сообщения
    public void sendMsg(String str) {
        outMessage.println(str);
        outMessage.flush();
    }
}

