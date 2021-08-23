package com.zpmc.ztos.infra.base.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BaseBO<T, ID> {
    JpaRepository<T, ID> getRepository();
}
