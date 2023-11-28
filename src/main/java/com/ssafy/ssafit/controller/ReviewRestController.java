package com.ssafy.ssafit.controller;


import java.util.List;
import java.util.Objects;

import com.ssafy.ssafit.common.auth.JwtTokenProvider;
import com.ssafy.ssafit.model.dto.User;
import com.ssafy.ssafit.model.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.ssafy.ssafit.model.dto.Review;
import com.ssafy.ssafit.model.service.ReviewService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@Validated
@RequestMapping("/reviews")
@Api(tags = "리뷰 관리 api")
@RequiredArgsConstructor
public class ReviewRestController {

	private final ReviewService reviewService;
	private final UserService userService;
	@Value("${JWT.secret.key}")
	String secretKey;

	@PostMapping
	@ApiOperation(value = "리뷰 등록")
	public ResponseEntity<Review> createReview(@RequestBody Review review) {
		reviewService.save(review);
		return new ResponseEntity<>(review, HttpStatus.CREATED);
	}

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "리뷰 리스트 조회")
	public List<Review> readReviewList(@RequestParam Long videoIdx) {
		return reviewService.getList(videoIdx);
	}

	@GetMapping("/{idx}")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "특정 리뷰 조회")
	public Review readReview(@PathVariable Long idx) {
		return reviewService.readReview(idx);
	}

	@PostMapping(value = "/{idx}")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "리뷰 수정")
	public ResponseEntity<Review> updateReview(@PathVariable Long idx, @RequestBody Review review, HttpServletRequest request) {

		if(request.getHeader("loginUser") == null || JwtTokenProvider.isExpired(request.getHeader("loginUser"), secretKey)) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		User loginUser = userService.findByNickName(JwtTokenProvider.getLoginId(request.getHeader("loginUser"), secretKey)).get();
		Review targetReview = reviewService.readReview(idx);
		if(!Objects.equals(targetReview.getUserIdx(), loginUser.getIdx())) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		targetReview.updateContent(review.getContent());
		reviewService.updateReview(targetReview);
		return new ResponseEntity<>(targetReview, HttpStatus.OK);
	}

	@DeleteMapping("/{idx}")
	@ApiOperation(value = "리뷰 삭제")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity deleteReview(@PathVariable Long idx, HttpServletRequest request) {
		if(request.getHeader("loginUser") == null || JwtTokenProvider.isExpired(request.getHeader("loginUser"), secretKey)) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		User loginUser = userService.findByNickName(JwtTokenProvider.getLoginId(request.getHeader("loginUser"), secretKey)).get();
		Review review = reviewService.readReview(idx);
		if(!Objects.equals(review.getUserIdx(), loginUser.getIdx())) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		reviewService.removeReview(idx);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
