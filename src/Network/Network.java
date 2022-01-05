package Network;

import General.CommonObject;
import General.Constant;

import java.util.ArrayList;
import java.util.HashMap;

public class Network extends CommonObject {
    private HashMap<String, Node> nodeList = null; 			// list of nodes within the network
    private ArrayList<Node> nodeList2 = null;
    private HashMap<String, Link> linkList =null;			// list of links within the network
    private ArrayList<Link> linkList2 = null;
    private HashMap<String, Route> routeList =null;			// list of links within the network
    private ArrayList<Route> routeList2 = null;

    public Network(String name, int index) {
        super(name, index);

        this.nodeList = new HashMap<String,Node>();
        this.nodeList2 = new ArrayList<Node>();

        this.linkList = new HashMap<String,Link>();
        this.linkList2 = new ArrayList<Link>();

        this.routeList = new HashMap<String,Route>();
        this.routeList2 = new ArrayList<Route>();
    }

    public void addNode(Node node){
        this.nodeList.put(node.getName(), node);
        this.nodeList2.add(node);
    }

    public void addLink(Link link){
        this.linkList.put(link.getName(), link);
        this.linkList2.add(link);
    }

    public void addRoute(Route route){
        this.routeList.put(route.getName(), route);
        this.routeList2.add(route);
    }

    public void generateLink(String nodeAName, String nodeBName) {
        Node nodeA = this.getNodeList().get(nodeAName);
        Node nodeB = this.getNodeList().get(nodeBName);

        String name = nodeA.getName()+"-"+nodeB.getName();
        int index = this.getLinkList2().size();

        Link link = new Link(name, index, nodeA, nodeB, this);
        this.addLink(link);
    }

    public void generateTopology() {
        for (int i = 0; i < Constant.M; i++) {
            Node nod = new Node("Middle_" + i, this.nodeList2.size(), this);
            this.addNode(nod);
        }

        for (int i = 0; i < Constant.R; i++) {
            Node nod = new Node("Ingress_" + i, this.nodeList2.size(), this);
            this.addNode(nod);
        }

        for (int i = 0; i < Constant.R; i++) {
            Node nod = new Node("Egress_" + i, this.nodeList2.size(), this);
            this.addNode(nod);
        }

        for (int i = 0; i < Constant.M; i++) {
            Node nodm = this.nodeList.get("Middle_" + i);
            for (int j = 0; j < Constant.R; j++) {
                Node nodi = this.nodeList.get("Ingress_" + j);
                Node node = this.nodeList.get("Egress_" + j);
                this.generateLink(nodi.getName(), nodm.getName());
                this.generateLink(nodm.getName(), node.getName());
            }
        }
        for (int i = 0; i < Constant.R; i++) {
            Node nodi = this.nodeList.get("Ingress_" + i);
            for (int j = 0; j < Constant.R; j++) {
                Node node = this.nodeList.get("Egress_" + j);
                for (int m = 0; m < Constant.M; m++) {
                    Node nodm = this.nodeList.get("Middle_" + m);
                    String nameAMB = nodi.getName() + "-" + nodm.getName() + "-" + node.getName();
                    Route route = new Route(nameAMB, this.getRouteList2().size(), nodi, nodm, node, this);
                    this.addRoute(route);
                }
            }
        }
    }

    public Link findLink(Node nodeA, Node nodeB){
        return this.getLinkList().get(nodeA.getName()+"-"+nodeB.getName());
    }

    public double getB() {
        double F_total = 0.0;
        double B_total = 0.0;
//        double total = (Constant.rho * Constant.n * Constant.r) / (Constant.m);
        double total = Constant.rho * Constant.n * Constant.r;
        for(Route route: this.getRouteList2()){
            F_total += Constant.Rho;
            B_total += route.getPb()*Constant.Rho;
        }
        System.out.println("total offered load is: "+total+".");
        System.out.println("network offered load is: "+F_total+".");
        System.out.println("network blocked load is: "+B_total+".");
        return (1 - (F_total - B_total)/total);
    }

    public HashMap<String, Node> getNodeList() {
        return nodeList;
    }

    public ArrayList<Node> getNodeList2() {
        return nodeList2;
    }

    public HashMap<String, Link> getLinkList() {
        return linkList;
    }

    public ArrayList<Link> getLinkList2() {
        return linkList2;
    }

    public HashMap<String, Route> getRouteList() {
        return routeList;
    }

    public ArrayList<Route> getRouteList2() {
        return routeList2;
    }
}
