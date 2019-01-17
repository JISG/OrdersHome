package com.orders.home.ordersHome;

public class Deliver {
    private int kdt;
    private int hour;
    private int minute;
    private int neighbor;
    private int id;
    private String tipoTortilla;


    public Deliver(int kdt, int hour, int minute, int neighbor, int id) {
        this.kdt = kdt;
        this.hour = hour;
        this.minute = minute;
        this.neighbor = neighbor;
        this.id = id;
    }

    public Deliver(int kdt, int hour, int minute, String tipoTortilla, int id){
        this.kdt = kdt;
        this.hour = hour;
        this.minute = minute;
        this.tipoTortilla = tipoTortilla;
        this.id = id;
    }

    public String getTipoTortilla() {
        return tipoTortilla;
    }

    public void setTipoTortilla(String tipoTortilla) {
        this.tipoTortilla = tipoTortilla;
    }

    public int getTime2Deliver(){
        return ((hour * 60) + minute);
    }

    public int getNeighbor() {
        return neighbor;
    }

    public void setNeighbor(int neighbor) {
        this.neighbor = neighbor;
    }

    public int getKdt() {
        return kdt;
    }

    public void setKdt(int kdt) {
        this.kdt = kdt;
    }

    public int getHour() {
        return hour;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }



}
