package com.example.smartwarehouse;

public class WarehouseEntry {

    private String name;
    private String address;
    private String area;
    private int number;

    public WarehouseEntry(String name, String address, String area, int number) {
        this.name = name;
        this.address = address;
        this.area = area;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getArea() {
        return area;
    }

    public int getNumber() {
        return number;
    }
}
