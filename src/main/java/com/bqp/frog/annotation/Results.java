package com.bqp.frog.annotation;

import java.lang.annotation.*;

/**
 * 自定义数据库表字段到类属性的映射，和{@link Result}配合使用
 * <p/>
 * <p>比如我们需要将数据库user表中的user_id,user_name字段，分别映射到类User的id,name属性
 * <p/>
 * <pre>
 * interface UserDao {
 *
 *   {@literal@}Results({
 *     {@literal@}Result(column = "user_id", property = "id"),
 *     {@literal@}Result(column = "user_name", property = "name")
 *   })
 *   {@literal@}SQL("select user_id, user_name from user where user_id = :1")
 *   public User getUserById(int id);
 *
 *   {@literal@}Results({
 *     {@literal@}Result(column = "user_id", property = "id"),
 *     {@literal@}Result(column = "user_name", property = "name")
 *   })
 *   {@literal@}SQL("select user_id, user_name from user where user_name = :1")
 *   public User getUserByName(String name);
 *
 * }
 * </pre>
 * <p/>
 * <P>我们还可以把该注解放在UserDao上，效果和上面的代码一样
 * <p/>
 * <pre>
 * {@literal@}Results({
 *   {@literal@}Result(column = "user_id", property = "id"),
 *   {@literal@}Result(column = "user_name", property = "name")
 * })
 * interface UserDao {
 *
 *   {@literal@}SQL("select user_id, user_name from user where user_id = :1")
 *   public User getUserById(int id);
 *
 *   {@literal@}SQL("select user_id, user_name from user where user_name = :1")
 *   public User getUserByName(String name);
 *
 * }
 * </pre>
 *
 * @author ash
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Results {

    Result[] value() default {};

}
