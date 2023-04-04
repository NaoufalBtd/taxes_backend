package taxes.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import taxes.bean.Employe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeDao extends JpaRepository<Employe, Long> {

    Employe findByCin(String cin);

    int deleteByCin(String cin);

    Employe findBySalaire(double salaire);

    List<Employe> findBySocieteIce(String ice);
    Page<Employe> findBySocieteIce(String ice, Pageable pageable);
    Page<Employe> findBySocieteIceAndNomContains(String ice, String name, Pageable pageable);

    @Query("select e from Employe e where e.societe.ice = ?1 and e not in (select t.employe from TaxeIREmployes t where t.taxeIR.mois = ?2 and t.taxeIR.annee = ?3)")
    List<Employe> findUndeclaredEmployes(String ice, int mois, int annee);

    @Query("select count(e) from Employe e where e.societe.ice = ?1")
    int currentEmployesCount(String ice);
}
