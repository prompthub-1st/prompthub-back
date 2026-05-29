package com.prompthub.user;

import com.prompthub.user.dto.UserDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/auth/me")
public class MyServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        resp.setHeader("Access-Control-Allow-Credentials", "true");
        resp.setContentType("application/json; charset=UTF-8");

        HttpSession session = req.getSession(false);

        if (session == null || session.getAttribute("loginUser") == null) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.setContentType("application/json; charset=UTF-8");

            resp.getWriter().write(
                    "{"
                            + "\"success\":false,"
                            + "\"data\":null,"
                            + "\"message\":\"로그인이 필요합니다.\""
                            + "}"
            );
            return;
        }

        UserDTO loginUser = (UserDTO) session.getAttribute("loginUser");

        resp.setContentType("application/json; charset=UTF-8");

        resp.getWriter().write(String.format(
                "{"
                        + "\"success\":true,"
                        + "\"data\":{"
                        + "\"userId\":%d,"
                        + "\"loginId\":\"%s\","
                        + "\"nickname\":\"%s\""
                        + "},"
                        + "\"message\":\"로그인 사용자 조회 성공\""
                        + "}",
                loginUser.getUserId(),
                loginUser.getLoginId(),
                loginUser.getNickname()
        ));

    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        resp.setHeader("Access-Control-Allow-Credentials", "true");
        resp.setHeader("Access-Control-Allow-Methods", "GET, OPTIONS");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
