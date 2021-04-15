package com.handFirst.作业.main;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sin = new Scanner(System.in);
        boolean t = true;
        while (t){
            System.out.println("1-添加 2-删除 3-查询");
            switch (sin.nextInt()){
                case 1:
                    System.out.println("输入编号，书名，作者，数量（用空格分隔）");
                    Books book = new Books();
                    String id = sin.nextLine();
                    if (book.chkID(id)){
                        book.id  = id;
                        book.name = sin.nextLine();
                        book.auther = sin.nextLine();
                        book.num  = sin.nextLine();
                    }else{
                        System.out.println("请输入编号");
                        sin.next();
                        sin.next();
                        sin.next();
                        continue;
                    }
                    System.out.println(book.add(book));
                    break;
                case 2:
                    System.out.println("请输入要删除的编号： ");
                    System.out.println(Books.del(sin.next()));
                    break;
                case 3:
                    System.out.println("请输入要查询的编号： ");
                    Books.show(sin.next());
            }

        }
    }
}
