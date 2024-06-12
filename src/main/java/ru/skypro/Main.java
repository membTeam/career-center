package ru.skypro;

public class Main {
    public static void main(String[] args) {

        var lsFlight = SelectFlight.getFlight();

        System.out.println("Кол-во рейсов: " + lsFlight.size());
        lsFlight.forEach(System.out::println);

    }
}