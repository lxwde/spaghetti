package com.zpmc.ztos.customized


import com.zpmc.ztos.infra.business.base.AbstractBaseCalc;

class UserDefinedCalc extends AbstractBaseCalc {

    @Override
    public int calc(int from, int to) {
        int sum = 0;
        for (int i = from; i < to; i++) {
            sum += i;
        }
        return sum;
    }
}
