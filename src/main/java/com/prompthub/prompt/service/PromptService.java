package com.prompthub.prompt.service;

import com.prompthub.prompt.dao.PromptDAO;
import com.prompthub.prompt.dto.PromptDTO;

import java.sql.Connection;
import java.util.List;

import static com.prompthub.common.JDBCTemplate.close;
import static com.prompthub.common.JDBCTemplate.getConnection;

public class PromptService {

    private PromptDAO promptDAO = new PromptDAO();

    // 등록
    public int insertPrompt(PromptDTO dto) {
        Connection con = getConnection();

        int result = promptDAO.insertPrompt(con, dto);

        close(con);

        return result;
    }


    // 전체 조회
    public List<PromptDTO> selectAllPrompts() {
        Connection con = getConnection();

        List<PromptDTO> promptList =
                promptDAO.selectAllPrompts(con);

        close(con);

        return promptList;
    }


    // 상세 조회
    public PromptDTO selectPromptById(Long promptId) {
        Connection con = getConnection();

        PromptDTO dto =
                promptDAO.selectPromptById(con, promptId);

        close(con);

        return dto;
    }


    // 수정
    public int updatePrompt(PromptDTO dto) {
        Connection con = getConnection();
        int result =
                promptDAO.updatePrompt(con, dto);

        close(con);

        return result;
    }


    // 삭제
    public int deletePrompt(Long promptId) {
        Connection con = getConnection();
        int result =
                promptDAO.deletePrompt(con, promptId);

        close(con);

        return result;
    }


    // 조회수 증가
    public int increaseViewCount(Long promptId) {

        Connection con = getConnection();

        int result =
                promptDAO.increaseViewCount(con, promptId);

        close(con);

        return result;
    }
}
