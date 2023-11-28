package com.ssafy.ssafit.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SearchCondition {
    private String key = "none";
    private String inputString;

    public void setKey(String key) {
        this.key = key;
    }

    public void setinputString(String inputString) {
        this.inputString = inputString;
    }

}
