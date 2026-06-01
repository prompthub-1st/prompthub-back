package com.prompthub.user;

import com.prompthub.user.dto.UserDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/auth/login")
public class LoginServlet extends HttpServlet {

    private final UserService userService = new UserService();

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Access-Control-Allow-Origin", "http://localhost:3000/");
        resp.setHeader("Access-Control-Allow-Credentials", "true");
        resp.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //CORS 해결
        resp.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        resp.setHeader("Access-Control-Allow-Credentials", "true");

        try{
            // 1. 요청 인코딩 설정
            req.setCharacterEncoding("UTF-8");

            // 2. 프론트에서 보낸 id/password 받기
            String id = req.getParameter("id");
            String password = req.getParameter("password");

            System.out.println("[Login] Accpeted Request : " + id);

            // 3. userService.findUser(id)호출
            UserDTO user = userService.findUser(id);
            System.out.println("[Login] Result: " + user);

            // 4. user == null 확인
            resp.setContentType("text/plain; charset=UTF-8");

            if(user == null) {
                System.out.println("[LOGIN] Id is not a possible : " + id);
                resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                resp.getWriter().write("USER_NOT_FOUND");
                resp.setContentType("application/json; charset=UTF-8");

                resp.getWriter().write(
                        "{"
                                + "\"success\":false,"
                                + "\"data\":null,"
                                + "\"message\":\"존재하지 않는 사용자입니다.\""
                                + "}"
                );
                return;
            }

            if(!user.getPasswordHash().equals(password)){
                System.out.println("[LOGIN] password failed : " + id);
                resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                resp.setContentType("application/json; charset=UTF-8");

                resp.getWriter().write(
                        "{"
                                + "\"success\":false,"
                                + "\"data\":null,"
                                + "\"message\":\"비밀번호가 일치하지 않습니다.\""
                                + "}"
                );
                return;
            }

            HttpSession session = req.getSession();
            session.setAttribute("loginUser", user);

            System.out.println("[LOGIN] Success - Session Saved : " + user.getLoginId());

            resp.setContentType("application/json; charset=UTF-8");
            resp.getWriter().write(String.format(
                    "{"
                            + "\"success\":true,"
                            + "\"data\":{"
                            + "\"userId\":%d,"
                            + "\"loginId\":\"%s\","
                            + "\"nickname\":\"%s\""
                            + "},"
                            + "\"message\":\"로그인 성공\""
                            + "}",
                    user.getUserId(),
                    user.getLoginId(),
                    user.getNickname()
            ));

        }catch (Exception e){
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.setContentType("application/json; charset=UTF-8");

            resp.getWriter().write(
                    "{"
                            + "\"success\":false,"
                            + "\"data\":null,"
                            + "\"message\":\"서버 오류가 발생했습니다.\""
                            + "}"
            );
        }



    }
}