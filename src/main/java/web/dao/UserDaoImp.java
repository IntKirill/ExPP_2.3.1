package web.dao;

import org.springframework.stereotype.Repository;
import web.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;


@Repository
public class UserDaoImp implements UserDao {


    @PersistenceContext
    public EntityManager entityManager;


    @Override
    public void save(User user) {
        entityManager.persist(user);
        //добавление
    }


    @Override
    public User findById(Long id) {
        return entityManager.find(User.class, id);
        //получение
    }

    @Override
    public void removeUserById(long id) {
        User user = entityManager.find(User.class, id);
        entityManager.remove(user);
        //удаление по ид
    }


    @Override
    public void updateUser(User user) {
        entityManager.merge(user);
        //изменение по ид
    }


    @Override
    public List<User> findAll() {
        return entityManager.createQuery("FROM User", User.class).getResultList();
        //весь список
    }
}
