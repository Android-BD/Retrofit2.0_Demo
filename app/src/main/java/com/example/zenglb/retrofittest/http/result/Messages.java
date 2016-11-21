package com.example.zenglb.retrofittest.http.result;

import java.util.List;

/**
 *
 * Created by zenglb on 2016/11/21.
 */

public class Messages {

	private List<ResultBean> result;

	public List<ResultBean> getResult() {
		return result;
	}

	public void setResult(List<ResultBean> result) {
		this.result = result;
	}

	public static class ResultBean {
		/**
		 * read : false
		 * created : 2015-11-24 18:49:49
		 * content : “1232”
		 * action_type : “dsdsds”
		 * message : 后院可能有人放火，我很害怕
		 * title : 报事提醒
		 * id : 8
		 * action_id : dddd
		 */

		private boolean read;
		private String created;
		private String content;
		private String action_type;
		private String message;
		private String title;
		private int id;
		private String action_id;

		public boolean isRead() {
			return read;
		}

		public void setRead(boolean read) {
			this.read = read;
		}

		public String getCreated() {
			return created;
		}

		public void setCreated(String created) {
			this.created = created;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

		public String getAction_type() {
			return action_type;
		}

		public void setAction_type(String action_type) {
			this.action_type = action_type;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getAction_id() {
			return action_id;
		}

		public void setAction_id(String action_id) {
			this.action_id = action_id;
		}

		@Override
		public String toString() {
			return "ResultBean{" +
					"action_id='" + action_id + '\'' +
					", read=" + read +
					", created='" + created + '\'' +
					", content='" + content + '\'' +
					", action_type='" + action_type + '\'' +
					", message='" + message + '\'' +
					", title='" + title + '\'' +
					", id=" + id +
					'}';
		}
	}

	@Override
	public String toString() {
		return "Messages{" +
				"result=" + result +
				'}';
	}
}
