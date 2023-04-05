package taxes.service.facade;


import org.springframework.data.domain.PageRequest;
import taxes.bean.FactureGagne;

import javax.swing.plaf.PanelUI;
import java.util.Date;
import java.util.List;

public interface FactureGagneFacade {


    //    @Autowired
    //    TaxeIsService taxeIsService;
    List<FactureGagne> findBySocieteIceAndDateFactureBetween(String ice, Date startDate, Date endDate);

    public  int save(FactureGagne factureGagne);
    

    List<Object[]> getLastSixMonthsIncomeInvoicesSumPerMonth(String ice);

    List<FactureGagne> findBySociete(String ice, PageRequest pageRequest);

    List<FactureGagne> findBySocieteAndDate(String ice, Date startDate, Date endDate, PageRequest pageRequest);
}
