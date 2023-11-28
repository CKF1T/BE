package com.ssafy.ssafit.model.service;

import com.google.firebase.messaging.*;
import com.ssafy.ssafit.model.dao.SubscribeDao;
import com.ssafy.ssafit.model.dao.UserDao;
import com.ssafy.ssafit.model.dao.VideoDao;
import com.ssafy.ssafit.model.dto.RequestPushMessage;
import com.ssafy.ssafit.model.dto.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.ssafy.ssafit.model.dto.RequestPushMessage.pushMessageConstant.*;


@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationScheduler {

    private final FirebaseMessaging instance = FirebaseMessaging.getInstance();

    private final VideoDao videoDao;
    private final SubscribeService subscribeService;
    private final UserDao userDao;
    private final SubscribeDao subscribeDao;

    @Scheduled(cron = "0 0 09 * * ?")
    public void pushMorningWorkOutAlarm() throws FirebaseMessagingException {
        log.info("아침 운동 알림");
        RequestPushMessage requestPushMessage = new RequestPushMessage(MORNING, videoDao.getRandomVideo());
        pushAlarm(requestPushMessage);
    }

    @Scheduled(cron = "0 0 13 * * ?")
    public void pushLunchWorkOutAlarm() throws FirebaseMessagingException {
        log.info("점심 운동 알림");
        RequestPushMessage requestPushMessage = new RequestPushMessage(LUNCH, videoDao.getRandomVideo());
        pushAlarm(requestPushMessage);
    }

    @Scheduled(cron = "0 0 19 * * ?")
    public void pushDinnerWorkOutAlarm() throws FirebaseMessagingException {
        log.info("저녁 운동 알림");
        RequestPushMessage requestPushMessage = new RequestPushMessage(DINNER, videoDao.getRandomVideo());
        pushAlarm(requestPushMessage);
    }

    private MulticastMessage pushAlarm(RequestPushMessage data) throws FirebaseMessagingException {

        MulticastMessage message = getMessage(data);
        sendMessage(message);
        return message;
    }

    private MulticastMessage getMessage(RequestPushMessage data) {

        MulticastMessage.Builder builder = MulticastMessage.builder();
        List<User> subscribers = subscribeService.findSubscribers();
        log.info("subscribers {}: ", subscribers.size());
        List<User> filteredSubscribers = subscribers.stream().filter(s -> userDao.findByIdx(s.getIdx()).get().getSubscribe()).collect(Collectors.toList());
        log.info("filteredSubscribers {}: ", filteredSubscribers.size());
        List<String> tokens = filteredSubscribers.stream().map(f -> subscribeDao.getFcmTokenByUserIdx(f.getIdx())).collect(Collectors.toList());
        log.info("token {}: ", tokens.size());

        Long videoIdx = data.getVideo().getIdx();
        String link = "http://localhost:5173/video/" + videoIdx;

        return builder
                .putData("title", data.getData().getTitle())
                .putData("body", data.getData().getBody())
                .putData("url", data.getVideo().getUrl())
                .putData("link", link)
                .addAllTokens(tokens)
                .build();
    }

    public BatchResponse sendMessage(MulticastMessage message) throws FirebaseMessagingException {
        return this.instance.sendMulticast(message);
    }
}
