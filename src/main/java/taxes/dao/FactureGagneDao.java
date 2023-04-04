package taxes.dao;

import taxes.bean.FactureGagne;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Date;
import java.util.List;

@Repository
public interface FactureGagneDao extends JpaRepository<FactureGagne,Long> {

    List<FactureGagne> findBySocieteIceAndDateFactureBetween(String ice, Date debut, Date fin);

}
