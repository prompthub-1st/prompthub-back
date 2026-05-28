package com.prompthub.user;

import com.prompthub.common.JDBCTemplate;

import java.sql.Connection;

public class ConncectionTest {
    public static void main(String[] args) {
        Connection con = JDBCTemplate.getConnection();

        if(con != null) {
            System.out.println("DB 연결 성공");
        }


    }

}
