package eu.heisenbug.commoditiescharts.service;

import eu.heisenbug.commoditiescharts.utils.Symbol;

import java.time.LocalDate;

public interface CommoditiesService {

    String retrieveCommoditiesPrices(LocalDate startDate, LocalDate endDate, Symbol commodity);
}
