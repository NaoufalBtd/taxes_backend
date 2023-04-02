package taxes.service.impl;


import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import taxes.bean.Employe;
import taxes.dao.EmployeDao;
import taxes.service.facade.EmployeFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class EmployeService implements EmployeFacade {

    @Autowired
    private EmployeDao employeDao;


    public Employe findByCin(String cin) {
        return employeDao.findByCin(cin);
    }

    public List<Employe> findAll(PageRequest pageRequest) {
        return employeDao.findAll(pageRequest).getContent();
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

    @Override
    public List<Employe> findBySocieteIce(String ice) {
        return employeDao.findBySocieteIce(ice);
    }

    @Override
    public Employe findBySalaire(double salaire) {
        return null;
    }



}
