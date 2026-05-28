package com.prompthub.user.dto;

import java.time.LocalDateTime;

public class UserDTO {

    private Long userId;
    private String id;
    private String passwordHash;
    private String nickname;
    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;

    public UserDTO() {
    }

    public UserDTO(Long userId, String id, String passwordHash, String nickname, LocalDateTime createdAt, LocalDateTime deletedAt) {
        this.userId = userId;
        this.id = id;
        this.passwordHash = passwordHash;
        this.nickname = nickname;
        this.createdAt = createdAt;
        this.deletedAt = deletedAt;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "userId=" + userId +
                ", id='" + id + '\'' +
                ", passwordHash='" + passwordHash + '\'' +
                ", nickname='" + nickname + '\'' +
                ", createdAt=" + createdAt +
                ", deletedAt=" + deletedAt +
                '}';
    }
}
