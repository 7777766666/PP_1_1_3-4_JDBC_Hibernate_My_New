package jm.task.core.jdbc.service;

public class Lol1 {
    public static void main(String[] args) {

        Rrr rr = new Tiger("Amur");
rr.print();


    }
}


class Animal{

}

class Tiger implements Rrr{
String name;

    public Tiger(String name) {
        this.name = name;
    }

    @Override
    public void print() {

    }
}

 interface Rrr{
    void print();

}