package taxes.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import taxes.bean.Societe;
import taxes.bean.TaxeIR;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Repository
public interface TaxeIRDao extends JpaRepository<TaxeIR, Long> {


    @Transactional
    int deleteByMoisAndAnneeAndSocieteIce(int mois, int annee, String ice);

    TaxeIR findByMoisAndAnneeAndSocieteIce(int mois, int annee, String ice);

    TaxeIR findBySocieteAndDateDeclaration(Societe societe, Date dateDeclaration);

    @Query("select sum(t.montantIR)from TaxeIR t where t.annee=:annee And t.mois=:mois")
    BigDecimal calculStatic(@Param("mois") int mois ,@Param("annee") int annee );


}






