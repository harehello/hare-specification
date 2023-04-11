

### 使用示例

包含开发过程中常用的构建条件，主要包括以下示例，每种条件都可接受3个或2个参数，3个参数时为动态构建，第一个参数为判断，成立则添加要构建的条件语句，具体使用如下：

#### eq > equal

````java

Specification specification = new HareSpecification<UserDO>()
    .eq(1 == 1, "username", "Hare")
    .eq("age", 25);

````

#### ne > notEqual

````java
Specification specification = new HareSpecification<UserDO>()
                .ne(1 == 1, "username", "Hare")
                .eq("age", 25);
````



#### like > like

````java
Specification specification = new HareSpecification<UserDO>()
    .like(1 == 1, "username", "H")//  -> username like '%H%'
    .likeLeft("nickname", "H")//  -> nickname like '%H'
    .likeRight("nickname", "H");//  -> nickname like 'H%'
````



#### le > lessThan

````java
Specification specification = new HareSpecification<UserDO>()
    .le(1 == 2, "username", "H")//  -> 不构建
    .le("age", "25");//  -> age < 25
````



#### lt > lessThanOrEqualTo

````
Specification specification = new HareSpecification<UserDO>()
    .lt("age", "25");//  -> age <= 25
````



#### ge > greaterThan

````
Specification specification = new HareSpecification<UserDO>()
    .ge("age", "25");//  -> age > 25
````



#### gt > greaterThanOrEqualTo

````java
Specification specification = new HareSpecification<UserDO>()
    .gt("age", "25");//  -> age >= 25
````



#### in > in

````
Specification specification = new HareSpecification<UserDO>()
	.in("username", Arrays.asList("Hare"));//  -> in('Hare')
````



#### isNull > isNull

````
Specification specification = new HareSpecification<UserDO>()
	.isNull("username");// -> username is null
````



#### isNotNull > isNotNull

````
Specification specification = new HareSpecification<UserDO>()
	.isNotNull("username");// -> username is not null
````



#### between

````
Specification specification = new HareSpecification<UserDO>()
	.between("age", 25, 30);// -> age between 25 and 30
````



#### 排序

````
Specification specification = new HareSpecification<UserDO>()
    .between("age", 25, 30)
    .asc("age")
    .desc("id");
````



#### OR

````
Specification specification = new HareSpecification<UserDO>()
    .eq("username", "Hare")
    .or()
    .eq("username", "Tony")
    .andOr()
    .eq("nickname", "Hare")
    .eq("nickname", "Tony");

// username = 'Hare' or username = 'Tony' and (nickname = 'Hare' or nickname = 'Tony')
````



