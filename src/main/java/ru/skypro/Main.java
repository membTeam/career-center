package ru.skypro;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;


public class Main {
    public static void main(String[] args) {
        /*
        Вылет до текущего момента времени.
        Сегменты с датой прилёта раньше даты вылета.
        Перелеты, где общее время, проведённое на земле,
            превышает два часа
            (время на земле — это интервал между прилётом одного сегмента
                и вылетом следующего за ним).
         */

        var nowDate = LocalDateTime.now();
        List<Flight> lsFlight = new ArrayList<>();

        FlightBuilder.createFlights()
                .stream()
                .forEach(flight-> {
                    var lsSegmErr = flight.getSegments().stream()
                        .filter(segment -> !(segment.getArrivalDate().isAfter(segment.getDepartureDate())
                                    && segment.getDepartureDate().isAfter(nowDate)))
                        .toList();

                    if (lsSegmErr.size() > 0) {
                        return;
                    }

                    var numberAccount = new Object(){
                        public int sumData;
                        public Instant instArriv;

                        public void setSumData(LocalDateTime departureDate) {
                            Instant instDepart = departureDate.toInstant(ZoneOffset.UTC);
                            Duration dur = Duration.between(instDepart, instArriv);

                            int minutes = (int) dur.toMinutes();
                            sumData += Math.abs(minutes);
                        }
                    };

                    flight.getSegments().forEach(segment -> {
                            if (numberAccount.instArriv == null) {
                                numberAccount.instArriv = segment.getArrivalDate().toInstant(ZoneOffset.UTC);
                                return;
                            }

                            numberAccount.setSumData(segment.getDepartureDate());
                        }
                    );

                    if (numberAccount.sumData / 60 <= 2) {
                        lsFlight.add(flight);
                    }
                });

        System.out.println("Кол-во рейсов: " + lsFlight.size());
        lsFlight.forEach(System.out::println);

    }
}