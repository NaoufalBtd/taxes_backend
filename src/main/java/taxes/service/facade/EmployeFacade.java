package taxes.service.facade;


import org.springframework.data.domain.PageRequest;
import taxes.bean.Employe;

import java.util.List;

public interface EmployeFacade {

    public Employe findByCin(String cin) ;
    public List<Employe> findAll(PageRequest pageRequest);

    public List<Employe> findUndeclaredEmployes(String ice, int mois, int annee);

    List<Employe> findBySociete(String ice, PageRequest pageRequest);

    List<Employe> findBySociete(String ice);


    List<Employe> findBySocieteAndName(String ice, String name, PageRequest pageRequest);

    public int deleteByCin(String cin) ;

    public int save(Employe employe) ;
    public List<Employe> findBySocieteIce(String ice);
    Employe  findBySalaire(double salaire );

    int currentEmployesCount(String ice);
}
