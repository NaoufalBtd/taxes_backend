package taxes.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import taxes.bean.ISItem;


public interface ISItemDao extends JpaRepository<ISItem, Long> {

}
