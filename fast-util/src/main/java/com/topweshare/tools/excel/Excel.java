package com.topweshare.tools.excel;

import java.lang.annotation.*;

/**
 * Excel导出注解
 * @author dingzhenhao
 *
 */
@Target({ElementType.FIELD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Excel {
	 //列名
    String name() default "";
     
    //宽度
    int width() default 20;
 
    //忽略该字段
    boolean skip() default false;
    
}