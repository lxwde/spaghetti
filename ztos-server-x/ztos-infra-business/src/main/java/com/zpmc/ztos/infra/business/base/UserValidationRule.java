package com.zpmc.ztos.infra.business.base;

import com.zpmc.ztos.infra.business.account.User;

public interface UserValidationRule {
    boolean validate(User user);
}
