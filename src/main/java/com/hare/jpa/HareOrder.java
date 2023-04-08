package com.hare.jpa;

import org.springframework.data.domain.Sort;

/**
 * 排序
 * @author wang cheng
 */
public class HareOrder {
    private String field;
    private Sort.Direction direction = Sort.Direction.ASC;

    public HareOrder(String field, Sort.Direction direction) {
        this.field = field;
        this.direction = direction;
    }

    public static HareOrder asc(String field){
        return new HareOrder(field, Sort.Direction.ASC);
    }
    public static HareOrder desc(String field){
        return new HareOrder(field, Sort.Direction.DESC);
    }

    public String getField() {
        return field;
    }

    public Sort.Direction getDirection() {
        return direction;
    }
}
