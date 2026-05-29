package com.prompthub.user.dao;

import com.prompthub.user.dto.UserDTO;

import java.sql.*;

import static com.prompthub.common.JDBCTemplate.close;

public class UserDAO {
    public UserDTO selectUserById(Connection con, String id) {
        PreparedStatement pstmt = null;
        ResultSet rset = null;
        UserDTO user = null;

        String query = "SELECT user_id, login_id, password_hash, nickname, created_at, deleted_at "
                + "FROM users "
                + "WHERE login_id = ? "
                +"AND deleted_at IS NULL ";

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, id);
            rset = pstmt.executeQuery();

            if(rset.next()){
                user = new UserDTO();

                user.setUserId(rset.getLong("user_id"));
                user.setLoginId(rset.getString("login_id"));
                user.setPasswordHash(rset.getString("password_hash"));
                user.setNickname(rset.getString("nickname"));
                user.setCreatedAt(rset.getTimestamp("created_at").toLocalDateTime());
                Timestamp deletedAt = rset.getTimestamp("deleted_at");

                if(deletedAt != null){
                    user.setDeletedAt(deletedAt.toLocalDateTime());
                }else{
                    user.setDeletedAt(null);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally{
            close(rset);
            close(pstmt);
        }
        return user;
    }
}
