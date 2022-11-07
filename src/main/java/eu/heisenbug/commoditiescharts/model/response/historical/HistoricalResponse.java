package eu.heisenbug.commoditiescharts.model.response.historical;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class HistoricalResponse {

    private HistoricalResponseData data;
}
