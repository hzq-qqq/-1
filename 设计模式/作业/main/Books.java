package com.handFirst.作业.main;

import java.util.ArrayList;

public class Books {
    final static ArrayList<Books> BOOKS = new ArrayList<>();
    public String id;
    public String name;
    public String auther;
    public String num;

    public static boolean del(String id) {
        for (Books book : BOOKS) {
            if (book.id.equals(id)){
                BOOKS.remove(book);
                System.out.println("删除成功");
                return true;
            }
        }
        System.out.println("删除失败");
        return false;
    }

    public static void show(String id1) {
        for (Books book : BOOKS) {
            if (id1.equals(book.id)){
                System.out.println(book);
            }
        }
        System.out.println("id 对象不存在");
    }

    public boolean chkID(String id) {
        for (Books book : BOOKS) {
            if (book.id.equals(id)){
                return false;
            }
        }
        return true;
    }

    public boolean add(Books books) {
        if (books.chkID(books.id)){
           BOOKS.add(books);
            System.out.println("添加对象成功");
            return true;
        }
        System.out.println("添加对象失败");
        return false;
    }
}
