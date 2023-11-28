package com.ssafy.ssafit.common.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@Configuration
@Slf4j
public class FirebaseConfig {

    @Value("${firebase-create-scoped}")
    String fireBaseCreateScoped;


    @Value("${firebase.config}")
    private String firebaseSdkPath;

    @Getter
    private FirebaseMessaging instance;

    @PostConstruct
    public void firebaseSetting() throws Exception {
        try {
            GoogleCredentials googleCredentials = GoogleCredentials.fromStream(new ClassPathResource("firebase/" + firebaseSdkPath).getInputStream())
                    .createScoped((Arrays.asList(fireBaseCreateScoped)));
            FirebaseOptions secondaryAppConfig = FirebaseOptions.builder()
                    .setCredentials(googleCredentials)
                    .build();
            FirebaseApp app = FirebaseApp.initializeApp(secondaryAppConfig);
            this.instance = FirebaseMessaging.getInstance(app);
            log.info("Firebase initialized successfully.");

        } catch (Exception e) {
            log.error("Firebase initialize Exception: {}", e.getMessage());
        }
    }
}
