package eu.heisenbug.commoditiescharts.service;

import eu.heisenbug.commoditiescharts.service.caching.CachingService;
import eu.heisenbug.commoditiescharts.utils.Symbol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

import java.time.LocalDate;

import static eu.heisenbug.commoditiescharts.utils.Routes.COMMODITIES_BASE_URL;
import static eu.heisenbug.commoditiescharts.utils.Routes.TIMESERIES_ENDPOINT;
import static eu.heisenbug.commoditiescharts.utils.Routes.DASH;

@Service
public class CommoditiesServiceImpl implements CommoditiesService {

    Logger LOGGER = LoggerFactory.getLogger(CommoditiesServiceImpl.class);

    @Value("${commodities.service.access.key}")
    private String accessKey;

    private RestTemplate restTemplate;

    @Autowired
    private CachingService cachingService;

    @PostConstruct
    private void init() {
        restTemplate = new RestTemplate();
    }

    @Override
    public String retrieveCommoditiesPrices(LocalDate startDate, LocalDate endDate, Symbol commodity) {
        String url = COMMODITIES_BASE_URL + DASH + TIMESERIES_ENDPOINT +
                "?access_key=" + this.getAccessKeyParam() +
                "&start_date=" + startDate +
                "&end_date=" + endDate +
                "&base=USD" +
                "&symbols=" + commodity.value;

        if (cachingService.isDataCached(startDate, commodity)) {
            LOGGER.info("Hit {} on {} in cache. Skipping request to API.", commodity, startDate);
            return cachingService.getData(startDate, commodity);
        }

        try {
            LOGGER.info("Executing GET request for {} on {} to historical endpoint.", commodity, startDate);
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            if (!response.getStatusCode().is2xxSuccessful()) {
                LOGGER.error("Commodities prices service responded with {}", response.getStatusCode());
            }
            LOGGER.info("Response GET request to historical endpoint: {}", response.getStatusCode().value());
            String body = response.getBody();
            cachingService.saveData(startDate, body, commodity);
            return body;
        } catch (RestClientException e) {
            LOGGER.error("Error sending request to commodities prices service. Exception: {}", e.toString());
            return null;
        }
    }

    private String getAccessKeyParam() {
        return accessKey;
    }
}
