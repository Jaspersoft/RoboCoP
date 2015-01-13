package com.rain.utils.android.robocop.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Tom Koptel on 1/13/15.
 */
public class ContentProviderTableFieldModel {
    public static enum Type {
        STRING("TEXT", "String", "getString"),
        DOUBLE("REAL", "double", "getDouble"),
        INT("NUMERIC", "int", "getInt"),
        LONG("NUMERIC", "long", "getLong"),
        BOOlEAN("NUMERIC", "boolean", "getInt"),
        DATE("DATE", "String", "getString"),
        DATETIME("DATETIME", "String", "getString"),
        ;

        private final String mSqlitePresentation;
        private final String mJavaPresentation;
        private final String mCursorGetter;

        Type(String sqlitePresentation, String javaPresentation, String mCursorGetter) {
            this.mSqlitePresentation = sqlitePresentation;
            this.mJavaPresentation = javaPresentation;
            this.mCursorGetter = mCursorGetter;
        }

        public String getSqlitePresentation() {
            return mSqlitePresentation;
        }

        public String getJavaPresentation() {
            return mJavaPresentation;
        }

        public String getCursorGetter() {
            return mCursorGetter;
        }

        static String getJavaPresentation(String field) {
            return seekType(field, new Function<Type, String>() {
                @Override
                public String apply(Type type) {
                    return type.getJavaPresentation();
                }
            });
        }

        static String getSqlitePresentation(String field) {
            return seekType(field, new Function<Type, String>() {
                @Override
                public String apply(Type type) {
                    return type.getSqlitePresentation();
                }
            });
        }

        static String getCursorGetter(String field) {
            return seekType(field, new Function<Type, String>() {
                @Override
                public String apply(Type type) {
                    return type.getCursorGetter();
                }
            });
        }

        private static String seekType(String field, Function<Type, String> func) {
            String lowerField = field.toLowerCase();
            String presentation;
            for (Type type : values()) {
                presentation = type.getJavaPresentation().toLowerCase();
                if (presentation.equals(lowerField)) {
                    return func.apply(type);
                }
            }
            throw new IllegalArgumentException("For field: " + field + " no Type registered");
        }
    }

    @SerializedName("type")
    private String mFieldType;

    @SerializedName("name")
    private String mFieldName;

    @SerializedName("unique")
    private boolean isUnique;

    @SerializedName("nullable")
    private boolean isNullable;

    @SerializedName("default")
    private String mDefaultValue;

    @SerializedName("check")
    private String check;

    public ContentProviderTableFieldModel(String fieldType, String fieldName) {
        mFieldType = fieldType;
        mFieldName = fieldName;
    }

    public String getFieldType() {
        return mFieldType;
    }

    public String getFieldName() {
        return mFieldName;
    }

    public String getConstantString() {
        return StringUtils.getConstantString(mFieldName);
    }

    public String getTypeString() {
        return Type.getSqlitePresentation(Type.getSqlitePresentation(mFieldType));
    }

    public String getJavaTypeString() {
        return Type.getJavaPresentation(mFieldType);
    }

    public String getJavaTypeStringGetter() {
        return Type.getCursorGetter(mFieldType);
    }

    public String getPrivateVariableName() {
        return StringUtils.getPrivateVariableName(mFieldName);
    }

    public String getNameAsTitleCase() {
        return StringUtils.convertToTitleCase(mFieldName);
    }
}