package com.ssafy.ssafit.model.service;

import com.ssafy.ssafit.model.dao.SubscribeDao;
import com.ssafy.ssafit.model.dao.UserDao;
import com.ssafy.ssafit.model.dto.Subscribe;
import com.ssafy.ssafit.model.dto.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubscribeServiceImpl implements SubscribeService {

    private final SubscribeDao subscribeDao;
    private final UserDao userDao;
    @Override
    public void regist(Long userIdx, String fcmToken) {
        Subscribe subscribe = new Subscribe(userIdx, fcmToken);
        subscribeDao.regist(subscribe);
    }

    @Override
    public void refreshToken(Long userIdx, String fcmToken) {
        Subscribe subscribe = subscribeDao.findByUserIdx(userIdx).get();
        if(!subscribe.getFcmToken().equals(fcmToken)) {
            subscribe.refreshToken(fcmToken);
            subscribeDao.update(subscribe);
        }
    }

    @Override
    public void activateSubscribe(Long userIdx) {
        Optional<Subscribe> tmp = subscribeDao.findByUserIdx(userIdx);
        Subscribe subscribe = tmp.get();
        subscribe.setActive();
        subscribeDao.update(subscribe);
    }

    @Override
    public void inactivateSubscribe(Long userIdx) {
        Optional<Subscribe> tmp = subscribeDao.findByUserIdx(userIdx);
        Subscribe subscribe = tmp.get();
        subscribe.setInActive();
        log.info("subscribe {}", subscribe.toString());
        subscribeDao.update(subscribe);
    }

    @Override
    public List<User> findSubscribers() {
        List<Long> subscriberIdxs = subscribeDao.findSubscribers();
        return subscriberIdxs.stream().map(idx -> userDao.findByIdx(idx).get()).collect(Collectors.toList());
    }

}
