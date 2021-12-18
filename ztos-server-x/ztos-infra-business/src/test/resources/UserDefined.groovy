
package com.zpmc.ztos.customized

import com.zpmc.ztos.infra.business.account.User;

def calcSum(from, to) {
    int res = 0;
    for (int i = from; i <= to; i++) {
        res += i
    }
    return res
}

def createMyUser() {
    return User.create("userGroovy", "userGroovy")
}



