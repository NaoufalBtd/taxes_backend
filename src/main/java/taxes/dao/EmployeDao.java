package taxes.dao;

import taxes.bean.Employe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface  EmployeDao extends JpaRepository<Employe, Long> {

    Employe findByCin ( String cin) ;

    int deleteByCin(String cin);
    Employe findBySalaire(double salaire );
    List<Employe> findBySocieteIce(String ice);

}
