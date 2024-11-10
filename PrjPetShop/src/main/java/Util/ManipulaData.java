package Util;

import java.sql.Date;
import java.text.ParseException;
import java.time.LocalDate;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

public class ManipulaData {
    public Date string2date(String data){
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        Date dataSaida = Date.valueOf(LocalDate.parse(data, formato));
        
        return dataSaida;
    }
    
    public String date2String(String data) {
        try {
            java.util.Date date = new SimpleDateFormat("yyyy-MM-dd").parse(data);
            data = new SimpleDateFormat("dd/MM/yyyy").format(date);
        } catch (ParseException e) {
            System.out.println(e);
        }

        return data;
    }
}
