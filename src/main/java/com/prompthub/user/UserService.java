package com.prompthub.user;

import com.prompthub.user.dao.UserDAO;
import com.prompthub.user.dto.UserDTO;

import java.sql.Connection;
import java.util.List;

import static com.prompthub.common.JDBCTemplate.close;
import static com.prompthub.common.JDBCTemplate.getConnection;

public class UserService {

    private final UserDAO userDAO = new UserDAO();

    public List<UserDTO> findAllUsers(){
        Connection con = getConnection();

        try{
            return userDAO.selectUserById(con, );
        }finally {
            close(con);
        }
    }
}
