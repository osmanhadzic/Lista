package com.company;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connector {
    static Connection conn;



    public static Connection getConnection(){
        String url ="jdbc:mysql://localhost:3306/sakila?useSSL=false&serverTimezone=Asia/Shanghai";
        String name ="root";
        String password = "konjuh123";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url,name,password);
            System.out.println("Conneted");
            return conn;

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
