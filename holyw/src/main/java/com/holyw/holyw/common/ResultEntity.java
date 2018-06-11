package com.holyw.holyw.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;
import java.util.Map;

public class ResultEntity extends ResponseEntity{


    public ResultEntity(HttpStatus status) {
        super(status);
    }

    public ResultEntity(@Nullable Object body, HttpStatus status) {
        super(body, status);
    }

    public ResultEntity(MultiValueMap headers, HttpStatus status) {
        super(headers, status);
    }

    public ResultEntity(@Nullable Object body, @Nullable MultiValueMap headers, HttpStatus status) {
        super(body, headers, status);
    }

    public static ResultEntity result(String message, Object data, HttpStatus status) {
        Map<String,Object> map = new HashMap<>();
        map.put("message",message);
        map.put("result",status);
        map.put("data",data);
        return new ResultEntity(map, HttpStatus.OK);
    }

}
