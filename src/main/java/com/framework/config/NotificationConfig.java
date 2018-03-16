package com.framework.config;

import com.kratos.kits.notification.config.annotation.builder.NotificationProviders;
import com.kratos.kits.notification.config.annotation.builder.NotificationTypes;
import com.kratos.kits.notification.config.annotation.configuration.NotificationKitConfigurerAdaptor;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NotificationConfig extends NotificationKitConfigurerAdaptor {
    @Override
    public void configure(NotificationProviders notificationProvider) throws Exception {
        notificationProvider
                .alidayuProvider()
                .setAppId("24495956")
                .setAppSecret("895912d66f8740c478c967b2167019fb")
                .setServerUrl("http://gw.api.taobao.com/router/rest")
                .setSmsFreeSignName("鼎骏出行")
                .setSmsTemplateCode("SMS_71820364")
                .setSmsType("normal")
                .and()
                .ipyyProvider()
                .setServerUrl("https://dx.ipyy.net/sms.aspx")
                .setAccountName("KA00012")
                .setPassword("KA00012789")
                .setSmsFreeSignName("鼎骏出行");
    }

    @Override
    public void configure(NotificationTypes notificationTypes) throws Exception {
        notificationTypes
                .smsVerifyCode().ipyyProvider()
                .and()
                .smsBroadcast().ipyyProvider()
                .and()
                .voiceVerifyCode().alidayuProvider();
    }
}
