package General;

import java.math.BigDecimal;

public class Constant {
    public final static int n = 10;
    public final static int m = 5;
    public final static int r = 10;
    public final static int w = 5;

    public final static int x = 5;

    public final static double rho = 1.5;

    public final static double epison = 0.000001;

    public final static int W = w;
    public final static int M = m;
    public final static int R = r;
//    public final static double Rho = rho;
    public final static double Rho = CalculateCarriedLoad();

    private static double CalculateCarriedLoad(){
        BigDecimal rhoI = BigDecimal.valueOf(rho).multiply(BigDecimal.valueOf(1.0).subtract(Erlang(rho,x)));
        BigDecimal rhoE = rhoI.multiply(BigDecimal.valueOf(1.0).subtract(Erlang(rhoI.doubleValue(),x)));
        BigDecimal rhoRoute = rhoE.divide(BigDecimal.valueOf(m*r),15,BigDecimal.ROUND_HALF_DOWN).multiply(BigDecimal.valueOf(n));

        System.out.println("Port Carried Load: rho = "+rhoE.doubleValue()+".");
        System.out.println("Network Carried Load: rho = "+rhoRoute.doubleValue()+".");
        return rhoRoute.doubleValue();
    }

    private static BigDecimal Erlang(double rho, int device){
        BigDecimal rhox = BigDecimal.valueOf(Math.pow(rho,device));
        BigDecimal xfac = getFactorial(device);
        BigDecimal rhox_xfac = rhox.divide(xfac,15,BigDecimal.ROUND_HALF_DOWN);
        BigDecimal sum = BigDecimal.valueOf(0.0);
        for (int k = 0; k <= device; k++) {
            BigDecimal rhox_tmp = BigDecimal.valueOf(Math.pow(rho,k));
            BigDecimal xfac_tmp = getFactorial(k);
            BigDecimal rhox_xfac_tmp = rhox_tmp.divide(xfac_tmp,15,BigDecimal.ROUND_HALF_DOWN);
            sum = sum.add(rhox_xfac_tmp);
        }
        return rhox_xfac.divide(sum,15,BigDecimal.ROUND_HALF_DOWN);
    }

    private static BigDecimal getFactorial(double number) {
        if (number <= 1)
            return BigDecimal.valueOf(1);
        else
            return BigDecimal.valueOf(number).multiply(getFactorial(number - 1));
    }

    private static BigDecimal getFactorial(double number1, double number2) {
        return (getFactorial(number1)).divide(getFactorial(number2),15,BigDecimal.ROUND_HALF_DOWN);
    }
}
