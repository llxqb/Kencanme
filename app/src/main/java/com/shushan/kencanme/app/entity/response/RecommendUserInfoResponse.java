package com.shushan.kencanme.app.entity.response;

import java.util.List;

/**
 * ClassName: RecommendUserInfoResponse
 * author: li.liu
 * date: 2019-05-28
 */
public class RecommendUserInfoResponse{


    /**
     * uid : 547
     * nickname : Thot
     * cover : https://pbs.twimg.com/media/D7pC0AWUEAErDdS.jpg
     * sex : 2
     * birthday : 851443200
     * city : Padang
     * declaration :
     * vip : 0
     * vip_time : 0
     * svip : 0
     * height : 178
     * weight : 56
     * bust : 38B
     * occupation : anchor
     * label : []
     * beans : 0
     * contact : []
     * token :
     * age : 23
     * wrong_login_num : 0
     * wrong_time : 0
     * forbidden : 0
     * pushing_age : 18-30
     * pushing_gender : 2
     * exposure : 0
     * last_login_time : 0
     * trait : https://pbs.twimg.com/media/D7pC0AWUEAErDdS.jpg
     * album : [{"id":672597,"album_url":"https://pbs.twimg.com/media/D7pC0AeUcAIH_Mp.jpg","album_type":3,"cost":1,"state":0},{"id":672598,"album_url":"https://pbs.twimg.com/media/D7pC00ZU8AAH9q-.jpg","album_type":3,"cost":1,"state":0}]
     * pushing_small_age : 18
     * pushing_large_age : 30
     * active_time : -1
     * is_friend : 0
     * relation : 0   0没有关系1喜欢2互相喜欢（好友）3黑名单
     * is_see_contact : 0
     * rongyun_userid : Kencanme547
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
    private int wrong_login_num;
    private int wrong_time;
    private int forbidden;
    private String pushing_age;
    private int pushing_gender;
    private int exposure;
    private int last_login_time;
    private String trait;
    private String pushing_small_age;
    private String pushing_large_age;
    private int active_time;
    private int is_friend;
    private int relation;
    private int is_see_contact;//是否已经查看过联系方式
    private String rongyun_userid;
    private List<String> label;
    private List<ContactWay> contact;
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

    public int getWrong_login_num() {
        return wrong_login_num;
    }

    public void setWrong_login_num(int wrong_login_num) {
        this.wrong_login_num = wrong_login_num;
    }

    public int getWrong_time() {
        return wrong_time;
    }

    public void setWrong_time(int wrong_time) {
        this.wrong_time = wrong_time;
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

    public int getActive_time() {
        return active_time;
    }

    public void setActive_time(int active_time) {
        this.active_time = active_time;
    }

    public int getIs_friend() {
        return is_friend;
    }

    public void setIs_friend(int is_friend) {
        this.is_friend = is_friend;
    }

    public int getRelation() {
        return relation;
    }

    public void setRelation(int relation) {
        this.relation = relation;
    }

    public int getIs_see_contact() {
        return is_see_contact;
    }

    public void setIs_see_contact(int is_see_contact) {
        this.is_see_contact = is_see_contact;
    }

    public String getRongyun_userid() {
        return rongyun_userid;
    }

    public void setRongyun_userid(String rongyun_userid) {
        this.rongyun_userid = rongyun_userid;
    }

    public List<String> getLabel() {
        return label;
    }

    public void setLabel(List<String> label) {
        this.label = label;
    }

    public List<ContactWay> getContact() {
        return contact;
    }

    public void setContact(List<ContactWay> contact) {
        this.contact = contact;
    }

    public List<AlbumBean> getAlbum() {
        return album;
    }

    public void setAlbum(List<AlbumBean> album) {
        this.album = album;
    }

    public static class AlbumBean {
        /**
         * id : 672597
         * album_url : https://pbs.twimg.com/media/D7pC0AeUcAIH_Mp.jpg
         * album_type : 3
         * cost : 1
         * state : 0
         */

        private int id;
        private String album_url;
        private int album_type;
        private int cost;
        private int state;

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

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }
    }
}
