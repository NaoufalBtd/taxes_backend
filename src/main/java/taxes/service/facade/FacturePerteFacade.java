package taxes.service.facade;

import org.springframework.data.domain.PageRequest;
import taxes.bean.FacturePerte;
import taxes.bean.Societe;

import java.util.Date;
import java.util.List;

public interface FacturePerteFacade {

    List<FacturePerte> findBySociete(String ice, PageRequest pageRequest);

    List<FacturePerte> findBySocieteAndDate(String ice, Date startDate, Date endDate, PageRequest pageRequest);

    public int save(FacturePerte facturePerte);


    List<Object[]> getIncomeInvoicesSumByMonth(String ice, long month);
}
