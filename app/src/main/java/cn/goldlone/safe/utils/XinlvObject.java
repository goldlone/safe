package cn.goldlone.safe.utils;

import android.os.Parcelable;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;

/**
 * Created by lenovo on 2016/9/28.
 */
@AVClassName("XinlvObject")
public class XinlvObject extends AVObject {
    public static final Parcelable.Creator CREATOR = AVObjectCreator.instance;

    public static final String NAME = "name";
    public static final String XINLV = "xinlv";

    public XinlvObject(){
        super("XinlvObject");
    }

    public String getName(){
        return  getString(NAME);
    }
    public int getXinlv(){
        return getInt(XINLV);
    }

    public void setName(String name){
        put(NAME,name);
    }

    public void setXinlv(int xinlv){
        put(XINLV,xinlv);
    }

}