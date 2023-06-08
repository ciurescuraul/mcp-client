package com.example.mcpclient;

import com.example.mcpclient.model.Message;
import com.example.mcpclient.service.ClientService;

import java.util.List;

public class TestApp {
    public static void main(String[] args) {

//        List<Message> messages = ClientService.parseJsonFromUrlByDate("20180131");
//        List<Message> messages = ClientService.parseJsonFromUrlByDate("20180201");
        List<Message> messages = ClientService.parseJsonFromUrlByDate("20180202");

        System.out.println(messages.size());
        messages.forEach(System.out::println);

    }
}
