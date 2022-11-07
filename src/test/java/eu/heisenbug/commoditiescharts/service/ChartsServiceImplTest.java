package eu.heisenbug.commoditiescharts.service;

import eu.heisenbug.commoditiescharts.model.response.historical.HistoricalResponse;
import eu.heisenbug.commoditiescharts.model.response.historical.HistoricalResponseData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ChartsServiceImplTest {

    private final ChartsServiceImpl chartsService = new ChartsServiceImpl();

    @Test
    public void GIVEN_validHistoricalResponse_WHEN_parseHistoricalResponse_THEN_responseIsParsed() {
        String input = "{\"data\":{\"success\":true,\"timeseries\":true,\"start_date\":\"2022-10-30\"," +
                "\"end_date\":\"2022-11-05\",\"base\":\"USD\",\"rates\":{\"2022-10-30\":{\"BTC\":0.0000480260570175," +
                "\"USD\":1},\"2022-10-31\":{\"BTC\":0.0000484770451496,\"USD\":1},\"2022-11-01\":{\"BTC\":0" +
                ".0000487902220492,\"USD\":1},\"2022-11-02\":{\"BTC\":0.0000488136691942,\"USD\":1}," +
                "\"2022-11-03\":{\"BTC\":0.0000496318803435,\"USD\":1},\"2022-11-04\":{\"BTC\":0.000049487740031," +
                "\"USD\":1},\"2022-11-05\":{\"BTC\":0.000047289048873,\"USD\":1}}}}";

        HistoricalResponse response = chartsService.parseHistoricalResponse(input);

        Assertions.assertNotNull(response);

        HistoricalResponseData data = response.getData();
        Assertions.assertTrue(data.isSuccess());
        Assertions.assertEquals("2022-10-30", data.getStartDate());
        Assertions.assertEquals(7, data.getRates().size());
    }

    @Test
    public void WHEN_getTimeFrame_THEN_rangeIsReturnedCorrectly() {
        List<LocalDate> result = chartsService.getTimeFrame();
        Assertions.assertEquals(7, result.size());
    }
}
