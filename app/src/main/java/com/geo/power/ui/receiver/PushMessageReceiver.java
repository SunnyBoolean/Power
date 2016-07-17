package com.geo.power.ui.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.geo.com.geo.power.Constants;
import com.geo.com.geo.power.bean.PushInfo;

import org.json.JSONException;
import org.json.JSONObject;

import cn.bmob.push.PushConstants;
import ui.geo.com.power.R;

/**
 * Created by 李伟 on 2016/7/17.
 * 接收推送消息
 */
public class PushMessageReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(PushConstants.ACTION_MESSAGE)) {
            String mesg = intent.getStringExtra("msg");
            String json = "";
            try {
                JSONObject jobj = new JSONObject(mesg);
                json = jobj.getString("alert").replace("'\n'","");
                PushInfo push = PushInfo.jsonToBean(json);
                notifyNewMessage(context,push);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    private void notifyNewMessage(Context context,PushInfo push) {
        StringBuilder msg =new StringBuilder(push.push_username);
        if(push.push_code == Constants.PushCode.PUSH_CODE_COMMENT){
            msg.append("评论了你的计划");
        }else if(push.push_code == Constants.PushCode.PUSH_CODE_ZAN){
            msg.append("评论了赞了你的计划");
        }
        CharSequence title = "收到新的消息";
        Notification.Builder builder = new Notification.Builder(context);
        builder.setTicker(title);
        builder.setSmallIcon(R.drawable.ic_launcher);
        builder.setWhen(System.currentTimeMillis());
        builder.setAutoCancel(true);
        builder.setContentIntent(PendingIntent.getActivity(context, 0,
                new Intent(Intent.ACTION_DELETE), 0));
        Notification noti = builder.build();
        RemoteViews remoteView = new RemoteViews(context.getPackageName(),
                R.layout.notification_push);
        remoteView.setImageViewResource(R.id.notification_push_image, R.drawable.ic_launcher);
        remoteView.setTextViewText(R.id.notification_push_content, msg);
        noti.contentView = remoteView;
        NotificationManager mnotiManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        // 注意第一个参数，如果多次调用notify，如果这个id参数不变那就只更新此界面，而不重新创建
        mnotiManager.notify(R.string.app_name, noti);
    }

}
