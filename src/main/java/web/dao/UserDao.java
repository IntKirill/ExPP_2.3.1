package web.dao;

import web.model.User;

import javax.transaction.Transactional;
import java.util.List;

public interface UserDao {

    void save (User user);

    User findById(Long id);

    void removeUserById(long id);

    void updateUser(User user);

    List<User> findAll();
}
