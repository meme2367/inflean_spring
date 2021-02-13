package com.project.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter @Setter
public class ResponseDTO<T> {
    private int statusCode;
    private String message;
    private T data;
}