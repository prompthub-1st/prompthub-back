package com.prompthub.prompt.controller;

import com.prompthub.prompt.dto.PromptDTO;
import com.prompthub.prompt.service.PromptService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/api/prompts/update")
public class PromptUpdateServlet extends HttpServlet {

    private final PromptService promptService =
            new PromptService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        Long promptId =
                Long.parseLong(
                        req.getParameter("promptId")
                );

        Long categoryId =
                Long.parseLong(
                        req.getParameter("categoryId")
                );

        String title =
                req.getParameter("title");

        String description =
                req.getParameter("description");

        String content =
                req.getParameter("content");


        PromptDTO dto = new PromptDTO();

        dto.setPromptId(promptId);
        dto.setCategoryId(categoryId);
        dto.setTitle(title);
        dto.setDescription(description);
        dto.setContent(content);

        int result = promptService.updatePrompt(dto);

        resp.setContentType(
                "text/plain;charset=UTF-8"
        );

        if (result > 0) {
            resp.getWriter().print("수정 성공");
        } else {
            resp.getWriter().print("수정 실패");
        }
    }
}
