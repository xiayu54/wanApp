package com.yuan.fastec.latte.app;

/**
 * @author XYuan
 * Version  1.0
 * Description
 *  枚举类是唯一的单例，只能初始化一次
 */
public enum  ConfigType {
    API_HOST,   // 配置网络的域名
    APPLICATION_CONTEXT,
    CONFIG_READY,   // 配置是否完成
    ICON,
    INTERCEPTOR
}
