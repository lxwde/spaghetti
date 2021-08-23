package com.zpmc.ztos.infra.base.common.utils;

import com.sun.istack.localization.Localizable;
import com.zpmc.ztos.infra.base.common.model.FastMath;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;

//import org.apache.commons.math.exception.util.LocalizedFormats;

public class MathUtils {
    public static final double EPSILON = (double)1.110223E-16f;
    public static final double SAFE_MIN = Double.MIN_NORMAL;
    public static final double TWO_PI = Math.PI * 2;
    private static final byte NB = -1;
    private static final short NS = -1;
    private static final byte PB = 1;
    private static final short PS = 1;
    private static final byte ZB = 0;
    private static final short ZS = 0;
    private static final int NAN_GAP = 0x400000;
    private static final long SGN_MASK = Long.MIN_VALUE;
    private static final int SGN_MASK_FLOAT = Integer.MIN_VALUE;
    private static final long[] FACTORIALS = new long[]{1L, 1L, 2L, 6L, 24L, 120L, 720L, 5040L, 40320L, 362880L, 3628800L, 39916800L, 479001600L, 6227020800L, 87178291200L, 1307674368000L, 20922789888000L, 355687428096000L, 6402373705728000L, 121645100408832000L, 2432902008176640000L};

    private MathUtils() {
    }

    public static int addAndCheck(int x, int y) {
        long s = (long)x + (long)y;
        if (s < Integer.MIN_VALUE || s > Integer.MAX_VALUE) {
  //          throw MathRuntimeException.createArithmeticException(LocalizedFormats.OVERFLOW_IN_ADDITION, x, y);
        }
        return (int)s;
    }

    public static long addAndCheck(long a, long b) {
//        return MathUtils.addAndCheck(a, b, LocalizedFormats.OVERFLOW_IN_ADDITION);
        return 0;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static long addAndCheck(long a, long b, Localizable pattern) {
        if (a > b) {
            return MathUtils.addAndCheck(b, a, pattern);
        }
        if (a < 0L) {
            if (b >= 0L) return a + b;
 //           if (Long.MIN_VALUE - b > a) throw MathRuntimeException.createArithmeticException(pattern, a, b);
            return a + b;
        }
//        if (a > Long.MAX_VALUE - b) throw MathRuntimeException.createArithmeticException(pattern, a, b);
        return a + b;
    }

    public static long binomialCoefficient(int n, int k) {
        MathUtils.checkBinomial(n, k);
        if (n == k || k == 0) {
            return 1L;
        }
        if (k == 1 || k == n - 1) {
            return n;
        }
        if (k > n / 2) {
            return MathUtils.binomialCoefficient(n, n - k);
        }
        long result = 1L;
        if (n <= 61) {
            int i = n - k + 1;
            for (int j = 1; j <= k; ++j) {
                result = result * (long)i / (long)j;
                ++i;
            }
        } else if (n <= 66) {
            int i = n - k + 1;
            for (int j = 1; j <= k; ++j) {
                long d = MathUtils.gcd(i, j);
                result = result / ((long)j / d) * ((long)i / d);
                ++i;
            }
        } else {
            int i = n - k + 1;
            for (int j = 1; j <= k; ++j) {
                long d = MathUtils.gcd(i, j);
                result = MathUtils.mulAndCheck(result / ((long)j / d), (long)i / d);
                ++i;
            }
        }
        return result;
    }

    public static double binomialCoefficientDouble(int n, int k) {
        MathUtils.checkBinomial(n, k);
        if (n == k || k == 0) {
            return 1.0;
        }
        if (k == 1 || k == n - 1) {
            return n;
        }
        if (k > n / 2) {
            return MathUtils.binomialCoefficientDouble(n, n - k);
        }
        if (n < 67) {
            return MathUtils.binomialCoefficient(n, k);
        }
        double result = 1.0;
        for (int i = 1; i <= k; ++i) {
            result *= (double)(n - k + i) / (double)i;
        }
        return FastMath.floor(result + 0.5);
    }

    public static double binomialCoefficientLog(int n, int k) {
        int i;
        MathUtils.checkBinomial(n, k);
        if (n == k || k == 0) {
            return 0.0;
        }
        if (k == 1 || k == n - 1) {
            return FastMath.log(n);
        }
        if (n < 67) {
            return FastMath.log(MathUtils.binomialCoefficient(n, k));
        }
        if (n < 1030) {
            return FastMath.log(MathUtils.binomialCoefficientDouble(n, k));
        }
        if (k > n / 2) {
            return MathUtils.binomialCoefficientLog(n, n - k);
        }
        double logSum = 0.0;
        for (i = n - k + 1; i <= n; ++i) {
            logSum += FastMath.log(i);
        }
        for (i = 2; i <= k; ++i) {
            logSum -= FastMath.log(i);
        }
        return logSum;
    }

    private static void checkBinomial(int n, int k) throws IllegalArgumentException {
//        if (n < k) {
//            throw MathRuntimeException.createIllegalArgumentException(LocalizedFormats.BINOMIAL_INVALID_PARAMETERS_ORDER, n, k);
//        }
//        if (n < 0) {
//            throw MathRuntimeException.createIllegalArgumentException(LocalizedFormats.BINOMIAL_NEGATIVE_PARAMETER, n);
//        }
    }

    public static int compareTo(double x, double y, double eps) {
        if (MathUtils.equals(x, y, eps)) {
            return 0;
        }
        if (x < y) {
            return -1;
        }
        return 1;
    }

    public static double cosh(double x) {
        return (FastMath.exp(x) + FastMath.exp(-x)) / 2.0;
    }

    @Deprecated
    public static boolean equals(float x, float y) {
        return Float.isNaN(x) && Float.isNaN(y) || x == y;
    }

    public static boolean equalsIncludingNaN(float x, float y) {
        return Float.isNaN(x) && Float.isNaN(y) || MathUtils.equals(x, y, 1);
    }

    public static boolean equals(float x, float y, float eps) {
        return MathUtils.equals(x, y, 1) || FastMath.abs(y - x) <= eps;
    }

    public static boolean equalsIncludingNaN(float x, float y, float eps) {
        return MathUtils.equalsIncludingNaN(x, y) || FastMath.abs(y - x) <= eps;
    }

    public static boolean equals(float x, float y, int maxUlps) {
        assert (maxUlps > 0 && maxUlps < 0x400000);
        int xInt = Float.floatToIntBits(x);
        int yInt = Float.floatToIntBits(y);
        if (xInt < 0) {
            xInt = Integer.MIN_VALUE - xInt;
        }
        if (yInt < 0) {
            yInt = Integer.MIN_VALUE - yInt;
        }
        boolean isEqual = FastMath.abs(xInt - yInt) <= maxUlps;
        return isEqual && !Float.isNaN(x) && !Float.isNaN(y);
    }

    public static boolean equalsIncludingNaN(float x, float y, int maxUlps) {
        return Float.isNaN(x) && Float.isNaN(y) || MathUtils.equals(x, y, maxUlps);
    }

    @Deprecated
    public static boolean equals(float[] x, float[] y) {
        if (x == null || y == null) {
            return !(x == null ^ y == null);
        }
        if (x.length != y.length) {
            return false;
        }
        for (int i = 0; i < x.length; ++i) {
            if (MathUtils.equals(x[i], y[i])) continue;
            return false;
        }
        return true;
    }

    public static boolean equalsIncludingNaN(float[] x, float[] y) {
        if (x == null || y == null) {
            return !(x == null ^ y == null);
        }
        if (x.length != y.length) {
            return false;
        }
        for (int i = 0; i < x.length; ++i) {
            if (MathUtils.equalsIncludingNaN(x[i], y[i])) continue;
            return false;
        }
        return true;
    }

    public static boolean equals(double x, double y) {
        return Double.isNaN(x) && Double.isNaN(y) || x == y;
    }

    public static boolean equalsIncludingNaN(double x, double y) {
        return Double.isNaN(x) && Double.isNaN(y) || MathUtils.equals(x, y, 1);
    }

    public static boolean equals(double x, double y, double eps) {
        return MathUtils.equals(x, y) || FastMath.abs(y - x) <= eps;
    }

    public static boolean equalsIncludingNaN(double x, double y, double eps) {
        return MathUtils.equalsIncludingNaN(x, y) || FastMath.abs(y - x) <= eps;
    }

    public static boolean equals(double x, double y, int maxUlps) {
        assert (maxUlps > 0 && maxUlps < 0x400000);
        long xInt = Double.doubleToLongBits(x);
        long yInt = Double.doubleToLongBits(y);
        if (xInt < 0L) {
            xInt = Long.MIN_VALUE - xInt;
        }
        if (yInt < 0L) {
            yInt = Long.MIN_VALUE - yInt;
        }
        return FastMath.abs(xInt - yInt) <= (long)maxUlps;
    }

    public static boolean equalsIncludingNaN(double x, double y, int maxUlps) {
        return Double.isNaN(x) && Double.isNaN(y) || MathUtils.equals(x, y, maxUlps);
    }

    public static boolean equals(double[] x, double[] y) {
        if (x == null || y == null) {
            return !(x == null ^ y == null);
        }
        if (x.length != y.length) {
            return false;
        }
        for (int i = 0; i < x.length; ++i) {
            if (MathUtils.equals(x[i], y[i])) continue;
            return false;
        }
        return true;
    }

    public static boolean equalsIncludingNaN(double[] x, double[] y) {
        if (x == null || y == null) {
            return !(x == null ^ y == null);
        }
        if (x.length != y.length) {
            return false;
        }
        for (int i = 0; i < x.length; ++i) {
            if (MathUtils.equalsIncludingNaN(x[i], y[i])) continue;
            return false;
        }
        return true;
    }

    public static long factorial(int n) {
        if (n < 0) {
 //           throw MathRuntimeException.createIllegalArgumentException(LocalizedFormats.FACTORIAL_NEGATIVE_PARAMETER, n);
        }
        if (n > 20) {
            throw new ArithmeticException("factorial value is too large to fit in a long");
        }
        return FACTORIALS[n];
    }

    public static double factorialDouble(int n) {
        if (n < 0) {
   //         throw MathRuntimeException.createIllegalArgumentException(LocalizedFormats.FACTORIAL_NEGATIVE_PARAMETER, n);
        }
        if (n < 21) {
            return MathUtils.factorial(n);
        }
        return FastMath.floor(FastMath.exp(MathUtils.factorialLog(n)) + 0.5);
    }

    public static double factorialLog(int n) {
        if (n < 0) {
   //         throw MathRuntimeException.createIllegalArgumentException(LocalizedFormats.FACTORIAL_NEGATIVE_PARAMETER, n);
        }
        if (n < 21) {
            return FastMath.log(MathUtils.factorial(n));
        }
        double logSum = 0.0;
        for (int i = 2; i <= n; ++i) {
            logSum += FastMath.log(i);
        }
        return logSum;
    }

    public static int gcd(int p, int q) {
        int t;
        int k;
        int u = p;
        int v = q;
        if (u == 0 || v == 0) {
            if (u == Integer.MIN_VALUE || v == Integer.MIN_VALUE) {
     //           throw MathRuntimeException.createArithmeticException(LocalizedFormats.GCD_OVERFLOW_32_BITS, p, q);
            }
            return FastMath.abs(u) + FastMath.abs(v);
        }
        if (u > 0) {
            u = -u;
        }
        if (v > 0) {
            v = -v;
        }
        for (k = 0; (u & 1) == 0 && (v & 1) == 0 && k < 31; ++k) {
            u /= 2;
            v /= 2;
        }
        if (k == 31) {
    //        throw MathRuntimeException.createArithmeticException(LocalizedFormats.GCD_OVERFLOW_32_BITS, p, q);
        }
        int n = t = (u & 1) == 1 ? v : -(u / 2);
        while (true) {
            if ((t & 1) == 0) {
                t /= 2;
                continue;
            }
            if (t > 0) {
                u = -t;
            } else {
                v = t;
            }
            if ((t = (v - u) / 2) == 0) break;
        }
        return -u * (1 << k);
    }

    public static long gcd(long p, long q) {
        long t;
        int k;
        long u = p;
        long v = q;
        if (u == 0L || v == 0L) {
            if (u == Long.MIN_VALUE || v == Long.MIN_VALUE) {
    //            throw MathRuntimeException.createArithmeticException(LocalizedFormats.GCD_OVERFLOW_64_BITS, p, q);
            }
            return FastMath.abs(u) + FastMath.abs(v);
        }
        if (u > 0L) {
            u = -u;
        }
        if (v > 0L) {
            v = -v;
        }
        for (k = 0; (u & 1L) == 0L && (v & 1L) == 0L && k < 63; ++k) {
            u /= 2L;
            v /= 2L;
        }
        if (k == 63) {
    //        throw MathRuntimeException.createArithmeticException(LocalizedFormats.GCD_OVERFLOW_64_BITS, p, q);
        }
        long l = t = (u & 1L) == 1L ? v : -(u / 2L);
        while (true) {
            if ((t & 1L) == 0L) {
                t /= 2L;
                continue;
            }
            if (t > 0L) {
                u = -t;
            } else {
                v = t;
            }
            if ((t = (v - u) / 2L) == 0L) break;
        }
        return -u * (1L << k);
    }

    public static int hash(double value) {
        return new Double(value).hashCode();
    }

    public static int hash(double[] value) {
        return Arrays.hashCode(value);
    }

    public static byte indicator(byte x) {
        return x >= 0 ? (byte)1 : -1;
    }

    public static double indicator(double x) {
        if (Double.isNaN(x)) {
            return Double.NaN;
        }
        return x >= 0.0 ? 1.0 : -1.0;
    }

    public static float indicator(float x) {
        if (Float.isNaN(x)) {
            return Float.NaN;
        }
        return x >= 0.0f ? 1.0f : -1.0f;
    }

    public static int indicator(int x) {
        return x >= 0 ? 1 : -1;
    }

    public static long indicator(long x) {
        return x >= 0L ? 1L : -1L;
    }

    public static short indicator(short x) {
        return x >= 0 ? (short)1 : -1;
    }

    public static int lcm(int a, int b) {
        if (a == 0 || b == 0) {
            return 0;
        }
        int lcm = FastMath.abs(MathUtils.mulAndCheck(a / MathUtils.gcd(a, b), b));
        if (lcm == Integer.MIN_VALUE) {
   //         throw MathRuntimeException.createArithmeticException(LocalizedFormats.LCM_OVERFLOW_32_BITS, a, b);
        }
        return lcm;
    }

    public static long lcm(long a, long b) {
        if (a == 0L || b == 0L) {
            return 0L;
        }
        long lcm = FastMath.abs(MathUtils.mulAndCheck(a / MathUtils.gcd(a, b), b));
        if (lcm == Long.MIN_VALUE) {
   //         throw MathRuntimeException.createArithmeticException(LocalizedFormats.LCM_OVERFLOW_64_BITS, a, b);
        }
        return lcm;
    }

    public static double log(double base, double x) {
        return FastMath.log(x) / FastMath.log(base);
    }

    public static int mulAndCheck(int x, int y) {
        long m = (long)x * (long)y;
        if (m < Integer.MIN_VALUE || m > Integer.MAX_VALUE) {
            throw new ArithmeticException("overflow: mul");
        }
        return (int)m;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static long mulAndCheck(long a, long b) {
        String msg = "overflow: multiply";
        if (a > b) {
            return MathUtils.mulAndCheck(b, a);
        }
        if (a < 0L) {
            if (b < 0L) {
                if (a < Long.MAX_VALUE / b) throw new ArithmeticException(msg);
                return a * b;
            }
            if (b <= 0L) return 0L;
            if (Long.MIN_VALUE / b > a) throw new ArithmeticException(msg);
            return a * b;
        }
        if (a <= 0L) return 0L;
        if (a > Long.MAX_VALUE / b) throw new ArithmeticException(msg);
        return a * b;
    }

    @Deprecated
    public static double nextAfter(double d, double direction) {
        if (Double.isNaN(d) || Double.isInfinite(d)) {
            return d;
        }
        if (d == 0.0) {
            return direction < 0.0 ? -4.9E-324 : Double.MIN_VALUE;
        }
        long bits = Double.doubleToLongBits(d);
        long sign = bits & Long.MIN_VALUE;
        long exponent = bits & 0x7FF0000000000000L;
        long mantissa = bits & 0xFFFFFFFFFFFFFL;
        if (d * (direction - d) >= 0.0) {
            if (mantissa == 0xFFFFFFFFFFFFFL) {
                return Double.longBitsToDouble(sign | exponent + 0x10000000000000L);
            }
            return Double.longBitsToDouble(sign | exponent | mantissa + 1L);
        }
        if (mantissa == 0L) {
            return Double.longBitsToDouble(sign | exponent - 0x10000000000000L | 0xFFFFFFFFFFFFFL);
        }
        return Double.longBitsToDouble(sign | exponent | mantissa - 1L);
    }

    @Deprecated
    public static double scalb(double d, int scaleFactor) {
        return FastMath.scalb(d, scaleFactor);
    }

    public static double normalizeAngle(double a, double center) {
        return a - Math.PI * 2 * FastMath.floor((a + Math.PI - center) / (Math.PI * 2));
    }

    public static double[] normalizeArray(double[] values, double normalizedSum) throws ArithmeticException, IllegalArgumentException {
        int i;
        if (Double.isInfinite(normalizedSum)) {
 //           throw MathRuntimeException.createIllegalArgumentException(LocalizedFormats.NORMALIZE_INFINITE, new Object[0]);
        }
        if (Double.isNaN(normalizedSum)) {
 //           throw MathRuntimeException.createIllegalArgumentException(LocalizedFormats.NORMALIZE_NAN, new Object[0]);
        }
        double sum = 0.0;
        int len = values.length;
        double[] out = new double[len];
        for (i = 0; i < len; ++i) {
            if (Double.isInfinite(values[i])) {
 //               throw MathRuntimeException.createArithmeticException(LocalizedFormats.INFINITE_ARRAY_ELEMENT, values[i], i);
            }
            if (Double.isNaN(values[i])) continue;
            sum += values[i];
        }
        if (sum == 0.0) {
 //           throw MathRuntimeException.createArithmeticException(LocalizedFormats.ARRAY_SUMS_TO_ZERO, new Object[0]);
        }
        for (i = 0; i < len; ++i) {
            out[i] = Double.isNaN(values[i]) ? Double.NaN : values[i] * normalizedSum / sum;
        }
        return out;
    }

    public static double round(double x, int scale) {
        return MathUtils.round(x, scale, 4);
    }

    public static double round(double x, int scale, int roundingMethod) {
        try {
            return new BigDecimal(Double.toString(x)).setScale(scale, roundingMethod).doubleValue();
        }
        catch (NumberFormatException ex) {
            if (Double.isInfinite(x)) {
                return x;
            }
            return Double.NaN;
        }
    }

    public static float round(float x, int scale) {
        return MathUtils.round(x, scale, 4);
    }

    public static float round(float x, int scale, int roundingMethod) {
        float sign = MathUtils.indicator(x);
        float factor = (float) FastMath.pow(10.0, scale) * sign;
        return (float) MathUtils.roundUnscaled(x * factor, sign, roundingMethod) / factor;
    }

    private static double roundUnscaled(double unscaled, double sign, int roundingMethod) {
        switch (roundingMethod) {
            case 2: {
                if (sign == -1.0) {
                    unscaled = FastMath.floor(MathUtils.nextAfter(unscaled, Double.NEGATIVE_INFINITY));
                    break;
                }
                unscaled = FastMath.ceil(MathUtils.nextAfter(unscaled, Double.POSITIVE_INFINITY));
                break;
            }
            case 1: {
                unscaled = FastMath.floor(MathUtils.nextAfter(unscaled, Double.NEGATIVE_INFINITY));
                break;
            }
            case 3: {
                if (sign == -1.0) {
                    unscaled = FastMath.ceil(MathUtils.nextAfter(unscaled, Double.POSITIVE_INFINITY));
                    break;
                }
                unscaled = FastMath.floor(MathUtils.nextAfter(unscaled, Double.NEGATIVE_INFINITY));
                break;
            }
            case 5: {
                unscaled = MathUtils.nextAfter(unscaled, Double.NEGATIVE_INFINITY);
                double fraction = unscaled - FastMath.floor(unscaled);
                if (fraction > 0.5) {
                    unscaled = FastMath.ceil(unscaled);
                    break;
                }
                unscaled = FastMath.floor(unscaled);
                break;
            }
            case 6: {
                double fraction = unscaled - FastMath.floor(unscaled);
                if (fraction > 0.5) {
                    unscaled = FastMath.ceil(unscaled);
                    break;
                }
                if (fraction < 0.5) {
                    unscaled = FastMath.floor(unscaled);
                    break;
                }
                if (FastMath.floor(unscaled) / 2.0 == FastMath.floor(Math.floor(unscaled) / 2.0)) {
                    unscaled = FastMath.floor(unscaled);
                    break;
                }
                unscaled = FastMath.ceil(unscaled);
                break;
            }
            case 4: {
                unscaled = MathUtils.nextAfter(unscaled, Double.POSITIVE_INFINITY);
                double fraction = unscaled - FastMath.floor(unscaled);
                if (fraction >= 0.5) {
                    unscaled = FastMath.ceil(unscaled);
                    break;
                }
                unscaled = FastMath.floor(unscaled);
                break;
            }
            case 7: {
                if (unscaled == FastMath.floor(unscaled)) break;
                throw new ArithmeticException("Inexact result from rounding");
            }
            case 0: {
                unscaled = FastMath.ceil(MathUtils.nextAfter(unscaled, Double.POSITIVE_INFINITY));
                break;
            }
            default: {
  //              throw MathRuntimeException.createIllegalArgumentException(LocalizedFormats.INVALID_ROUNDING_METHOD, roundingMethod, "ROUND_CEILING", 2, "ROUND_DOWN", 1, "ROUND_FLOOR", 3, "ROUND_HALF_DOWN", 5, "ROUND_HALF_EVEN", 6, "ROUND_HALF_UP", 4, "ROUND_UNNECESSARY", 7, "ROUND_UP", 0);
            }
        }
        return unscaled;
    }

    public static byte sign(byte x) {
        return (byte)(x == 0 ? 0 : (x > 0 ? 1 : -1));
    }

    public static double sign(double x) {
        if (Double.isNaN(x)) {
            return Double.NaN;
        }
        return x == 0.0 ? 0.0 : (x > 0.0 ? 1.0 : -1.0);
    }

    public static float sign(float x) {
        if (Float.isNaN(x)) {
            return Float.NaN;
        }
        return x == 0.0f ? 0.0f : (x > 0.0f ? 1.0f : -1.0f);
    }

    public static int sign(int x) {
        return x == 0 ? 0 : (x > 0 ? 1 : -1);
    }

    public static long sign(long x) {
        return x == 0L ? 0L : (x > 0L ? 1L : -1L);
    }

    public static short sign(short x) {
        return (short)(x == 0 ? 0 : (x > 0 ? 1 : -1));
    }

    public static double sinh(double x) {
        return (FastMath.exp(x) - FastMath.exp(-x)) / 2.0;
    }

    public static int subAndCheck(int x, int y) {
        long s = (long)x - (long)y;
        if (s < Integer.MIN_VALUE || s > Integer.MAX_VALUE) {
 //           throw MathRuntimeException.createArithmeticException(LocalizedFormats.OVERFLOW_IN_SUBTRACTION, x, y);
        }
        return (int)s;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static long subAndCheck(long a, long b) {
        String msg = "overflow: subtract";
 //       if (b != Long.MIN_VALUE) return MathUtils.addAndCheck(a, -b, LocalizedFormats.OVERFLOW_IN_ADDITION);
        if (a >= 0L) throw new ArithmeticException(msg);
        return a - b;
    }

    public static int pow(int k, int e) throws IllegalArgumentException {
        if (e < 0) {
 //           throw MathRuntimeException.createIllegalArgumentException(LocalizedFormats.POWER_NEGATIVE_PARAMETERS, k, e);
        }
        int result = 1;
        int k2p = k;
        while (e != 0) {
            if ((e & 1) != 0) {
                result *= k2p;
            }
            k2p *= k2p;
            e >>= 1;
        }
        return result;
    }

    public static int pow(int k, long e) throws IllegalArgumentException {
        if (e < 0L) {
 //           throw MathRuntimeException.createIllegalArgumentException(LocalizedFormats.POWER_NEGATIVE_PARAMETERS, k, e);
        }
        int result = 1;
        int k2p = k;
        while (e != 0L) {
            if ((e & 1L) != 0L) {
                result *= k2p;
            }
            k2p *= k2p;
            e >>= 1;
        }
        return result;
    }

    public static long pow(long k, int e) throws IllegalArgumentException {
        if (e < 0) {
 //           throw MathRuntimeException.createIllegalArgumentException(LocalizedFormats.POWER_NEGATIVE_PARAMETERS, k, e);
        }
        long result = 1L;
        long k2p = k;
        while (e != 0) {
            if ((e & 1) != 0) {
                result *= k2p;
            }
            k2p *= k2p;
            e >>= 1;
        }
        return result;
    }

    public static long pow(long k, long e) throws IllegalArgumentException {
        if (e < 0L) {
 //           throw MathRuntimeException.createIllegalArgumentException(LocalizedFormats.POWER_NEGATIVE_PARAMETERS, k, e);
        }
        long result = 1L;
        long k2p = k;
        while (e != 0L) {
            if ((e & 1L) != 0L) {
                result *= k2p;
            }
            k2p *= k2p;
            e >>= 1;
        }
        return result;
    }

    public static BigInteger pow(BigInteger k, int e) throws IllegalArgumentException {
        if (e < 0) {
 //           throw MathRuntimeException.createIllegalArgumentException(LocalizedFormats.POWER_NEGATIVE_PARAMETERS, k, e);
        }
        return k.pow(e);
    }

    public static BigInteger pow(BigInteger k, long e) throws IllegalArgumentException {
        if (e < 0L) {
 //           throw MathRuntimeException.createIllegalArgumentException(LocalizedFormats.POWER_NEGATIVE_PARAMETERS, k, e);
        }
        BigInteger result = BigInteger.ONE;
        BigInteger k2p = k;
        while (e != 0L) {
            if ((e & 1L) != 0L) {
                result = result.multiply(k2p);
            }
            k2p = k2p.multiply(k2p);
            e >>= 1;
        }
        return result;
    }

    public static BigInteger pow(BigInteger k, BigInteger e) throws IllegalArgumentException {
        if (e.compareTo(BigInteger.ZERO) < 0) {
 //           throw MathRuntimeException.createIllegalArgumentException(LocalizedFormats.POWER_NEGATIVE_PARAMETERS, k, e);
        }
        BigInteger result = BigInteger.ONE;
        BigInteger k2p = k;
        while (!BigInteger.ZERO.equals(e)) {
            if (e.testBit(0)) {
                result = result.multiply(k2p);
            }
            k2p = k2p.multiply(k2p);
            e = e.shiftRight(1);
        }
        return result;
    }

    public static double distance1(double[] p1, double[] p2) {
        double sum = 0.0;
        for (int i = 0; i < p1.length; ++i) {
            sum += FastMath.abs(p1[i] - p2[i]);
        }
        return sum;
    }

    public static int distance1(int[] p1, int[] p2) {
        int sum = 0;
        for (int i = 0; i < p1.length; ++i) {
            sum += FastMath.abs(p1[i] - p2[i]);
        }
        return sum;
    }

    public static double distance(double[] p1, double[] p2) {
        double sum = 0.0;
        for (int i = 0; i < p1.length; ++i) {
            double dp = p1[i] - p2[i];
            sum += dp * dp;
        }
        return FastMath.sqrt(sum);
    }

    public static double distance(int[] p1, int[] p2) {
        double sum = 0.0;
        for (int i = 0; i < p1.length; ++i) {
            double dp = p1[i] - p2[i];
            sum += dp * dp;
        }
        return FastMath.sqrt(sum);
    }

    public static double distanceInf(double[] p1, double[] p2) {
        double max = 0.0;
        for (int i = 0; i < p1.length; ++i) {
            max = FastMath.max(max, FastMath.abs(p1[i] - p2[i]));
        }
        return max;
    }

    public static int distanceInf(int[] p1, int[] p2) {
        int max = 0;
        for (int i = 0; i < p1.length; ++i) {
            max = FastMath.max(max, FastMath.abs(p1[i] - p2[i]));
        }
        return max;
    }

    public static void checkOrder(double[] val, OrderDirection dir, boolean strict) {
        double previous = val[0];
        boolean ok = true;
        int max = val.length;
        for (int i = 1; i < max; ++i) {
            switch (dir) {
                case INCREASING: {
                    if (strict) {
                        if (!(val[i] <= previous)) break;
                        ok = false;
                        break;
                    }
                    if (!(val[i] < previous)) break;
                    ok = false;
                    break;
                }
                case DECREASING: {
                    if (strict) {
                        if (!(val[i] >= previous)) break;
                        ok = false;
                        break;
                    }
                    if (!(val[i] > previous)) break;
                    ok = false;
                    break;
                }
                default: {
                    throw new IllegalArgumentException();
                }
            }
            if (!ok) {
  //              throw new NonMonotonousSequenceException(val[i], (Number)previous, i, dir, strict);
            }
            previous = val[i];
        }
    }

    public static void checkOrder(double[] val) {
        MathUtils.checkOrder(val, OrderDirection.INCREASING, true);
    }

    @Deprecated
    public static void checkOrder(double[] val, int dir, boolean strict) {
        if (dir > 0) {
            MathUtils.checkOrder(val, OrderDirection.INCREASING, strict);
        } else {
            MathUtils.checkOrder(val, OrderDirection.DECREASING, strict);
        }
    }

    public static double safeNorm(double[] v) {
        double rdwarf = 3.834E-20;
        double rgiant = 1.304E19;
        double s1 = 0.0;
        double s2 = 0.0;
        double s3 = 0.0;
        double x1max = 0.0;
        double x3max = 0.0;
        double floatn = v.length;
        double agiant = rgiant / floatn;
        for (int i = 0; i < v.length; ++i) {
            double xabs = Math.abs(v[i]);
            if (xabs < rdwarf || xabs > agiant) {
                double r;
                if (xabs > rdwarf) {
                    if (xabs > x1max) {
                        r = x1max / xabs;
                        s1 = 1.0 + s1 * r * r;
                        x1max = xabs;
                        continue;
                    }
                    r = xabs / x1max;
                    s1 += r * r;
                    continue;
                }
                if (xabs > x3max) {
                    r = x3max / xabs;
                    s3 = 1.0 + s3 * r * r;
                    x3max = xabs;
                    continue;
                }
                if (xabs == 0.0) continue;
                r = xabs / x3max;
                s3 += r * r;
                continue;
            }
            s2 += xabs * xabs;
        }
        double norm = s1 != 0.0 ? x1max * Math.sqrt(s1 + s2 / x1max / x1max) : (s2 == 0.0 ? x3max * Math.sqrt(s3) : (s2 >= x3max ? Math.sqrt(s2 * (1.0 + x3max / s2 * (x3max * s3))) : Math.sqrt(x3max * (s2 / x3max + x3max * s3))));
        return norm;
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    public static enum OrderDirection {
        INCREASING,
        DECREASING;

    }
}
