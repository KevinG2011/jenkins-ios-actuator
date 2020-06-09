package com.pepper.spring.pojo;

public class Saxophone implements IInstrument {

    @Override
    public void play() {
        System.out.println("TOOT TOOT TOOT");
    }
}