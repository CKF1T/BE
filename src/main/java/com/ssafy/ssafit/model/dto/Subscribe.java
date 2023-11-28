package com.ssafy.ssafit.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class Subscribe {

    private Long idx;
    private Long userIdx;
    private String fcmToken;
    private boolean activation;
    public Subscribe(Long userIdx, String fcmToken) {
        this.userIdx = userIdx;
        this.fcmToken = fcmToken;
    }

    public void setActive() {
        this.activation = true;
    }

    public void setInActive() {
        this.activation = false;
    }

    public void refreshToken(String newToken) {
        this.fcmToken = newToken;
    }
}
