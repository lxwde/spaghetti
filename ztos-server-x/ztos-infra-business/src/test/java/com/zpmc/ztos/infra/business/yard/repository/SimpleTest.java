package com.zpmc.ztos.infra.business.yard.repository;

import org.junit.Test;

import java.util.List;

public class SimpleTest {

    @Test
    public void test() {
        int n = 3;
        int[] A = new int[n], L = new int[n], P = new int[n];
        int res = checkRecord(n, A, L, P);
        System.out.println(res);
    }

    public  int checkRecord(int curr, int n, List<List<Character>> tmp) {
        for (int i = 0; i < n; i++) {
            for (List<Character> str : tmp) {
            }
        }
        return 0;
    }










    public int checkRecord(int n, int[] A, int[] L, int[]P) {
        // A, P, L
        // A < 2
        // L <= 3
        if (n == 1) {
            A[0] = 1;
            L[0] = 1;
            P[0] = 1;
            return A[0] + L[0] + P[0];
        }
        if (n == 2) {
            //"PP" , "AP", "PA", "LP", "PL", "AL", "LA", "LL"
            checkRecord(n - 1, A, L, P);
            A[1] = L[0] + P[0];
            L[1] = A[0] + L[0] + P[0];
            P[1] = A[0] + L[0] + P[0];
            return A[1] + L[1] + P[1];
        }
        checkRecord(n - 1, A, L, P);
        A[n - 1] = L[n - 2] + P[n - 2];
        L[n - 1] = A[n - 2] + P[n - 2] + L[n - 2] - L[n - 3];
        P[n - 1] = A[n - 2] + L[n - 2] + P[n - 2];
        return A[n - 1] + L[n - 1] + P[n - 1];
    }
}
