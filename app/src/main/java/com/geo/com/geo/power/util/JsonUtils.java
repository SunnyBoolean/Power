package com.geo.com.geo.power.util;

public class JsonUtils {
	/**
	 * 
	 * @param userId  发表评论的用户的id
	 * @param createTime  发表评论的时间
	 * @param questionId  所评论的问题的id
	 * @param userName  发表评论的用户名
	 * @param content  评论的内容
	 * @return
	 */
	public static String buildPushJson(String userId, String createTime,
			String questionId, String userName, String content) {
		StringBuilder json = new StringBuilder();
		json.append("{");
		// 用户id，也即是评论者用户id
		json.append("\"userId\":");
		json.append("\"" + userId + "\",");
		// 评论的时间
		json.append("\"createTime\":");
		json.append("\"" + createTime + "\",");
		// 评论的问题的id
		json.append("\"questionId\":");
		json.append("\"" + questionId + "\",");
		// 评论者的用户名
		json.append("\"userName\":");
		json.append("\"" + userName + "\",");
		// 评论的内容
		json.append("\"content\":");
		json.append("\"" + content + "\"}");
		return json.toString();
	}
}
