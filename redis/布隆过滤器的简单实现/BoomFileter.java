package com.redis.布隆过滤器的简单实现;

import java.util.BitSet;

public class BoomFileter {
//     每个哈希函数使用的prim
    private final int[] prims = new int[]{1,3,5,7,9,11,13};
//     hash
    private final Hash[] hashList = new Hash[prims.length];

//    bit数组
    private BitSet bitSet = new BitSet(256 << 22);

    public BoomFileter(){
        for (int i = 0; i < hashList.length; i++) {
            hashList[i] = new Hash(prims[i]);
        }
    }

    public void add(String key){
        for (Hash hash : hashList) {
            bitSet.set(hash.hash(key),true);
        }
    }

    public boolean contains(String key){
        if (key == null){
            return false;
        }
        for (Hash hash : hashList) {
            if (!bitSet.get(hash.hash(key))){
                return false;
            }
        }
        return true;
    }


    private static class Hash {
//        每个哈希对象独有的一个 prime
        private final int prime;

        private Hash(int prime) {
            this.prime = prime;
        }
//        计算哈希值
        private int hash(String key){
            int hash = key.length();
            for (int i = 0; i < key.length(); i++) {
                hash += key.charAt(i);
            }
            return hash % prime;
        }
    }

    public static void main(String[] args) {
        final BoomFileter boomFileter = new BoomFileter();
        for (int i = 0; i < 100000000; i++) {
            boomFileter.add(i + "");
        }
        final long start = System.currentTimeMillis();
        System.out.println(boomFileter.contains("64728947"));
        final long end = System.currentTimeMillis();
        System.out.println("执行的时间为： " + (end - start));

        final long start1 = System.currentTimeMillis();
        for (int i = 0; i < 100000000; i++) {
            if ((i + "").equals(64728947+"")){
              break;
            }
        }
        final long end1 = System.currentTimeMillis();
        System.out.println("执行的时间为： " + (end1 - start1));
        
    }
}
