package com.CraftDemo.CarPoolApplication.Repositories;

import com.CraftDemo.CarPoolApplication.exceptions.UserAlreadyExistException;
import com.CraftDemo.CarPoolApplication.exceptions.UserNotFoundException;
import com.CraftDemo.CarPoolApplication.models.User;
import com.CraftDemo.CarPoolApplication.utils.ValidationUtils;


import java.util.Map;
import java.util.Objects;

import static com.CraftDemo.CarPoolApplication.utils.UserConstants.*;


public class UserRepository extends BaseRepository {



    public void addUser(User user){
        ValidationUtils.ensureNotNull(user , NULL_USER_VALIDATION_MESSAGE);
        Map<String, User> userNameMapInstance = getUserNameMapInstance();
        Map<String, User> userIdMapInstance = getUserIdMapInstance();
        User dbUser = userNameMapInstance.get(user.getName());
        if(Objects.isNull(dbUser) || !dbUser.equals(user)){
            userIdMapInstance.put(user.getId(),user);
            userNameMapInstance.put(user.getName(),user);
            return;
        }
        throw new UserAlreadyExistException(DUPLICATE_USER_EXCEPTION_MESSAGE);
    }


    public void updateUser(User user){
        ValidationUtils.ensureNotNull(user , NULL_USER_VALIDATION_MESSAGE);
        Map<String, User> userIdMapInstance = getUserIdMapInstance();
        User dbUser = userIdMapInstance.get(user.getId());
        ValidationUtils.ensureNotNull(dbUser , new UserNotFoundException(USER_NOT_FOUND_EXCEPTION_MESSAGE));
        userIdMapInstance.put(dbUser.getId(),user);
    }


}
