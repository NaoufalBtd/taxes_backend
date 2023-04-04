package taxes.dao;

import taxes.bean.TauxTaxeIS;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface TauxTaxeIsDao extends JpaRepository<TauxTaxeIS,Long> {

    @Query("SELECT t FROM TauxTaxeIS t WHERE (t.resultatMin <= :resultatAvantImpot AND t.resultMax >= :resultatAvantImpot) or (t.resultatMin <= :resultatAvantImpot AND t.resultMax is null )" )
    TauxTaxeIS findByResultatAvantImpot(@Param("resultatAvantImpot") double resultatAvantImpot);
    TauxTaxeIS findByDateApplicationDebutAndDateApplicationFin(Date dateDebut ,Date dateFin);
    int deleteByDateApplicationDebutAndDateApplicationFin(Date dateDebut, Date dateFin);

}
