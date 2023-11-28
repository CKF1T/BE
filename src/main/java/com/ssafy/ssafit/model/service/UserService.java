package com.ssafy.ssafit.model.service;

import com.ssafy.ssafit.model.dto.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

	void signup(User user);

	Optional<User> login(User user);

	Optional<User> findByIdx(Long idx);

	Optional<User> findByNickName(String nickName);

	void subscribe(User loginUser);

	void unsubscribe(User loginUser);

}