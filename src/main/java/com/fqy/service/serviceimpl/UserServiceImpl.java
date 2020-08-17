package com.fqy.service.serviceimpl;

import com.fqy.dao.UserDao;
import com.fqy.pojo.User;
import com.fqy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserServiceImpl  implements UserService {

    @Autowired
    UserDao userDao ;

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }
}
