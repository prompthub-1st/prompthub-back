package com.prompthub.user;

import com.prompthub.user.dao.UserDAO;
import com.prompthub.user.dto.UserDTO;

import java.sql.Connection;
import java.util.List;

import static com.prompthub.common.JDBCTemplate.close;
import static com.prompthub.common.JDBCTemplate.getConnection;

// id 값 받아와서 있는지 없는지 확인
public class UserService {

    private final UserDAO userDAO = new UserDAO();

    public UserDTO findUser(String id){
        Connection con = getConnection();

        try{
            return userDAO.selectUserById(con, id);
        }finally {
            close(con);
        }
    }
}
