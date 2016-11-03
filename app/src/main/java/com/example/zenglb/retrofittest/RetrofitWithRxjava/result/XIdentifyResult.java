package com.example.zenglb.retrofittest.RetrofitWithRxjava.result;

/**
 * Created by zenglb on 2016/8/12.
 */
@Deprecated
public class XIdentifyResult {

    /**
     * is_project : false
     * code : IDT_CENTER
     * name : 管理中心员工
     * need_position : false
     */

    private boolean is_project;
    private String code;
    private String name;
    private boolean need_position;

    public boolean isIs_project() {
        return is_project;
    }

    public void setIs_project(boolean is_project) {
        this.is_project = is_project;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isNeed_position() {
        return need_position;
    }

    public void setNeed_position(boolean need_position) {
        this.need_position = need_position;
    }

    @Override
    public String toString() {
        return "XIdentifyResult{" +
                "code='" + code + '\'' +
                ", is_project=" + is_project +
                ", name='" + name + '\'' +
                ", need_position=" + need_position +
                '}';
    }
}
