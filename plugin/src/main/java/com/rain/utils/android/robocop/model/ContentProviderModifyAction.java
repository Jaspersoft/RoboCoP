package com.rain.utils.android.robocop.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created with Android Studio.
 * User: Tom Koptel
 * Date: 11/30/14
 */
public class ContentProviderModifyAction {
    @SerializedName("type")
    private String mType;
    @SerializedName("action")
    private String mAction;

    public Type getType() {
        return Type.valueOf(mType.toUpperCase());
    }

    public Action getAction() {
        return Action.valueOf(mAction.toUpperCase());
    }

    public static enum Type {
        CASCADE, DELETE;
    }

    public static enum Action {
        RESTRICT, SET_NULL, SET_DEFAULT, CASCADE;

        @Override
        public String toString() {
            return super.toString().replaceAll("_", " ");
        }
    }

}
