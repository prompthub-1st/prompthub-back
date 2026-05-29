package com.prompthub.prompt.controller;

import com.prompthub.prompt.dto.PromptDTO;
import com.prompthub.prompt.service.PromptService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

@WebServlet("/api/prompts/list")
public class PromptListServlet extends HttpServlet {

    private final PromptService promptService =
            new PromptService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<PromptDTO> promptList =
                promptService.selectAllPrompts();

        resp.setContentType(
                "application/json;charset=UTF-8"
        );

        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.writeValue(
                resp.getWriter(),
                promptList
        );


    }
}
