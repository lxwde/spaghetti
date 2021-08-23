package com.zpmc.ztos.infra.business.base;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BaseBO<T, ID> {
    JpaRepository<T, ID> getRepository();
}
