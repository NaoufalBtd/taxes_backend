package taxes.service.impl;


import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import taxes.bean.Employe;
import taxes.dao.EmployeDao;
import taxes.service.facade.EmployeFacade;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
@AllArgsConstructor
public class EmployeService implements EmployeFacade {

    private final EmployeDao employeDao;
    private final SocieteService societeService;


    public Employe findByCin(String cin) {
        return employeDao.findByCin(cin);
    }

    public List<Employe> findAll(PageRequest pageRequest) {
        return employeDao.findAll(pageRequest).getContent();
    }

    @Override
    public List<Employe> findBySociete(String ice, PageRequest pageRequest) {
        return employeDao.findBySocieteIce(ice, pageRequest).getContent();
    }
    @Override
    public List<Employe> findBySociete(String ice) {
        return employeDao.findBySocieteIce(ice);
    }
    @Override
    public List<Employe> findBySocieteAndName(String ice, String name, PageRequest pageRequest) {
        return employeDao.findBySocieteIceAndNomContains(ice, name, pageRequest).getContent();
    }

    public int deleteByCin(String cin) {
        return employeDao.deleteByCin(cin);
    }

    public int save(Employe employe) {
        if (findByCin(employe.getCin()) != null) {
            return -1;
        } else {
            employeDao.save(employe);
            return 1;
        }
    }

    public List<Employe> findUndeclaredEmployes(String ice, int mois, int annee) {
        //create date from mois and annee and check if date is in the past
        Date date = new Date(annee, mois, 1);
        if (date.before(new Date())) {
            throw new RuntimeException("Date is in the future");
        }
        return employeDao.findUndeclaredEmployes(ice, mois, annee);
    }

    @Override
    public List<Employe> findBySocieteIce(String ice) {
        return employeDao.findBySocieteIce(ice);
    }

    @Override
    public Employe findBySalaire(double salaire) {
        return null;
    }

    @Override
    public int currentEmployesCount(String ice) {
        var societe = societeService.findByIce(ice);
        if (societe == null) {
            throw new RuntimeException("Societe not found");
        }

        return employeDao.currentEmployesCount(ice);
    }



}
