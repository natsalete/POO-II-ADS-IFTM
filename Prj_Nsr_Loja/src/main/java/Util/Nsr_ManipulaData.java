package Util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 *
 * @author natsa
 */
public class Nsr_ManipulaData {

    public Date Nsr_string2Date(String nsr_data) {
        DateTimeFormatter formato
                = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        Date nsr_dataSaida = Date.valueOf(LocalDate.parse(nsr_data, formato));

        return nsr_dataSaida;
    }

    public String Nsr_date2String(String nsr_data) {
        try {
            java.util.Date nsr_date = new SimpleDateFormat("yyyy-MM-dd").
                    parse(nsr_data);
            nsr_data = new SimpleDateFormat("dd/MM/yyyy").format(nsr_date);
        } catch (ParseException e) {
            System.out.println(e);
        }
        return nsr_data;
    }

}
