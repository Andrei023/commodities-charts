package eu.heisenbug.commoditiescharts.controller;

import eu.heisenbug.commoditiescharts.model.DatePricePair;
import eu.heisenbug.commoditiescharts.service.ChartsService;
import eu.heisenbug.commoditiescharts.utils.Symbol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
public class ChartsController {

    @Autowired
    private ChartsService chartsService;

    @RequestMapping("/charts")
    public ModelAndView index(Model model) {
        Map<Symbol, List<DatePricePair>> result = chartsService.getPricesMap(Arrays.asList(Symbol.BITCOIN, Symbol.GOLD, Symbol.BRENT_CRUDE_OIL));

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("chart.html");
        Map<String, Object> modelData = modelAndView.getModel();

        modelData.put("btcList", chartsService.getPricesFromMap(result, Symbol.BITCOIN));
        modelData.put("goldList", chartsService.getPricesFromMap(result, Symbol.GOLD));
        modelData.put("oilList", chartsService.getPricesFromMap(result, Symbol.BRENT_CRUDE_OIL));
        modelData.put("daysList", chartsService.getTimeFrame());

        return modelAndView;
    }
}
