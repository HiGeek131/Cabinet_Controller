/*
    <one line to give the program's name and a brief idea of what it does.>
    Copyright (C) <year>  <name of author>

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

public class Main {
    public static final String db_url = "jbdc:mysql://192.168.2.221:3306/gary_db?useSSL=false";
    public static final String db_user = "test";
    public static final String sb_password = "";
    public static void main(String[] args) {
        System.out.println(FileTool.readFile("/sys/class/thermal/thermal_zone0/temp"));
        MyThread myThread = new MyThread("test1");
        myThread.start();
    }
}

class MyThread extends Thread {
    private String name;
    public MyThread(String name) {
        this.name = name;
    }
    @SuppressWarnings("InfiniteLoopStatement")
    public void run() {
        InetAddress address = null;
        String hostName = "";
        try {
            address = InetAddress.getLocalHost();
            hostName = address.getHostName().toString();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        if (! hostName.isEmpty()) {
            System.out.println("Host Name:" + hostName);
        }

        while (true) {
            System.out.println(this.name + "RUN");

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
