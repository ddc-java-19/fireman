package edu.cnm.deepdive.fireman.service;

import edu.cnm.deepdive.fireman.model.dao.UserRepository;
import edu.cnm.deepdive.fireman.model.entity.User;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements AbstractUserService {

  private final UserRepository userRepository;

  @Autowired
  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public User getOrCreate(String oauthKey, String displayName) {
    return userRepository
        .findByOauthKey(oauthKey)
        .orElseGet(() -> {
          User user = new User();
          user.setOauthKey(oauthKey);
          user.setDisplayName(displayName);
          return userRepository.save(user);
        });
  }

  @Override
  public User get(UUID externalKey) {
    return userRepository
        .findByExternalKey(externalKey)
        .orElseThrow();
  }

  @Override
  public User getCurrent() {
    return (User) SecurityContextHolder.getContext()
        .getAuthentication()
        .getPrincipal();
  }

  @Override
  public User update(User user) {
    return userRepository
        .findById(getCurrent().getId())
        .map((u) -> {
          String displayName = u.getDisplayName();
          if (displayName != null) {
            u.setDisplayName(displayName);
          }
          return userRepository.save(u);
        })
        .orElseThrow();
  }
}