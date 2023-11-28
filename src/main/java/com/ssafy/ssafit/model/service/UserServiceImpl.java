package com.ssafy.ssafit.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.ssafit.model.dao.UserDao;
import com.ssafy.ssafit.model.dto.User;

import java.util.List;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {

	private final UserDao userDao;

	@Autowired
	public UserServiceImpl(UserDao userDao) {
		this.userDao = userDao;
	}


	@Override
	public void signup(User user) {
		userDao.save(user);
	}


	@Override
	public Optional<User> login(User user) {
		List<User> users = userDao.findAllByNickName(user.getNickName());

		return users.stream()
					.filter(u -> u.getPassword().equals(user.getPassword()))
					.findAny();

	}

	@Override
	public Optional<User> findByIdx(Long idx) {
		return userDao.findByIdx(idx);
	}

	@Override
	public Optional<User> findByNickName(String nickName) {
		return userDao.findByNickName(nickName);
	}

	@Override
	public void subscribe(User loginUser) {
		Optional<User> tmp = userDao.findByIdx(loginUser.getIdx());
		if(tmp.isPresent()) {
			User user = tmp.get();
			user.subscribe();
			userDao.update(user);
		} else {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public void unsubscribe(User loginUser) {
		Optional<User> tmp = userDao.findByIdx(loginUser.getIdx());
		if(tmp.isPresent()) {
			User user = tmp.get();
			user.unsubscribe();
			userDao.update(user);
		} else {
			throw new IllegalArgumentException();
		}
	}

}
