package Network;

import General.CommonObject;
import General.Constant;

import java.math.BigDecimal;
import java.util.Random;

public class Link extends CommonObject {
    private Network associatedNetwork = null;					// network that the link belongs to

    private double f;
    private double[] q = null;
    private double[] Alpha = null;

    public Link(String name, int index, Node nodeA, Node nodeB, Network associatedNetwork) {
        super(name, index);
        this.associatedNetwork = associatedNetwork;

        f = 1.0;
        q = new double[Constant.W + 1];
        Alpha = new double[Constant.W + 1];

        for (int i = 0; i <= Constant.W; i++) {
//            Alpha[i] = (new Random()).nextDouble();
//            q[i] = (new Random()).nextDouble();
            Alpha[i] = 1.0;
            q[i] = 1.0;
        }
    }

    public void calculatingq() {
        for (int w = 1; w < Constant.W; w++) {
            BigDecimal product = BigDecimal.valueOf(1.0);
            for (int i = 1; i <= w; i++) {
                BigDecimal numerator = BigDecimal.valueOf(Constant.W - i + 1);
                BigDecimal denumerator = BigDecimal.valueOf(this.Alpha[i]);
                BigDecimal quotient = numerator.divide(denumerator,15,BigDecimal.ROUND_HALF_DOWN);
                product = product.multiply(quotient);
            }
            product = product.multiply(BigDecimal.valueOf(q[0]));
            this.q[w] = product.doubleValue();
//            System.out.println("(link)"+this.getName()+": q["+w+"] = "+q[w]+".");
        }
    }

    public void calculatingq0() {
        BigDecimal sum = BigDecimal.valueOf(1.0);
        for (int w = 1; w < Constant.W; w++) {
            BigDecimal product = BigDecimal.valueOf(1.0);
            for (int i = 1; i <= w; i++) {
                BigDecimal numerator = BigDecimal.valueOf(Constant.W - i + 1);
                BigDecimal denumerator = BigDecimal.valueOf(this.Alpha[i]);
                BigDecimal quotient = numerator.divide(denumerator,15,BigDecimal.ROUND_HALF_DOWN);
                product = product.multiply(quotient);
            }
            sum = sum.add(product);
        }
        BigDecimal result = BigDecimal.valueOf(1.0).divide(sum,15,BigDecimal.ROUND_HALF_DOWN);
        this.q[0] = result.doubleValue();
//        System.out.println("(link)"+this.getName()+": q[0] = "+q[0]+".");
    }

    public void calculatingf() {
        BigDecimal sum = BigDecimal.valueOf(0.0);
        for (int k = 1; k < Constant.W; k++) {
            BigDecimal coefficient1 = BigDecimal.valueOf((double) k / (double) Constant.W);
            BigDecimal coefficient2 = BigDecimal.valueOf(this.q[k]);
            BigDecimal product = coefficient1.multiply(coefficient2);
            sum = sum.add(product);
        }
        this.f = sum.doubleValue();
//        System.out.println("(link)"+this.getName()+": f = "+f+".");
    }

    public void calculatingAlpha() {
        Alpha[0] = 0.0;
        for (int w = 1; w <= Constant.W; w++) {
            BigDecimal sum = BigDecimal.valueOf(0.0);
            for(Route route : this.associatedNetwork.getRouteList2()){
                if(route.isLinkinRoute(this)) {
                    int index_link = route.getLinkList().indexOf(this);
                    double f_link = 0;
                    if (index_link == 1){
                        f_link = route.getPr2()[w];
                    }
                    else if (index_link == 0){
                        f_link = route.getPr1()[w];
                    }
                    else {
                        System.out.println("Error happens in link file. Please check. The link index is greater than 1.");
                    }

                    BigDecimal rho = BigDecimal.valueOf(Constant.Rho);
                    rho = rho.multiply(BigDecimal.valueOf(f_link));
                    sum = sum.add(rho);
                }
            }
            this.Alpha[w] = sum.doubleValue();
//            System.out.println("(link)"+this.getName()+": Alpha["+w+"] = "+Alpha[w]+".");
        }
    }

    public double getF() {
        return f;
    }

    public double[] getQ() {
        return q;
    }

    public double[] getAlpha() {
        return Alpha;
    }
}
