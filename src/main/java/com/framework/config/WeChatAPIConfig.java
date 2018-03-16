package com.framework.config;


import com.kratos.kits.wechat.config.annotation.builder.WeChatPayConfig;
import com.kratos.kits.wechat.config.annotation.configuration.WeChatAPIConfigurer;
import org.springframework.context.annotation.Configuration;

/**
 * 微信配置
 */
@Configuration
public class WeChatAPIConfig implements WeChatAPIConfigurer {

    @Override
    public void configure(WeChatPayConfig weChatPayConfig) {
        weChatPayConfig.setAppId("wx7caa47f20e19566e");
        weChatPayConfig.setAppSecret("77ahhdskkqwuiqjhda8989wejasdq2ad");
        weChatPayConfig.setApiKey("77ahhdskkqwuiqjhda8989wejasdq2ad");
        weChatPayConfig.setMchId("1486889962");
        weChatPayConfig.setNotifyUrl("http://www.dldjshop.com/wechat/pay/notify");
    }
}
