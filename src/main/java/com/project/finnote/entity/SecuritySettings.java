package com.project.finnote.entity;

import java.time.LocalDateTime;

public class SecuritySettings {
    private Integer settingId;
    private Integer userId;
    private Boolean backupRequired;
    private LocalDateTime lastBackup;

    public SecuritySettings(Integer settingId, Integer userId, Boolean backupRequired, LocalDateTime lastBackup) {
        this.settingId = settingId;
        this.userId = userId;
        this.backupRequired = backupRequired;
        this.lastBackup = lastBackup;
    }

    public Integer getSettingId() {
        return settingId;
    }

    public void setSettingId(Integer settingId) {
        this.settingId = settingId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Boolean getBackupRequired() {
        return backupRequired;
    }

    public void setBackupRequired(Boolean backupRequired) {
        this.backupRequired = backupRequired;
    }

    public LocalDateTime getLastBackup() {
        return lastBackup;
    }

    public void setLastBackup(LocalDateTime lastBackup) {
        this.lastBackup = lastBackup;
    }

    @Override
    public String toString() {
        return "SecuritySettings{" +
                "settingId=" + settingId +
                ", userId=" + userId +
                ", backupRequired=" + backupRequired +
                ", lastBackup=" + lastBackup +
                '}';
    }
}
