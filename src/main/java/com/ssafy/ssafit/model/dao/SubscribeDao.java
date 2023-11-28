package com.ssafy.ssafit.model.dao;

import com.ssafy.ssafit.model.dto.Subscribe;
import com.ssafy.ssafit.model.dto.User;

import java.util.List;
import java.util.Optional;

public interface SubscribeDao {
    void regist(Subscribe subscribe);

    Optional<Subscribe> findByUserIdx(Long userIdx);

    void update(Subscribe subscribe);

    List<Long> findSubscribers();

    String getFcmTokenByUserIdx(Long userIdx);

}
