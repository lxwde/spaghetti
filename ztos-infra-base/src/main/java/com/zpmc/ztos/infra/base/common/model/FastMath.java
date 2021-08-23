package com.zpmc.ztos.infra.base.common.model;

public class FastMath {
    public static final double PI = Math.PI;
    public static final double E = Math.E;
    private static final double[] EXP_INT_TABLE_A;
    private static final double[] EXP_INT_TABLE_B;
    private static final double[] EXP_FRAC_TABLE_A;
    private static final double[] EXP_FRAC_TABLE_B;
    private static final double[] FACT;
    private static final double[][] LN_MANT;
    private static final double LN_2_A = 0.6931470632553101;
    private static final double LN_2_B = 1.1730463525082348E-7;
    private static final double[][] LN_SPLIT_COEF;
    private static final double[][] LN_QUICK_COEF;
    private static final double[][] LN_HI_PREC_COEF;
    private static final double[] SINE_TABLE_A;
    private static final double[] SINE_TABLE_B;
    private static final double[] COSINE_TABLE_A;
    private static final double[] COSINE_TABLE_B;
    private static final double[] TANGENT_TABLE_A;
    private static final double[] TANGENT_TABLE_B;
    private static final long[] RECIP_2PI;
    private static final long[] PI_O_4_BITS;
    private static final double[] EIGHTHS;
    private static final double[] CBRTTWO;
    private static final long HEX_40000000 = 0x40000000L;
    private static final long MASK_30BITS = -1073741824L;
    private static final double TWO_POWER_52 = 4.503599627370496E15;

    private FastMath() {
    }

    private static double doubleHighPart(double d) {
        if (d > -2.2250738585072014E-308 && d < Double.MIN_NORMAL) {
            return d;
        }
        long xl = Double.doubleToLongBits(d);
        return Double.longBitsToDouble(xl &= 0xFFFFFFFFC0000000L);
    }

    public static double sqrt(double a) {
        return Math.sqrt(a);
    }

    public static double cosh(double x) {
        if (x != x) {
            return x;
        }
        if (x > 20.0) {
            return FastMath.exp(x) / 2.0;
        }
        if (x < -20.0) {
            return FastMath.exp(-x) / 2.0;
        }
        double[] hiPrec = new double[2];
        if (x < 0.0) {
            x = -x;
        }
        FastMath.exp(x, 0.0, hiPrec);
        double ya = hiPrec[0] + hiPrec[1];
        double yb = -(ya - hiPrec[0] - hiPrec[1]);
        double temp = ya * 1.073741824E9;
        double yaa = ya + temp - temp;
        double yab = ya - yaa;
        double recip = 1.0 / ya;
        temp = recip * 1.073741824E9;
        double recipa = recip + temp - temp;
        double recipb = recip - recipa;
        recipb += (1.0 - yaa * recipa - yaa * recipb - yab * recipa - yab * recipb) * recip;
        recipb += -yb * recip * recip;
        temp = ya + recipa;
        yb += -(temp - ya - recipa);
        ya = temp;
        temp = ya + recipb;
        yb += -(temp - ya - recipb);
        ya = temp;
        double result = ya + yb;
        return result *= 0.5;
    }

    public static double sinh(double x) {
        double result;
        boolean negate = false;
        if (x != x) {
            return x;
        }
        if (x > 20.0) {
            return FastMath.exp(x) / 2.0;
        }
        if (x < -20.0) {
            return -FastMath.exp(-x) / 2.0;
        }
        if (x == 0.0) {
            return x;
        }
        if (x < 0.0) {
            x = -x;
            negate = true;
        }
        if (x > 0.25) {
            double[] hiPrec = new double[2];
            FastMath.exp(x, 0.0, hiPrec);
            double ya = hiPrec[0] + hiPrec[1];
            double yb = -(ya - hiPrec[0] - hiPrec[1]);
            double temp = ya * 1.073741824E9;
            double yaa = ya + temp - temp;
            double yab = ya - yaa;
            double recip = 1.0 / ya;
            temp = recip * 1.073741824E9;
            double recipa = recip + temp - temp;
            double recipb = recip - recipa;
            recipb += (1.0 - yaa * recipa - yaa * recipb - yab * recipa - yab * recipb) * recip;
            recipb += -yb * recip * recip;
            recipa = -recipa;
            recipb = -recipb;
            temp = ya + recipa;
            yb += -(temp - ya - recipa);
            ya = temp;
            temp = ya + recipb;
            yb += -(temp - ya - recipb);
            ya = temp;
            result = ya + yb;
            result *= 0.5;
        } else {
            double[] hiPrec = new double[2];
            FastMath.expm1(x, hiPrec);
            double ya = hiPrec[0] + hiPrec[1];
            double yb = -(ya - hiPrec[0] - hiPrec[1]);
            double denom = 1.0 + ya;
            double denomr = 1.0 / denom;
            double denomb = -(denom - 1.0 - ya) + yb;
            double ratio = ya * denomr;
            double temp = ratio * 1.073741824E9;
            double ra = ratio + temp - temp;
            double rb = ratio - ra;
            temp = denom * 1.073741824E9;
            double za = denom + temp - temp;
            double zb = denom - za;
            rb += (ya - za * ra - za * rb - zb * ra - zb * rb) * denomr;
            rb += yb * denomr;
            rb += -ya * denomb * denomr * denomr;
            temp = ya + ra;
            yb += -(temp - ya - ra);
            ya = temp;
            temp = ya + rb;
            yb += -(temp - ya - rb);
            ya = temp;
            result = ya + yb;
            result *= 0.5;
        }
        if (negate) {
            result = -result;
        }
        return result;
    }

    public static double tanh(double x) {
        double result;
        boolean negate = false;
        if (x != x) {
            return x;
        }
        if (x > 20.0) {
            return 1.0;
        }
        if (x < -20.0) {
            return -1.0;
        }
        if (x == 0.0) {
            return x;
        }
        if (x < 0.0) {
            x = -x;
            negate = true;
        }
        if (x >= 0.5) {
            double[] hiPrec = new double[2];
            FastMath.exp(x * 2.0, 0.0, hiPrec);
            double ya = hiPrec[0] + hiPrec[1];
            double yb = -(ya - hiPrec[0] - hiPrec[1]);
            double na = -1.0 + ya;
            double nb = -(na + 1.0 - ya);
            double temp = na + yb;
            nb += -(temp - na - yb);
            na = temp;
            double da = 1.0 + ya;
            double db = -(da - 1.0 - ya);
            temp = da + yb;
            db += -(temp - da - yb);
            da = temp;
            temp = da * 1.073741824E9;
            double daa = da + temp - temp;
            double dab = da - daa;
            double ratio = na / da;
            temp = ratio * 1.073741824E9;
            double ratioa = ratio + temp - temp;
            double ratiob = ratio - ratioa;
            ratiob += (na - daa * ratioa - daa * ratiob - dab * ratioa - dab * ratiob) / da;
            ratiob += nb / da;
            result = ratioa + (ratiob += -db * na / da / da);
        } else {
            double[] hiPrec = new double[2];
            FastMath.expm1(x * 2.0, hiPrec);
            double ya = hiPrec[0] + hiPrec[1];
            double yb = -(ya - hiPrec[0] - hiPrec[1]);
            double na = ya;
            double nb = yb;
            double da = 2.0 + ya;
            double db = -(da - 2.0 - ya);
            double temp = da + yb;
            db += -(temp - da - yb);
            da = temp;
            temp = da * 1.073741824E9;
            double daa = da + temp - temp;
            double dab = da - daa;
            double ratio = na / da;
            temp = ratio * 1.073741824E9;
            double ratioa = ratio + temp - temp;
            double ratiob = ratio - ratioa;
            ratiob += (na - daa * ratioa - daa * ratiob - dab * ratioa - dab * ratiob) / da;
            ratiob += nb / da;
            result = ratioa + (ratiob += -db * na / da / da);
        }
        if (negate) {
            result = -result;
        }
        return result;
    }

    public static double acosh(double a) {
        return FastMath.log(a + FastMath.sqrt(a * a - 1.0));
    }

    public static double asinh(double a) {
        double absAsinh;
        boolean negative = false;
        if (a < 0.0) {
            negative = true;
            a = -a;
        }
        if (a > 0.167) {
            absAsinh = FastMath.log(FastMath.sqrt(a * a + 1.0) + a);
        } else {
            double a2 = a * a;
            absAsinh = a > 0.097 ? a * (1.0 - a2 * (0.3333333333333333 - a2 * (0.2 - a2 * (0.14285714285714285 - a2 * (0.1111111111111111 - a2 * (0.09090909090909091 - a2 * (0.07692307692307693 - a2 * (0.06666666666666667 - a2 * 0.058823529411764705 * 15.0 / 16.0) * 13.0 / 14.0) * 11.0 / 12.0) * 9.0 / 10.0) * 7.0 / 8.0) * 5.0 / 6.0) * 3.0 / 4.0) / 2.0) : (a > 0.036 ? a * (1.0 - a2 * (0.3333333333333333 - a2 * (0.2 - a2 * (0.14285714285714285 - a2 * (0.1111111111111111 - a2 * (0.09090909090909091 - a2 * 0.07692307692307693 * 11.0 / 12.0) * 9.0 / 10.0) * 7.0 / 8.0) * 5.0 / 6.0) * 3.0 / 4.0) / 2.0) : (a > 0.0036 ? a * (1.0 - a2 * (0.3333333333333333 - a2 * (0.2 - a2 * (0.14285714285714285 - a2 * 0.1111111111111111 * 7.0 / 8.0) * 5.0 / 6.0) * 3.0 / 4.0) / 2.0) : a * (1.0 - a2 * (0.3333333333333333 - a2 * 0.2 * 3.0 / 4.0) / 2.0)));
        }
        return negative ? -absAsinh : absAsinh;
    }

    public static double atanh(double a) {
        double absAtanh;
        boolean negative = false;
        if (a < 0.0) {
            negative = true;
            a = -a;
        }
        if (a > 0.15) {
            absAtanh = 0.5 * FastMath.log((1.0 + a) / (1.0 - a));
        } else {
            double a2 = a * a;
            absAtanh = a > 0.087 ? a * (1.0 + a2 * (0.3333333333333333 + a2 * (0.2 + a2 * (0.14285714285714285 + a2 * (0.1111111111111111 + a2 * (0.09090909090909091 + a2 * (0.07692307692307693 + a2 * (0.06666666666666667 + a2 * 0.058823529411764705)))))))) : (a > 0.031 ? a * (1.0 + a2 * (0.3333333333333333 + a2 * (0.2 + a2 * (0.14285714285714285 + a2 * (0.1111111111111111 + a2 * (0.09090909090909091 + a2 * 0.07692307692307693)))))) : (a > 0.003 ? a * (1.0 + a2 * (0.3333333333333333 + a2 * (0.2 + a2 * (0.14285714285714285 + a2 * 0.1111111111111111)))) : a * (1.0 + a2 * (0.3333333333333333 + a2 * 0.2))));
        }
        return negative ? -absAtanh : absAtanh;
    }

    public static double signum(double a) {
        return a < 0.0 ? -1.0 : (a > 0.0 ? 1.0 : a);
    }

    public static float signum(float a) {
        return a < 0.0f ? -1.0f : (a > 0.0f ? 1.0f : a);
    }

    public static double nextUp(double a) {
        return FastMath.nextAfter(a, Double.POSITIVE_INFINITY);
    }

    public static float nextUp(float a) {
        return FastMath.nextAfter(a, Double.POSITIVE_INFINITY);
    }

    public static double random() {
        return Math.random();
    }

    public static double exp(double x) {
        return FastMath.exp(x, 0.0, null);
    }

    private static double exp(double x, double extra, double[] hiPrec) {
        double intPartB;
        double intPartA;
        int intVal;
        if (x < 0.0) {
            intVal = (int)(-x);
            if (intVal > 746) {
                if (hiPrec != null) {
                    hiPrec[0] = 0.0;
                    hiPrec[1] = 0.0;
                }
                return 0.0;
            }
            if (intVal > 709) {
                double result = FastMath.exp(x + 40.19140625, extra, hiPrec) / 2.85040095144011776E17;
                if (hiPrec != null) {
                    hiPrec[0] = hiPrec[0] / 2.85040095144011776E17;
                    hiPrec[1] = hiPrec[1] / 2.85040095144011776E17;
                }
                return result;
            }
            if (intVal == 709) {
                double result = FastMath.exp(x + 1.494140625, extra, hiPrec) / 4.455505956692757;
                if (hiPrec != null) {
                    hiPrec[0] = hiPrec[0] / 4.455505956692757;
                    hiPrec[1] = hiPrec[1] / 4.455505956692757;
                }
                return result;
            }
            intPartA = EXP_INT_TABLE_A[750 - ++intVal];
            intPartB = EXP_INT_TABLE_B[750 - intVal];
            intVal = -intVal;
        } else {
            intVal = (int)x;
            if (intVal > 709) {
                if (hiPrec != null) {
                    hiPrec[0] = Double.POSITIVE_INFINITY;
                    hiPrec[1] = 0.0;
                }
                return Double.POSITIVE_INFINITY;
            }
            intPartA = EXP_INT_TABLE_A[750 + intVal];
            intPartB = EXP_INT_TABLE_B[750 + intVal];
        }
        int intFrac = (int)((x - (double)intVal) * 1024.0);
        double fracPartA = EXP_FRAC_TABLE_A[intFrac];
        double fracPartB = EXP_FRAC_TABLE_B[intFrac];
        double epsilon = x - ((double)intVal + (double)intFrac / 1024.0);
        double z = 0.04168701738764507;
        z = z * epsilon + 0.1666666505023083;
        z = z * epsilon + 0.5000000000042687;
        z = z * epsilon + 1.0;
        z = z * epsilon + -3.940510424527919E-20;
        double tempA = intPartA * fracPartA;
        double tempB = intPartA * fracPartB + intPartB * fracPartA + intPartB * fracPartB;
        double tempC = tempB + tempA;
        double result = extra != 0.0 ? tempC * extra * z + tempC * extra + tempC * z + tempB + tempA : tempC * z + tempB + tempA;
        if (hiPrec != null) {
            hiPrec[0] = tempA;
            hiPrec[1] = tempC * extra * z + tempC * extra + tempC * z + tempB;
        }
        return result;
    }

    public static double expm1(double x) {
        return FastMath.expm1(x, null);
    }

    private static double expm1(double x, double[] hiPrecOut) {
        if (x != x || x == 0.0) {
            return x;
        }
        if (x <= -1.0 || x >= 1.0) {
            double[] hiPrec = new double[2];
            FastMath.exp(x, 0.0, hiPrec);
            if (x > 0.0) {
                return -1.0 + hiPrec[0] + hiPrec[1];
            }
            double ra = -1.0 + hiPrec[0];
            double rb = -(ra + 1.0 - hiPrec[0]);
            return ra + (rb += hiPrec[1]);
        }
        boolean negative = false;
        if (x < 0.0) {
            x = -x;
            negative = true;
        }
        int intFrac = (int)(x * 1024.0);
        double tempA = EXP_FRAC_TABLE_A[intFrac] - 1.0;
        double tempB = EXP_FRAC_TABLE_B[intFrac];
        double temp = tempA + tempB;
        tempB = -(temp - tempA - tempB);
        tempA = temp;
        temp = tempA * 1.073741824E9;
        double baseA = tempA + temp - temp;
        double baseB = tempB + (tempA - baseA);
        double epsilon = x - (double)intFrac / 1024.0;
        double zb = 0.008336750013465571;
        zb = zb * epsilon + 0.041666663879186654;
        zb = zb * epsilon + 0.16666666666745392;
        zb = zb * epsilon + 0.49999999999999994;
        zb *= epsilon;
        double za = epsilon;
        double temp2 = za + (zb *= epsilon);
        zb = -(temp2 - za - zb);
        za = temp2;
        temp2 = za * 1.073741824E9;
        temp2 = za + temp2 - temp2;
        zb += za - temp2;
        za = temp2;
        double ya = za * baseA;
        temp2 = ya + za * baseB;
        double yb = -(temp2 - ya - za * baseB);
        ya = temp2;
        temp2 = ya + zb * baseA;
        yb += -(temp2 - ya - zb * baseA);
        ya = temp2;
        temp2 = ya + zb * baseB;
        yb += -(temp2 - ya - zb * baseB);
        ya = temp2;
        temp2 = ya + baseA;
        yb += -(temp2 - baseA - ya);
        ya = temp2;
        temp2 = ya + za;
        yb += -(temp2 - ya - za);
        ya = temp2;
        temp2 = ya + baseB;
        yb += -(temp2 - ya - baseB);
        ya = temp2;
        temp2 = ya + zb;
        yb += -(temp2 - ya - zb);
        ya = temp2;
        if (negative) {
            double denom = 1.0 + ya;
            double denomr = 1.0 / denom;
            double denomb = -(denom - 1.0 - ya) + yb;
            double ratio = ya * denomr;
            temp2 = ratio * 1.073741824E9;
            double ra = ratio + temp2 - temp2;
            double rb = ratio - ra;
            temp2 = denom * 1.073741824E9;
            za = denom + temp2 - temp2;
            zb = denom - za;
            rb += (ya - za * ra - za * rb - zb * ra - zb * rb) * denomr;
            rb += yb * denomr;
            rb += -ya * denomb * denomr * denomr;
            ya = -ra;
            yb = -rb;
        }
        if (hiPrecOut != null) {
            hiPrecOut[0] = ya;
            hiPrecOut[1] = yb;
        }
        return ya + yb;
    }

    private static double slowexp(double x, double[] result) {
        double[] xs = new double[2];
        double[] ys = new double[2];
        double[] facts = new double[2];
        double[] as = new double[2];
        FastMath.split(x, xs);
        ys[1] = 0.0;
        ys[0] = 0.0;
        for (int i = 19; i >= 0; --i) {
            FastMath.splitMult(xs, ys, as);
            ys[0] = as[0];
            ys[1] = as[1];
            FastMath.split(FACT[i], as);
            FastMath.splitReciprocal(as, facts);
            FastMath.splitAdd(ys, facts, as);
            ys[0] = as[0];
            ys[1] = as[1];
        }
        if (result != null) {
            result[0] = ys[0];
            result[1] = ys[1];
        }
        return ys[0] + ys[1];
    }

    private static void split(double d, double[] split) {
        if (d < 8.0E298 && d > -8.0E298) {
            double a = d * 1.073741824E9;
            split[0] = d + a - a;
            split[1] = d - split[0];
        } else {
            double a = d * 9.313225746154785E-10;
            split[0] = (d + a - d) * 1.073741824E9;
            split[1] = d - split[0];
        }
    }

    private static void resplit(double[] a) {
        double c = a[0] + a[1];
        double d = -(c - a[0] - a[1]);
        if (c < 8.0E298 && c > -8.0E298) {
            double z = c * 1.073741824E9;
            a[0] = c + z - z;
            a[1] = c - a[0] + d;
        } else {
            double z = c * 9.313225746154785E-10;
            a[0] = (c + z - c) * 1.073741824E9;
            a[1] = c - a[0] + d;
        }
    }

    private static void splitMult(double[] a, double[] b, double[] ans) {
        ans[0] = a[0] * b[0];
        ans[1] = a[0] * b[1] + a[1] * b[0] + a[1] * b[1];
        FastMath.resplit(ans);
    }

    private static void splitAdd(double[] a, double[] b, double[] ans) {
        ans[0] = a[0] + b[0];
        ans[1] = a[1] + b[1];
        FastMath.resplit(ans);
    }

    private static void splitReciprocal(double[] in, double[] result) {
        double b = 2.384185791015625E-7;
        double a = 0.9999997615814209;
        if (in[0] == 0.0) {
            in[0] = in[1];
            in[1] = 0.0;
        }
        result[0] = 0.9999997615814209 / in[0];
        result[1] = (2.384185791015625E-7 * in[0] - 0.9999997615814209 * in[1]) / (in[0] * in[0] + in[0] * in[1]);
        if (result[1] != result[1]) {
            result[1] = 0.0;
        }
        FastMath.resplit(result);
        for (int i = 0; i < 2; ++i) {
            double err = 1.0 - result[0] * in[0] - result[0] * in[1] - result[1] * in[0] - result[1] * in[1];
            result[1] = result[1] + (err *= result[0] + result[1]);
        }
    }

    private static void quadMult(double[] a, double[] b, double[] result) {
        double[] xs = new double[2];
        double[] ys = new double[2];
        double[] zs = new double[2];
        FastMath.split(a[0], xs);
        FastMath.split(b[0], ys);
        FastMath.splitMult(xs, ys, zs);
        result[0] = zs[0];
        result[1] = zs[1];
        FastMath.split(b[1], ys);
        FastMath.splitMult(xs, ys, zs);
        double tmp = result[0] + zs[0];
        result[1] = result[1] - (tmp - result[0] - zs[0]);
        result[0] = tmp;
        tmp = result[0] + zs[1];
        result[1] = result[1] - (tmp - result[0] - zs[1]);
        result[0] = tmp;
        FastMath.split(a[1], xs);
        FastMath.split(b[0], ys);
        FastMath.splitMult(xs, ys, zs);
        tmp = result[0] + zs[0];
        result[1] = result[1] - (tmp - result[0] - zs[0]);
        result[0] = tmp;
        tmp = result[0] + zs[1];
        result[1] = result[1] - (tmp - result[0] - zs[1]);
        result[0] = tmp;
        FastMath.split(a[1], xs);
        FastMath.split(b[1], ys);
        FastMath.splitMult(xs, ys, zs);
        tmp = result[0] + zs[0];
        result[1] = result[1] - (tmp - result[0] - zs[0]);
        result[0] = tmp;
        tmp = result[0] + zs[1];
        result[1] = result[1] - (tmp - result[0] - zs[1]);
        result[0] = tmp;
    }

    private static double expint(int p, double[] result) {
        double[] xs = new double[2];
        double[] as = new double[2];
        double[] ys = new double[2];
        xs[0] = Math.E;
        xs[1] = 1.4456468917292502E-16;
        FastMath.split(1.0, ys);
        while (p > 0) {
            if ((p & 1) != 0) {
                FastMath.quadMult(ys, xs, as);
                ys[0] = as[0];
                ys[1] = as[1];
            }
            FastMath.quadMult(xs, xs, as);
            xs[0] = as[0];
            xs[1] = as[1];
            p >>= 1;
        }
        if (result != null) {
            result[0] = ys[0];
            result[1] = ys[1];
            FastMath.resplit(result);
        }
        return ys[0] + ys[1];
    }

    public static double log(double x) {
        return FastMath.log(x, null);
    }

    private static double log(double x, double[] hiPrec) {
        if (x == 0.0) {
            return Double.NEGATIVE_INFINITY;
        }
        long bits = Double.doubleToLongBits(x);
        if (((bits & Long.MIN_VALUE) != 0L || x != x) && x != 0.0) {
            if (hiPrec != null) {
                hiPrec[0] = Double.NaN;
            }
            return Double.NaN;
        }
        if (x == Double.POSITIVE_INFINITY) {
            if (hiPrec != null) {
                hiPrec[0] = Double.POSITIVE_INFINITY;
            }
            return Double.POSITIVE_INFINITY;
        }
        int exp = (int)(bits >> 52) - 1023;
        if ((bits & 0x7FF0000000000000L) == 0L) {
            if (x == 0.0) {
                if (hiPrec != null) {
                    hiPrec[0] = Double.NEGATIVE_INFINITY;
                }
                return Double.NEGATIVE_INFINITY;
            }
            bits <<= 1;
            while ((bits & 0x10000000000000L) == 0L) {
                --exp;
                bits <<= 1;
            }
        }
        if ((exp == -1 || exp == 0) && x < 1.01 && x > 0.99 && hiPrec == null) {
            double xa = x - 1.0;
            double xb = xa - x + 1.0;
            double tmp = xa * 1.073741824E9;
            double aa = xa + tmp - tmp;
            double ab = xa - aa;
            xa = aa;
            xb = ab;
            double ya = LN_QUICK_COEF[LN_QUICK_COEF.length - 1][0];
            double yb = LN_QUICK_COEF[LN_QUICK_COEF.length - 1][1];
            for (int i = LN_QUICK_COEF.length - 2; i >= 0; --i) {
                aa = ya * xa;
                ab = ya * xb + yb * xa + yb * xb;
                tmp = aa * 1.073741824E9;
                ya = aa + tmp - tmp;
                yb = aa - ya + ab;
                aa = ya + LN_QUICK_COEF[i][0];
                ab = yb + LN_QUICK_COEF[i][1];
                tmp = aa * 1.073741824E9;
                ya = aa + tmp - tmp;
                yb = aa - ya + ab;
            }
            aa = ya * xa;
            ab = ya * xb + yb * xa + yb * xb;
            tmp = aa * 1.073741824E9;
            ya = aa + tmp - tmp;
            yb = aa - ya + ab;
            return ya + yb;
        }
        double[] lnm = LN_MANT[(int)((bits & 0xFFC0000000000L) >> 42)];
        double epsilon = (double)(bits & 0x3FFFFFFFFFFL) / (4.503599627370496E15 + (double)(bits & 0xFFC0000000000L));
        double lnza = 0.0;
        double lnzb = 0.0;
        if (hiPrec != null) {
            double tmp = epsilon * 1.073741824E9;
            double aa = epsilon + tmp - tmp;
            double ab = epsilon - aa;
            double xa = aa;
            double xb = ab;
            double numer = bits & 0x3FFFFFFFFFFL;
            double denom = 4.503599627370496E15 + (double)(bits & 0xFFC0000000000L);
            aa = numer - xa * denom - xb * denom;
            xb += aa / denom;
            double ya = LN_HI_PREC_COEF[LN_HI_PREC_COEF.length - 1][0];
            double yb = LN_HI_PREC_COEF[LN_HI_PREC_COEF.length - 1][1];
            for (int i = LN_HI_PREC_COEF.length - 2; i >= 0; --i) {
                aa = ya * xa;
                ab = ya * xb + yb * xa + yb * xb;
                tmp = aa * 1.073741824E9;
                ya = aa + tmp - tmp;
                yb = aa - ya + ab;
                aa = ya + LN_HI_PREC_COEF[i][0];
                ab = yb + LN_HI_PREC_COEF[i][1];
                tmp = aa * 1.073741824E9;
                ya = aa + tmp - tmp;
                yb = aa - ya + ab;
            }
            aa = ya * xa;
            ab = ya * xb + yb * xa + yb * xb;
            lnza = aa + ab;
            lnzb = -(lnza - aa - ab);
        } else {
            lnza = -0.16624882440418567;
            lnza = lnza * epsilon + 0.19999954120254515;
            lnza = lnza * epsilon + -0.2499999997677497;
            lnza = lnza * epsilon + 0.3333333333332802;
            lnza = lnza * epsilon + -0.5;
            lnza = lnza * epsilon + 1.0;
            lnza *= epsilon;
        }
        double a = 0.6931470632553101 * (double)exp;
        double b = 0.0;
        double c = a + lnm[0];
        double d = -(c - a - lnm[0]);
        a = c;
        b += d;
        c = a + lnza;
        d = -(c - a - lnza);
        a = c;
        b += d;
        c = a + 1.1730463525082348E-7 * (double)exp;
        d = -(c - a - 1.1730463525082348E-7 * (double)exp);
        a = c;
        b += d;
        c = a + lnm[1];
        d = -(c - a - lnm[1]);
        a = c;
        b += d;
        c = a + lnzb;
        d = -(c - a - lnzb);
        a = c;
        b += d;
        if (hiPrec != null) {
            hiPrec[0] = a;
            hiPrec[1] = b;
        }
        return a + b;
    }

    public static double log1p(double x) {
        double xpa = 1.0 + x;
        double xpb = -(xpa - 1.0 - x);
        if (x == -1.0) {
            return x / 0.0;
        }
        if (x > 0.0 && 1.0 / x == 0.0) {
            return x;
        }
        if (x > 1.0E-6 || x < -1.0E-6) {
            double[] hiPrec = new double[2];
            double lores = FastMath.log(xpa, hiPrec);
            if (Double.isInfinite(lores)) {
                return lores;
            }
            double fx1 = xpb / xpa;
            double epsilon = 0.5 * fx1 + 1.0;
            return (epsilon *= fx1) + hiPrec[1] + hiPrec[0];
        }
        double y = x * 0.333333333333333 - 0.5;
        y = y * x + 1.0;
        return y *= x;
    }

    public static double log10(double x) {
        double[] hiPrec = new double[2];
        double lores = FastMath.log(x, hiPrec);
        if (Double.isInfinite(lores)) {
            return lores;
        }
        double tmp = hiPrec[0] * 1.073741824E9;
        double lna = hiPrec[0] + tmp - tmp;
        double lnb = hiPrec[0] - lna + hiPrec[1];
        double rln10a = 0.4342944622039795;
        double rln10b = 1.9699272335463627E-8;
        return 1.9699272335463627E-8 * lnb + 1.9699272335463627E-8 * lna + 0.4342944622039795 * lnb + 0.4342944622039795 * lna;
    }

    public static double pow(double x, double y) {
        double yb;
        double ya;
        double tmp1;
        double[] lns = new double[2];
        if (y == 0.0) {
            return 1.0;
        }
        if (x != x) {
            return x;
        }
        if (x == 0.0) {
            long bits = Double.doubleToLongBits(x);
            if ((bits & Long.MIN_VALUE) != 0L) {
                long yi = (long)y;
                if (y < 0.0 && y == (double)yi && (yi & 1L) == 1L) {
                    return Double.NEGATIVE_INFINITY;
                }
                if (y < 0.0 && y == (double)yi && (yi & 1L) == 1L) {
                    return -0.0;
                }
                if (y > 0.0 && y == (double)yi && (yi & 1L) == 1L) {
                    return -0.0;
                }
            }
            if (y < 0.0) {
                return Double.POSITIVE_INFINITY;
            }
            if (y > 0.0) {
                return 0.0;
            }
            return Double.NaN;
        }
        if (x == Double.POSITIVE_INFINITY) {
            if (y != y) {
                return y;
            }
            if (y < 0.0) {
                return 0.0;
            }
            return Double.POSITIVE_INFINITY;
        }
        if (y == Double.POSITIVE_INFINITY) {
            if (x * x == 1.0) {
                return Double.NaN;
            }
            if (x * x > 1.0) {
                return Double.POSITIVE_INFINITY;
            }
            return 0.0;
        }
        if (x == Double.NEGATIVE_INFINITY) {
            if (y != y) {
                return y;
            }
            if (y < 0.0) {
                long yi = (long)y;
                if (y == (double)yi && (yi & 1L) == 1L) {
                    return -0.0;
                }
                return 0.0;
            }
            if (y > 0.0) {
                long yi = (long)y;
                if (y == (double)yi && (yi & 1L) == 1L) {
                    return Double.NEGATIVE_INFINITY;
                }
                return Double.POSITIVE_INFINITY;
            }
        }
        if (y == Double.NEGATIVE_INFINITY) {
            if (x * x == 1.0) {
                return Double.NaN;
            }
            if (x * x < 1.0) {
                return Double.POSITIVE_INFINITY;
            }
            return 0.0;
        }
        if (x < 0.0) {
            if (y >= 4.503599627370496E15 || y <= -4.503599627370496E15) {
                return FastMath.pow(-x, y);
            }
            if (y == (double)((long)y)) {
                return ((long)y & 1L) == 0L ? FastMath.pow(-x, y) : -FastMath.pow(-x, y);
            }
            return Double.NaN;
        }
        if (y < 8.0E298 && y > -8.0E298) {
            tmp1 = y * 1.073741824E9;
            ya = y + tmp1 - tmp1;
            yb = y - ya;
        } else {
            tmp1 = y * 9.313225746154785E-10;
            double tmp2 = tmp1 * 9.313225746154785E-10;
            ya = (tmp1 + tmp2 - tmp1) * 1.073741824E9 * 1.073741824E9;
            yb = y - ya;
        }
        double lores = FastMath.log(x, lns);
        if (Double.isInfinite(lores)) {
            return lores;
        }
        double lna = lns[0];
        double lnb = lns[1];
        double tmp12 = lna * 1.073741824E9;
        double tmp2 = lna + tmp12 - tmp12;
        lnb += lna - tmp2;
        lna = tmp2;
        double aa = lna * ya;
        double ab = lna * yb + lnb * ya + lnb * yb;
        lna = aa + ab;
        lnb = -(lna - aa - ab);
        double z = 0.008333333333333333;
        z = z * lnb + 0.041666666666666664;
        z = z * lnb + 0.16666666666666666;
        z = z * lnb + 0.5;
        z = z * lnb + 1.0;
        double result = FastMath.exp(lna, z *= lnb, null);
        return result;
    }

    private static double[] slowLog(double xi) {
        double[] x = new double[2];
        double[] x2 = new double[2];
        double[] y = new double[2];
        double[] a = new double[2];
        FastMath.split(xi, x);
        x[0] = x[0] + 1.0;
        FastMath.resplit(x);
        FastMath.splitReciprocal(x, a);
        x[0] = x[0] - 2.0;
        FastMath.resplit(x);
        FastMath.splitMult(x, a, y);
        x[0] = y[0];
        x[1] = y[1];
        FastMath.splitMult(x, x, x2);
        y[0] = LN_SPLIT_COEF[LN_SPLIT_COEF.length - 1][0];
        y[1] = LN_SPLIT_COEF[LN_SPLIT_COEF.length - 1][1];
        for (int i = LN_SPLIT_COEF.length - 2; i >= 0; --i) {
            FastMath.splitMult(y, x2, a);
            y[0] = a[0];
            y[1] = a[1];
            FastMath.splitAdd(y, LN_SPLIT_COEF[i], a);
            y[0] = a[0];
            y[1] = a[1];
        }
        FastMath.splitMult(y, x, a);
        y[0] = a[0];
        y[1] = a[1];
        return y;
    }

    private static double slowSin(double x, double[] result) {
        double[] xs = new double[2];
        double[] ys = new double[2];
        double[] facts = new double[2];
        double[] as = new double[2];
        FastMath.split(x, xs);
        ys[1] = 0.0;
        ys[0] = 0.0;
        for (int i = 19; i >= 0; --i) {
            FastMath.splitMult(xs, ys, as);
            ys[0] = as[0];
            ys[1] = as[1];
            if ((i & 1) == 0) continue;
            FastMath.split(FACT[i], as);
            FastMath.splitReciprocal(as, facts);
            if ((i & 2) != 0) {
                facts[0] = -facts[0];
                facts[1] = -facts[1];
            }
            FastMath.splitAdd(ys, facts, as);
            ys[0] = as[0];
            ys[1] = as[1];
        }
        if (result != null) {
            result[0] = ys[0];
            result[1] = ys[1];
        }
        return ys[0] + ys[1];
    }

    private static double slowCos(double x, double[] result) {
        double[] xs = new double[2];
        double[] ys = new double[2];
        double[] facts = new double[2];
        double[] as = new double[2];
        FastMath.split(x, xs);
        ys[1] = 0.0;
        ys[0] = 0.0;
        for (int i = 19; i >= 0; --i) {
            FastMath.splitMult(xs, ys, as);
            ys[0] = as[0];
            ys[1] = as[1];
            if ((i & 1) != 0) continue;
            FastMath.split(FACT[i], as);
            FastMath.splitReciprocal(as, facts);
            if ((i & 2) != 0) {
                facts[0] = -facts[0];
                facts[1] = -facts[1];
            }
            FastMath.splitAdd(ys, facts, as);
            ys[0] = as[0];
            ys[1] = as[1];
        }
        if (result != null) {
            result[0] = ys[0];
            result[1] = ys[1];
        }
        return ys[0] + ys[1];
    }

    private static void buildSinCosTables() {
        double[] as;
        double[] ys;
        int i;
        double[] result = new double[2];
        for (i = 0; i < 7; ++i) {
            double x = (double)i / 8.0;
            FastMath.slowSin(x, result);
            FastMath.SINE_TABLE_A[i] = result[0];
            FastMath.SINE_TABLE_B[i] = result[1];
            FastMath.slowCos(x, result);
            FastMath.COSINE_TABLE_A[i] = result[0];
            FastMath.COSINE_TABLE_B[i] = result[1];
        }
        for (i = 7; i < 14; ++i) {
            double[] xs = new double[2];
            ys = new double[2];
            as = new double[2];
            double[] bs = new double[2];
            double[] temps = new double[2];
            if ((i & 1) == 0) {
                xs[0] = SINE_TABLE_A[i / 2];
                xs[1] = SINE_TABLE_B[i / 2];
                ys[0] = COSINE_TABLE_A[i / 2];
                ys[1] = COSINE_TABLE_B[i / 2];
                FastMath.splitMult(xs, ys, result);
                FastMath.SINE_TABLE_A[i] = result[0] * 2.0;
                FastMath.SINE_TABLE_B[i] = result[1] * 2.0;
                FastMath.splitMult(ys, ys, as);
                FastMath.splitMult(xs, xs, temps);
                temps[0] = -temps[0];
                temps[1] = -temps[1];
                FastMath.splitAdd(as, temps, result);
                FastMath.COSINE_TABLE_A[i] = result[0];
                FastMath.COSINE_TABLE_B[i] = result[1];
                continue;
            }
            xs[0] = SINE_TABLE_A[i / 2];
            xs[1] = SINE_TABLE_B[i / 2];
            ys[0] = COSINE_TABLE_A[i / 2];
            ys[1] = COSINE_TABLE_B[i / 2];
            as[0] = SINE_TABLE_A[i / 2 + 1];
            as[1] = SINE_TABLE_B[i / 2 + 1];
            bs[0] = COSINE_TABLE_A[i / 2 + 1];
            bs[1] = COSINE_TABLE_B[i / 2 + 1];
            FastMath.splitMult(xs, bs, temps);
            FastMath.splitMult(ys, as, result);
            FastMath.splitAdd(result, temps, result);
            FastMath.SINE_TABLE_A[i] = result[0];
            FastMath.SINE_TABLE_B[i] = result[1];
            FastMath.splitMult(ys, bs, result);
            FastMath.splitMult(xs, as, temps);
            temps[0] = -temps[0];
            temps[1] = -temps[1];
            FastMath.splitAdd(result, temps, result);
            FastMath.COSINE_TABLE_A[i] = result[0];
            FastMath.COSINE_TABLE_B[i] = result[1];
        }
        for (i = 0; i < 14; ++i) {
            double[] xs = new double[2];
            ys = new double[2];
            as = new double[]{COSINE_TABLE_A[i], COSINE_TABLE_B[i]};
            FastMath.splitReciprocal(as, ys);
            xs[0] = SINE_TABLE_A[i];
            xs[1] = SINE_TABLE_B[i];
            FastMath.splitMult(xs, ys, as);
            FastMath.TANGENT_TABLE_A[i] = as[0];
            FastMath.TANGENT_TABLE_B[i] = as[1];
        }
    }

    private static double polySine(double x) {
        double x2 = x * x;
        double p = 2.7553817452272217E-6;
        p = p * x2 + -1.9841269659586505E-4;
        p = p * x2 + 0.008333333333329196;
        p = p * x2 + -0.16666666666666666;
        p = p * x2 * x;
        return p;
    }

    private static double polyCosine(double x) {
        double x2 = x * x;
        double p = 2.479773539153719E-5;
        p = p * x2 + -0.0013888888689039883;
        p = p * x2 + 0.041666666666621166;
        p = p * x2 + -0.49999999999999994;
        return p *= x2;
    }

    private static double sinQ(double xa, double xb) {
        int idx = (int)(xa * 8.0 + 0.5);
        double epsilon = xa - EIGHTHS[idx];
        double sintA = SINE_TABLE_A[idx];
        double sintB = SINE_TABLE_B[idx];
        double costA = COSINE_TABLE_A[idx];
        double costB = COSINE_TABLE_B[idx];
        double sinEpsA = epsilon;
        double sinEpsB = FastMath.polySine(epsilon);
        double cosEpsA = 1.0;
        double cosEpsB = FastMath.polyCosine(epsilon);
        double temp = sinEpsA * 1.073741824E9;
        double temp2 = sinEpsA + temp - temp;
        sinEpsB += sinEpsA - temp2;
        sinEpsA = temp2;
        double a = 0.0;
        double b = 0.0;
        double t = sintA;
        double c = a + t;
        double d = -(c - a - t);
        a = c;
        b += d;
        t = costA * sinEpsA;
        c = a + t;
        d = -(c - a - t);
        a = c;
        b += d;
        b = b + sintA * cosEpsB + costA * sinEpsB;
        b = b + sintB + costB * sinEpsA + sintB * cosEpsB + costB * sinEpsB;
        if (xb != 0.0) {
            t = ((costA + costB) * (1.0 + cosEpsB) - (sintA + sintB) * (sinEpsA + sinEpsB)) * xb;
            c = a + t;
            d = -(c - a - t);
            a = c;
            b += d;
        }
        double result = a + b;
        return result;
    }

    private static double cosQ(double xa, double xb) {
        double pi2a = 1.5707963267948966;
        double pi2b = 6.123233995736766E-17;
        double a = 1.5707963267948966 - xa;
        double b = -(a - 1.5707963267948966 + xa);
        return FastMath.sinQ(a, b += 6.123233995736766E-17 - xb);
    }

    private static double tanQ(double xa, double xb, boolean cotanFlag) {
        int idx = (int)(xa * 8.0 + 0.5);
        double epsilon = xa - EIGHTHS[idx];
        double sintA = SINE_TABLE_A[idx];
        double sintB = SINE_TABLE_B[idx];
        double costA = COSINE_TABLE_A[idx];
        double costB = COSINE_TABLE_B[idx];
        double sinEpsA = epsilon;
        double sinEpsB = FastMath.polySine(epsilon);
        double cosEpsA = 1.0;
        double cosEpsB = FastMath.polyCosine(epsilon);
        double temp = sinEpsA * 1.073741824E9;
        double temp2 = sinEpsA + temp - temp;
        sinEpsB += sinEpsA - temp2;
        sinEpsA = temp2;
        double a = 0.0;
        double b = 0.0;
        double t = sintA;
        double c = a + t;
        double d = -(c - a - t);
        a = c;
        b += d;
        t = costA * sinEpsA;
        c = a + t;
        d = -(c - a - t);
        a = c;
        b += d;
        b = b + sintA * cosEpsB + costA * sinEpsB;
        b = b + sintB + costB * sinEpsA + sintB * cosEpsB + costB * sinEpsB;
        double sina = a + b;
        double sinb = -(sina - a - b);
        d = 0.0;
        c = 0.0;
        b = 0.0;
        a = 0.0;
        t = costA * 1.0;
        c = a + t;
        d = -(c - a - t);
        a = c;
        b += d;
        t = -sintA * sinEpsA;
        c = a + t;
        d = -(c - a - t);
        a = c;
        b += d;
        b = b + costB * 1.0 + costA * cosEpsB + costB * cosEpsB;
        double cosa = a + (b -= sintB * sinEpsA + sintA * sinEpsB + sintB * sinEpsB);
        double cosb = -(cosa - a - b);
        if (cotanFlag) {
            double tmp = cosa;
            cosa = sina;
            sina = tmp;
            tmp = cosb;
            cosb = sinb;
            sinb = tmp;
        }
        double est = sina / cosa;
        temp = est * 1.073741824E9;
        double esta = est + temp - temp;
        double estb = est - esta;
        temp = cosa * 1.073741824E9;
        double cosaa = cosa + temp - temp;
        double cosab = cosa - cosaa;
        double err = (sina - esta * cosaa - esta * cosab - estb * cosaa - estb * cosab) / cosa;
        err += sinb / cosa;
        err += -sina * cosb / cosa / cosa;
        if (xb != 0.0) {
            double xbadj = xb + est * est * xb;
            if (cotanFlag) {
                xbadj = -xbadj;
            }
            err += xbadj;
        }
        return est + err;
    }

    private static void reducePayneHanek(double x, double[] result) {
        boolean bitsum;
        long shpiB;
        long shpiA;
        long shpi0;
        int idx;
        int shift;
        long inbits = Double.doubleToLongBits(x);
        int exponent = (int)(inbits >> 52 & 0x7FFL) - 1023;
        inbits &= 0xFFFFFFFFFFFFFL;
        inbits |= 0x10000000000000L;
        inbits <<= 11;
        if ((shift = ++exponent - ((idx = exponent >> 6) << 6)) != 0) {
            shpi0 = idx == 0 ? 0L : RECIP_2PI[idx - 1] << shift;
            shpi0 |= RECIP_2PI[idx] >>> 64 - shift;
            shpiA = RECIP_2PI[idx] << shift | RECIP_2PI[idx + 1] >>> 64 - shift;
            shpiB = RECIP_2PI[idx + 1] << shift | RECIP_2PI[idx + 2] >>> 64 - shift;
        } else {
            shpi0 = idx == 0 ? 0L : RECIP_2PI[idx - 1];
            shpiA = RECIP_2PI[idx];
            shpiB = RECIP_2PI[idx + 1];
        }
        long a = inbits >>> 32;
        long b = inbits & 0xFFFFFFFFL;
        long c = shpiA >>> 32;
        long d = shpiA & 0xFFFFFFFFL;
        long ac = a * c;
        long bd = b * d;
        long bc = b * c;
        long ad = a * d;
        long prodB = bd + (ad << 32);
        long prodA = ac + (ad >>> 32);
        boolean bita = (bd & Long.MIN_VALUE) != 0L;
        boolean bitb = (ad & 0x80000000L) != 0L;
        boolean bl = bitsum = (prodB & Long.MIN_VALUE) != 0L;
        if (bita && bitb || (bita || bitb) && !bitsum) {
            ++prodA;
        }
        bita = (prodB & Long.MIN_VALUE) != 0L;
        bitb = (bc & 0x80000000L) != 0L;
        prodA += bc >>> 32;
        boolean bl2 = bitsum = ((prodB += bc << 32) & Long.MIN_VALUE) != 0L;
        if (bita && bitb || (bita || bitb) && !bitsum) {
            ++prodA;
        }
        c = shpiB >>> 32;
        d = shpiB & 0xFFFFFFFFL;
        ac = a * c;
        bc = b * c;
        ad = a * d;
        bita = (prodB & Long.MIN_VALUE) != 0L;
        bitb = ((ac += bc + ad >>> 32) & Long.MIN_VALUE) != 0L;
        boolean bl3 = bitsum = ((prodB += ac) & Long.MIN_VALUE) != 0L;
        if (bita && bitb || (bita || bitb) && !bitsum) {
            ++prodA;
        }
        c = shpi0 >>> 32;
        d = shpi0 & 0xFFFFFFFFL;
        bd = b * d;
        bc = b * c;
        ad = a * d;
        int intPart = (int)((prodA += bd + (bc + ad << 32)) >>> 62);
        prodA <<= 2;
        prodA |= prodB >>> 62;
        prodB <<= 2;
        a = prodA >>> 32;
        b = prodA & 0xFFFFFFFFL;
        c = PI_O_4_BITS[0] >>> 32;
        d = PI_O_4_BITS[0] & 0xFFFFFFFFL;
        ac = a * c;
        bd = b * d;
        bc = b * c;
        ad = a * d;
        long prod2B = bd + (ad << 32);
        long prod2A = ac + (ad >>> 32);
        bita = (bd & Long.MIN_VALUE) != 0L;
        bitb = (ad & 0x80000000L) != 0L;
        boolean bl4 = bitsum = (prod2B & Long.MIN_VALUE) != 0L;
        if (bita && bitb || (bita || bitb) && !bitsum) {
            ++prod2A;
        }
        bita = (prod2B & Long.MIN_VALUE) != 0L;
        bitb = (bc & 0x80000000L) != 0L;
        prod2A += bc >>> 32;
        boolean bl5 = bitsum = ((prod2B += bc << 32) & Long.MIN_VALUE) != 0L;
        if (bita && bitb || (bita || bitb) && !bitsum) {
            ++prod2A;
        }
        c = PI_O_4_BITS[1] >>> 32;
        d = PI_O_4_BITS[1] & 0xFFFFFFFFL;
        ac = a * c;
        bc = b * c;
        ad = a * d;
        bita = (prod2B & Long.MIN_VALUE) != 0L;
        bitb = ((ac += bc + ad >>> 32) & Long.MIN_VALUE) != 0L;
        boolean bl6 = bitsum = ((prod2B += ac) & Long.MIN_VALUE) != 0L;
        if (bita && bitb || (bita || bitb) && !bitsum) {
            ++prod2A;
        }
        a = prodB >>> 32;
        b = prodB & 0xFFFFFFFFL;
        c = PI_O_4_BITS[0] >>> 32;
        d = PI_O_4_BITS[0] & 0xFFFFFFFFL;
        ac = a * c;
        bc = b * c;
        ad = a * d;
        bita = (prod2B & Long.MIN_VALUE) != 0L;
        bitb = ((ac += bc + ad >>> 32) & Long.MIN_VALUE) != 0L;
        boolean bl7 = bitsum = ((prod2B += ac) & Long.MIN_VALUE) != 0L;
        if (bita && bitb || (bita || bitb) && !bitsum) {
            ++prod2A;
        }
        double tmpA = (double)(prod2A >>> 12) / 4.503599627370496E15;
        double tmpB = (double)(((prod2A & 0xFFFL) << 40) + (prod2B >>> 24)) / 4.503599627370496E15 / 4.503599627370496E15;
        double sumA = tmpA + tmpB;
        double sumB = -(sumA - tmpA - tmpB);
        result[0] = intPart;
        result[1] = sumA * 2.0;
        result[2] = sumB * 2.0;
    }

    public static double sin(double x) {
        boolean negative = false;
        int quadrant = 0;
        double xb = 0.0;
        double xa = x;
        if (x < 0.0) {
            negative = true;
            xa = -xa;
        }
        if (xa == 0.0) {
            long bits = Double.doubleToLongBits(x);
            if (bits < 0L) {
                return -0.0;
            }
            return 0.0;
        }
        if (xa != xa || xa == Double.POSITIVE_INFINITY) {
            return Double.NaN;
        }
        if (xa > 3294198.0) {
            double[] reduceResults = new double[3];
            FastMath.reducePayneHanek(xa, reduceResults);
            quadrant = (int)reduceResults[0] & 3;
            xa = reduceResults[1];
            xb = reduceResults[2];
        } else if (xa > 1.5707963267948966) {
            double remB;
            double remA;
            int k = (int)(xa * 0.6366197723675814);
            while (true) {
                double a = (double)(-k) * 1.570796251296997;
                remA = xa + a;
                remB = -(remA - xa - a);
                a = (double)(-k) * 7.549789948768648E-8;
                double b = remA;
                remA = a + b;
                remB += -(remA - b - a);
                a = (double)(-k) * 6.123233995736766E-17;
                b = remA;
                remA = a + b;
                remB += -(remA - b - a);
                if (remA > 0.0) break;
                --k;
            }
            quadrant = k & 3;
            xa = remA;
            xb = remB;
        }
        if (negative) {
            quadrant ^= 2;
        }
        switch (quadrant) {
            case 0: {
                return FastMath.sinQ(xa, xb);
            }
            case 1: {
                return FastMath.cosQ(xa, xb);
            }
            case 2: {
                return -FastMath.sinQ(xa, xb);
            }
            case 3: {
                return -FastMath.cosQ(xa, xb);
            }
        }
        return Double.NaN;
    }

    public static double cos(double x) {
        int quadrant = 0;
        double xa = x;
        if (x < 0.0) {
            xa = -xa;
        }
        if (xa != xa || xa == Double.POSITIVE_INFINITY) {
            return Double.NaN;
        }
        double xb = 0.0;
        if (xa > 3294198.0) {
            double[] reduceResults = new double[3];
            FastMath.reducePayneHanek(xa, reduceResults);
            quadrant = (int)reduceResults[0] & 3;
            xa = reduceResults[1];
            xb = reduceResults[2];
        } else if (xa > 1.5707963267948966) {
            double remB;
            double remA;
            int k = (int)(xa * 0.6366197723675814);
            while (true) {
                double a = (double)(-k) * 1.570796251296997;
                remA = xa + a;
                remB = -(remA - xa - a);
                a = (double)(-k) * 7.549789948768648E-8;
                double b = remA;
                remA = a + b;
                remB += -(remA - b - a);
                a = (double)(-k) * 6.123233995736766E-17;
                b = remA;
                remA = a + b;
                remB += -(remA - b - a);
                if (remA > 0.0) break;
                --k;
            }
            quadrant = k & 3;
            xa = remA;
            xb = remB;
        }
        switch (quadrant) {
            case 0: {
                return FastMath.cosQ(xa, xb);
            }
            case 1: {
                return -FastMath.sinQ(xa, xb);
            }
            case 2: {
                return -FastMath.cosQ(xa, xb);
            }
            case 3: {
                return FastMath.sinQ(xa, xb);
            }
        }
        return Double.NaN;
    }

    public static double tan(double x) {
        boolean negative = false;
        int quadrant = 0;
        double xa = x;
        if (x < 0.0) {
            negative = true;
            xa = -xa;
        }
        if (xa == 0.0) {
            long bits = Double.doubleToLongBits(x);
            if (bits < 0L) {
                return -0.0;
            }
            return 0.0;
        }
        if (xa != xa || xa == Double.POSITIVE_INFINITY) {
            return Double.NaN;
        }
        double xb = 0.0;
        if (xa > 3294198.0) {
            double[] reduceResults = new double[3];
            FastMath.reducePayneHanek(xa, reduceResults);
            quadrant = (int)reduceResults[0] & 3;
            xa = reduceResults[1];
            xb = reduceResults[2];
        } else if (xa > 1.5707963267948966) {
            double remB;
            double remA;
            int k = (int)(xa * 0.6366197723675814);
            while (true) {
                double a = (double)(-k) * 1.570796251296997;
                remA = xa + a;
                remB = -(remA - xa - a);
                a = (double)(-k) * 7.549789948768648E-8;
                double b = remA;
                remA = a + b;
                remB += -(remA - b - a);
                a = (double)(-k) * 6.123233995736766E-17;
                b = remA;
                remA = a + b;
                remB += -(remA - b - a);
                if (remA > 0.0) break;
                --k;
            }
            quadrant = k & 3;
            xa = remA;
            xb = remB;
        }
        if (xa > 1.5) {
            double pi2a = 1.5707963267948966;
            double pi2b = 6.123233995736766E-17;
            double a = 1.5707963267948966 - xa;
            double b = -(a - 1.5707963267948966 + xa);
            xa = a + (b += 6.123233995736766E-17 - xb);
            xb = -(xa - a - b);
            quadrant ^= 1;
            negative ^= true;
        }
//        double result = !(quadrant & true) ? FastMath.tanQ(xa, xb, false) : -FastMath.tanQ(xa, xb, true);
//        if (negative) {
//            result = -result;
//        }
//        return result;
        return 0;
    }

    public static double atan(double x) {
        return FastMath.atan(x, 0.0, false);
    }

    private static double atan(double xa, double xb, boolean leftPlane) {
        int idx;
        boolean negate = false;
        if (xa == 0.0) {
            return leftPlane ? FastMath.copySign(Math.PI, xa) : xa;
        }
        if (xa < 0.0) {
            xa = -xa;
            xb = -xb;
            negate = true;
        }
        if (xa > 1.633123935319537E16) {
            return negate ^ leftPlane ? -1.5707963267948966 : 1.5707963267948966;
        }
        if (xa < 1.0) {
            idx = (int)((-1.7168146928204135 * xa * xa + 8.0) * xa + 0.5);
        } else {
            double temp = 1.0 / xa;
            idx = (int)(-((-1.7168146928204135 * temp * temp + 8.0) * temp) + 13.07);
        }
        double epsA = xa - TANGENT_TABLE_A[idx];
        double epsB = -(epsA - xa + TANGENT_TABLE_A[idx]);
        double temp = epsA + (epsB += xb - TANGENT_TABLE_B[idx]);
        epsB = -(temp - epsA - epsB);
        epsA = temp;
        temp = xa * 1.073741824E9;
        double ya = xa + temp - temp;
        double yb = xb + xa - ya;
        xa = ya;
        xb += yb;
        if (idx == 0) {
            double denom = 1.0 / (1.0 + (xa + xb) * (TANGENT_TABLE_A[idx] + TANGENT_TABLE_B[idx]));
            ya = epsA * denom;
            yb = epsB * denom;
        } else {
            double temp2 = xa * TANGENT_TABLE_A[idx];
            double za = 1.0 + temp2;
            double zb = -(za - 1.0 - temp2);
            temp2 = xb * TANGENT_TABLE_A[idx] + xa * TANGENT_TABLE_B[idx];
            temp = za + temp2;
            zb += -(temp - za - temp2);
            za = temp;
            ya = epsA / za;
            temp = ya * 1.073741824E9;
            double yaa = ya + temp - temp;
            double yab = ya - yaa;
            temp = za * 1.073741824E9;
            double zaa = za + temp - temp;
            double zab = za - zaa;
            yb = (epsA - yaa * zaa - yaa * zab - yab * zaa - yab * zab) / za;
            yb += -epsA * (zb += xb * TANGENT_TABLE_B[idx]) / za / za;
            yb += epsB / za;
        }
        epsA = ya;
        epsB = yb;
        double epsA2 = epsA * epsA;
        yb = 0.07490822288864472;
        yb = yb * epsA2 + -0.09088450866185192;
        yb = yb * epsA2 + 0.11111095942313305;
        yb = yb * epsA2 + -0.1428571423679182;
        yb = yb * epsA2 + 0.19999999999923582;
        yb = yb * epsA2 + -0.33333333333333287;
        yb = yb * epsA2 * epsA;
        ya = epsA;
        temp = ya + yb;
        yb = -(temp - ya - yb);
        ya = temp;
        double za = EIGHTHS[idx] + ya;
        double zb = -(za - EIGHTHS[idx] - ya);
        temp = za + (yb += epsB / (1.0 + epsA * epsA));
        zb += -(temp - za - yb);
        za = temp;
        double result = za + zb;
        double resultb = -(result - za - zb);
        if (leftPlane) {
            double pia = Math.PI;
            double pib = 1.2246467991473532E-16;
            za = Math.PI - result;
            zb = -(za - Math.PI + result);
            result = za + (zb += 1.2246467991473532E-16 - resultb);
            resultb = -(result - za - zb);
        }
        if (negate ^ leftPlane) {
            result = -result;
        }
        return result;
    }

    public static double atan2(double y, double x) {
        double r;
        if (x != x || y != y) {
            return Double.NaN;
        }
        if (y == 0.0) {
            double result = x * y;
            double invx = 1.0 / x;
            double invy = 1.0 / y;
            if (invx == 0.0) {
                if (x > 0.0) {
                    return y;
                }
                return FastMath.copySign(Math.PI, y);
            }
            if (x < 0.0 || invx < 0.0) {
                if (y < 0.0 || invy < 0.0) {
                    return -Math.PI;
                }
                return Math.PI;
            }
            return result;
        }
        if (y == Double.POSITIVE_INFINITY) {
            if (x == Double.POSITIVE_INFINITY) {
                return 0.7853981633974483;
            }
            if (x == Double.NEGATIVE_INFINITY) {
                return 2.356194490192345;
            }
            return 1.5707963267948966;
        }
        if (y == Double.NEGATIVE_INFINITY) {
            if (x == Double.POSITIVE_INFINITY) {
                return -0.7853981633974483;
            }
            if (x == Double.NEGATIVE_INFINITY) {
                return -2.356194490192345;
            }
            return -1.5707963267948966;
        }
        if (x == Double.POSITIVE_INFINITY) {
            if (y > 0.0 || 1.0 / y > 0.0) {
                return 0.0;
            }
            if (y < 0.0 || 1.0 / y < 0.0) {
                return -0.0;
            }
        }
        if (x == Double.NEGATIVE_INFINITY) {
            if (y > 0.0 || 1.0 / y > 0.0) {
                return Math.PI;
            }
            if (y < 0.0 || 1.0 / y < 0.0) {
                return -Math.PI;
            }
        }
        if (x == 0.0) {
            if (y > 0.0 || 1.0 / y > 0.0) {
                return 1.5707963267948966;
            }
            if (y < 0.0 || 1.0 / y < 0.0) {
                return -1.5707963267948966;
            }
        }
        if (Double.isInfinite(r = y / x)) {
            return FastMath.atan(r, 0.0, x < 0.0);
        }
        double ra = FastMath.doubleHighPart(r);
        double rb = r - ra;
        double xa = FastMath.doubleHighPart(x);
        double xb = x - xa;
        rb += (y - ra * xa - ra * xb - rb * xa - rb * xb) / x;
        double temp = ra + rb;
        rb = -(temp - ra - rb);
        ra = temp;
        if (ra == 0.0) {
            ra = FastMath.copySign(0.0, y);
        }
        double result = FastMath.atan(ra, rb, x < 0.0);
        return result;
    }

    public static double asin(double x) {
        if (x != x) {
            return Double.NaN;
        }
        if (x > 1.0 || x < -1.0) {
            return Double.NaN;
        }
        if (x == 1.0) {
            return 1.5707963267948966;
        }
        if (x == -1.0) {
            return -1.5707963267948966;
        }
        if (x == 0.0) {
            return x;
        }
        double temp = x * 1.073741824E9;
        double xa = x + temp - temp;
        double xb = x - xa;
        double ya = xa * xa;
        double yb = xa * xb * 2.0 + xb * xb;
        ya = -ya;
        yb = -yb;
        double za = 1.0 + ya;
        double zb = -(za - 1.0 - ya);
        temp = za + yb;
        zb += -(temp - za - yb);
        za = temp;
        double y = FastMath.sqrt(za);
        temp = y * 1.073741824E9;
        ya = y + temp - temp;
        yb = y - ya;
        yb += (za - ya * ya - 2.0 * ya * yb - yb * yb) / (2.0 * y);
        double dx = zb / (2.0 * y);
        double r = x / y;
        temp = r * 1.073741824E9;
        double ra = r + temp - temp;
        double rb = r - ra;
        rb += (x - ra * ya - ra * yb - rb * ya - rb * yb) / y;
        temp = ra + (rb += -x * dx / y / y);
        rb = -(temp - ra - rb);
        ra = temp;
        return FastMath.atan(ra, rb, false);
    }

    public static double acos(double x) {
        if (x != x) {
            return Double.NaN;
        }
        if (x > 1.0 || x < -1.0) {
            return Double.NaN;
        }
        if (x == -1.0) {
            return Math.PI;
        }
        if (x == 1.0) {
            return 0.0;
        }
        if (x == 0.0) {
            return 1.5707963267948966;
        }
        double temp = x * 1.073741824E9;
        double xa = x + temp - temp;
        double xb = x - xa;
        double ya = xa * xa;
        double yb = xa * xb * 2.0 + xb * xb;
        ya = -ya;
        yb = -yb;
        double za = 1.0 + ya;
        double zb = -(za - 1.0 - ya);
        temp = za + yb;
        zb += -(temp - za - yb);
        za = temp;
        double y = FastMath.sqrt(za);
        temp = y * 1.073741824E9;
        ya = y + temp - temp;
        yb = y - ya;
        yb += (za - ya * ya - 2.0 * ya * yb - yb * yb) / (2.0 * y);
        yb += zb / (2.0 * y);
        y = ya + yb;
        yb = -(y - ya - yb);
        double r = y / x;
        if (Double.isInfinite(r)) {
            return 1.5707963267948966;
        }
        double ra = FastMath.doubleHighPart(r);
        double rb = r - ra;
        rb += (y - ra * xa - ra * xb - rb * xa - rb * xb) / x;
        temp = ra + (rb += yb / x);
        rb = -(temp - ra - rb);
        ra = temp;
        return FastMath.atan(ra, rb, x < 0.0);
    }

    public static double cbrt(double x) {
        long inbits = Double.doubleToLongBits(x);
        int exponent = (int)(inbits >> 52 & 0x7FFL) - 1023;
        boolean subnormal = false;
        if (exponent == -1023) {
            if (x == 0.0) {
                return x;
            }
            subnormal = true;
            inbits = Double.doubleToLongBits(x *= 1.8014398509481984E16);
            exponent = (int)(inbits >> 52 & 0x7FFL) - 1023;
        }
        if (exponent == 1024) {
            return x;
        }
        int exp3 = exponent / 3;
        double p2 = Double.longBitsToDouble(inbits & Long.MIN_VALUE | (long)(exp3 + 1023 & 0x7FF) << 52);
        double mant = Double.longBitsToDouble(inbits & 0xFFFFFFFFFFFFFL | 0x3FF0000000000000L);
        double est = -0.010714690733195933;
        est = est * mant + 0.0875862700108075;
        est = est * mant + -0.3058015757857271;
        est = est * mant + 0.7249995199969751;
        est = est * mant + 0.5039018405998233;
        est *= CBRTTWO[exponent % 3 + 2];
        double xs = x / (p2 * p2 * p2);
        est += (xs - est * est * est) / (3.0 * est * est);
        est += (xs - est * est * est) / (3.0 * est * est);
        double temp = est * 1.073741824E9;
        double ya = est + temp - temp;
        double yb = est - ya;
        double za = ya * ya;
        double zb = ya * yb * 2.0 + yb * yb;
        temp = za * 1.073741824E9;
        double temp2 = za + temp - temp;
        zb += za - temp2;
        za = temp2;
        zb = za * yb + ya * zb + zb * yb;
        double na = xs - (za *= ya);
        double nb = -(na - xs + za);
        est += (na + (nb -= zb)) / (3.0 * est * est);
        est *= p2;
        if (subnormal) {
            est *= 3.814697265625E-6;
        }
        return est;
    }

    public static double toRadians(double x) {
        if (Double.isInfinite(x) || x == 0.0) {
            return x;
        }
        double facta = 0.01745329052209854;
        double factb = 1.997844754509471E-9;
        double xa = FastMath.doubleHighPart(x);
        double xb = x - xa;
        double result = xb * 1.997844754509471E-9 + xb * 0.01745329052209854 + xa * 1.997844754509471E-9 + xa * 0.01745329052209854;
        if (result == 0.0) {
            result *= x;
        }
        return result;
    }

    public static double toDegrees(double x) {
        if (Double.isInfinite(x) || x == 0.0) {
            return x;
        }
        double facta = 57.2957763671875;
        double factb = 3.145894820876798E-6;
        double xa = FastMath.doubleHighPart(x);
        double xb = x - xa;
        return xb * 3.145894820876798E-6 + xb * 57.2957763671875 + xa * 3.145894820876798E-6 + xa * 57.2957763671875;
    }

    public static int abs(int x) {
        return x < 0 ? -x : x;
    }

    public static long abs(long x) {
        return x < 0L ? -x : x;
    }

    public static float abs(float x) {
        return x < 0.0f ? -x : (x == 0.0f ? 0.0f : x);
    }

    public static double abs(double x) {
        return x < 0.0 ? -x : (x == 0.0 ? 0.0 : x);
    }

    public static double ulp(double x) {
        if (Double.isInfinite(x)) {
            return Double.POSITIVE_INFINITY;
        }
        return FastMath.abs(x - Double.longBitsToDouble(Double.doubleToLongBits(x) ^ 1L));
    }

    public static float ulp(float x) {
        if (Float.isInfinite(x)) {
            return Float.POSITIVE_INFINITY;
        }
        return FastMath.abs(x - Float.intBitsToFloat(Float.floatToIntBits(x) ^ 1));
    }

    public static double scalb(double d, int n) {
        if (n > -1023 && n < 1024) {
            return d * Double.longBitsToDouble((long)(n + 1023) << 52);
        }
        if (Double.isNaN(d) || Double.isInfinite(d) || d == 0.0) {
            return d;
        }
        if (n < -2098) {
            return d > 0.0 ? 0.0 : -0.0;
        }
        if (n > 2097) {
            return d > 0.0 ? Double.POSITIVE_INFINITY : Double.NEGATIVE_INFINITY;
        }
        long bits = Double.doubleToLongBits(d);
        long sign = bits & Long.MIN_VALUE;
        int exponent = (int)(bits >>> 52) & 0x7FF;
        long mantissa = bits & 0xFFFFFFFFFFFFFL;
        int scaledExponent = exponent + n;
        if (n < 0) {
            if (scaledExponent > 0) {
                return Double.longBitsToDouble(sign | (long)scaledExponent << 52 | mantissa);
            }
            if (scaledExponent > -53) {
                long mostSignificantLostBit = (mantissa |= 0x10000000000000L) & 1L << -scaledExponent;
                mantissa >>>= 1 - scaledExponent;
                if (mostSignificantLostBit != 0L) {
                    ++mantissa;
                }
                return Double.longBitsToDouble(sign | mantissa);
            }
            return sign == 0L ? 0.0 : -0.0;
        }
        if (exponent == 0) {
            while (mantissa >>> 52 != 1L) {
                mantissa <<= 1;
                --scaledExponent;
            }
            mantissa &= 0xFFFFFFFFFFFFFL;
            if (++scaledExponent < 2047) {
                return Double.longBitsToDouble(sign | (long)scaledExponent << 52 | mantissa);
            }
            return sign == 0L ? Double.POSITIVE_INFINITY : Double.NEGATIVE_INFINITY;
        }
        if (scaledExponent < 2047) {
            return Double.longBitsToDouble(sign | (long)scaledExponent << 52 | mantissa);
        }
        return sign == 0L ? Double.POSITIVE_INFINITY : Double.NEGATIVE_INFINITY;
    }

    public static float scalb(float f, int n) {
        if (n > -127 && n < 128) {
            return f * Float.intBitsToFloat(n + 127 << 23);
        }
        if (Float.isNaN(f) || Float.isInfinite(f) || f == 0.0f) {
            return f;
        }
        if (n < -277) {
            return f > 0.0f ? 0.0f : -0.0f;
        }
        if (n > 276) {
            return f > 0.0f ? Float.POSITIVE_INFINITY : Float.NEGATIVE_INFINITY;
        }
        int bits = Float.floatToIntBits(f);
        int sign = bits & Integer.MIN_VALUE;
        int exponent = bits >>> 23 & 0xFF;
        int mantissa = bits & 0x7FFFFF;
        int scaledExponent = exponent + n;
        if (n < 0) {
            if (scaledExponent > 0) {
                return Float.intBitsToFloat(sign | scaledExponent << 23 | mantissa);
            }
            if (scaledExponent > -24) {
                int mostSignificantLostBit = (mantissa |= 0x800000) & 1 << -scaledExponent;
                mantissa >>>= 1 - scaledExponent;
                if (mostSignificantLostBit != 0) {
                    ++mantissa;
                }
                return Float.intBitsToFloat(sign | mantissa);
            }
            return sign == 0 ? 0.0f : -0.0f;
        }
        if (exponent == 0) {
            while (mantissa >>> 23 != 1) {
                mantissa <<= 1;
                --scaledExponent;
            }
            mantissa &= 0x7FFFFF;
            if (++scaledExponent < 255) {
                return Float.intBitsToFloat(sign | scaledExponent << 23 | mantissa);
            }
            return sign == 0 ? Float.POSITIVE_INFINITY : Float.NEGATIVE_INFINITY;
        }
        if (scaledExponent < 255) {
            return Float.intBitsToFloat(sign | scaledExponent << 23 | mantissa);
        }
        return sign == 0 ? Float.POSITIVE_INFINITY : Float.NEGATIVE_INFINITY;
    }

    public static double nextAfter(double d, double direction) {
        long bits;
        long sign;
        if (Double.isNaN(d) || Double.isNaN(direction)) {
            return Double.NaN;
        }
        if (d == direction) {
            return direction;
        }
        if (Double.isInfinite(d)) {
            return d < 0.0 ? -1.7976931348623157E308 : Double.MAX_VALUE;
        }
        if (d == 0.0) {
            return direction < 0.0 ? -4.9E-324 : Double.MIN_VALUE;
        }
        if (direction < d ^ (sign = (bits = Double.doubleToLongBits(d)) & Long.MIN_VALUE) == 0L) {
            return Double.longBitsToDouble(sign | (bits & Long.MAX_VALUE) + 1L);
        }
        return Double.longBitsToDouble(sign | (bits & Long.MAX_VALUE) - 1L);
    }

    public static float nextAfter(float f, double direction) {
        int bits;
        int sign;
        if (Double.isNaN(f) || Double.isNaN(direction)) {
            return Float.NaN;
        }
        if ((double)f == direction) {
            return (float)direction;
        }
        if (Float.isInfinite(f)) {
            return f < 0.0f ? -3.4028235E38f : Float.MAX_VALUE;
        }
        if (f == 0.0f) {
            return direction < 0.0 ? -1.4E-45f : Float.MIN_VALUE;
        }
        if (direction < (double)f ^ (sign = (bits = Float.floatToIntBits(f)) & Integer.MIN_VALUE) == 0) {
            return Float.intBitsToFloat(sign | (bits & Integer.MAX_VALUE) + 1);
        }
        return Float.intBitsToFloat(sign | (bits & Integer.MAX_VALUE) - 1);
    }

    public static double floor(double x) {
        if (x != x) {
            return x;
        }
        if (x >= 4.503599627370496E15 || x <= -4.503599627370496E15) {
            return x;
        }
        long y = (long)x;
        if (x < 0.0 && (double)y != x) {
            --y;
        }
        if (y == 0L) {
            return x * (double)y;
        }
        return y;
    }

    public static double ceil(double x) {
        if (x != x) {
            return x;
        }
        double y = FastMath.floor(x);
        if (y == x) {
            return y;
        }
        if ((y += 1.0) == 0.0) {
            return x * y;
        }
        return y;
    }

    public static double rint(double x) {
        double y = FastMath.floor(x);
        double d = x - y;
        if (d > 0.5) {
            if (y == -1.0) {
                return -0.0;
            }
            return y + 1.0;
        }
        if (d < 0.5) {
            return y;
        }
        long z = (long)y;
        return (z & 1L) == 0L ? y : y + 1.0;
    }

    public static long round(double x) {
        return (long) FastMath.floor(x + 0.5);
    }

    public static int round(float x) {
        return (int) FastMath.floor(x + 0.5f);
    }

    public static int min(int a, int b) {
        return a <= b ? a : b;
    }

    public static long min(long a, long b) {
        return a <= b ? a : b;
    }

    public static float min(float a, float b) {
        if (a > b) {
            return b;
        }
        if (a < b) {
            return a;
        }
        if (a != b) {
            return Float.NaN;
        }
        int bits = Float.floatToRawIntBits(a);
        if (bits == Integer.MIN_VALUE) {
            return a;
        }
        return b;
    }

    public static double min(double a, double b) {
        if (a > b) {
            return b;
        }
        if (a < b) {
            return a;
        }
        if (a != b) {
            return Double.NaN;
        }
        long bits = Double.doubleToRawLongBits(a);
        if (bits == Long.MIN_VALUE) {
            return a;
        }
        return b;
    }

    public static int max(int a, int b) {
        return a <= b ? b : a;
    }

    public static long max(long a, long b) {
        return a <= b ? b : a;
    }

    public static float max(float a, float b) {
        if (a > b) {
            return a;
        }
        if (a < b) {
            return b;
        }
        if (a != b) {
            return Float.NaN;
        }
        int bits = Float.floatToRawIntBits(a);
        if (bits == Integer.MIN_VALUE) {
            return b;
        }
        return a;
    }

    public static double max(double a, double b) {
        if (a > b) {
            return a;
        }
        if (a < b) {
            return b;
        }
        if (a != b) {
            return Double.NaN;
        }
        long bits = Double.doubleToRawLongBits(a);
        if (bits == Long.MIN_VALUE) {
            return b;
        }
        return a;
    }

    public static double hypot(double x, double y) {
        int expY;
        if (Double.isInfinite(x) || Double.isInfinite(y)) {
            return Double.POSITIVE_INFINITY;
        }
        if (Double.isNaN(x) || Double.isNaN(y)) {
            return Double.NaN;
        }
        int expX = FastMath.getExponent(x);
        if (expX > (expY = FastMath.getExponent(y)) + 27) {
            return FastMath.abs(x);
        }
        if (expY > expX + 27) {
            return FastMath.abs(y);
        }
        int middleExp = (expX + expY) / 2;
        double scaledX = FastMath.scalb(x, -middleExp);
        double scaledY = FastMath.scalb(y, -middleExp);
        double scaledH = FastMath.sqrt(scaledX * scaledX + scaledY * scaledY);
        return FastMath.scalb(scaledH, middleExp);
    }

    public static double IEEEremainder(double dividend, double divisor) {
        return StrictMath.IEEEremainder(dividend, divisor);
    }

    public static double copySign(double magnitude, double sign) {
        long m = Double.doubleToLongBits(magnitude);
        long s = Double.doubleToLongBits(sign);
        if (m >= 0L && s >= 0L || m < 0L && s < 0L) {
            return magnitude;
        }
        return -magnitude;
    }

    public static float copySign(float magnitude, float sign) {
        int m = Float.floatToIntBits(magnitude);
        int s = Float.floatToIntBits(sign);
        if (m >= 0 && s >= 0 || m < 0 && s < 0) {
            return magnitude;
        }
        return -magnitude;
    }

    public static int getExponent(double d) {
        return (int)(Double.doubleToLongBits(d) >>> 52 & 0x7FFL) - 1023;
    }

    public static int getExponent(float f) {
        return (Float.floatToIntBits(f) >>> 23 & 0xFF) - 127;
    }

    static {
        int i;
        EXP_INT_TABLE_A = new double[1500];
        EXP_INT_TABLE_B = new double[1500];
        EXP_FRAC_TABLE_A = new double[1025];
        EXP_FRAC_TABLE_B = new double[1025];
        FACT = new double[20];
        LN_MANT = new double[1024][];
        LN_SPLIT_COEF = new double[][]{{2.0, 0.0}, {0.6666666269302368, 3.9736429850260626E-8}, {0.3999999761581421, 2.3841857910019882E-8}, {0.2857142686843872, 1.7029898543501842E-8}, {0.2222222089767456, 1.3245471311735498E-8}, {0.1818181574344635, 2.4384203044354907E-8}, {0.1538461446762085, 9.140260083262505E-9}, {0.13333332538604736, 9.220590270857665E-9}, {0.11764700710773468, 1.2393345855018391E-8}, {0.10526403784751892, 8.251545029714408E-9}, {0.0952233225107193, 1.2675934823758863E-8}, {0.08713622391223907, 1.1430250008909141E-8}, {0.07842259109020233, 2.404307984052299E-9}, {0.08371849358081818, 1.176342548272881E-8}, {0.03058958f, 1.2958646899018938E-9}, {0.14982303977012634, 1.225743062930824E-8}};
        LN_QUICK_COEF = new double[][]{{1.0, 5.669184079525E-24}, {-0.25, -0.25}, {0.3333333134651184, 1.986821492305628E-8}, {-0.25, -6.663542893624021E-14}, {0.19999998807907104, 1.1921056801463227E-8}, {-0.1666666567325592, -7.800414592973399E-9}, {0.1428571343421936, 5.650007086920087E-9}, {-0.1250253f, -7.44321345601866E-11}, {0.11113807559013367, 9.219544613762692E-9}};
        LN_HI_PREC_COEF = new double[][]{{1.0, -6.032174644509064E-23}, {-0.25, -0.25}, {0.3333333134651184, 1.9868161777724352E-8}, {-0.2499999701976776, -2.957007209750105E-8}, {0.19999954104423523, 1.5830993332061267E-10}, {-0.1662488f, -2.6033824355191673E-8}};
        SINE_TABLE_A = new double[14];
        SINE_TABLE_B = new double[14];
        COSINE_TABLE_A = new double[14];
        COSINE_TABLE_B = new double[14];
        TANGENT_TABLE_A = new double[14];
        TANGENT_TABLE_B = new double[14];
        RECIP_2PI = new long[]{2935890503282001226L, 9154082963658192752L, 3952090531849364496L, 9193070505571053912L, 7910884519577875640L, 113236205062349959L, 4577762542105553359L, -5034868814120038111L, 4208363204685324176L, 5648769086999809661L, 2819561105158720014L, -4035746434778044925L, -302932621132653753L, -2644281811660520851L, -3183605296591799669L, 6722166367014452318L, -3512299194304650054L, -7278142539171889152L};
        PI_O_4_BITS = new long[]{-3958705157555305932L, -4267615245585081135L};
        EIGHTHS = new double[]{0.0, 0.125, 0.25, 0.375, 0.5, 0.625, 0.75, 0.875, 1.0, 1.125, 1.25, 1.375, 1.5, 1.625};
        CBRTTWO = new double[]{0.6299605249474366, 0.7937005259840998, 1.0, 1.2599210498948732, 1.5874010519681994};
        FastMath.FACT[0] = 1.0;
        for (i = 1; i < FACT.length; ++i) {
            FastMath.FACT[i] = FACT[i - 1] * (double)i;
        }
        double[] tmp = new double[2];
        double[] recip = new double[2];
        for (i = 0; i < 750; ++i) {
            FastMath.expint(i, tmp);
            FastMath.EXP_INT_TABLE_A[i + 750] = tmp[0];
            FastMath.EXP_INT_TABLE_B[i + 750] = tmp[1];
            if (i == 0) continue;
            FastMath.splitReciprocal(tmp, recip);
            FastMath.EXP_INT_TABLE_A[750 - i] = recip[0];
            FastMath.EXP_INT_TABLE_B[750 - i] = recip[1];
        }
        for (i = 0; i < EXP_FRAC_TABLE_A.length; ++i) {
            FastMath.slowexp((double)i / 1024.0, tmp);
            FastMath.EXP_FRAC_TABLE_A[i] = tmp[0];
            FastMath.EXP_FRAC_TABLE_B[i] = tmp[1];
        }
        for (i = 0; i < LN_MANT.length; ++i) {
            double d = Double.longBitsToDouble((long)i << 42 | 0x3FF0000000000000L);
            FastMath.LN_MANT[i] = FastMath.slowLog(d);
        }
        FastMath.buildSinCosTables();
    }
}
