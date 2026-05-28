package com.prompthub;

import com.prompthub.common.JDBCTemplate;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/test")
public class TestServlet extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {

            conn = JDBCTemplate.getConnection();

            String sql =
                    "SELECT " +
                            "    prompt_id, " +
                            "    title " +
                            "FROM prompts " +
                            "ORDER BY prompt_id";

            pstmt = conn.prepareStatement(sql);

            rs = pstmt.executeQuery();

            out.println("<h1>프롬프트 목록</h1>");

            while (rs.next()) {

                long promptId = rs.getLong("prompt_id");

                String title = rs.getString("title");

                out.println(promptId + " : " + title + "<br>");
            }

        } catch (Exception e) {

            e.printStackTrace();

            out.println("<h1>에러 발생</h1>");

        } finally {

            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}