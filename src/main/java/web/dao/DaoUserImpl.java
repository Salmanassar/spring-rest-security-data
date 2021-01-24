package web.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import web.model.User;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Component
public class DaoUserImpl implements DaoUser {

    private final UserRepository userRepository;

    @Autowired
    public DaoUserImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(User user) {
        if (findUserByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("There is the user with the same email");
        }
        return userRepository.save(user);
    }

    @Override
    public User updateUser(User updatedUser) {
        User currentUser = userRepository.findById(updatedUser.getId()).get();
        currentUser.setId(updatedUser.getId());
        currentUser.setFirstName(updatedUser.getFirstName());
        currentUser.setLastName(updatedUser.getLastName());
        currentUser.setAge(updatedUser.getAge());
        currentUser.setEmail(updatedUser.getEmail());
        currentUser.setPassword(updatedUser.getPassword());
        currentUser.setRoles(updatedUser.getRoles());
        return userRepository.save(currentUser);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.delete(getUserById(id).get());
    }

    @Override
    public List<User> listUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

}
