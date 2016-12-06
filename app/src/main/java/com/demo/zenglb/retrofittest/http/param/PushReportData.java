package com.demo.zenglb.retrofittest.http.param;

/**
 * Created by zenglb on 2016/11/22.
 */

public class PushReportData {

	/**
	 * msg_id : asdfs3123fdsa
	 * account_id : 11243500
	 * created : 2016-01-01 12:33:11
	 */

	private String msg_id;
	private String account_id;
	private String created;

	public PushReportData(String account_id, String created, String msg_id) {
		this.account_id = account_id;
		this.created = created;
		this.msg_id = msg_id;
	}

	public String getMsg_id() {
		return msg_id;
	}

	public void setMsg_id(String msg_id) {
		this.msg_id = msg_id;
	}

	public String getAccount_id() {
		return account_id;
	}

	public void setAccount_id(String account_id) {
		this.account_id = account_id;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	@Override
	public String toString() {
		return "PushReportData{" +
				"account_id='" + account_id + '\'' +
				", msg_id='" + msg_id + '\'' +
				", created='" + created + '\'' +
				'}';
	}
}
