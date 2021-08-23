package com.zpmc.ztos.infra.business.account.repository;

import com.zpmc.ztos.infra.business.account.User;
import com.zpmc.ztos.infra.business.account.UserDO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {
    List<User> findAllByFirstName(String firstName);
    User findOneByFirstNameAndLastName(String firstName, String lastName);
    Page<UserDO> findByFirstName(String firstName, Pageable pageable);
}
