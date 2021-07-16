package com.zhaolq.mars.demo.a;

import lombok.Data;

import java.math.BigDecimal;

/**
 *
 *
 * @author zhaolq
 * @date 2021/6/24 14:39
 */
public class Test {
    public static void main(String[] args) {
        assert 1==2;
        Goods goods = new Goods();

        Producer producer = new Producer(goods);
        Consumer consumer = new Consumer(goods);

        Thread producerThread = new Thread(producer);
        Thread consumerThread = new Thread(consumer);

        producerThread.start();
        consumerThread.start();
    }
}

class Producer implements Runnable {
    private Goods goods;

    public Producer(Goods goods) {
        this.goods = goods;
    }

    @Override
    public void run() {
        goods.setName("烧鸡");
        goods.setPrice(new BigDecimal(66));
    }
}

class Consumer implements Runnable {
    private Goods goods;

    public Consumer(Goods goods) {
        this.goods = goods;
    }

    @Override
    public void run() {
        System.out.println("商品名称: " + goods.getName() + "\t商品价格: " + goods.getPrice());
    }
}

@Data
class Goods {
    private String name;
    private BigDecimal price;
}