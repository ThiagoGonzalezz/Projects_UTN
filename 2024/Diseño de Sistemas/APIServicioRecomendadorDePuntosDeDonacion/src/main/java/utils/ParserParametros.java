package utils;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class ParserParametros {

    public static LocalTime parsearHorario(String horarioJson){
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH.mm.ss");
            return LocalTime.parse(horarioJson, formatter);
        } catch (DateTimeParseException e) {
            System.out.println("HorarioBuscado inválido: " + horarioJson);
            return null;
        }
    }

    public static List<DayOfWeek> parsearDias(String diasJson){
        try {
            List<DayOfWeek> diasList = new ArrayList<>();
            String[] diasArray = diasJson.split(",");

            for (String dia : diasArray) {
                switch (dia.trim().toLowerCase()) {
                    case "lunes":
                        diasList.add(DayOfWeek.MONDAY);
                        break;
                    case "martes":
                        diasList.add(DayOfWeek.TUESDAY);
                        break;
                    case "miercoles":
                        diasList.add(DayOfWeek.WEDNESDAY);
                        break;
                    case "jueves":
                        diasList.add(DayOfWeek.THURSDAY);
                        break;
                    case "viernes":
                        diasList.add(DayOfWeek.FRIDAY);
                        break;
                    case "sabado":
                        diasList.add(DayOfWeek.SATURDAY);
                        break;
                    case "domingo":
                        diasList.add(DayOfWeek.SUNDAY);
                        break;
                    default:
                        throw new IllegalArgumentException("Día inválido: " + dia);
                }
            }
            return diasList;
        } catch (IllegalArgumentException e) {
            System.out.println("DíasBuscados inválidos: " + diasJson);
            return null;
        }
    }

    public static Float parsearRadio(String radioJson) {
        try {
            return Float.parseFloat(radioJson);
        } catch (NumberFormatException e) {
            System.out.println("RadioEnKm inválidos: " + radioJson);
            return null;
        }
    }

    public static Double parsearCoordenada(String coordenadaJson) {
        try {
            return Double.parseDouble(coordenadaJson);
        } catch (NumberFormatException e) {
            System.out.println("Coordenadas geograficas inválidas: " + coordenadaJson);
            return null;
        }
    }

}
