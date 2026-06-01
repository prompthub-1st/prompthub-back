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

@WebServlet("/api/prompts/delete")
public class PromptDeleteServlet extends HttpServlet {

    private final PromptService promptService =
            new PromptService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        UserDTO loginUser = (UserDTO) session.getAttribute("loginUser");

        if (loginUser == null) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.getWriter().write("{\"message\":\"LOGIN_REQUIRED\"}");
            return;
        }

        Long promptId = Long.parseLong(req.getParameter("promptId"));

        PromptDTO dto = promptService.selectPromptById(promptId);

        if (!dto.getUserId().equals((loginUser.getUserId()))) {
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
            resp.getWriter().write("{\"message\":\"NO_PERMISSION\"}");
            return;
        }



        int result = promptService.deletePrompt(promptId);

        resp.setContentType("application/json;charset=UTF-8");

        if (result > 0) {
            resp.getWriter().write(
                    "{\"success\":true,\"message\":\"삭제 성공\"}"
            );
        } else {
            resp.getWriter().write(
                    "{\"success\":false,\"message\":\"삭제 실패\"}"
            );
        }
    }
}
