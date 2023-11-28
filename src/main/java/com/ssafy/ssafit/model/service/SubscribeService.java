package com.ssafy.ssafit.model.service;

import com.ssafy.ssafit.model.dto.User;

import java.util.List;

public interface SubscribeService {
    void regist(Long userIdx, String fcmToken);

    void refreshToken(Long userIdx, String fcmToken);

    void activateSubscribe(Long userIdx);
    void inactivateSubscribe(Long userIdx);

    List<User> findSubscribers();

}
