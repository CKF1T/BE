package com.ssafy.ssafit.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Video {
	private Long idx;
	private String title;
	private String channelName;
	private String url;
	private String id;  // youtube 고유 id
	private String part;
	private Long viewCnt;

	@Override
	public String toString() {
		return "Video [idx=" + idx + ", title=" + title + ", channelName=" + channelName + ", url=" + url + ", id=" + id
				+ ", part=" + part + ", viewCnt=" + viewCnt + "]";
	}
}
