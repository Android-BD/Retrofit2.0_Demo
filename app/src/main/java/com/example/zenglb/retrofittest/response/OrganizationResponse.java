package com.example.zenglb.retrofittest.response;

import java.io.Serializable;
import java.util.List;

/**
 * 乐帮获取用户服务 返回
 * 
 * @author liugz
 * 
 */
public class OrganizationResponse extends BaseResponse {
	private List<Organization> result;

	public OrganizationResponse(int code, String error, List<Organization> result) {
		super(code, error);
		this.result = result;
	}

	public List<Organization> getResult() {
		return result;
	}

	public void setResult(List<Organization> result) {
		this.result = result;
	}

	public class Organization implements Serializable {
		public String code;
		public String name;
		public boolean is_project;
		public boolean need_position;

		public boolean is_project() {
			return is_project;
		}

		public boolean isNeed_position() {
			return need_position;
		}

		public void setNeed_position(boolean need_position) {
			this.need_position = need_position;
		}

		public void setIs_project(boolean is_project) {
			this.is_project = is_project;
		}

		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return "Organization{" +
					"code='" + code + '\'' +
					", name='" + name + '\'' +
					", is_project=" + is_project +
					", need_position=" + need_position +
					'}';
		}
	}


	@Override
	public String toString() {
		return "OrganizationResponse{" +
				"result=" + result +
				'}';
	}
}
