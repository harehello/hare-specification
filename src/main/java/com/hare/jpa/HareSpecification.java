package com.hare.jpa;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * Specification核心构建
 * @author wang cheng
 */
public class HareSpecification<T> implements Specification<T> {
    private HareOperator hareOperator = HareOperator.AND;

    private final static String DOT = ".";
    private final static String PERCENT = "%";
    /**
     * and拼接集合 ? and ? and ...
     */
    private List<HareCondition> andHareConditions;
    /**
     * or拼接集合 ? or ? or ...
     */
    private List<HareCondition> orHareConditions;
    /**
     * andor拼接集合 ? and ? and (? or ? or ...)
     */
    private List<HareCondition> andOrHareConditions;
    /**
     * 排序集合
     */
    private List<HareOrder> hareOrders;

    /**
     * 去重
     */
    private Boolean distinct = Boolean.FALSE;

    public HareSpecification() {
        this.orHareConditions = new ArrayList<>();
        this.andOrHareConditions = new ArrayList<>();
        this.andHareConditions = new ArrayList<>();
        this.hareOrders = new ArrayList<>();
    }

    public HareSpecification and() {
        this.hareOperator = HareOperator.AND;
        return this;
    }
    public HareSpecification andOr() {
        this.hareOperator = HareOperator.AND_OR;
        return this;
    }
    public HareSpecification or() {
        this.hareOperator = HareOperator.OR;
        return this;
    }
    public HareSpecification asc(String field) {
        this.hareOrders.add(HareOrder.asc(field));
        return this;
    }
    public HareSpecification desc(String field) {
        this.hareOrders.add(HareOrder.desc(field));
        return this;
    }

    public HareSpecification distinct() {
        this.distinct = Boolean.TRUE;
        return this;
    }

    private HareSpecification addCondition(boolean isTrue, HareCondition hareCondition) {
        if (isTrue) {
            switch (hareOperator) {
                case AND:
                    this.andHareConditions.add(hareCondition);
                    break;
                case AND_OR:
                    this.andOrHareConditions.add(hareCondition);
                    break;
                case OR:
                    this.orHareConditions.add(hareCondition);
                    break;
            }
        }
        return this;
    }
    public HareSpecification eq(String field, Object value) {
        return eq(true, field, value);
    }
    public HareSpecification eq(boolean isTrue, String field, Object value) {
        return addCondition(isTrue, new HareCondition(field, HareCondition.Type.EQ, value));
    }
    public HareSpecification ne(String field, Object value) {
        return ne(true, field, value);
    }
    public HareSpecification ne(boolean isTrue, String field, Object value) {
        return addCondition(isTrue, new HareCondition(field, HareCondition.Type.NE, value));
    }

    public HareSpecification like(String field, Object value) {
        return like(true, field, value);
    }
    public HareSpecification like(boolean isTrue, String field, Object value) {
        return addCondition(isTrue, new HareCondition(field, HareCondition.Type.LIKE, value));
    }
    public HareSpecification likeLeft(String field, Object value) {
        return likeLeft(true, field, value);
    }
    public HareSpecification likeLeft(boolean isTrue, String field, Object value) {
        return addCondition(isTrue, new HareCondition(field, HareCondition.Type.LIKE_LEFT, value));
    }
    public HareSpecification likeRight(String field, Object value) {
        return likeRight(true, field, value);
    }
    public HareSpecification likeRight(boolean isTrue, String field, Object value) {
        return addCondition(isTrue, new HareCondition(field, HareCondition.Type.LIKE_RIGHT, value));
    }

    public HareSpecification lt(String field, Object value) {
        return lt(true, field, value);
    }
    public HareSpecification lt(boolean isTrue, String field, Object value) {
        return addCondition(isTrue, new HareCondition(field, HareCondition.Type.LT, value));
    }
    public HareSpecification le(String field, Object value) {
        return le(true, field, value);
    }
    public HareSpecification le(boolean isTrue, String field, Object value) {
        return addCondition(isTrue, new HareCondition(field, HareCondition.Type.LE, value));
    }

    public HareSpecification gt(String field, Object value) {
        return gt(true, field, value);
    }
    public HareSpecification gt(boolean isTrue, String field, Object value) {
        return addCondition(isTrue, new HareCondition(field, HareCondition.Type.GT, value));
    }
    public HareSpecification ge(String field, Object value) {
        return ge(true, field, value);
    }
    public HareSpecification ge(boolean isTrue, String field, Object value) {
        return addCondition(isTrue, new HareCondition(field, HareCondition.Type.GE, value));
    }

    public HareSpecification in(String field, Collection<?> values) {
        return in(true, field, values);
    }
    public HareSpecification in(boolean isTrue, String field, Collection<?> values) {
        return addCondition(isTrue, new HareCondition(field, HareCondition.Type.IN, values));
    }

    public HareSpecification isNull(String field) {
        return isNull(true, field);
    }
    public HareSpecification isNull(boolean isTrue, String field) {
        return addCondition(isTrue, new HareCondition(field, HareCondition.Type.IS_NULL, null));
    }

    public HareSpecification isNotNull(String field) {
        return isNotNull(true, field);
    }
    public HareSpecification isNotNull(boolean isTrue, String field) {
        return addCondition(isTrue, new HareCondition(field, HareCondition.Type.IS_NOT_NULL, null));
    }

    public HareSpecification between(String field, Object value1, Object value2) {
        return between(true, field, value1, value2);
    }
    public HareSpecification between(boolean isTrue, String field, Object value1, Object value2) {
        Object[] objects = {value1, value2};
        return addCondition(isTrue, new HareCondition(field, HareCondition.Type.BETWEEN, objects));
    }

    /**
     * 重写toPredicate实现自己的拼接逻辑
     * 拼接and、andor、or再添加排序
     * @param root
     * @param query
     * @param cb
     * @return
     */
    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        Predicate predicates = null;
        if (!andHareConditions.isEmpty()) {
            Predicate[] andPredicates = getPredicate(root, cb, andHareConditions);
            predicates = cb.and(cb.and(andPredicates));
        }
        if (!andOrHareConditions.isEmpty()) {
            Predicate[] andOrPredicates = getPredicate(root, cb, andOrHareConditions);
            predicates = Objects.nonNull(predicates) ? cb.and(predicates, cb.or(andOrPredicates)) : cb.and(cb.or(andOrPredicates));
        }
        if (!orHareConditions.isEmpty()) {
            Predicate[] orPredicates = getPredicate(root, cb, orHareConditions);
            predicates = Objects.nonNull(predicates) ? cb.or(predicates, cb.or(orPredicates)) : cb.or(cb.or(orPredicates));
        }

        query.distinct(distinct);

        List<Order> orderList = getOrderList(root, cb);
        if (!orderList.isEmpty()) {
            query.orderBy(orderList);
        }
        return predicates;
    }

    /**
     * 获取拼接条件
     * @param root
     * @param cb
     * @param hareConditions
     * @return
     */
    private Predicate[] getPredicate(Root<T> root, CriteriaBuilder cb, List<HareCondition> hareConditions) {
        Predicate[] predicates = new Predicate[hareConditions.size()];
        for (int i = 0; i < hareConditions.size(); i++) {
            HareCondition c = hareConditions.get(i);
            String fieldPath = c.field;
            Path<T> path = getPath(root, fieldPath);
            Object value = c.value;
            switch (c.type) {
                case EQ:
                    predicates[i] = cb.equal(path, value);
                    break;
                case NE:
                    predicates[i] = cb.notEqual(path, value);
                    break;
                case LIKE:
                    predicates[i] = cb.like((Expression<String>) path, concatLike(value, HareCondition.Type.LIKE));
                    break;
                case LIKE_LEFT:
                    predicates[i] = cb.like((Expression<String>) path, concatLike(value, HareCondition.Type.LIKE_LEFT));
                    break;
                case LIKE_RIGHT:
                    predicates[i] = cb.like((Expression<String>) path, concatLike(value, HareCondition.Type.LIKE_RIGHT));
                    break;
                case LT:
                    predicates[i] = cb.lessThan((Expression) path, (Comparable) value);
                    break;
                case LE:
                    predicates[i] = cb.lessThanOrEqualTo((Expression) path, (Comparable<Object>) value);
                    break;
                case GT:
                    predicates[i] = cb.greaterThan((Expression) path, (Comparable<Object>) value);
                    break;
                case GE:
                    predicates[i] = cb.greaterThanOrEqualTo((Expression) path, (Comparable<Object>) value);
                    break;
                case IN:
                    predicates[i] = path.in((Collection<?>)value);
                    break;
                case IS_NULL:
                    predicates[i] = path.isNull();
                    break;
                case IS_NOT_NULL:
                    predicates[i] = path.isNotNull();
                    break;
                case BETWEEN:
                    Object[] objects = (Object[]) value;
                    predicates[i] = cb.between((Expression) path, (Comparable<Object>) objects[0], (Comparable<Object>) objects[1]);
                    break;
                default: break;
            }
        }
        return predicates;
    }

    /**
     * 获取操作属性，OneToMany、ManyToMany时使用INNER连接
     * @param root
     * @param fieldPath
     * @param <X>
     * @return
     */
    private <X> Path<X> getPath(Root<X> root, String fieldPath) {
        if (Objects.isNull(fieldPath) || fieldPath.isEmpty()) {
            return root;
        }
        String[] fields = fieldPath.split("\\"+DOT);
        Path<?> path = root;
        Join join = null;
        for (int i = 0; i < fields.length; i++) {
            String field = fields[i];
            Class<?> javaType = path.get(field).getJavaType();
            if (Collection.class.isAssignableFrom(javaType)) {
                path = join = Objects.isNull(join) ? root.join(field) : join.join(field);
            } else {
                path = path.get(field);
            }
        }
        return (Path<X>) path;
    }

    /**
     * 拼接Like连接
     * @param value
     * @param type
     * @return
     */
    private static String concatLike(Object value, HareCondition.Type type) {
        switch (type) {
            case LIKE_LEFT:
                return PERCENT + value;
            case LIKE_RIGHT:
                return value + PERCENT;
            default:
                return PERCENT + value + PERCENT;
        }
    }

    /**
     * 获取排序集合
     * @param root
     * @param cb
     * @return
     */
    private List<Order> getOrderList(Root<T> root, CriteriaBuilder cb) {
        List<Order> orderList = new ArrayList<>();
        for (HareOrder hareOrder : hareOrders) {
            if (Objects.isNull(hareOrder)) {
                continue;
            }
            Path<T> path = getPath(root, hareOrder.getField());
            Sort.Direction direction = hareOrder.getDirection();
            if (Objects.isNull(path) || Objects.isNull(direction)) {
                continue;
            }
            switch (direction) {
                case ASC:
                    orderList.add(cb.asc(path));
                    break;
                case DESC:
                    orderList.add(cb.desc(path));
                    break;
            }
        }
        return orderList;
    }

}
