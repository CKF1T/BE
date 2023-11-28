package com.ssafy.ssafit.model.dto;

import lombok.Getter;

@Getter
public class AuthVo {

    Long idx;
    String nickName;
    String token;

    public AuthVo(Long idx, String nickName, String token) {
        this.idx = idx;
        this.nickName = nickName;
        this.token = token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
