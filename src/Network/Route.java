package Network;

import General.CommonObject;
import General.Constant;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

public class Route extends CommonObject {
    private ArrayList<Link> linkList = null;

    private double Pb;
//    private HashMap<Link, double[]> Pf = null;
    private double[] Pr1 = null;
    private double[] Pr2 = null;

    public Route(String name, int index, Node nodeSrc, Node nodeMid, Node nodeDst, Network associatedNetwork) {
        super(name, index);
        this.linkList = new ArrayList<Link>();
//        this.Pf = new HashMap<Link, double[]>();

        linkList.add((associatedNetwork).findLink(nodeSrc,nodeMid));
        linkList.add((associatedNetwork).findLink(nodeMid,nodeDst));

        Pr1 = new double[Constant.W + 1];
        Pr2 = new double[Constant.W + 1];

        Pb = 0;

        Pr1[0] = 0;
        Pr2[0] = 0;

//        this.calculatingPr1();
//        this.calculatingPr2();
    }

//    public void calculatingPr1() {
//        Link r2 = this.getLinkList().get(1);
//        for (int w = 1; w <= Constant.W; w++) {
//            BigDecimal sum = BigDecimal.valueOf(0.0);
//            for (int w1 = 0; w1 <= Constant.W; w1++) {
//                BigDecimal value = BigDecimal.valueOf(r2.getQ()[w1]);
//                value = value.multiply( BigDecimal.valueOf(1.0).subtract((this.getFactorial((Constant.W-w1),w)).divide(this.getFactorial(Constant.W,w),15,BigDecimal.ROUND_HALF_DOWN)));
//                sum = sum.add(value);
//            }
//            this.Pr1[w] = sum.doubleValue();
////            System.out.println("(route)"+this.getName()+": Pr1["+w+"] = "+Pr1[w]+".");
//        }
//    }
//
//    public void calculatingPr2() {
//        Link r1 = this.getLinkList().get(0);
//        for (int w = 1; w <= Constant.W; w++) {
//            BigDecimal sum = BigDecimal.valueOf(0.0);
//            for (int w1 = 0; w1 <= Constant.W; w1++) {
//                BigDecimal value = BigDecimal.valueOf(r1.getQ()[w1]);
//                value = value.multiply( BigDecimal.valueOf(1.0).subtract((this.getFactorial((Constant.W-w1),w)).divide(this.getFactorial(Constant.W,w),15,BigDecimal.ROUND_HALF_DOWN)));
//                sum = sum.add(value);
//            }
//            this.Pr2[w] = sum.doubleValue();
////            System.out.println("(route)"+this.getName()+": Pr1["+w+"] = "+Pr1[w]+".");
//        }
//    }
//
//    public void calculatingPb() {
//        Link r1 = this.getLinkList().get(0);
//        Link r2 = this.getLinkList().get(1);
//        BigDecimal sum = BigDecimal.valueOf(0.0);
//        for (int w1 = 1; w1 <= Constant.W; w1++) {
//            BigDecimal value1 = BigDecimal.valueOf(r1.getQ()[w1]);
//            for (int w2 = 0; w2 <= Constant.W; w2++) {
//                BigDecimal value2 = BigDecimal.valueOf(r2.getQ()[w2]);
//                BigDecimal value = value1.multiply(value2.multiply((this.getFactorial((Constant.W - w2), w1)).divide(this.getFactorial(Constant.W, w1), 15, BigDecimal.ROUND_HALF_DOWN)));
//                sum = sum.add(value);
//            }
//        }
//        this.Pb = sum.doubleValue();
////        System.out.println("(route)"+this.getName()+": Pb = "+Pb+".");
//    }

    public void calculatingPr1() {
        for (int w = 1; w <= Constant.W; w++) {
            BigDecimal value = BigDecimal.valueOf(1.0);
            value = value.subtract(BigDecimal.valueOf(this.linkList.get(1).getF()));
            value = value.pow(w);
            value = BigDecimal.valueOf(1.0).subtract(value);
            this.Pr1[w] = value.doubleValue();
//            System.out.println("(route)"+this.getName()+": Pr1["+w+"] = "+Pr1[w]+".");
        }
    }

    public void calculatingPr2() {
        for (int w = 1; w <= Constant.W; w++) {
            BigDecimal value = BigDecimal.valueOf(1.0);
            value = value.subtract(BigDecimal.valueOf(this.linkList.get(0).getF()));
            value = value.pow(w);
            value = BigDecimal.valueOf(1.0).subtract(value);
            this.Pr2[w] = value.doubleValue();
//            System.out.println("(route)"+this.getName()+": Pr2["+w+"] = "+Pr2[w]+".");
        }
    }

    public void calculatingPb() {
        BigDecimal fr1 = BigDecimal.valueOf(this.linkList.get(0).getF());
        BigDecimal fr2 = BigDecimal.valueOf(this.linkList.get(1).getF());
        BigDecimal B = (BigDecimal.valueOf(1.0).subtract(fr1.multiply(fr2))).pow(Constant.W);
        this.Pb = B.doubleValue();
//        System.out.println("(route)"+this.getName()+": Pb = "+Pb+".");
    }

    public ArrayList<Link> getLinkList() {
        return this.linkList;
    }

    public double[] getPr1() {
        return this.Pr1;
    }

    public double[] getPr2() {
        return this.Pr2;
    }

    public double getPb() {
        return this.Pb;
    }

    public boolean isLinkinRoute (Link link) {
        return this.linkList.contains(link);
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
}
