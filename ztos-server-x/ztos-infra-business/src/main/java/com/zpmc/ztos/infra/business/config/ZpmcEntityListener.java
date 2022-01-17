package com.zpmc.ztos.infra.business.config;

import com.zpmc.ztos.infra.business.base.AbstractEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Configurable;

import javax.persistence.*;

@Configurable
public class ZpmcEntityListener {
    private final static Logger logger = LoggerFactory.getLogger(ZpmcEntityListener.class);

    @PrePersist
    private void beforeSave(AbstractEntity entity) {
        logger.info("before save: {}", entity);
    }

    @PreUpdate
    private void beforeUpdate(AbstractEntity entity) {
        logger.info("before update: {}", entity);
    }

    @PreRemove
    private void beforeDelete(AbstractEntity entity) {
        logger.info("before delete: {}", entity);
    }

    @PostPersist
    private void afterSave(AbstractEntity entity) {
        logger.info("after save: ", entity);
    }

    @PostUpdate
    private void afterUpdate(AbstractEntity entity) {
        logger.info("after update: ", entity);
    }

    @PostRemove
    private void afterDelete(AbstractEntity entity) {
        logger.info("after delete: ", entity);
    }

    @PostLoad
    private void afterLoad(AbstractEntity entity) {
        logger.info("after load: ", entity);
    }
}
