package com.ssafy.ssafit.controller;

import com.ssafy.ssafit.model.service.NotificationScheduler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationScheduler notificationScheduler;

    /**
     *
     * @ for test
     */
    @PostMapping("/api/fcm")
    public ResponseEntity pushMessage() {
        try {
            notificationScheduler.pushMorningWorkOutAlarm();

            log.info("notification sent successfully");
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            log.info("pushMessage exception {} : ", e.getMessage());
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }



}
