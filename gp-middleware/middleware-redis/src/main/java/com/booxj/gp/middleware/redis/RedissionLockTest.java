package com.booxj.gp.middleware.redis;

public class RedissionLockTest {

    public static void main(String[] args) {
        RedissonManager.init(); //初始化
        for (int i = 0; i < 100; i++) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String key = "lock";
                        DistributedRedisLock.acquire(key);
                        Thread.sleep(1000); //获得锁之后可以进行相应的处理
                        System.out.println("======获得锁后进行相应的操作======");
                        DistributedRedisLock.release(key);
                        System.out.println("=============================");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            t.start();
        }

    }
}
