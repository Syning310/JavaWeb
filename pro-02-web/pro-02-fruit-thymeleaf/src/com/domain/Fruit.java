package com.domain;

public class Fruit {
    // javabean

    private String name;
    private Double price;
    private Integer count;
    private String remark;

    public Fruit() { }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "Fruit{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", count=" + count +
                ", remark='" + remark + '\'' +
                '}';
    }

    public Fruit(String name, Double price, Integer count, String remark) {
        this.name = name;
        this.price = price;
        this.count = count;
        this.remark = remark;
    }
}
