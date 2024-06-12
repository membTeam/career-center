package ru.skypro;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;


public class Main {
    public static void main(String[] args) {

        var lsFlight = SelectFlight.getFlight();

        System.out.println("Кол-во рейсов: " + lsFlight.size());
        lsFlight.forEach(System.out::println);

    }
}