package com.ssafy.ssafit.model.service;

import com.ssafy.ssafit.model.dao.ReviewDao;
import com.ssafy.ssafit.model.dto.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ReviewServiceImpl implements ReviewService{


	private final ReviewDao reviewDao;

	@Autowired
	public ReviewServiceImpl(ReviewDao reviewDao) {
		this.reviewDao = reviewDao;
	}

	@Override
	public List<Review> getList(Long videoIdx) {
		return reviewDao.findAll();
	}

	@Override
	public void save(Review review) {
		reviewDao.save(review);
	}

	@Transactional(readOnly = true)
	public Review readReview(Long reviewIdx) {
		return reviewDao.findOne(reviewIdx);
	}

	@Override
	public void removeReview(Long idx) {
		reviewDao.delete(idx);
	}

	@Override
	public void updateReview(Review review) {
		reviewDao.update(review);
	}
}
