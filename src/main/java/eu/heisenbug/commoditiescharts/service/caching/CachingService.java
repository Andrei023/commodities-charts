package eu.heisenbug.commoditiescharts.service.caching;

import eu.heisenbug.commoditiescharts.utils.Symbol;

import java.time.LocalDate;

public interface CachingService {

    boolean isDataCached(LocalDate startData, Symbol symbol);

    String getData(LocalDate startData, Symbol symbol);

    void saveData(LocalDate startData, String data, Symbol symbol);
}
