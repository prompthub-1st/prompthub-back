package com.prompthub.user;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/auth/logout")
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        {

            HttpSession session = req.getSession(false);

            if (session != null) {
                session.invalidate();
            }

            resp.setContentType("application/json; charset=UTF-8");

            resp.getWriter().write(
                    "{"
                            + "\"success\":true,"
                            + "\"data\":null,"
                            + "\"message\":\"로그아웃 성공\""
                            + "}"
            );
        }
    }
}
