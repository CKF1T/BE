package com.ssafy.ssafit.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RequestPushMessage {

    private pushMessageConstant data;
    private Video video;

    @Getter
    @AllArgsConstructor
    public enum pushMessageConstant {

        MORNING("좋은 아침입니다!", "하루를 여는 이런 운동 어떠세요?"),
        LUNCH("점심 식사 드셨나요?", "소화를 위해 가볍게 몸풀기 어떠세요?"),
        DINNER("자기 전에 운동 하셨나요?", "하루를 마무리 하기 전, 이런 운동 어떠세요?")
        ;

        final String title;
        final String body;
    }
}