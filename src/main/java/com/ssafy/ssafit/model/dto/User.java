package com.ssafy.ssafit.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class User {

	private Long idx;
	private String password;
	private String nickName;
	private boolean subscribe;

	public void subscribe() {
		this.subscribe = true;
	}

	public void unsubscribe() {
		this.subscribe = false;
	}
	public boolean getSubscribe() {
		return this.subscribe;
	}
}
