package taxes.service.facade;

import taxes.bean.TauxTaxeIS;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface TauxTaxeISFacade {
    TauxTaxeIS findByResultatAvantImpot(double resultatAvantImpot);

    TauxTaxeIS findByDateApplicationDebutAndDateApplicationFin(Date dateDebut , Date dateFin);
    int deleteByDateApplicationDebutAndDateApplicationFin(Date dateDebut, Date dateFin);
    public int save(TauxTaxeIS tauxTaxeIS);
    public List<TauxTaxeIS> findAll();
    public int update(TauxTaxeIS tauxTaxeIS);

}
