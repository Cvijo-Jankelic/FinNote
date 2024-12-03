package com.project.finnote.builders;

import com.project.finnote.entity.SecuritySettings;

import java.time.LocalDateTime;

public class SecuritySettingsBuilder {
    private Integer settingId;
    private Integer userId;
    private Boolean backupRequired;
    private LocalDateTime lastBackup;

    public SecuritySettingsBuilder setSettingId(Integer settingId) {
        this.settingId = settingId;
        return this;
    }

    public SecuritySettingsBuilder setUserId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public SecuritySettingsBuilder setBackupRequired(Boolean backupRequired) {
        this.backupRequired = backupRequired;
        return this;
    }

    public SecuritySettingsBuilder setLastBackup(LocalDateTime lastBackup) {
        this.lastBackup = lastBackup;
        return this;
    }

    public SecuritySettings createSecuritySettings() {
        return new SecuritySettings(settingId, userId, backupRequired, lastBackup);
    }
}