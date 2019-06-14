package com.shushan.kencanme.entity.response;

import java.io.Serializable;
import java.util.List;

/**
 * 登录成功/进入首页会执行
 * 个人资料
 */
public class PersonalInfoResponse implements Serializable {


    /**
     * uid : 6
     * nickname : yuu234
     * cover : https://menggoda.oss-ap-southeast-5.aliyuncs.com/video/20190613/9ac885d2a646c393ba6bad6c7a8513f3.mp4
     * sex : 1
     * birthday : 2014/06/14
     * city : yy123
     * declaration : yyyyy123
     * vip : 0
     * vip_time : 0
     * svip : 0
     * height : 176
     * weight : 72
     * bust : 35D
     * occupation : 护士
     * label : []
     * beans : 0
     * contact : [{"contactName":"google","contactValue":""},{"contactName":"google","contactValue":""},{"contactName":"google"}]
     * token : 7f6a2acf8aa4b8e85210ffc73866db58
     * age : 49
     * forbidden : 0
     * pushing_age : 18-30
     * pushing_gender : 2
     * exposure : 0
     * last_login_time : 1560498405
     * trait : https://menggoda.oss-ap-southeast-5.aliyuncs.com/trait/20190613/5d01ba17036ed.png
     * album : [{"id":9,"album_url":"https://menggoda.oss-ap-southeast-5.aliyuncs.com/video/20190613/3f9a8da5fae43abaee47d8f248ab1162.mp4","album_type":3,"cost":5},{"id":11,"album_url":"https://menggoda.oss-ap-southeast-5.aliyuncs.com/cover/20190614/5d0302fbed536.png","album_type":2,"cost":0},{"id":13,"album_url":"https://menggoda.oss-ap-southeast-5.aliyuncs.com/video/20190614/b7002fe81d9e1dc43f92c116f95c9dbd.mp4","album_type":3,"cost":8},{"id":14,"album_url":"https://menggoda.oss-ap-southeast-5.aliyuncs.com/cover/20190614/5d030d28409de.png","album_type":1,"cost":0},{"id":17,"album_url":"https://menggoda.oss-ap-southeast-5.aliyuncs.com/cover/20190614/5d030fa70cb4d.png","album_type":1,"cost":0},{"id":18,"album_url":"https://menggoda.oss-ap-southeast-5.aliyuncs.com/cover/20190614/5d031012d7ddf.png","album_type":3,"cost":5},{"id":19,"album_url":"https://menggoda.oss-ap-southeast-5.aliyuncs.com/cover/20190614/5d03122d1254c.png","album_type":2,"cost":0},{"id":21,"album_url":"https://menggoda.oss-ap-southeast-5.aliyuncs.com/cover/20190614/5d0315e970293.png","album_type":1,"cost":0},{"id":24,"album_url":"https://menggoda.oss-ap-southeast-5.aliyuncs.com/cover/20190614/5d03264968790.png","album_type":1,"cost":0},{"id":25,"album_url":"https://menggoda.oss-ap-southeast-5.aliyuncs.com/cover/20190614/5d0326d82ff92.png","album_type":2,"cost":0}]
     * pushing_small_age : 18
     * pushing_large_age : 30
     */

    private int uid;
    private String nickname;
    private String cover;
    private int sex;
    private String birthday;
    private String city;
    private String declaration;
    private int vip;
    private int vip_time;
    private int svip;
    private int height;
    private String weight;
    private String bust;
    private String occupation;
    private int beans;
    private String token;
    private int age;
    private int forbidden;
    private String pushing_age;
    private int pushing_gender;
    private int exposure;
    private int last_login_time;
    private String trait;
    private String pushing_small_age;
    private String pushing_large_age;
    private List<?> label;
    private List<ContactBean> contact;
    private List<AlbumBean> album;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDeclaration() {
        return declaration;
    }

    public void setDeclaration(String declaration) {
        this.declaration = declaration;
    }

    public int getVip() {
        return vip;
    }

    public void setVip(int vip) {
        this.vip = vip;
    }

    public int getVip_time() {
        return vip_time;
    }

    public void setVip_time(int vip_time) {
        this.vip_time = vip_time;
    }

    public int getSvip() {
        return svip;
    }

    public void setSvip(int svip) {
        this.svip = svip;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getBust() {
        return bust;
    }

    public void setBust(String bust) {
        this.bust = bust;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public int getBeans() {
        return beans;
    }

    public void setBeans(int beans) {
        this.beans = beans;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getForbidden() {
        return forbidden;
    }

    public void setForbidden(int forbidden) {
        this.forbidden = forbidden;
    }

    public String getPushing_age() {
        return pushing_age;
    }

    public void setPushing_age(String pushing_age) {
        this.pushing_age = pushing_age;
    }

    public int getPushing_gender() {
        return pushing_gender;
    }

    public void setPushing_gender(int pushing_gender) {
        this.pushing_gender = pushing_gender;
    }

    public int getExposure() {
        return exposure;
    }

    public void setExposure(int exposure) {
        this.exposure = exposure;
    }

    public int getLast_login_time() {
        return last_login_time;
    }

    public void setLast_login_time(int last_login_time) {
        this.last_login_time = last_login_time;
    }

    public String getTrait() {
        return trait;
    }

    public void setTrait(String trait) {
        this.trait = trait;
    }

    public String getPushing_small_age() {
        return pushing_small_age;
    }

    public void setPushing_small_age(String pushing_small_age) {
        this.pushing_small_age = pushing_small_age;
    }

    public String getPushing_large_age() {
        return pushing_large_age;
    }

    public void setPushing_large_age(String pushing_large_age) {
        this.pushing_large_age = pushing_large_age;
    }

    public List<?> getLabel() {
        return label;
    }

    public void setLabel(List<?> label) {
        this.label = label;
    }

    public List<ContactBean> getContact() {
        return contact;
    }

    public void setContact(List<ContactBean> contact) {
        this.contact = contact;
    }

    public List<AlbumBean> getAlbum() {
        return album;
    }

    public void setAlbum(List<AlbumBean> album) {
        this.album = album;
    }

    public static class ContactBean implements Serializable{
        /**
         * contactName : google
         * contactValue :
         */

        private String contactName;
        private String contactValue;

        public String getContactName() {
            return contactName;
        }

        public void setContactName(String contactName) {
            this.contactName = contactName;
        }

        public String getContactValue() {
            return contactValue;
        }

        public void setContactValue(String contactValue) {
            this.contactValue = contactValue;
        }
    }

    public static class AlbumBean {
        /**
         * id : 9
         * album_url : https://menggoda.oss-ap-southeast-5.aliyuncs.com/video/20190613/3f9a8da5fae43abaee47d8f248ab1162.mp4
         * album_type : 3
         * cost : 5
         */

        private int id;
        private String album_url;
        private int album_type;
        private int cost;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getAlbum_url() {
            return album_url;
        }

        public void setAlbum_url(String album_url) {
            this.album_url = album_url;
        }

        public int getAlbum_type() {
            return album_type;
        }

        public void setAlbum_type(int album_type) {
            this.album_type = album_type;
        }

        public int getCost() {
            return cost;
        }

        public void setCost(int cost) {
            this.cost = cost;
        }
    }
}
