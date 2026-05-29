package com.prompthub.prompt.dao;

import com.prompthub.prompt.dto.PromptDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.prompthub.common.JDBCTemplate.close;

public class PromptDAO {

    // 등록
    public int insertPrompt(Connection con, PromptDTO dto) {

        PreparedStatement pstmt = null;

        int result = 0;

        String sql =
                "INSERT INTO prompts(user_id, category_id, title, description, content) " +
                "VALUES (?, ?, ?, ?, ?)";

        try {
            pstmt = con.prepareStatement(sql);

            pstmt.setLong(1, dto.getUserId());
            pstmt.setLong(2, dto.getCategoryId());
            pstmt.setString(3, dto.getTitle());
            pstmt.setString(4, dto.getDescription());
            pstmt.setString(5, dto.getContent());

            result = pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);

        } finally {
            close(pstmt);
        }

        return result;
    }


    // 전체 조회
    public List<PromptDTO> selectAllPrompts(Connection con) {

        PreparedStatement pstmt = null;
        ResultSet rset = null;

        List<PromptDTO> promptList =
                new ArrayList<>();

        String sql =
                "SELECT * " +
                "FROM prompts " +
                "WHERE deleted_at IS NULL " +
                "ORDER BY created_at DESC";

        try {
            pstmt = con.prepareStatement(sql);
            rset = pstmt.executeQuery();

            while (rset.next()) {

                PromptDTO dto = new PromptDTO();

                dto.setPromptId(
                        rset.getLong("prompt_id")
                );

                dto.setUserId(
                        rset.getLong("user_id")
                );

                dto.setCategoryId(
                        rset.getLong("category_id")
                );

                dto.setTitle(
                        rset.getString("title")
                );

                dto.setDescription(
                        rset.getString("description")
                );

                dto.setContent(
                        rset.getString("content")
                );

                dto.setViewCount(
                        rset.getInt("view_count")
                );

                dto.setCreatedAt(
                        rset.getTimestamp("created_at")
                );

                dto.setUpdatedAt(
                        rset.getTimestamp("updated_at")
                );

                dto.setDeletedAt(
                        rset.getTimestamp("deleted_at")
                );

                promptList.add(dto);
            }
        } catch (SQLException e) {

            throw new RuntimeException(e);

        } finally {

            close(rset);
            close(pstmt);

        }

        return promptList;
    }


    // 상세 조회
    public PromptDTO selectPromptById(Connection con,
                                      Long promptId) {

        PreparedStatement pstmt = null;
        ResultSet rset = null;

        PromptDTO dto = null;

        String sql =
                "SELECT * " +
                "FROM prompts " +
                "WHERE prompt_id = ? " +
                "AND deleted_at IS NULL";

        try {

            pstmt = con.prepareStatement(sql);
            pstmt.setLong(1, promptId);

            rset = pstmt.executeQuery();

            if (rset.next()) {

                dto = new PromptDTO();

                dto.setPromptId(
                        rset.getLong("prompt_id")
                );

                dto.setUserId(
                        rset.getLong("user_id")
                );

                dto.setCategoryId(
                        rset.getLong("category_id")
                );

                dto.setTitle(
                        rset.getString("title")
                );

                dto.setDescription(
                        rset.getString("description")
                );

                dto.setContent(
                        rset.getString("content")
                );

                dto.setViewCount(
                        rset.getInt("view_count")
                );

                dto.setCreatedAt(
                        rset.getTimestamp("created_at")
                );

                dto.setUpdatedAt(
                        rset.getTimestamp("updated_at")
                );

                dto.setDeletedAt(
                        rset.getTimestamp("deleted_at")
                );
            }


        } catch (SQLException e) {

            throw new RuntimeException(e);

        } finally {

            close(rset);
            close(pstmt);

        }

        return dto;
    }


    // 수정
    public int updatePrompt(Connection con,
                            PromptDTO dto) {

        PreparedStatement pstmt = null;

        int result = 0;

        String sql =
                "UPDATE prompts " +
                "SET category_id = ?, " +
                "title = ?, " +
                "description = ?, " +
                "content = ? " +
                "WHERE prompt_id = ? " +
                "AND deleted_at IS NULL";

        try {

            pstmt = con.prepareStatement(sql);

            pstmt.setLong(1,
                    dto.getCategoryId());

            pstmt.setString(2,
                    dto.getTitle());

            pstmt.setString(3,
                    dto.getDescription());

            pstmt.setString(4,
                    dto.getContent());

            pstmt.setLong(5,
                    dto.getPromptId());

            result = pstmt.executeUpdate();

        } catch (SQLException e) {

            throw new RuntimeException(e);

        } finally {

            close(pstmt);

        }

        return result;
    }


    // 삭제 (soft delete)
    public int deletePrompt(Connection con,
                            Long promptId) {

        PreparedStatement pstmt = null;

        int result = 0;

        String sql =
                "UPDATE prompts " +
                "SET deleted_at = NOW() " +
                "WHERE prompt_id = ?";

        try {

            pstmt = con.prepareStatement(sql);
            pstmt.setLong(1, promptId);

            result = pstmt.executeUpdate();

        } catch (SQLException e) {

            throw new RuntimeException(e);

        } finally {

            close(pstmt);

        }

        return result;
    }



    // 조회수 증가
    public int increaseViewCount(Connection con,
                                 Long promptId) {

        PreparedStatement pstmt = null;

        int result = 0;

        String sql =
                "UPDATE prompts " +
                "SET view_count = view_count + 1 " +
                "WHERE prompt_id = ?";

        try {

            pstmt = con.prepareStatement(sql);
            pstmt.setLong(1, promptId);

            result = pstmt.executeUpdate();

        } catch (SQLException e) {

            throw new RuntimeException(e);

        } finally {

            close(pstmt);
        }

        return result;
    }
}
