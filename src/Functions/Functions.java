package Functions;

import General.Constant;

import java.math.BigDecimal;
import java.util.Random;

public class Functions {
    private double[] Alphar1 = null;
    private double[] qr1 = null;
    private double fr1;
    private double[] Alphar2 = null;
    private double[] qr2 = null;
    private double fr2;
    private double B;

    public Functions(){
        Alphar1 = new double[Constant.W + 1];
        qr1 = new double[Constant.W + 1];
        fr1 = (new Random()).nextDouble();

        Alphar2 = new double[Constant.W + 1];
        qr2 = new double[Constant.W + 1];
        fr2 = (new Random()).nextDouble();

        B = (new Random()).nextDouble();

        for (int i = 0; i <= Constant.W; i++) {
            Alphar1[i] = (new Random()).nextDouble();
            Alphar2[i] = (new Random()).nextDouble();
        }
    }

    public void calculatingB() {
        this.calculatingAlphar1();
        this.calculatingAlphar2();
        this.calculatingqr1_0();
        this.calculatingqr2_0();
        this.calculatingqr1();
        this.calculatingqr2();
        this.calculatingfr1();
        this.calculatingfr2();
        BigDecimal coefficient1 = BigDecimal.valueOf(this.fr1);
        BigDecimal coefficient2 = BigDecimal.valueOf(this.fr2);
        BigDecimal product = coefficient1.multiply(coefficient2);
        product = product.pow(Constant.W*Constant.M);
        B = product.doubleValue();
    }

    public void calculatingqr1() {
        for (int w = 1; w < Constant.W; w++) {
            BigDecimal product = BigDecimal.valueOf(1.0);
            for (int i = 1; i <= w; i++) {
                BigDecimal numerator = BigDecimal.valueOf(Constant.W - i + 1);
                BigDecimal denumerator = BigDecimal.valueOf(this.Alphar1[i]);
                BigDecimal quotient = numerator.divide(denumerator,15,BigDecimal.ROUND_HALF_DOWN);
                product = product.multiply(quotient);
            }
            product = product.multiply(BigDecimal.valueOf(qr1[0]));
            this.qr1[w] = product.doubleValue();
        }
    }

    public void calculatingqr1_0() {
        BigDecimal sum = BigDecimal.valueOf(1.0);
        for (int w = 1; w < Constant.W; w++) {
            BigDecimal product = BigDecimal.valueOf(1.0);
            for (int i = 1; i <= w; i++) {
                BigDecimal numerator = BigDecimal.valueOf(Constant.W - i + 1);
                BigDecimal denumerator = BigDecimal.valueOf(this.Alphar1[i]);
                BigDecimal quotient = numerator.divide(denumerator,15,BigDecimal.ROUND_HALF_DOWN);
                product = product.multiply(quotient);
            }
            sum = sum.add(product);
        }
        BigDecimal result = BigDecimal.valueOf(1.0).divide(sum,15,BigDecimal.ROUND_HALF_DOWN);
        this.qr1[0] = result.doubleValue();
    }

    public void calculatingqr2() {
        for (int w = 1; w < Constant.W; w++) {
            BigDecimal product = BigDecimal.valueOf(1.0);
            for (int i = 1; i <= w; i++) {
                BigDecimal numerator = BigDecimal.valueOf(Constant.W - i + 1);
                BigDecimal denumerator = BigDecimal.valueOf(this.Alphar2[i]);
                BigDecimal quotient = numerator.divide(denumerator,15,BigDecimal.ROUND_HALF_DOWN);
                product = product.multiply(quotient);
            }
            product = product.multiply(BigDecimal.valueOf(qr2[0]));
            this.qr2[w] = product.doubleValue();
        }
    }

    public void calculatingqr2_0() {
        BigDecimal sum = BigDecimal.valueOf(1.0);
        for (int w = 1; w < Constant.W; w++) {
            BigDecimal product = BigDecimal.valueOf(1.0);
            for (int i = 1; i <= w; i++) {
                BigDecimal numerator = BigDecimal.valueOf(Constant.W - i + 1);
                BigDecimal denumerator = BigDecimal.valueOf(this.Alphar2[i]);
                BigDecimal quotient = numerator.divide(denumerator,15,BigDecimal.ROUND_HALF_DOWN);
                product = product.multiply(quotient);
            }
            sum = sum.add(product);
        }
        BigDecimal result = BigDecimal.valueOf(1.0).divide(sum,15,BigDecimal.ROUND_HALF_DOWN);
        this.qr2[0] = result.doubleValue();
    }

    public void calculatingfr1() {
        BigDecimal sum = BigDecimal.valueOf(0.0);
        for (int k = 1; k < Constant.W; k++) {
            BigDecimal coefficient1 = BigDecimal.valueOf((double)k/(double)Constant.W);
            BigDecimal coefficient2 = BigDecimal.valueOf(this.qr1[k]);
            BigDecimal product = coefficient1.multiply(coefficient2);
            sum = sum.add(product);
        }
        this.fr1 = sum.doubleValue();
    }

    public void calculatingfr2() {
        BigDecimal sum = BigDecimal.valueOf(0.0);
        for (int k = 1; k < Constant.W; k++) {
            BigDecimal coefficient1 = BigDecimal.valueOf((double)k/(double)Constant.W);
            BigDecimal coefficient2 = BigDecimal.valueOf(this.qr2[k]);
            BigDecimal product = coefficient1.multiply(coefficient2);
            sum = sum.add(product);
        }
        this.fr2 = sum.doubleValue();
    }

    public void calculatingAlphar1() {
        Alphar1[0] = 0.0;
        for (int w = 1; w <= Constant.W; w++) {
            BigDecimal rho = BigDecimal.valueOf(Constant.Rho);
            rho = rho.multiply(BigDecimal.valueOf(2-Math.pow((1-fr1),w)-Math.pow((1-fr2),w)));
            Alphar1[w] = rho.doubleValue();
        }
    }

    public void calculatingAlphar2() {
        Alphar2[0] = 0.0;
        for (int w = 1; w <= Constant.W; w++) {
            BigDecimal rho = BigDecimal.valueOf(Constant.Rho);
            rho = rho.multiply(BigDecimal.valueOf(2-Math.pow((1-fr1),w)-Math.pow((1-fr2),w)));
            Alphar1[w] = rho.doubleValue();
        }
    }

    public BigDecimal Erlang(double rho, int x){
        BigDecimal rhox = BigDecimal.valueOf(Math.pow(rho,x));
        BigDecimal xfac = this.getFactorial(x);
        BigDecimal rhox_xfac = rhox.divide(xfac,15,BigDecimal.ROUND_HALF_DOWN);
        BigDecimal sum = BigDecimal.valueOf(0.0);
        for (int k = 0; k <= x; k++) {
            BigDecimal rhox_tmp = BigDecimal.valueOf(Math.pow(rho,k));
            BigDecimal xfac_tmp = this.getFactorial(k);
            BigDecimal rhox_xfac_tmp = rhox_tmp.divide(xfac_tmp,15,BigDecimal.ROUND_HALF_DOWN);
            sum = sum.add(rhox_xfac_tmp);
        }
        return rhox_xfac.divide(sum,15,BigDecimal.ROUND_HALF_DOWN);
    }

    public BigDecimal getFactorial(double number) {
        if (number <= 1)
            return BigDecimal.valueOf(1);
        else
            return BigDecimal.valueOf(number).multiply(getFactorial(number - 1));
    }

    public BigDecimal getFactorial(double number1, double number2) {
        return (getFactorial(number1)).divide(getFactorial(number2),15,BigDecimal.ROUND_HALF_DOWN);
    }

    public double getB() {
        return B;
    }
}
