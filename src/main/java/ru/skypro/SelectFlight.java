package ru.skypro;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;



public class SelectFlight {

    /**<pre>
    *  Вылет до текущего момента времени.
    *  Сегменты с датой прилёта раньше даты вылета.
    *  Перелеты, где общее время, проведённое на земле,
    *  превышает два часа
    * (время на земле — это интервал между прилётом одного сегмента
    * и вылетом следующего за ним).</pre>
     */
    public static List<Flight> getFlight() {

        var nowDateTime = LocalDateTime.now();
        List<Flight> resListFlight = new ArrayList<>();

        FlightBuilder.createFlights()
                .stream()
                .forEach(flight-> {
                    var lsSegmErr = flight.getSegments().stream()
                            .filter(segment -> !(segment.getArrivalDate().isAfter(segment.getDepartureDate())
                                    && segment.getDepartureDate().isAfter(nowDateTime)))
                            .toList();

                    if (lsSegmErr.size() > 0) {
                        return;
                    }

                    var objNumberAccount = new Object(){
                        public int numAccount;
                        public Instant instArriv;

                        public void setNumAccount(LocalDateTime departureDate) {
                            Instant instDepart = departureDate.toInstant(ZoneOffset.UTC);
                            Duration dur = Duration.between(instDepart, instArriv);

                            int minutes = (int) dur.toMinutes();
                            numAccount += Math.abs(minutes);
                        }

                        public int getHour()  {
                            return numAccount / 60;
                        }
                    };

                    flight.getSegments().forEach(segment -> {
                                if (objNumberAccount.instArriv == null) {
                                    objNumberAccount.instArriv = segment.getArrivalDate().toInstant(ZoneOffset.UTC);
                                    return;
                                }

                                objNumberAccount.setNumAccount(segment.getDepartureDate());
                            }
                    );

                    if (objNumberAccount.getHour() <= 2) {
                        resListFlight.add(flight);
                    }
                });


        return resListFlight;
    }

}
