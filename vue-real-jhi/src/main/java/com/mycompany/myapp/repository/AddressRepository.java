package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Address;
import com.mycompany.myapp.domain.Job;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Job entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {}
