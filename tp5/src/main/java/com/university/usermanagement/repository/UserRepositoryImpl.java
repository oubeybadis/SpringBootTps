//package com.university.usermanagement.repository;
//
//import com.university.usermanagement.model.User;
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.NoResultException;
//import jakarta.persistence.PersistenceContext;
//import jakarta.persistence.TypedQuery;
//import org.springframework.stereotype.Repository;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.Optional;
//
//@Repository
//@Transactional
//public class UserRepositoryImpl implements UserRepository{
//
//    @PersistenceContext
//    private EntityManager entityManager;
//
//    @Override
//    public Optional<User> findByEmailCustom(String email) {
//        String jpql = "SELECT u FROM User u WHERE u.email = :email";
//        TypedQuery<User> query = entityManager.createQuery(jpql, User.class);
//        query.setParameter("email", email);
//
//        try {
//            return Optional.of(query.getSingleResult());
//        } catch (NoResultException e) {
//            return Optional.empty();
//        }
//    }
//}
