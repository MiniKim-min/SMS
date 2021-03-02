package com.company;

public class Main {

    public static void main(String[] args) {
        try {
            (new Serial()).connect("COM8");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}