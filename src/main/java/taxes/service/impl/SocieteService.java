package taxes.service.impl;

import taxes.bean.Societe;
import taxes.dao.SocieteDao;
import taxes.service.facade.SocieteFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SocieteService implements SocieteFacade {
    @Autowired
    SocieteDao societeDao;

    public List<Societe> findAll() {
        return societeDao.findAll();
    }
    public Societe findByIce(String ice) {
        return societeDao.findByIce(ice);
    }

    public int save(Societe societe) {
        if (findByIce(societe.getIce()) != null) {
            return -1;
        } else {
            societeDao.save(societe);
            return 1;
        }

}
}
