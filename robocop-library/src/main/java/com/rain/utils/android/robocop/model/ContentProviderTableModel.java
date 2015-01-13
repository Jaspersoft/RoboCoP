package com.rain.utils.android.robocop.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: dustin
 * Date: 1/15/14
 * Time: 9:46 AM
 */
public class ContentProviderTableModel {
    @SerializedName("parcelable")
    private boolean isParcelable;
    @SerializedName("name")
    private String mTableName;

    @SerializedName("members")
    private List<ContentProviderTableFieldModel> mFields = new ArrayList<ContentProviderTableFieldModel>();

    public ContentProviderTableModel(String tableName) {
        mTableName = tableName;
    }

    public List<ContentProviderTableFieldModel> getFields() {
        return mFields;
    }

    public String getTableName() {
        return mTableName;
    }

    public String getTableClassName() {
        return StringUtils.convertToTitleCase(mTableName);
    }

    public String getTableConstantName() {
        return StringUtils.getConstantString(mTableName);
    }

    public boolean isParcelable() {
        return isParcelable;
    }
}
