package com.justin.projectmanager.utils;

import com.justin.projectmanager.dto.TaskItem;
import com.justin.projectmanager.dto.response.StatusResponse;
import com.justin.projectmanager.dto.response.TaskResponse;
import org.springframework.beans.BeanUtils;

import java.util.List;

public class TaskUtils {
    public static List<TaskResponse> transferTaskItemListToTaskResponse(List<TaskItem> taskItemList) {
        return taskItemList.stream().map(item -> {
            TaskResponse taskResponse = new TaskResponse();
            BeanUtils.copyProperties(item, taskResponse);

            // 手動處理關聯的 status
            if (item.getProjectStatus() != null) {
                StatusResponse statusResponse = new StatusResponse();
                BeanUtils.copyProperties(item.getProjectStatus(), statusResponse);
                taskResponse.setStatus(statusResponse);
            }
            return taskResponse;
        }).toList();
    }

    public static TaskResponse transferTaskItemToTaskResponse(TaskItem taskItem) {
            TaskResponse taskResponse = new TaskResponse();
            BeanUtils.copyProperties(taskItem, taskResponse);

            // 手動處理關聯的 status
            if (taskItem.getProjectStatus() != null) {
                StatusResponse statusResponse = new StatusResponse();
                BeanUtils.copyProperties(taskItem.getProjectStatus(), statusResponse);
                taskResponse.setStatus(statusResponse);
            }
            return taskResponse;
    }
}
