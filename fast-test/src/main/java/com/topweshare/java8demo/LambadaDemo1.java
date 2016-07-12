package com.topweshare.java8demo;

import java.util.Arrays;
import java.util.List;

/**
 * Created by mongoding on 2016/7/9.
 */
public class LambadaDemo1 {
    public static void main(String[] args) {
        Thread gaoDuanDaQiShangDangCi = new Thread( () -> {
            System.out.println("This is from an anonymous method (lambda exp).");
        } );
        gaoDuanDaQiShangDangCi.start();
        List features = Arrays.asList("Lambdas", "Default Method", "Stream API",
                "Date and Time API");
        features.forEach(n -> System.out.println(n));

    }
}
