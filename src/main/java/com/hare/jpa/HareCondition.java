package com.hare.jpa;

/**
 * 条件
 * @author wang cheng
 */
public class HareCondition {

    String field;
    Type type;
    Object value;

    public HareCondition(String field, Type type, Object value) {
        this.field = field;
        this.type = type;
        this.value = value;
    }

    /**
     * 类型
     */
    public enum Type {
        EQ,
        NE,
        GT,
        LT,
        GE,
        LE,
        IN,
        LIKE,
        LIKE_LEFT,
        LIKE_RIGHT,
        IS_NULL,
        IS_NOT_NULL,
        BETWEEN
    }
}
