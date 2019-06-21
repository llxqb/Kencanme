package com.shushan.kencanme.mvp.utils;

/**
 * app 中的一些逻辑
 */
public class AppUtils {
    /**
     * userType
     * 喜欢规则：
     * 1、男非VIP每天只能喜欢20个人次。
     * 2、男VIP增加至40人。
     * 3、超级VIP不限制次数。
     * 4、女非VIP每天喜欢40人。
     * 5、女VIP增至60人。
     * <p>
     * todayLikeNum :今日已喜欢数
     */

    public static boolean isLimitLike(int userType, int todayLikeNum) {
        switch (userType) {
            case 1:
                if (todayLikeNum > 20) {
                    return false;
                }
                break;
            case 2:
                if (todayLikeNum > 40) {
                    return false;
                }
                break;
            case 3:
                break;
            case 4:
                if (todayLikeNum > 40) {
                    return false;
                }
                break;
            case 5:
                if (todayLikeNum > 60) {
                    return false;
                }
                break;
        }
        return true;
    }

    /**
     * 用户类型
     * 1：男非VIP
     * 2：男VIP
     * 3：超级VIP
     * 4：女非VIP
     * 5：女VIP
     */
    public static int userType(int svip, int vip, int sex) {
        if (svip == 1) {
            return 3;
        } else {
            if (vip == 1) {
                if (sex == 1) {
                    return 2;
                } else {
                    return 5;
                }
            } else {
                if (sex == 1) {
                    return 1;
                } else {
                    return 4;
                }
            }
        }
    }

    /**
     * 超级曝光时间30min
     * 现在时间-曝光时间
     */
    public static int exposureRemainTime(long nowTime, long exposureTime) {
        long remainTime = 1800 - (nowTime - exposureTime);
        return (int) remainTime;
    }


    /**
     * 密聊规则
     * 男性：
     * vip 每天主动10个人发起聊天
     * 非vip 不可以
     * 超级vip 不限次数
     * <p>
     * 女性：
     * 非vip  每天主动10个人发起聊天    超过10个提示开通vip
     * vip    每天主动20个人发起聊天
     * <p>
     * 3、女性回复男性VIP用户消息，无需付费或开通VIP。
     * 4、男性回复女性用户，需开通VIP或付费（付费金额后台可调整）
     * <p>
     * 限制发聊天数
     */
    public static boolean isLimitMsg(int userType, int todaySendMsgNum) {
        switch (userType) {
            case 1:
                return false;
            case 2:
                if (todaySendMsgNum > 10) {
                    return false;
                }
                break;
            case 3:
                return true;
            case 4:
                if (todaySendMsgNum > 10) {
                    return false;
                }
                break;
            case 5:
                if (todaySendMsgNum > 20) {
                    return false;
                }
                break;
        }
        return true;
    }

    /**
     * 查看联系方式数  支付嗨豆数
     * 超级vip   每天免费查看3人 5嗨豆每次
     * vip       每天免费查看1人  10嗨豆每次
     * 普通      20嗨豆每次
     * return    嗨豆数
     */
    public static int lookContactType(int userType, int todayContactNum) {
        switch (userType) {
            case 1:
                return 20;
            case 2:
                if (todayContactNum >= 1) {
                    return 10;
                } else {
                    return 0;
                }
            case 3:
                if (todayContactNum >= 3) {
                    return 5;
                } else {
                    return 0;
                }
            case 4:
                return 20;
            case 5:
                if (todayContactNum >= 1) {
                    return 10;
                } else {
                    return 0;
                }
        }
        return 0;
    }

    /**
     * 消息发送规则：
     1、 用户注册APP后，5个机器人开始给用户发送消息。每个机器人发送消息间隔5分钟发第一条消息。
     2、后台可以设置添加机器人发送消息的间隔时间/或者每天时间段定时发送，发送内容，发送消息条数。
     3、用户一旦消费后，则停止自动发送消息。
     */
}
