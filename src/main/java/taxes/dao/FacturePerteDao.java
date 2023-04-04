package taxes.dao;

import taxes.bean.FacturePerte;
import taxes.bean.Societe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface FacturePerteDao extends JpaRepository<FacturePerte, Long> {


    List<FacturePerte> findBySocieteIceAndDateFactureBetween(String ice, Date debut, Date fin);



}

