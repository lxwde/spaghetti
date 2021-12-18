package com.zpmc.ztos.customized

import com.zpmc.ztos.infra.business.account.User
import com.zpmc.ztos.infra.business.base.UserValidationRule;

class UserDefinedValidationRule implements UserValidationRule {
    @Override
    boolean validate(User user) {
        return user.firstName.startsWith("user")
    }
}
