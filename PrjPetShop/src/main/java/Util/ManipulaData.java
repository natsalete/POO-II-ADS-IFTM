package Util;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ManipulaData {
    public Date string2date(String data){
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        Date dataSaida = Date.valueOf(LocalDate.parse(data, formato));
        
        return dataSaida;
    }
}
