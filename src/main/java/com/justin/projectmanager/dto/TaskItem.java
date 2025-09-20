package com.justin.projectmanager.dto;

import java.util.UUID;

public interface TaskItem {
    UUID getId();
    String getName();
    StatusInfo getProjectStatus();

    interface StatusInfo {
        UUID getId();
        String getName();
    }
}
