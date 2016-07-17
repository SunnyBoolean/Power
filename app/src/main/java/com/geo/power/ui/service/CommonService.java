package com.geo.power.ui.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.geo.com.geo.power.bean.DevicesInstallation;
import com.geo.com.geo.power.bean.UserInfo;

import java.util.List;

import cn.bmob.v3.BmobInstallation;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by 李伟 on 2016/7/17.
 */
public class CommonService extends Service{
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        updatePushInstallUid();
        return super.onStartCommand(intent, flags, startId);
    }

    public CommonService() {
        super();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * 此方法用于更新用户的uid到Installation表，在用户点对点推送时会用到这个uid
     */
    private void updatePushInstallUid(){
        final UserInfo user = UserInfo.getCurrentUser(this,UserInfo.class);
        BmobQuery<DevicesInstallation> query = new BmobQuery<DevicesInstallation>();
        query.addWhereEqualTo("installationId", BmobInstallation.getInstallationId(this));
        query.addWhereNotEqualTo("uid",user.getObjectId());
        query.findObjects(this, new FindListener<DevicesInstallation>() {
            @Override
            public void onSuccess(List<DevicesInstallation> object) {
                // TODO Auto-generated method stub
                if(object.size() > 0){
                    DevicesInstallation mbi = object.get(0);
                    mbi.uid = user.getObjectId();
                    mbi.update(CommonService.this,new UpdateListener() {
                        @Override
                        public void onSuccess() {
                        }

                        @Override
                        public void onFailure(int code, String msg) {
                        }
                    });
                }else{
                }
            }

            @Override
            public void onError(int code, String msg) {
                // TODO Auto-generated method stub
            }
        });
    }
}
