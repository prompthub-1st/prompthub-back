package com.prompthub.prompt.controller;

import com.prompthub.prompt.service.PromptService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/api/prompts/delete")
public class PromptDeleteServlet extends HttpServlet {

    private final PromptService promptService =
            new PromptService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Long promptId =
                Long.parseLong(
                        req.getParameter("promptId")
                );

        int result = promptService.deletePrompt(promptId);

        resp.setContentType(
                "text/plain;charset=UTF-8"
        );

        if (result > 0) {
            resp.getWriter().print("삭제 성공");
        } else {
            resp.getWriter().print("삭제 실패");
        }
    }
}
