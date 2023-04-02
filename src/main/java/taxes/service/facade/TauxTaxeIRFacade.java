package taxes.service.facade;

import taxes.bean.TauxTaxeIR;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface TauxTaxeIRFacade {


   public List<TauxTaxeIR> findByPourcentage(double pourcentage);

     @Query("select t from TauxTaxeIR t WHERE t.salaireMax >= :salaireNet AND t.salaireMin <= :salaireNet")
    public  TauxTaxeIR findBySalaireMaxAndSalaireMin(@Param("salaireNet") double salaireNet);


    public  List<TauxTaxeIR > findAll();

    public int save(TauxTaxeIR tauxTaxeIR );

    public  int deleteByPourcentage(double pourcentage);
}
