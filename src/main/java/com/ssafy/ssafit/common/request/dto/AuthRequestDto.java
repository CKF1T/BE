package com.ssafy.ssafit.common.request.dto;

import com.ssafy.ssafit.model.dto.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class AuthRequestDto {

    private User user;
    private String fcmToken;

}
