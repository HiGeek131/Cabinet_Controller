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
import java.sql.*;

public class MySQL {
    Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;
    private String dbUrl, dbUser, dbPasswd;

    MySQL(String url, String user, String passwd) {
        dbUrl = url;
        dbUser = user;
        dbPasswd = passwd;
    }

    boolean connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("connect to mysql");
            connection = DriverManager.getConnection(dbUrl, dbUser, dbPasswd);
            statement = connection.createStatement();
            return true;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    boolean isConnect() {
        try {
            if (connection.isClosed()) {
                return false;
            } else {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    void close() {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    ResultSet executeQueue(String sqlExecute) {
        boolean states;
        try {
            resultSet = statement.executeQuery(sqlExecute);
            return resultSet;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    int executeUpdate(String sqlExecute) {
        try {
            return statement.executeUpdate(sqlExecute);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
