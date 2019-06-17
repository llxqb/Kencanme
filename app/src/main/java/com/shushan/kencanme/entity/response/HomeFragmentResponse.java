package com.shushan.kencanme.entity.response;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class HomeFragmentResponse {

    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean implements Parcelable{
        /**
         * uid : 829
         * nickname : somili
         * trait : https://scontent-hkg3-1.xx.fbcdn.net/v/t1.0-9/60118816_458235338316949_8526854330014236672_n.jpg?_nc_cat=106&_nc_ht=scontent-hkg3-1.xx&oh=2e00e94e52b9925316f6c65455f55188&oe=5D90DD95
         * cover : https://scontent-hkg3-1.xx.fbcdn.net/v/t1.0-9/61184182_468493820624434_3912136013136265216_n.jpg?_nc_cat=107&_nc_ht=scontent-hkg3-1.xx&oh=c20e279740026f2371348bd42210e66f&oe=5D590A7E
         * sex : 2
         * city : Sula
         * last_login_time : 0
         * exposure_time : 0
         * age : 22
         * rongyun_token :
         * active_time : 43
         * is_like : 0   0未喜欢1喜欢
         */

        private int uid;
        private String nickname;
        private String trait;
        private String cover;
        private int sex;
        private String city;
        private int last_login_time;
        private int exposure_time;
        private int age;
        private String rongyun_token;
        private int active_time;
        private int is_like;

        public ListBean() {
        }

        protected ListBean(Parcel in) {
            uid = in.readInt();
            nickname = in.readString();
            trait = in.readString();
            cover = in.readString();
            sex = in.readInt();
            city = in.readString();
            last_login_time = in.readInt();
            exposure_time = in.readInt();
            age = in.readInt();
            rongyun_token = in.readString();
            active_time = in.readInt();
            is_like = in.readInt();
        }

        public static final Creator<ListBean> CREATOR = new Creator<ListBean>() {
            @Override
            public ListBean createFromParcel(Parcel in) {
                return new ListBean(in);
            }

            @Override
            public ListBean[] newArray(int size) {
                return new ListBean[size];
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

        public String getTrait() {
            return trait;
        }

        public void setTrait(String trait) {
            this.trait = trait;
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

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public int getLast_login_time() {
            return last_login_time;
        }

        public void setLast_login_time(int last_login_time) {
            this.last_login_time = last_login_time;
        }

        public int getExposure_time() {
            return exposure_time;
        }

        public void setExposure_time(int exposure_time) {
            this.exposure_time = exposure_time;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getRongyun_token() {
            return rongyun_token;
        }

        public void setRongyun_token(String rongyun_token) {
            this.rongyun_token = rongyun_token;
        }

        public int getActive_time() {
            return active_time;
        }

        public void setActive_time(int active_time) {
            this.active_time = active_time;
        }

        public int getIs_like() {
            return is_like;
        }

        public void setIs_like(int is_like) {
            this.is_like = is_like;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(uid);
            dest.writeString(nickname);
            dest.writeString(trait);
            dest.writeString(cover);
            dest.writeInt(sex);
            dest.writeString(city);
            dest.writeInt(last_login_time);
            dest.writeInt(exposure_time);
            dest.writeInt(age);
            dest.writeString(rongyun_token);
            dest.writeInt(active_time);
            dest.writeInt(is_like);
        }
    }
}
