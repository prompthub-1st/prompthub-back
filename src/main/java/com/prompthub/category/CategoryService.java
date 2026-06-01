package com.prompthub.category;

import com.prompthub.category.CategoryDAO;
import com.prompthub.category.CategoryDTO;


import java.sql.Connection;
import java.util.List;

import static com.prompthub.common.JDBCTemplate.close;
import static com.prompthub.common.JDBCTemplate.getConnection;

public class CategoryService {

    private final CategoryDAO categoryDAO = new CategoryDAO();

    public List<CategoryDTO> getAllCategories() {

        Connection con = getConnection();

        List<CategoryDTO> list = categoryDAO.selectAll(con);

        close(con);

        return list;
    }
}
