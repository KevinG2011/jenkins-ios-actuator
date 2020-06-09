package com.pepper.spring.pojo;

public class Instrumentalist implements IPerformer {
    private String song;
    private IInstrument instrument;

    @Override
    public void perform() {

    }

    public String getSong() {
        return song;
    }

    public void setSong(String song) {
        this.song = song;
    }

    public IInstrument getInstrument() {
        return instrument;
    }

    public void setInstrument(IInstrument instrument) {
        this.instrument = instrument;
    }

}