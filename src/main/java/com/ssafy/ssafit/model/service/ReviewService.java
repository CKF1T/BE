package com.ssafy.ssafit.model.service;

import com.ssafy.ssafit.model.dto.Review;

import java.util.List;

public interface ReviewService {
	List<Review> getList(Long videoIdx);

	void save(Review review);

	Review readReview(Long idx);

	void removeReview(Long idx);

	void updateReview(Review review);

}
