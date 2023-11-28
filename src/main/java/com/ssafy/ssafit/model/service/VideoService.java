package com.ssafy.ssafit.model.service;

import java.util.List;

import com.ssafy.ssafit.model.dto.SearchCondition;
import com.ssafy.ssafit.model.dto.Video;

public interface VideoService {
	List<Video> getList();

	Video getVideo(Long idx);

	List<Video> search(SearchCondition condition);

    Video getRandomVideo();
}
