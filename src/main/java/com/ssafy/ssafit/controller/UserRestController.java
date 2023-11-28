package com.ssafy.ssafit.controller;

import javax.servlet.http.HttpServletRequest;

import com.ssafy.ssafit.common.auth.JwtTokenProvider;
import com.ssafy.ssafit.common.request.dto.AuthRequestDto;
import com.ssafy.ssafit.model.dto.AuthVo;
import com.ssafy.ssafit.model.service.SubscribeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ssafy.ssafit.model.dto.User;
import com.ssafy.ssafit.model.service.UserService;


@RestController
@RequestMapping("/users")
@Api(tags = "사용자 관리 api")
@RequiredArgsConstructor
public class UserRestController {

	private final UserService userService;
	private final SubscribeService subscribeService;

	@Value("${JWT.secret.key}")
	String secretKey;

	@PostMapping("signup")
	@ApiOperation(value = "회원 가입")
	public ResponseEntity<Void> signup(@RequestBody AuthRequestDto authRequestDto) {
		if(authRequestDto.getUser().getNickName().isEmpty() || authRequestDto.getUser().getPassword().isEmpty()) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
		userService.signup(authRequestDto.getUser());
		subscribeService.regist(authRequestDto.getUser().getIdx(), authRequestDto.getFcmToken());
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@PostMapping("login")
	@ApiOperation(value = "로그인")
	public ResponseEntity<AuthVo> login(@RequestBody AuthRequestDto authRequestDto) {
		if(authRequestDto.getUser().getNickName().isEmpty() || authRequestDto.getUser().getPassword().isEmpty()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		User tmp = userService.login(authRequestDto.getUser())
				.orElseThrow(() -> new IllegalArgumentException("가입되지 않은 회원입니다."));
		AuthVo authVo = new AuthVo(tmp.getIdx(), tmp.getNickName(), JwtTokenProvider.createToken(tmp.getNickName(), secretKey));
		subscribeService.refreshToken(tmp.getIdx(), authRequestDto.getFcmToken());
		subscribeService.activateSubscribe(tmp.getIdx());
		return new ResponseEntity<>(authVo, HttpStatus.OK);
	}
	
	@PostMapping("logout")
	@ApiOperation(value = "로그아웃")
	public ResponseEntity<Void> logout(@RequestBody AuthRequestDto authRequestDto) {
		subscribeService.inactivateSubscribe(authRequestDto.getUser().getIdx());
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping("subscribe")
	@ApiOperation(value = "구독 신청")
	public ResponseEntity<Void> subscribe(@RequestBody AuthRequestDto authRequestDto, HttpServletRequest request) {
		if(request.getHeader("loginUser") == null || JwtTokenProvider.isExpired(request.getHeader("loginUser"), secretKey)) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		User loginUser = userService.findByNickName(JwtTokenProvider.getLoginId(request.getHeader("loginUser"), secretKey)).get();
		userService.subscribe(loginUser);
		subscribeService.regist(authRequestDto.getUser().getIdx(), authRequestDto.getFcmToken());
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping("unsubscribe")
	@ApiOperation(value = "구독 해제")
	public ResponseEntity<Void> unsubscribe(HttpServletRequest request) {
		if(request.getHeader("loginUser") == null || JwtTokenProvider.isExpired(request.getHeader("loginUser"), secretKey)) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		User loginUser = userService.findByNickName(JwtTokenProvider.getLoginId(request.getHeader("loginUser"), secretKey)).get();
		userService.unsubscribe(loginUser);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}