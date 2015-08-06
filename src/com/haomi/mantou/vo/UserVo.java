package com.haomi.mantou.vo;

public class UserVo{

	private String token;
	private String time;
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}

	
/*
	@Override
	public String toString() {
		return "UserVo [uid=" + uid + ", username=" + username + ", nickname="
				+ nickname + ", token=" + token + ", isnew=" + isnew
				+ ", avatar=" + avatar + "]";
	}
*/
}
