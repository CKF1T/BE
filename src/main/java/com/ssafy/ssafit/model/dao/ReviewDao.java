package com.ssafy.ssafit.model.dao;

import com.ssafy.ssafit.model.dto.Review;

import java.util.List;

public interface ReviewDao {
	public List<Review> findAll();

	public Review findOne(Long idx);

	public void save(Review review);

	public void delete(Long idx);

	public void update(Review review);

}
