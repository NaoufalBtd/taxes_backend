package taxes.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import taxes.bean.Societe;
import taxes.bean.User;
import taxes.dao.UserDao;
import taxes.service.facade.UserFacade;

@Service
public class UserService implements UserFacade {
    @Transactional
    @Override
    public User save(User user) {
        if (user == null) {
            return null;
        }
//        Societe societe = societeService.findByIce(user.getSociete().getIce());
//        if (societe == null) {
//            return null;
//        }

        String psw = user.getPassword();
        String encodedPsw = passwordEncoder.encode(psw);

        user.setPassword(encodedPsw);
//        user.setSociete(societe);
        return userDao.save(user);

    }


    @Override
    public int update(User user) {
        return 0;
    }

    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    public User findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public User findByUsernameAndPassword(String username, String password) {
        return null;
    }

    @Override
    public int deleteByUsername(String username) {
        return 0;
    }

    @Autowired
    private UserDao userDao;
    @Autowired
    private SocieteService societeService;
    @Autowired
    private PasswordEncoder passwordEncoder;
}
