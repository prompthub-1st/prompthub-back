package com.prompthub.user.dto;

public class LoginRequestDTO {
    private String loginId;
    private String password;

    public String getLoginId() { return loginId; }
    public String getPassword() { return password; }

    public void setLoginId(String loginId) { this.loginId = loginId; }
    public void setPassword(String password) { this.password = password; }
}
