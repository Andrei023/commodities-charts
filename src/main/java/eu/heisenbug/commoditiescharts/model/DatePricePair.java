package eu.heisenbug.commoditiescharts.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class DatePricePair {

    private LocalDate date;
    private double rate;
}
