package eu.heisenbug.commoditiescharts.service.caching;

import eu.heisenbug.commoditiescharts.utils.Symbol;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
public class CachingServiceImpl implements CachingService {

    private Map<Symbol, Map<LocalDate, String>> historicalData;

    @PostConstruct
    public void init() {
        historicalData = new HashMap<>();
        for (Symbol symbol : Symbol.values()) {
            historicalData.put(symbol, new HashMap<>());
        }
    }

    @Override
    public boolean isDataCached(LocalDate startData, Symbol symbol) {
        return historicalData.get(symbol).containsKey(startData);
    }

    @Override
    public String getData(LocalDate startData, Symbol symbol) {
        return historicalData.get(symbol).get(startData);
    }

    @Override
    public void saveData(LocalDate startData, String data, Symbol symbol) {
        historicalData.get(symbol).put(startData, data);
    }
}
