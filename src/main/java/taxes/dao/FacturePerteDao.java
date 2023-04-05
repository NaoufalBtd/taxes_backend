package taxes.dao;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import taxes.bean.FactureGagne;
import taxes.bean.FacturePerte;
import taxes.bean.Societe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import taxes.bean.SumInvoicesByMonth;

import java.util.Date;
import java.util.List;

@Repository
public interface FacturePerteDao extends JpaRepository<FacturePerte, Long> {

    List<FacturePerte> findBySocieteIceAndDateFactureBetween(String ice, Date debut, Date fin);

    List<FacturePerte> findBySocieteIceAndDateFactureBetween(String ice, Date debut, Date fin, PageRequest pageRequest);

    @Query("SELECT MONTH(f.dateFacture) as month, YEAR(f.dateFacture) as year, SUM(f.montantTTC) as sumTTC "
            + "FROM FactureGagne f "
            + "WHERE f.dateFacture >= :sixMonthsAgo "
            + "GROUP BY YEAR(f.dateFacture), MONTH(f.dateFacture)")
    public List<Object[]> getSumTTCByMonthForLastSixMonths(@Param("sixMonthsAgo") Date sixMonthsAgo);
    List<FacturePerte> findBySocieteIce(String ice, PageRequest pageRequest);


}

