package com.prompthub.user.dao;

import com.prompthub.user.dto.PromptDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.prompthub.common.JDBCTemplate.close;

    public class PromptDAO {

        public List<PromptDTO> selectPromptsByUserId(Connection con, Long userId) {
            PreparedStatement pstmt = null;
            ResultSet rset = null;
            List<PromptDTO> prompts = new ArrayList<>();

            String query = "SELECT prompt_id, title, description, content, view_count, user_id "
                    + "FROM prompts "
                    + "WHERE user_id = ? "
                    + "AND deleted_at IS NULL";

            try {
                pstmt = con.prepareStatement(query);
                pstmt.setLong(1, userId);

                rset = pstmt.executeQuery();

                while (rset.next()) {
                    PromptDTO prompt = new PromptDTO();

                    prompt.setPromptId(rset.getLong("prompt_id"));
                    prompt.setTitle(rset.getString("title"));
                    prompt.setDescription(rset.getString("description"));
                    prompt.setContent(rset.getString("content"));
                    prompt.setViewCount(rset.getInt("view_count"));
                    prompt.setUserId(rset.getLong("user_id"));

                    prompts.add(prompt);
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                close(rset);
                close(pstmt);
            }

            return prompts;
        }
}
