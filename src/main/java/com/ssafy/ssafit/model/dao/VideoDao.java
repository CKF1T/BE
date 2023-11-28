package com.ssafy.ssafit.model.dao;

import java.util.List;

import com.ssafy.ssafit.model.dto.SearchCondition;
import com.ssafy.ssafit.model.dto.Video;

public interface VideoDao {
	List<Video> selectAll();

	Video selectOne(Long idx);

	void updateViewCnt(Long idx);

	List<Video> search(SearchCondition condition);

    Video getRandomVideo();
}
