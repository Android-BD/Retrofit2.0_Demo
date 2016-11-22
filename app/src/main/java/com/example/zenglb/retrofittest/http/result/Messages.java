package com.example.zenglb.retrofittest.http.result;

/**
 * Created by zenglb on 2016/11/21.
 */
public class Messages {

	/**
	 * read : false
	 * created : 2015-11-24 18:49:49
	 * content : null
	 * action_type : null
	 * message : 后院可能有人放火，我很害怕
	 * title : 报事提醒
	 * id : 8
	 * action_id : null
	 */

	private boolean read;
	private String created;
	private Object content;
	private Object action_type;
	private String message;
	private String title;
	private int id;
	private Object action_id;

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

	public Object getContent() {
		return content;
	}

	public void setContent(Object content) {
		this.content = content;
	}

	public Object getAction_type() {
		return action_type;
	}

	public void setAction_type(Object action_type) {
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

	public Object getAction_id() {
		return action_id;
	}

	public void setAction_id(Object action_id) {
		this.action_id = action_id;
	}

	@Override
	public String toString() {
		return "Messages{" +
				"action_id=" + action_id +
				", read=" + read +
				", created='" + created + '\'' +
				", content=" + content +
				", action_type=" + action_type +
				", message='" + message + '\'' +
				", title='" + title + '\'' +
				", id=" + id +
				'}';
	}
}
