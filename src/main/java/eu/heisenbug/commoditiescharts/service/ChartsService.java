package eu.heisenbug.commoditiescharts.service;

import eu.heisenbug.commoditiescharts.model.DatePricePair;
import eu.heisenbug.commoditiescharts.utils.Symbol;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface ChartsService {

    Map<Symbol, List<DatePricePair>> getPricesMap(List<Symbol> symbols);

    Object getPricesFromMap(Map<Symbol, List<DatePricePair>> result, Symbol symbol);

    List<LocalDate> getTimeFrame();
}
