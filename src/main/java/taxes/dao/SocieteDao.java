package taxes.dao;


import taxes.bean.Societe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SocieteDao extends JpaRepository<Societe, Long> {



    Societe findByLibelle(String libelle);

    int deleteByLibelle(String libelle);

    Societe findByIce(String ice);
}