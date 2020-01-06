package xyz.wongs.weathertop.datastructure.link.oneway;

public class OneWayLink {

    Node head = null;


    public void addNode(int data){
        Node nwNode = new Node(data);
        if(null==head){
            head = nwNode;
            return ;
        }
        Node tmp = head;
        while (tmp.next != null) {
            tmp = tmp.next;
        }
        tmp.next = nwNode;
    }

    public int length(){
        int length = 0;
        Node tmp = head;
        while (tmp != null) {
            length++;
            tmp = tmp.next;
        }
        return length;
    }


    public static void main(String[] args) {
        OneWayLink oneWayLink = new OneWayLink();
        oneWayLink.addNode(2);
        oneWayLink.addNode(3);
        oneWayLink.addNode(4);
        System.out.println(oneWayLink.head.data);
        System.out.println(oneWayLink.length());

    }

    class Node {
        // 节点的引用，指向下一个节点
        Node next = null;
        // 节点的对象，即内容
        int data;
        public Node(int data) {
            this.data = data;
        }
    }

}
