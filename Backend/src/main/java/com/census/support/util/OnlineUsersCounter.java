package com.census.support.util;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class OnlineUsersCounter implements HttpSessionListener {

    private static int numberOfUsersOnline;

    public OnlineUsersCounter() {
        numberOfUsersOnline = 0;
    }

    public static int getNumberOfUsersOnline() {
        return numberOfUsersOnline;
    }

    public void sessionCreated(HttpSessionEvent event) {

        System.out.println("Session created by Id : " + event.getSession().getId());
        synchronized (this) {
            numberOfUsersOnline++;
        }

    }

    public void sessionDestroyed(HttpSessionEvent event) {

        System.out.println("Session destroyed by Id : " + event.getSession().getId());
        synchronized (this) {
            numberOfUsersOnline--;
        }

    }

}