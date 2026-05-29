package com.prompthub.user;

import com.prompthub.user.dao.PromptDAO;
import com.prompthub.user.dto.PromptDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import static com.prompthub.common.JDBCTemplate.close;
import static com.prompthub.common.JDBCTemplate.getConnection;

@WebServlet("/prompts")
public class PromptServlet extends HttpServlet {

    private final PromptDAO promptDAO = new PromptDAO();

    private String safe(String value) {
        if (value == null) return "";
        return value
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        resp.setHeader("Access-Control-Allow-Credentials", "true");
        resp.setContentType("application/json; charset=UTF-8");

        String userIdParam = req.getParameter("userId");

        if (userIdParam == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write(
                    "{"
                            + "\"success\":false,"
                            + "\"data\":[],"
                            + "\"message\":\"userId가 필요합니다.\""
                            + "}"
            );
            return;
        }

        Long userId = Long.parseLong(userIdParam);
        Connection con = getConnection();

        try {
            List<PromptDTO> prompts = promptDAO.selectPromptsByUserId(con, userId);

            StringBuilder data = new StringBuilder();
            data.append("[");

            for (int i = 0; i < prompts.size(); i++) {
                PromptDTO p = prompts.get(i);

                data.append(String.format(
                        "{"
                                + "\"promptId\":%d,"
                                + "\"title\":\"%s\","
                                + "\"description\":\"%s\","
                                + "\"content\":\"%s\","
                                + "\"viewCount\":%d,"
                                + "\"userId\":%d"
                                + "}",
                        p.getPromptId(),
                        safe(p.getTitle()),
                        safe(p.getDescription()),
                        safe(p.getContent()),
                        p.getViewCount(),
                        p.getUserId()
                ));

                if (i < prompts.size() - 1) {
                    data.append(",");
                }
            }

            data.append("]");

            resp.getWriter().write(
                    "{"
                            + "\"success\":true,"
                            + "\"data\":" + data + ","
                            + "\"message\":\"프롬프트 조회 성공\""
                            + "}"
            );

        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write(
                    "{"
                            + "\"success\":false,"
                            + "\"data\":[],"
                            + "\"message\":\"프롬프트 조회 실패\""
                            + "}"
            );
        } finally {
            close(con);
        }
    }
}
