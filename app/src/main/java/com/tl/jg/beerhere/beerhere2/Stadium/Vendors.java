package com.tl.jg.beerhere.beerhere2.Stadium;

/**
 * Created by Jim on 7/7/2016.
 */
public class Vendors {
    private int id;
    private String name;
    private String number;
    private String stadium;
    private int balance;
    private String phoneNumber;

    public Vendors()
    {
    }
    public Vendors(int id,String name, String number, String stadium, int balance, String phoneNumber)
    {
        this.id=id;
        this.name=name;
        this.number=number;
        this.stadium=stadium;
        this.balance=balance;
        this.phoneNumber=phoneNumber;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setNumber(String number) {
        this.number = number;
    }
    public void setStadium(String stadium) {
        this.stadium = stadium;
    }
    public void setBalance(int balance){this.balance= balance;}
    public void setPhoneNumber(String phoneNumber){this.phoneNumber=phoneNumber;}
    public int getId() {return id;}
    public String getName() {
        return name;
    }
    public String getNumber(){
        return number;
    }
    public String getStadium(){
        return stadium;
    }
    public int getBalance(){return balance;}
    public String getPhoneNumber(){return phoneNumber;}
}