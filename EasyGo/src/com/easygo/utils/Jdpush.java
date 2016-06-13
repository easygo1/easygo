package com.easygo.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;

public class Jdpush {
	protected static final Logger LOG = LoggerFactory.getLogger(Jdpush.class);

	// demo App defined in resources/jpush-api.conf
	private static final String appKey = "257eaf986bff5290d9dc7737";
	private static final String masterSecret = "59f59fa0e602519baaf409cf";

	public static final String TITLE = "EasyGo温馨提示";
	//public static final String ALERT = "您有一条新的订单！";
	public static JPushClient jpushClient = null;

	public static void sendPush(int user_id,String message) {
		String user_alias = user_id + "";

		jpushClient = new JPushClient(masterSecret, appKey, 3);

		PushPayload payload = buildPushObject_android_tag_alertWithTitle(user_alias,message);

		try {
			System.out.println(payload.toString());
			PushResult result = jpushClient.sendPush(payload);
			System.out.println(result + "................................");

			LOG.info("Got result - " + result);

		} catch (APIConnectionException e) {
			LOG.error("Connection error. Should retry later. ", e);

		} catch (APIRequestException e) {
			LOG.error(
					"Error response from JPush server. Should review and fix it. ",
					e);
			LOG.info("HTTP Status: " + e.getStatus());
			LOG.info("Error Code: " + e.getErrorCode());
			LOG.info("Error Message: " + e.getErrorMessage());
			LOG.info("Msg ID: " + e.getMsgId());
		}
	}

	public static PushPayload buildPushObject_android_tag_alertWithTitle(
			String user_alias,String message) {
		return PushPayload.newBuilder().setPlatform(Platform.android())
				// 设置接受的平台
				.setAudience(Audience.alias(user_alias))
				// Audience设置为all，说明采用广播方式推送，所有用户都可以接收到
				.setNotification(Notification.android(message, TITLE, null))
				.build();
	}
}
