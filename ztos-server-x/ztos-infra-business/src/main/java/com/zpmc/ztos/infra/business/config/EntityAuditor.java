package com.zpmc.ztos.infra.business.config;


import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class EntityAuditor implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        // TODO: read from current user information
        return Optional.of("anonymous");
    }

}
