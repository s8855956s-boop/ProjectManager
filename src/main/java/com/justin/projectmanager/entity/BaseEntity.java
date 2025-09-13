package com.justin.projectmanager.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BaseEntity {

    private LocalDateTime createDate;

    private String createUser;

    private LocalDateTime modifyDate;

    private String modifyUser;
}
