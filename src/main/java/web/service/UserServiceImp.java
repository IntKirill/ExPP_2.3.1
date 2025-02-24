package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.dao.UserDao;
import web.model.User;

import javax.transaction.Transactional;
import java.util.List;


@Service
public class UserServiceImp implements UserService {



    public UserDao userDao;

    @Autowired
    public UserServiceImp(UserDao userDao) {
        this.userDao = userDao;
    }

    @Transactional
    @Override
    public void save(User user) {
        userDao.save(user);

    }

    @Transactional
    @Override
    public User findById(Long id) {
        return userDao.findById(id);
    }


    @Transactional
    @Override
    public void removeUserById(long id) {
        userDao.removeUserById(id);
    }


    @Transactional
    @Override
    public void updateUser(User user) {
        userDao.updateUser(user);

    }

    @Transactional
    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }
}
