package com.justin.projectmanager.dto.response;


import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class TaskResponse {
    private UUID id;
    private String name;
    private StatusResponse status;
}
