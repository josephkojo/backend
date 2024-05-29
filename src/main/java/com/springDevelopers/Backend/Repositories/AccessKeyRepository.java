package com.springDevelopers.Backend.Repositories;

import com.springDevelopers.Backend.Entities.AccessKey;
import com.springDevelopers.Backend.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccessKeyRepository  extends JpaRepository<AccessKey, Integer> {
    List<AccessKey> findAccessKeyByUser(User user);
    AccessKey findAccessKeyById(Integer Id);
    AccessKey getActiveKeyByUser(User user);

}
