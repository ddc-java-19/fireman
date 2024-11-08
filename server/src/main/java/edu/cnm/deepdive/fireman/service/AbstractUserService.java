package edu.cnm.deepdive.fireman.service;

import java.util.UUID;

import edu.cnm.deepdive.fireman.model.entity.User;
import java.util.UUID;

public interface AbstractUserService {


  User getOrCreate(String oauthKey, String displayName);

  User get(UUID externalKey);

  User getCurrent();

  User update(User user);

}

