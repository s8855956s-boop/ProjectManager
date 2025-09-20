package com.justin.projectmanager.utils;

import com.justin.projectmanager.entity.AppUser;
import com.justin.projectmanager.entity.BaseEntity;

import java.time.LocalDateTime;

public class BaseEntityUtils {
    public static void setAuditFields(BaseEntity entity, boolean isNew) {
        LocalDateTime now = LocalDateTime.now();
        if (isNew) {
            entity.setCreateDate(now);
            entity.setCreateUser("NotSet");
        }
        entity.setModifyDate(now);
        entity.setModifyUser("NotSet");
    }
}
