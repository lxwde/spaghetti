package com.zpmc.ztos.infra.business.account.repository;

import com.zpmc.ztos.infra.business.account.User;
import com.zpmc.ztos.infra.business.account.UserDO;
import org.geolatte.geom.Polygon;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {
    String FIND_ALL_BY_FIRST_NAME = "findAllByFirstName";

    @Cacheable(value = FIND_ALL_BY_FIRST_NAME, key = "#p0")
    List<User> findAllByFirstName(String firstName);
    // @Query(" where location SDO_GEOM.SDO_CENTROID(#firstName)")
    User findOneByFirstNameAndLastName(String firstName, String lastName);
    List<User> findByFirstName(String firstName, Pageable pageable);

    @Query("")
    List<User> findUsersByLocation(Polygon location);
}
