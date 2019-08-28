package com.shushan.kencanme.app.entity.response;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * 登录成功/进入首页会执行
 * 个人资料
 */
public class PersonalInfoResponse implements Parcelable {


    /**
     * uid : 6
     * nickname : yuu234
     * cover : http://test.shushanbao.com/Public/uploads/video/20190813/c60958742adadc212e19e25b7ef84616.mp4
     * sex : 1
     * birthday : 677991628
     * city : yy123
     * declaration : yyyyy123hhhhkkk
     * vip : 0
     * vip_time : 0
     * svip : 0
     * height : 176
     * weight : 72
     * bust : 35D
     * occupation : 护士
     * label : ["ffgggg","hhhhh","uuuu"]
     * beans : 534
     * contact : [{"email":"ttttgvhn","name":"google"},{"email":"yikbbh","name":"facebook"},{"email":"whatsapp@163.com","name":"WhatsApp"}]
     * token : 9f58679b1886b6adb0b01695edba9be4
     * age : 28
     * forbidden : 0
     * pushing_age : 18-50
     * pushing_gender : 2
     * exposure : 3
     * last_login_time : 1566891395
     * trait : http://test.shushanbao.com/Public/uploads/trait/1565841604.jpg
     * lang : en_us
     * code : APUYG78
     * platform : Android
     * is_own : 0
     * state : 0   0正常1审核中2审核不通过
     * album : [{"id":5334,"album_url":"http://test.shushanbao.com/Public/uploads/video/20190813/4884115b8a6cc665a175461dc2390511.mp4","album_type":1,"cost":0,"status":0},{"id":5335,"album_url":"http://test.shushanbao.com/Public/uploads/cover/1565692374.jpg","album_type":3,"cost":5,"status":0}]
     * pushing_small_age : 18
     * pushing_large_age : 50
     * new_like : {"count":5,"trait":null,"state":1}
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
    private String height;
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
    private String lang;
    private String code;
    private String platform;
    private int is_own;
    private int state;
    private String pushing_small_age;
    private String pushing_large_age;
    private NewLikeBean new_like;
    private List<String> label;
    private List<ContactBean> contact;
    private List<AlbumBean> album;

    protected PersonalInfoResponse(Parcel in) {
        uid = in.readInt();
        nickname = in.readString();
        cover = in.readString();
        sex = in.readInt();
        birthday = in.readString();
        city = in.readString();
        declaration = in.readString();
        vip = in.readInt();
        vip_time = in.readInt();
        svip = in.readInt();
        height = in.readString();
        weight = in.readString();
        bust = in.readString();
        occupation = in.readString();
        beans = in.readInt();
        token = in.readString();
        age = in.readInt();
        forbidden = in.readInt();
        pushing_age = in.readString();
        pushing_gender = in.readInt();
        exposure = in.readInt();
        last_login_time = in.readInt();
        trait = in.readString();
        lang = in.readString();
        code = in.readString();
        platform = in.readString();
        is_own = in.readInt();
        state = in.readInt();
        pushing_small_age = in.readString();
        pushing_large_age = in.readString();
        label = in.createStringArrayList();
    }

    public static final Creator<PersonalInfoResponse> CREATOR = new Creator<PersonalInfoResponse>() {
        @Override
        public PersonalInfoResponse createFromParcel(Parcel in) {
            return new PersonalInfoResponse(in);
        }

        @Override
        public PersonalInfoResponse[] newArray(int size) {
            return new PersonalInfoResponse[size];
        }
    };

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

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
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

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public int getIs_own() {
        return is_own;
    }

    public void setIs_own(int is_own) {
        this.is_own = is_own;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
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

    public NewLikeBean getNew_like() {
        return new_like;
    }

    public void setNew_like(NewLikeBean new_like) {
        this.new_like = new_like;
    }

    public List<String> getLabel() {
        return label;
    }

    public void setLabel(List<String> label) {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(uid);
        dest.writeString(nickname);
        dest.writeString(cover);
        dest.writeInt(sex);
        dest.writeString(birthday);
        dest.writeString(city);
        dest.writeString(declaration);
        dest.writeInt(vip);
        dest.writeInt(vip_time);
        dest.writeInt(svip);
        dest.writeString(height);
        dest.writeString(weight);
        dest.writeString(bust);
        dest.writeString(occupation);
        dest.writeInt(beans);
        dest.writeString(token);
        dest.writeInt(age);
        dest.writeInt(forbidden);
        dest.writeString(pushing_age);
        dest.writeInt(pushing_gender);
        dest.writeInt(exposure);
        dest.writeInt(last_login_time);
        dest.writeString(trait);
        dest.writeString(lang);
        dest.writeString(code);
        dest.writeString(platform);
        dest.writeInt(is_own);
        dest.writeInt(state);
        dest.writeString(pushing_small_age);
        dest.writeString(pushing_large_age);
        dest.writeStringList(label);
    }

    public static class NewLikeBean {
        /**
         * count : 5
         * trait : null
         * state : 1
         */

        private int count;
        private String trait;
        private int state;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getTrait() {
            return trait;
        }

        public void setTrait(String trait) {
            this.trait = trait;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }
    }

    public static class ContactBean {
        /**
         * email : ttttgvhn
         * name : google
         */

        private String email;
        private String name;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class AlbumBean {
        /**
         * id : 5334
         * album_url : http://test.shushanbao.com/Public/uploads/video/20190813/4884115b8a6cc665a175461dc2390511.mp4
         * album_type : 1
         * cost : 0
         * status : 0
         */

        private int id;
        private String album_url;
        private int album_type;
        private int cost;
        private int status;

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

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
