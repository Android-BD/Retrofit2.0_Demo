package com.demo.zenglb.retrofittest.http.param;

/**
 * Created by zenglb on 2016/11/22.
 */
public class JobsData {

	public JobsData(String project_code, String role_code) {
		this.project_code = project_code;
		this.role_code = role_code;
	}

	/**
	 * project_code : 44030029
	 * role_code : LB10001
	 */

	private String project_code;
	private String role_code;

	public String getProject_code() {
		return project_code;
	}

	public void setProject_code(String project_code) {
		this.project_code = project_code;
	}

	public String getRole_code() {
		return role_code;
	}

	public void setRole_code(String role_code) {
		this.role_code = role_code;
	}
}
