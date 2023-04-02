package taxes.dao;

import taxes.bean.TaxeIREmployes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaxeIREmployesDao extends JpaRepository<TaxeIREmployes,Long> {

    TaxeIREmployes findByTaxeIRId(Long id);



    int deleteByTaxeIRMoisAndTaxeIRAnneeAndSocieteIce(int mois, int annee, String ice);
}
