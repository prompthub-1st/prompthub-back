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
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;

@WebServlet("/api/prompts/detail")
public class PromptDetailServlet extends HttpServlet {

    private final PromptService promptService =
            new PromptService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Long promptId = Long.parseLong(req.getParameter("id"));

        // 게시글 조회
        PromptDTO dto = promptService.selectPromptById(promptId);

        HttpSession session = req.getSession(false);
        UserDTO loginUser = (session != null)
                ? (UserDTO) session.getAttribute("loginUser")
                : null;

        Long loginUserId = (loginUser != null)
                ? loginUser.getUserId()
                : null;

        // 작성자가 아니면 조회수 증가
        if (loginUserId == null || !dto.getUserId().equals(loginUserId)) {
            promptService.increaseViewCount(promptId);
            // 증가된 조회수 반영 위해 재조회
            dto = promptService.selectPromptById(promptId);
        }

        resp.setContentType("application/json;charset=UTF-8");

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(resp.getWriter(), dto);

    }
}
