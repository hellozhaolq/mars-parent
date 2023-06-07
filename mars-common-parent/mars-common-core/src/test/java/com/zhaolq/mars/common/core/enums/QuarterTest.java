package com.zhaolq.mars.common.core.enums;

import org.junit.jupiter.api.Test;

/**
 *
 *
 * @author zhaolq
 * @date 2021/6/18 10:39
 */
public class QuarterTest {

    /**
     * 枚举类也可以声明在内部类中
     *
     * 枚举类成员
     *      枚举跟普通类一样可以用自己的变量、方法和构造函数，构造函数默认且只能使用 private 访问修饰符，所以外部无法调用。
     *      枚举既可以包含具体方法，也可以包含抽象方法。 如果枚举类具有抽象方法，则枚举类的每个实例都必须实现它。
     */
    enum InnerEnum {
        RED {
            @Override
            public String getColor() {//枚举对象实现抽象方法
                return "红色";
            }
        },
        GREEN {
            @Override
            public String getColor() {//枚举对象实现抽象方法
                return "绿色";
            }
        },
        BLUE {
            @Override
            public String getColor() {//枚举对象实现抽象方法
                return "蓝色";
            }
        };

        public abstract String getColor();//定义抽象方法
    }

    @Test
    public void test() {

        // 使用实例
        Quarter q1 = Quarter.Q1;
        System.out.println("q1.getValue(): " + q1.getValue());
        System.out.println("q1.name(): " + q1.name());
        System.out.println("q1: " + q1);
        System.out.println("q1.compareTo(q1): " + q1.compareTo(q1));

        /**
         values(), ordinal() 和 valueOf() 方法
         enum 定义的枚举类默认继承了 java.lang.Enum 类，并实现了 java.lang.Seriablizable 和 java.lang.Comparable 两个接口。
         values(), ordinal() 和 valueOf() 方法位于 java.lang.Enum 类中：
         values() 返回枚举类中所有的值。
         ordinal()方法可以找到每个枚举常量的索引，就像数组索引一样。
         valueOf()方法返回指定字符串值的枚举常量。
         */
        System.out.println("q1.ordinal(): " + q1.ordinal());
        System.out.println("Quarter.valueOf(\"Q1\"): " + Quarter.valueOf("Q1"));

        // 迭代枚举元素
        for (Quarter quarter : Quarter.values()) {
            System.out.println(quarter);
        }

        // 在 switch 中使用枚举类
        switch (q1) {
            case Q1:
                System.out.println("第一季度");
                break;
            case Q2:
                System.out.println("第二季度");
                break;
            case Q3:
                System.out.println("第三季度");
                break;
            case Q4:
                System.out.println("第四季度");
                break;
        }
    }

}
