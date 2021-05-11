
package fr.lyes.gds.Security.service;


import fr.lyes.gds.Buisness.Admin.Dao.UserDao;
import fr.lyes.gds.Buisness.Admin.Data.Dto.UserDto;
import fr.lyes.gds.Buisness.Admin.Data.Entities.User;
import fr.lyes.gds.Security.data.UserPrinciple;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserDetailServicesImpl implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private ModelMapper modelMapper;


    private static final Logger logger = LoggerFactory.getLogger(UserDetailServicesImpl.class);



    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findByUsername(username);
        System.out.println("findUser"+user.getProfilImage());
if (user == null)
            logger.error("user in UserDetailServicesImpl is null");
            new UsernameNotFoundException("User Not Found with -> username or email : " + username);
        UserDto userdto = modelMapper.map(user, UserDto.class);
        return UserPrinciple.build(userdto);
    }

}
