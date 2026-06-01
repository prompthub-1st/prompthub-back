package com.prompthub.category;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prompthub.category.CategoryService;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/api/categories")
public class CategoryServlet extends HttpServlet {

    private final CategoryService categoryService = new CategoryService();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        resp.setContentType("application/json;charset=utf-8");

        try {
            List<CategoryDTO> list = categoryService.getAllCategories();

            Map<String, Object> result = new HashMap<>();
            result.put("success", true);
            result.put("data", list);

            resp.getWriter().write(
                    objectMapper.writeValueAsString(result)
            );

        } catch (Exception e) {

            resp.setStatus(500);

            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", "카테고리 조회 실패");

            resp.getWriter().write(
                    objectMapper.writeValueAsString(error)
            );
        }
    }
}