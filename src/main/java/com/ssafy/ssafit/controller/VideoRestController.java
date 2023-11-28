package com.ssafy.ssafit.controller;

import java.util.List;

import com.ssafy.ssafit.model.dto.SearchCondition;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ssafy.ssafit.model.dto.Video;
import com.ssafy.ssafit.model.service.VideoService;

@RestController
@RequestMapping("/videos")
@Api(tags="운동 영상 api")
@RequiredArgsConstructor
public class VideoRestController {

	private final VideoService videoService;

	@GetMapping
	@ApiOperation(value = "운동 영상 리스트 조회")
	public ResponseEntity<?> readVideoList(@RequestParam(required = false) String key, @RequestParam(required = false) String inputString) {

		if(key != null && inputString != null) {
			SearchCondition condition = new SearchCondition(key, inputString);
			List<Video> list = videoService.search(condition);
			if(list == null || list.isEmpty()) {
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(list, HttpStatus.OK);
		}
		List<Video> list = videoService.getList();
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@GetMapping("/{idx}")
	@ApiOperation(value = "특정 운동 영상 조회")
	public ResponseEntity<Video> detail(@PathVariable Long idx) {
		Video Video = videoService.getVideo(idx);
		
		if (Video == null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(Video, HttpStatus.OK);
	}
}
