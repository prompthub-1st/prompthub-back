package com.prompthub.category;

import com.prompthub.category.CategoryDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static com.prompthub.common.JDBCTemplate.close;

public class CategoryDAO {

    public List<CategoryDTO> selectAll(Connection con) {

        PreparedStatement pstmt = null;
        ResultSet rset = null;

        List<CategoryDTO> list = new ArrayList<>();

        String sql = "SELECT category_id, name FROM categories ORDER BY category_id";

        try {
            pstmt = con.prepareStatement(sql);
            rset = pstmt.executeQuery();

            while (rset.next()) {
                CategoryDTO dto = new CategoryDTO();
                dto.setCategoryId(rset.getLong("category_id"));
                dto.setName(rset.getString("name"));

                list.add(dto);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);

        } finally {
            close(rset);
            close(pstmt);
        }

        return list;
    }
}