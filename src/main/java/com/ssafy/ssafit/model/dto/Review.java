package com.ssafy.ssafit.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Review {

	private Long idx;
	private String content;
	private String nickName;
	private LocalDateTime createdAt = LocalDateTime.now();
	private Long videoIdx;
	private Long userIdx;


	protected Review(String content, Long videoIdx, String nickName) {
		this.content = content;
		this.videoIdx = videoIdx;
		this.nickName = nickName;
	}

	@Override
	public String toString() {
		return "Review [idx=" + idx + ", content=" + content + ", createdAt=" + createdAt + ", videoIdx=" + videoIdx
				+ ", userIdx=" + userIdx + "]";
	}


	public void updateContent(String newContent) {
		this.content = newContent;
	}
}
