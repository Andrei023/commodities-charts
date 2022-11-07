package eu.heisenbug.commoditiescharts.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.heisenbug.commoditiescharts.model.DatePricePair;
import eu.heisenbug.commoditiescharts.model.response.historical.HistoricalResponse;
import eu.heisenbug.commoditiescharts.utils.Date;
import eu.heisenbug.commoditiescharts.utils.Math;
import eu.heisenbug.commoditiescharts.utils.Symbol;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.VisibleForTesting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ChartsServiceImpl implements ChartsService {

    private static final int DAYS_TIMEFRAME = 7;

    Logger LOGGER = LoggerFactory.getLogger(ChartsServiceImpl.class);

    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private CommoditiesService commoditiesService;

    @Override
    public Map<Symbol, List<DatePricePair>> getPricesMap(List<Symbol> symbols) {
        Map<Symbol, List<DatePricePair>> pricesMap = new HashMap<>();
        for (Symbol symbol : symbols) {
            String response =
                    commoditiesService.retrieveCommoditiesPrices(this.getStartDate(), this.getEndDate(), symbol);
            HistoricalResponse historicalResponse = this.parseHistoricalResponse(response);
            pricesMap.put(symbol, extractDatePricePairs(historicalResponse.getData().getRates(), symbol));
        }
        return pricesMap;
    }

    @Override
    public Object getPricesFromMap(Map<Symbol, List<DatePricePair>> result, Symbol symbol) {
        return result.get(symbol).stream().map(DatePricePair::getRate).collect(Collectors.toList());
    }

    @Override
    public List<LocalDate> getTimeFrame() {
        LocalDate start = this.getStartDate();
        LocalDate end = this.getEndDate();

        int days = (int) start.until(end.plusDays(1), ChronoUnit.DAYS);

        return Stream.iterate(start, d -> d.plusDays(1))
                .limit(days)
                .collect(Collectors.toList());
    }

    @VisibleForTesting
    protected HistoricalResponse parseHistoricalResponse(String response) {
        if (StringUtils.isBlank(response)) {
            return null;
        }
        try {
            return mapper.readValue(response, HistoricalResponse.class);
        } catch (JsonProcessingException e) {
            LOGGER.error("Exception encountered while processing response from historical endpoint: {}", e.toString());
            return null;
        }
    }

    @VisibleForTesting
    protected List<DatePricePair> extractDatePricePairs(Map<String, Map<String, Number>> responseMap,
                                                        Symbol symbol) {
        List<DatePricePair> datePricePairs = new ArrayList<>();
        for (Map.Entry<String, Map<String, Number>> entry : responseMap.entrySet()) {
            DatePricePair datePricePair = new DatePricePair();
            datePricePair.setDate(Date.stringToLocalDate(entry.getKey()));
            datePricePair.setRate(Math.invertValue((Double) entry.getValue().get(symbol.value)));
            datePricePairs.add(datePricePair);
        }
        return datePricePairs;
    }

    private LocalDate getStartDate() {
        return LocalDate.now().minusDays(DAYS_TIMEFRAME).minusDays(1);
    }

    private LocalDate getEndDate() {
        return LocalDate.now().minusDays(2);
    }
}
