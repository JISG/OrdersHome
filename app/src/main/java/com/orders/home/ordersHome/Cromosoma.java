package com.orders.home.ordersHome;


import java.util.ArrayList;
public class Cromosoma {
    private int[] cromoRoute;
    private int[][] cromoDistance;
    private int[][] cromoBadConditions;
    private Deliver[] cromoToDeliver;

    public Deliver[] toDeliver;
    public Deliver[] toDeliverAftr;

    //Deliver horaDeSalida; //predefinida
    private int kdtT;
    private int kdtE;
    private int olguraEntrega = 15;
    private int maxSpeed = 14; // m/s
    private int startHour = 6 * 60;
    private int identity = 0;

    //Pesos de prioridad fitness
    double WT = 0.99;
    double WD = 0.0001;

    ArrayList<Deliver> cromoListDeliver = new ArrayList();


    public Cromosoma(int[][] cromoDistance, int[][] cromoBadCondtions, Deliver[] cromoToDeliver, int kdtT){
        //this.cromoRoute = cromoRoute;
        this.cromoDistance = cromoDistance;
        this.cromoBadConditions = cromoBadCondtions;
        this.cromoToDeliver = cromoToDeliver;

//        for (int i = 0; i < cromoToDeliver.length; i++) {
//            identity += cromoToDeliver[i].getId();
//        }

    }
    public Cromosoma(Deliver[] cromoToDeliver){
        this.cromoToDeliver = cromoToDeliver;
    }


    public Cromosoma(){

    }


    public int getNewIdentity(){
        int idnt = 0;
        for (int i = 0; i < cromoToDeliver.length; i++) {
            idnt += cromoToDeliver[i].getId();
        }

        return idnt;
    }

    public double fitness(){
        //splitList();

        //setCromoRouteFromDeliver();
        int cont = 0;

        double time2Arrive = startHour;
        int penalty = 0;
        int timeDeliver = 0;
        double fitness = 0;

        for (int i = 0; i < cromoToDeliver.length-1; i++) {
            timeDeliver = cromoToDeliver[i + 1].getTime2Deliver();

            time2Arrive += getTime2Arrive(cromoToDeliver[i].getNeighbor(), cromoToDeliver[i + 1].getNeighbor());

            double doft = time2Arrive - timeDeliver;
            penalty += Math.abs(doft);
        }

        int totalDistance = 0;
        for(int i = 0; i < cromoToDeliver.length-1; i++){
            totalDistance += getTotalDistance(cromoToDeliver[i].getNeighbor(), cromoToDeliver[i + 1].getNeighbor());
        }

        fitness = penalty;
        //System.out.println(time2Arrive);
        fitness = (WT * penalty) + (WD * totalDistance);

        return fitness;



    }

    public double fitness(int pos){
        int cont = 0;

        double time2Arrive = startHour;
        int penalty = 0;
        int timeDeliverOne = 0;
        int timeDeliverTwo = 0;
        double fitness = 0;

        for (int i = 0; i < cromoToDeliver.length-2; i++) {
            timeDeliverOne = cromoToDeliver[i + 1].getTime2Deliver();
            timeDeliverTwo = cromoToDeliver[i + 2].getTime2Deliver();
            //time2Arrive += getTime2Arrive(cromoToDeliver[i].getNeighbor(), cromoToDeliver[i + 1].getNeighbor());

            double doft = timeDeliverOne - timeDeliverTwo;
            penalty += Math.abs(doft);
        }

        return penalty;


    }

    public int getTotalDistance(int neighbor1, int neighbor2){
        return(cromoDistance[neighbor1][neighbor2]);
    }


    public int getTime2Arrive(int indexDistance1, int indexDistance2){
//       this.cromoToDeliver[i].getNeighbor() System.out.println("entrega1 " + indexDistance1 + " Entrega2 " + indexDistance2);
        double distance = cromoDistance[indexDistance1][indexDistance2];
        int time = (int) distance / maxSpeed;
        //System.out.println("time -> " + indexDistance1 + " to " + indexDistance2 + " = " + time);

        return time / 60; //resultado en segundos
    }


    public int sec2Min(int seconds){

        return seconds / 60;
    }

    public int getKdtT() {
        return kdtT;
    }

    public void setKdtT(int kdtT) {
        this.kdtT = kdtT;
    }

    public int[] getCromoRoute() {
        int[] cr = new int[this.cromoToDeliver.length];
        for (int i = 0; i < this.cromoToDeliver.length; i++) {
            cr[i] = cromoToDeliver[i].getNeighbor();
        }
        return cromoRoute;
    }

    public void setCromoRoute(int[] cromoRoute) {
        for (int i = 0; i < cromoRoute.length; i++) {
            cromoToDeliver[i].setNeighbor(i);
        }
    }


    public int[][] getCromoDistance() {
        return cromoDistance;
    }

    public void setCromoDistance(int[][] cromoDistance) {
        this.cromoDistance = cromoDistance;
    }

    public int getIdentity() {
        return identity;
    }

    public void setIdentity(int identity) {
        this.identity = identity;
    }

    public int[][] getCromoBadConditions() {
        return cromoBadConditions;
    }

    public void setCromoBadConditions(int[][] cromoBadConditions) {
        this.cromoBadConditions = cromoBadConditions;
    }

    public Deliver[] getCromoToDeliver() {
        return this.cromoToDeliver;
    }

    public void setCromoToDeliver(Deliver[] cromoToDeliver) {
        this.cromoToDeliver = cromoToDeliver;
    }




}

