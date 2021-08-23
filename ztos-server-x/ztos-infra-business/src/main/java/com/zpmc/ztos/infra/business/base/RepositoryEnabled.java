package com.zpmc.ztos.infra.business.base;

import com.zpmc.ztos.infra.base.config.ApplicationContextProvider;
import com.zpmc.ztos.infra.business.account.repository.UserRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositoryEnabled<T extends JpaRepository> {

//    public static T getUserRepository() {
//        if (ApplicationContextProvider.APPLICATION_CONTEXT != null) {
//            return ApplicationContextProvider.APPLICATION_CONTEXT.getBean(T.class);
//        }
//        return T;
//    }
}
