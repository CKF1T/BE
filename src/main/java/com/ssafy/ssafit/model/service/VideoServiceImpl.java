package com.ssafy.ssafit.model.service;

import java.util.List;

import com.ssafy.ssafit.model.dto.SearchCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.ssafit.model.dao.VideoDao;
import com.ssafy.ssafit.model.dto.Video;

@Service
public class VideoServiceImpl implements VideoService {

	private final VideoDao videoDao;

	@Autowired
	public VideoServiceImpl(VideoDao videoDao) {
		this.videoDao = videoDao;
	}


	@Override
	public List<Video> getList() {
		return videoDao.selectAll();
	}

	@Override
	public Video getVideo(Long idx) {
		videoDao.updateViewCnt(idx);
		return videoDao.selectOne(idx);
	}

	@Override
	public List<Video> search(SearchCondition condition) {
		return videoDao.search(condition);
	}

	@Override
	public Video getRandomVideo() {
		return videoDao.getRandomVideo();
	}

}
