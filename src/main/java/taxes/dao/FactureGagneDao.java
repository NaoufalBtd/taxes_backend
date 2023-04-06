package taxes.dao;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import taxes.bean.FactureGagne;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import taxes.bean.SumInvoicesByMonth;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Repository
public interface FactureGagneDao extends JpaRepository<FactureGagne,Long> {
    List<FactureGagne> findBySocieteIceAndDateFactureBetween(String ice, Date debut, Date fin);
    
    List<FactureGagne> findBySocieteIceAndDateFactureBetween(String ice, Date debut, Date fin, PageRequest pageRequest);
//    @Query("SELECT MONTH(f.dateFacture) as month, YEAR(f.dateFacture) as year, SUM(f.montantTTC) as sumTTC "
//            + "FROM FactureGagne f "
//            + "WHERE f.dateFacture >= :sixMonthsAgo AND f.societe.ice = :ice "
//            + "GROUP BY YEAR(f.dateFacture), MONTH(f.dateFacture)")
//    public List<Object[]> getSumTTCByMonthForLastSixMonths(@Param("sixMonthsAgo") Date sixMonthsAgo, @Param("ice") String ice);

    @Query("SELECT MONTH(f.dateFacture) as month, YEAR(f.dateFacture) as year, SUM(f.montantTTC) as sumTTC "
            + "FROM FactureGagne f "
            + "WHERE f.dateFacture >= :months AND f.societe.ice = :ice "
            + "GROUP BY YEAR(f.dateFacture), MONTH(f.dateFacture)")
    public List<Object[]> getSumTTCByMonth(@Param("months") Date months, @Param("ice") String ice);
    List<FactureGagne> findBySocieteIce(String ice, PageRequest pageRequest);
}
