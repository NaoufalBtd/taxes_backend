package taxes.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import taxes.bean.NotificationISDetail;

import java.util.List;

@Repository

public interface NotificationISDetailDao extends JpaRepository<NotificationISDetail, Long> {

//    NotificationISDetail findByLibelle(String libelle);
//    int deleteByLibelle(String libelle);



    @Query("SELECT nd FROM NotificationISDetail nd WHERE NOT EXISTS "
            + "(SELECT t.notificationISDetail FROM TaxeIS t WHERE t.annee = :year AND t.trimestre = :trimester AND nd.societe = t.societe) AND nd.societe.ice = :ice")
    List<NotificationISDetail> findNotificationISDetailsWithoutTaxeIsBySocieteIce(@Param("year") int year, @Param("trimester") int trimester, @Param("ice") String ice);
}