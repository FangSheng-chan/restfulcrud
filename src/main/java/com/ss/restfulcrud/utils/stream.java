package com.ss.restfulcrud.utils;

import java.util.ArrayList;
import java.util.stream.Stream;

public class stream {
    public static void main(String[] args) {
        String[] str = {"张三", "李四", "王五"};
        StringBuffer stringBuffers = new StringBuffer();
        stringBuffers.append("ss");
        stringBuffers.append("dd");
        stringBuffers.append("zz");

        ArrayList<String> list = new ArrayList<>();

        list.add("1");
        list.add("2");
        list.add("3");
        Stream<String> stream = Stream.of(str);
        stream.forEach(name -> System.out.println(name));

        Stream.of(list).forEach(name -> {
            System.out.println(name);
        });

        Stream.of(str).forEach(stringBuffers::append);
        System.out.println(stringBuffers);
    }
}
