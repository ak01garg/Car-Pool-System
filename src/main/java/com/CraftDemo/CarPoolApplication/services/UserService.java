package com.CraftDemo.CarPoolApplication.services;


import com.CraftDemo.CarPoolApplication.Repositories.UserRepository;
import com.CraftDemo.CarPoolApplication.dto.request.AddUserRequest;
import com.CraftDemo.CarPoolApplication.models.User;
import com.CraftDemo.CarPoolApplication.utils.ValidationUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.CraftDemo.CarPoolApplication.utils.UserConstants.NULL_USER_VALIDATION_MESSAGE;


public class UserService {

    private final UserRepository userRepository;

    public UserService(){
        this.userRepository = new UserRepository();
    }

    public void updateUser(User user){
        userRepository.updateUser(user);
    }

    public User getUserByName(String userName){
        Map<String, User> userNameMapInstance = userRepository.getUserNameMapInstance();
        if(Objects.nonNull(userNameMapInstance)){
            return userNameMapInstance.get(userName);
        }
        return null;
    }

    public void addUser(AddUserRequest addUserRequest){
        ValidationUtils.ensureNotNull(addUserRequest,NULL_USER_VALIDATION_MESSAGE);
        User user = new User(addUserRequest.getName(),addUserRequest.getAge(),addUserRequest.getGender());
        userRepository.addUser(user);
    }

    public List<User> getAllUsers(){
        return new ArrayList<>(userRepository.getUserIdMapInstance().values());
    }

    public User getUserById(String userId) {
        return userRepository.getUserIdMapInstance().get(userId);
    }
}
