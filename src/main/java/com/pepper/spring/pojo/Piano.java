package com.pepper.spring.pojo;

public class Piano implements IInstrument {

    @Override
    public void play() {
        System.out.println("PLINK PLINK PLINK");
    }
}