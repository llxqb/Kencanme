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
     *
     * 女性：
     * 非vip  每天主动10个人发起聊天    超过10个提示开通vip
     * vip    每天主动20个人发起聊天
     *
     */


}
