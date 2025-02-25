package models.converters;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.DayOfWeek;
@Converter(autoApply = true)
public class DayOfWeekAttributteConverter implements AttributeConverter<DayOfWeek, String> {
    @Override
    public String convertToDatabaseColumn(DayOfWeek dayOfWeek){
        if(dayOfWeek == null){
            return null;
        }
        return  switch(dayOfWeek){
            case MONDAY -> "Lunes";
            case TUESDAY -> "Martes";
            case WEDNESDAY -> "Miercoles";
            case THURSDAY -> "Jueves";
            case FRIDAY -> "Viernes";
            case SATURDAY -> "Sabado";
            case SUNDAY -> "Domingo";
        };
    }
    @Override
    public DayOfWeek convertToEntityAttribute(String diaDeLaSemana){
        if(diaDeLaSemana == null){
            return null;
        }
        return  switch(diaDeLaSemana){
            case "Lunes" -> DayOfWeek.MONDAY;
            case "Martes" -> DayOfWeek.TUESDAY;
            case "Miercoles" -> DayOfWeek.WEDNESDAY;
            case "Jueves" -> DayOfWeek.THURSDAY;
            case "Viernes" -> DayOfWeek.FRIDAY;
            case "Sabado" -> DayOfWeek.SATURDAY;
            case "Domingo" -> DayOfWeek.SUNDAY;
            default -> throw new IllegalArgumentException("Día inválido: " + diaDeLaSemana);
        };
    }
}
