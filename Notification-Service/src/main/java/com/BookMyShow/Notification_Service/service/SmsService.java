package com.BookMyShow.Notification_Service.service;


import org.springframework.stereotype.Service;

@Service
public class SmsService {

    public void sendSms(String phone, String message) {
        System.out.println("Sending SMS to: " + phone);
    }
}
