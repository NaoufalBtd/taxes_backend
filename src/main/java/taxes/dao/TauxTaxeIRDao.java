package taxes.dao;


import taxes.bean.TauxTaxeIR;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository

public interface TauxTaxeIRDao extends JpaRepository<TauxTaxeIR, Long> {


    List<TauxTaxeIR> findByPourcentage(double pourcentage);

    @Query("select t from TauxTaxeIR t WHERE t.salaireMax >= :salaireNet AND t.salaireMin <= :salaireNet")
    TauxTaxeIR findBySalaireMaxAndSalaireMin(@Param("salaireNet") double salaireNet);

@Transactional
    int deleteByPourcentage (double pourcentage);


    @Override
    TauxTaxeIR getReferenceById(Long aLong);
}



