package com.tl.jg.beerhere.beerhere2.Stadium;

/**
 * Created by Jim on 6/28/2016.
 */
public class Stadiums {
    private int id;
    private String name;
    public Stadiums()
    {
    }
    public Stadiums(int id,String name)
    {
        this.id=id;
        this.name=name;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }

}