package taxes.dao;

import taxes.bean.TaxeIS;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface TaxeISDao extends JpaRepository<TaxeIS, Long> {

    TaxeIS findByAnneeAndTrimestreAndSocieteIce(int annee, int trimestre, String ice);
    TaxeIS findByAnneeAndTrimestre(int annee, int trimestre);

    int deleteByAnneeAndTrimestre(int annee, int trimestre);

    int deleteBySocieteIce(String ice);

    List<TaxeIS> findBySocieteIce(String ice);
    List<TaxeIS> findAll();

    @Query("SELECT sum (t.montantIs) FROM TaxeIS t WHERE t.annee=:annee AND t.trimestre=:trimestre")
    BigDecimal calcStatique(@Param("trimestre") int trimestre, @Param("annee") int annee);

    int deleteByAnneeAndTrimestreAndSocieteIce(int annee, int trimestre, String ice);
}
