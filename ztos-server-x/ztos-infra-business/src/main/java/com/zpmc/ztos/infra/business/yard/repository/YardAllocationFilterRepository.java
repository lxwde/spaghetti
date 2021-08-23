package com.zpmc.ztos.infra.business.yard.repository;

import com.zpmc.ztos.infra.business.yard.YardAllocationFilter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface YardAllocationFilterRepository extends JpaRepository<YardAllocationFilter, Integer> {
    YardAllocationFilter findOneByName(String name);
}
