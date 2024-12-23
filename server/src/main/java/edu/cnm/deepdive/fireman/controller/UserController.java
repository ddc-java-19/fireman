package edu.cnm.deepdive.fireman.controller;

import edu.cnm.deepdive.fireman.model.entity.User;
import edu.cnm.deepdive.fireman.service.AbstractUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@Profile("service")
public class UserController {

  private final AbstractUserService userService;

  @Autowired
  public UserController(AbstractUserService userService) {
    this.userService = userService;
  }

  @GetMapping(path = "/me", produces = MediaType.APPLICATION_JSON_VALUE)
  public User get() {
    return userService.getCurrent();
  }

  @PutMapping(path = "/me",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public User put(@RequestBody User user) {
    return userService.update(user);
  }
}
