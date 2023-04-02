package taxes.service.facade;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import taxes.bean.User;

public interface UserFacade {
    public User save(User user);


    public int update(User user);

    public User findByUsername(String username);
    public User findByEmail(String email);
    public User findByUsernameAndPassword(String username, String password);
    public int deleteByUsername(String username);
}
