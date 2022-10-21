package com.cg.dev.string;

public class CodePoint {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        int a =0x010000;

        boolean validCodePoint = Character.isValidCodePoint(a);
        System.out.println(validCodePoint);
        String b=":sc,}{~`";
        System.out.println(b.length());
        System.out.println(b.codePointCount(0,b.length()));


    }
}