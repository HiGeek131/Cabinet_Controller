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
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class Main {
    public static final String db_url = "jdbc:mysql://192.168.2.221:3306/gary_db?useSSL=false";
    public static final String db_user = "test";
    public static final String sb_password = "";
    public static void main(String[] args) {
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
        hostName = hostName.replaceAll("[^a-zA-Z0-9_]", "");
        if (! hostName.isEmpty()) {
            System.out.println("Host Name:" + hostName);
        }
        MySQL mySQL = new MySQL(Main.db_url, Main.db_user, Main.sb_password);

        for (int i = 0; i < 10; i++) {
            if (mySQL.connect()) {
                System.out.println("mysql connect success");
                break;
            }
            System.out.println("mysql connect fail " + i);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (!mySQL.isConnect()) {
            System.out.println("mysql connect error,please cheek network");
            while (true);
        }
//        String mysqlCommand = String.format("create table if not exists '%s' ('temp' float, 'time' timestamp)", hostName);
//        ResultSet resultSet = mySQL.executeQueue(mysqlCommand);
//        resultSet.

        try {
            DatabaseMetaData databaseMetaData = mySQL.connection.getMetaData();
            ResultSet resultSet = databaseMetaData.getTables(null, null, hostName, null);
            if(resultSet.next()) {
                System.out.println("the table is existed");
            }
            else {
                System.out.println("the table is not exists");
                System.out.println("create new table named " + hostName);
                String mysqlCommand = String.format("create table %s("
                        + "temp int not null,"
                        + "cpu int not null,"
                        + "ram int not null,"
                        + "disk int not null,"
                        + "time timestamp not null);"
                        , hostName);
                System.out.println(mysqlCommand);
                if (mySQL.executeUpdate(mysqlCommand) == 0) {
                    System.out.println("create success");
                } else {
                    System.out.println("create fail");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String resultString;
        int temp;
        while (true) {
            resultString = FileTool.readFile("/sys/class/thermal/thermal_zone0/temp");
            if (resultString != null) {
                temp = Integer.parseInt(resultString);
                temp /= 1000;
                System.out.println(temp);
            }

            try {       //延时
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
