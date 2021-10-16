package com.tplatform.optlockingservice;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.MappedSuperclass;

@Setter
@Getter
@MappedSuperclass
public class BaseEntity {

//    @Version
    private Long version;
}
