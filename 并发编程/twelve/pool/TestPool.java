package com.并发编程.twelve.pool;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class TestPool {
    @FunctionalInterface // 拒绝策略
    interface RejectPolicy<T> {
        void reject(BlockingQueue<T> queue, T task);
    }
    class BlockingQueue<T> {
        // 1. 任务队列
        private Deque<T> queue = new ArrayDeque<>();
        // 2. 锁
        private ReentrantLock lock = new ReentrantLock();
        // 3. 生产者条件变量
        private Condition fullWaitSet = lock.newCondition();
        // 4. 消费者条件变量
        private Condition emptyWaitSet = lock.newCondition();
        // 5. 容量
        private int capcity;
        public BlockingQueue(int capcity) {
            this.capcity = capcity;
        }
        // 带超时阻塞获取
        public T poll(long timeout, TimeUnit unit) {
            lock.lock();
            try {
                // 将 timeout 统一转换为 纳秒 返回值是剩余等待的时间
                long nanos = unit.toNanos(timeout);
                while (queue.isEmpty()) {
                    try {
                        // 返回值是剩余时间
                        if (nanos <= 0) {
                            return null;
                        }
                        nanos = emptyWaitSet.awaitNanos(nanos);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                T t = queue.removeFirst();
                fullWaitSet.signal();
                return t;
            } finally {
                lock.unlock();
            }
        }
        // 阻塞获取
        public T take() {
            lock.lock();
            try {
                while (queue.isEmpty()) {
                    try {
//                        永久等待
                        emptyWaitSet.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                T t = queue.removeFirst();
                fullWaitSet.signal();
                return t;
            } finally {
                lock.unlock();
            }
        }
        // 阻塞添加 —— 任务woek中
        public void put(T task) {
            lock.lock();
            try {
//                当 queue 阻塞队列满时，采取无线等待
                while (queue.size() == capcity) {
                    try {
                        log.debug("等待加入任务队列 {} ...", task);

//                        永久等待
                        fullWaitSet.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.debug("加入任务队列 {}", task);
                queue.addLast(task);
                emptyWaitSet.signal();
            } finally {
                lock.unlock();
            }
        }
        // 带超时时间阻塞添加
        public boolean offer(T task, long timeout, TimeUnit timeUnit) {
            lock.lock();
            try {
                long nanos = timeUnit.toNanos(timeout);
                while (queue.size() == capcity) {
                    try {
                        if(nanos <= 0) {
                            return false;
                        }
                        log.debug("等待加入任务队列 {} ...", task);
                        nanos = fullWaitSet.awaitNanos(nanos);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.debug("加入任务队列 {}", task);
                queue.addLast(task);
                emptyWaitSet.signal();
                return true;
            } finally {
                lock.unlock();
            }
        }
        public int size() {
            lock.lock();
            try {
                return queue.size();
            } finally {
                lock.unlock();
            }
        }
        public void tryPut(RejectPolicy<T> rejectPolicy, T task) {
            lock.lock();
            try {
                // 判断队列是否满
                if(queue.size() == capcity) {
//                    拒绝策略 当阻塞队列满时采用的阻塞策略
                    rejectPolicy.reject(this, task);
                } else {
//                   阻塞任务队列没有满，则将任务加入到任务队列中
                    log.debug("加入任务队列 {}", task);
                    queue.addLast(task);
                    emptyWaitSet.signal();
                }
            } finally {
                lock.unlock();
            }
        }
    }
    class ThreadPool {
        // 任务队列
        private BlockingQueue<Runnable> taskQueue;
        // 线程集合
        private HashSet<Worker> workers = new HashSet<>();
        // 核心线程数
        private int coreSize;
        // 获取任务时的超时时间
        private long timeout;
        private TimeUnit timeUnit;
//        当任务队列满了之后 的处理方式
//        1.无限等待 2. 超时等待  3. 抛出异常处理 4.不执行
        private RejectPolicy<Runnable> rejectPolicy;
        // 执行任务
        public void execute(Runnable task) {
            // 当任务数没有超过 coreSize 时，直接交给 worker 对象执行
            // 如果任务数超过 coreSize 时，加入任务队列暂存
//            将锁是为了防止多个线程对同一个任务进行操作
            synchronized (workers) {
//                当 工作线程数量小于 和兴数  直接将任务加入到工作之中开始工作
                if(workers.size() < coreSize) {
                    Worker worker = new Worker(task);
                    log.debug("新增 worker{}, {}", worker, task);
                    workers.add(worker);
                    worker.start();
                } else {
//                    这里是线程数量大于 核心数量时的获取任务的策略 —— 尝试将任务加入到阻塞队列中
// taskQueue.put(task);
                    // 1) 死等
                    // 2) 带超时等待
                    // 3) 让调用者放弃任务执行
                    // 4) 让调用者抛出异常
                    // 5) 让调用者自己执行任务
                    taskQueue.tryPut(rejectPolicy, task);
                }
            }
        }
        public ThreadPool(int coreSize, long timeout, TimeUnit timeUnit, int queueCapcity,
                          RejectPolicy<Runnable> rejectPolicy) {
            this.coreSize = coreSize;
            this.timeout = timeout;
            this.timeUnit = timeUnit;
            this.taskQueue = new BlockingQueue<>(queueCapcity);
            this.rejectPolicy = rejectPolicy;
        }

        /**
         * 执行任务的线程
         */
        class Worker extends Thread{
            private Runnable task;
            public Worker(Runnable task) {
                this.task = task;
            }
            @Override
            public void run() {
                // 执行任务
                // 1) 当 task 不为空，执行任务
                // 2) 当 task 执行完毕，再接着从任务队列获取任务并执行
// while(task != null || (task = taskQueue.take()) != null) {
//                当前任务不null 执行程序
//                从任务队列中获取任务来执行
//                poll 超时获取任务
                while(task != null || (task = taskQueue.poll(timeout, timeUnit)) != null) {
                    try {
                        log.debug("正在执行...{}", task);
                        task.run();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        task = null;
                    }
                }
//                当任务被执行完成释放当前线程
                synchronized (workers) {
                    log.debug("worker 被移除{}", this);
                    workers.remove(this);
                }
            }
        }
    }
    @Test
   void test(){
        ThreadPool threadPool = new ThreadPool(1,
                1000, TimeUnit.MILLISECONDS, 1, (queue, task)->{
            // 1. 死等
//        queue.put(task);
            // 2) 带超时等待
          queue.offer(task, 1500, TimeUnit.MILLISECONDS);
            // 3) 让调用者放弃任务执行
// log.debug("放弃{}", task);
            // 4) 让调用者抛出异常
// throw new RuntimeException("任务执行失败 " + task);
            // 5) 让调用者自己执行任务
            task.run();
        });
        for (int i = 0; i < 4; i++) {
            int j = i;
            threadPool.execute(() -> {
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.debug("{}", j);
            });
        }
    }
}
