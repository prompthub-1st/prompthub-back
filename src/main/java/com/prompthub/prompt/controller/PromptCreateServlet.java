package com.prompthub.prompt.controller;

import com.prompthub.prompt.dto.PromptDTO;
import com.prompthub.prompt.service.PromptService;
import com.prompthub.user.dto.UserDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/api/prompts/create")
public class PromptCreateServlet extends HttpServlet {

    private final PromptService promptService =
            new PromptService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        HttpSession session = req.getSession(false);
        UserDTO loginUser = (UserDTO) session.getAttribute("loginUser");

        if (loginUser == null) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.setContentType("application/json;charset=UTF-8");
            resp.getWriter().write("{\"success\":false,\"message\":\"LOGIN_REQUIRED\"}");
            return;
        }


        String title = req.getParameter("title");
        String description = req.getParameter("description");
        String content = req.getParameter("content");

        Long categoryId = Long.parseLong(req.getParameter("categoryId"));

        PromptDTO dto = new PromptDTO();

        dto.setUserId(loginUser.getUserId());

        dto.setCategoryId(categoryId);
        dto.setTitle(title);
        dto.setDescription(description);
        dto.setContent(content);

        int result = promptService.insertPrompt(dto);

        resp.setContentType("application/json;charset=UTF-8");

        if (result > 0) {
            resp.getWriter().write(
                    "{\"success\":true,\"message\":\"글쓰기 성공\"}"
            );
        } else {
            resp.getWriter().write(
                    "{\"success\":false,\"message\":\"글쓰기 실패\"}"
            );
        }
    }
}
