package com.salton123.appxmly.model;

import android.util.Pair;

import java.io.Serializable;

/**
 * User: 巫金生(newSalton@outlook.com)
 * Date: 2017/7/21 15:34
 * Time: 15:34
 * Description:
 */
public class ApiPair extends Pair<String,String> implements Serializable{
    /**
     * Constructor for a Pair.
     *
     * @param first  the first object in the Pair
     * @param second the second object in the pair
     */
    public ApiPair(String first, String second) {
        super(first, second);
    }


//    private String name ;
//    private String method ;



    //
//    public ApiPair(String name, String method) {
//        super();
//        this.name = name;
//        this.method = method;
//    }
//
//    protected ApiPair(Parcel in) {
//        name = in.readString();
//        method = in.readString();
//    }

//    public static final Creator<ApiPair> CREATOR = new Creator<ApiPair>() {
//        @Override
//        public ApiPair createFromParcel(Parcel in) {
//            return new ApiPair(in);
//        }
//
//        @Override
//        public ApiPair[] newArray(int size) {
//            return new ApiPair[size];
//        }
//    };
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getMethod() {
//        return method;
//    }
//
//    public void setMethod(String method) {
//        this.method = method;
//    }
//
//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeString(name);
//        dest.writeString(method);
//    }

}
