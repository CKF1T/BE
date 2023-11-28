package com.ssafy.ssafit.model.dao;

import com.ssafy.ssafit.model.dto.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {

	void save(User user);

	List<User> findAllByNickName(String nickname);

    Optional<User> findByIdx(Long idx);

	Optional<User> findByNickName(String nickName);

	void update(User user);
}