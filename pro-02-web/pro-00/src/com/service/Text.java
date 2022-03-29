package com.service;

public class Text {
    public static void main(String[] args) {
        FruitService f = new FruitService();
        int up = f.update("宁", 1, 200, "hello world");
        System.out.println(up + " row语句受影响");
    }
}
