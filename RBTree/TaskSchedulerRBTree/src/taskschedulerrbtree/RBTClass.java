/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taskschedulerrbtree;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JRadioButton;
import javax.swing.SwingUtilities;

/**
 *
 * @author meetbrahmbhatt
 */
public class RBTClass {
    private static final boolean RED = true;
    private static final boolean BLACK = false;
    private int numberOfNodes = 0;
    public TreeNode root;
    public DrawingFrame frameobject=null;
    private class TreeNode{
        private TaskClass value;
        private int unfairnessvalue;
        private TreeNode leftchild, rightchild;
        private TreeNode parentNode;
        private boolean color;
        public TreeNode(TaskClass tvalue, int tunfairnessvalue){
            this.value=tvalue;
            this.unfairnessvalue=tunfairnessvalue;
            this.leftchild=null;
            this.rightchild=null;
            this.parentNode=null;
        }
    }
    RBTClass(){
        root= null;
    }
    private boolean isRed(TreeNode x) {
        if (x == null) return false;
        return x.color == RED;
    }
    public int treeSize(){
        return numberOfNodes;
    }
    private int nodeValue(TreeNode x){
        return x.unfairnessvalue;
    }
    public void insertNode(TaskClass tvalue, int unfairnessvalue){
        if(this.numberOfNodes==0){
            //no element in tree so its gonna be root element;
            root= new TreeNode(tvalue, unfairnessvalue);
            root.color=BLACK;
            root.leftchild=null;
            root.rightchild=null;
            
        }else{
            
            TreeNode createdNode = new TreeNode(tvalue, unfairnessvalue);
            
            
            createdNode.color = RED;
            TreeNode temp = (TreeNode)root;
            
            boolean isRightPlaceFound = false;
            
            while(!isRightPlaceFound){
                
                if(temp.unfairnessvalue>=createdNode.unfairnessvalue){
                    if(temp.leftchild!=null){
                        temp = temp.leftchild;
                    }else{
                        isRightPlaceFound=true;
                        temp.leftchild = createdNode;
                        createdNode.parentNode=temp;
                    }
                }else{
                    
                    if(temp.rightchild!=null){
                        
                        temp = temp.rightchild;
                    }else{
                        
                        isRightPlaceFound=true;
                        temp.rightchild=createdNode;
                        createdNode.parentNode=temp;
                        
                    }
                }
            }
            checkRBViolation(createdNode);
        }
        this.numberOfNodes++;
        
    }
    private void checkRBViolation(TreeNode x){
        TreeNode papa = x.parentNode;
        TreeNode grandPa = papa.parentNode;
        TreeNode uncle=null;
        
        
        if(grandPa!=null){
            if(isLeftNodeOfParent(papa)){
                uncle = grandPa.rightchild;
            }else{
                uncle = grandPa.leftchild;
            }
            if(uncle == null){
                
            }else{
                if( (papa !=null) && (grandPa != null) && (uncle!=null)){
                    if(isRed(papa) && isRed(uncle)){
                        papa.color = BLACK;
                        uncle.color = BLACK;
                        if(grandPa!=root){
                            grandPa.color=RED;
                            checkRBViolation(grandPa);
                        }
                        return;
                    }else if(!isRed(papa) && !isRed(uncle)){
                        return;
                    }
                }
            }
            if(papa != null && grandPa != null){
                if(isLeftNodeOfParent(papa)){
                    if(isLeftNodeOfParent(x)){
                        rightRotation(x);
                    }else{
                        TreeNode pNode = x.parentNode;
                        TreeNode gNode = pNode.parentNode;
                        TreeNode anode=null;
                        TreeNode bnode = null;
                        if(gNode!=null){
                            anode = gNode.parentNode;
                            bnode = gNode.rightchild;
                        }
                        TreeNode cnode = x.leftchild;
                        TreeNode dnode = x.rightchild;
                        TreeNode enode = pNode.leftchild;
                        
                        gNode.leftchild=x;
                        x.parentNode = gNode;
                        x.leftchild=pNode;
                        pNode.parentNode=x;
                        pNode.rightchild = dnode;
                        if(dnode != null)
                            dnode.parentNode=pNode;
                        
                        rightRotation(pNode);
                    }
                }else{
                    if(isLeftNodeOfParent(x)){
                        
                        TreeNode pNode = x.parentNode;
                        TreeNode gNode = pNode.parentNode;
                        
                        TreeNode anode = null;
                        TreeNode bnode = null;
                        if(gNode !=null){
                            anode = gNode.parentNode;
                            bnode = gNode.leftchild;
                        }
                        TreeNode cnode = x.leftchild;
                        TreeNode dnode = x.rightchild;
                        TreeNode enode = pNode.rightchild;
                        
                        x.parentNode = gNode;
                        gNode.rightchild = x;
                        pNode.parentNode = x;
                        x.rightchild = pNode;
                        pNode.leftchild = dnode;
                        if(dnode != null)
                            dnode.parentNode = pNode;
                        
                        leftRotation(pNode);
                    }else{
                        leftRotation(x);
                    }
                }
            }
        }
        
    }
    private void rightRotation(TreeNode x){
        TreeNode pNode = x.parentNode;
        TreeNode gNode = pNode.parentNode;
        TreeNode anode=null;
        TreeNode bnode = null;
        if(gNode!=null){
            anode = gNode.parentNode;
            bnode = gNode.rightchild;
        }
        TreeNode cnode = pNode.rightchild;
        TreeNode dnode = x.rightchild;
        TreeNode enode = x.leftchild;
        
        if(anode != null){
            if(isLeftNodeOfParent(gNode)){
                anode.leftchild = pNode;
            }else{
                anode.rightchild = pNode;
            }
            
        }
        pNode.parentNode = anode;
        pNode.leftchild = x;
        pNode.rightchild = gNode;
        x.leftchild = enode;
        x.rightchild = dnode;
        gNode.leftchild = cnode;
        gNode.rightchild = bnode;
        x.parentNode = pNode;
        gNode.parentNode = pNode;
        if(enode != null)
            enode.parentNode = x;
        if(dnode != null)
            dnode.parentNode = x;
        if(cnode != null)
            cnode.parentNode=gNode;
        if(bnode != null)
            bnode.parentNode=gNode;
        //color change;
        gNode.color=RED;
        x.color = RED;
        pNode.color=BLACK;
        
        if(pNode.parentNode == null){
            root = pNode;
        }
        
    }
    private void leftRotation(TreeNode x){
//        if(x.value.taskId==4 && x.value.unfairnessvalue==9)
//            System.out.println("check violation for task 9");
        TreeNode pNode = x.parentNode;
        TreeNode gNode = pNode.parentNode;
                        
        TreeNode anode = null;
        TreeNode bnode = null;
        if(gNode !=null){
            anode = gNode.parentNode;
            bnode = gNode.leftchild;
        }
        
        TreeNode cnode = pNode.leftchild;
        TreeNode dnode = x.leftchild;
        TreeNode enode = x.rightchild;
        
//        if(x.value.taskId==4 && x.value.unfairnessvalue==9){
//            System.out.println("anode:"+anode.value.taskId);
//            System.out.println("anode colore:"+anode.color);
//            System.out.println("gNode:"+gNode.value.taskId);
//            System.out.println("gNode colore:"+gNode.color);
//            System.out.println("xNode:"+x.value.taskId);
//            System.out.println("x colore:"+x.color);
//            System.out.println("pNode:"+pNode.value.taskId);
//            System.out.println("pNode colore:"+pNode.color);
//        }
        
        if(anode != null){
            if(isLeftNodeOfParent(gNode)){
                anode.leftchild = pNode;
            }else{
                anode.rightchild = pNode;
            }
        }
        pNode.leftchild = gNode;
        pNode.rightchild = x;
        gNode.leftchild = bnode;
        gNode.rightchild = cnode;
        x.leftchild = dnode;
        x.rightchild = enode;
        
        pNode.parentNode = anode;
        
        gNode.parentNode = pNode;
        
        x.parentNode = pNode;
        
        if(bnode != null)
            bnode.parentNode = gNode;
        if(cnode != null)
            cnode.parentNode = gNode;
        if(dnode != null)
            dnode.parentNode = x;
        if(enode != null)
            enode.parentNode =x;
        
        //color change
        pNode.color = BLACK;
        gNode.color = RED;
        x.color=RED;     
        
        if(pNode.parentNode == null){
            root = pNode;
        }
        
    }
    
    
    /*private void rightRotation(TreeNode x){
        TreeNode papa = x.parentNode;
        TreeNode grandPa = papa.parentNode;
        
        papa.color=BLACK;
        x.color=RED;
        grandPa.color=RED;
        grandPa.leftchild = papa.rightchild;
        if(papa.rightchild!=null){
            papa.rightchild.parentNode=grandPa;
        }
        papa.rightchild=grandPa;
        papa.parentNode=grandPa.parentNode;
        if(papa.parentNode == null){
            root = papa;
        }else{
            papa.parentNode.leftchild=papa;
        }
        grandPa.parentNode=papa;
        

        if(grandPa.rightchild.color == RED){
            grandPa.color=BLACK;
            x.color=BLACK;
        }
    }*/
    /*private void leftRotation(TreeNode x){
        TreeNode papa = x.parentNode;
        TreeNode grandPa = papa.parentNode;
        papa.color=BLACK;
        x.color=RED;
        grandPa.color=RED;
        grandPa.rightchild = papa.leftchild;
        if(papa.leftchild!=null){
            papa.leftchild.parentNode=grandPa;
        }
        papa.leftchild = grandPa;
        papa.parentNode=grandPa.parentNode;
        if(papa.parentNode == null){
            root = papa;
        }else{
            papa.parentNode.rightchild=papa;
        }
        grandPa.parentNode=papa;
        
        
        if(grandPa.leftchild !=null){
            if(grandPa.leftchild.color == RED){
                grandPa.color=BLACK;
                x.color=BLACK;
            }
        }
        
        
    }*/
    private boolean isLeftNodeOfParent(TreeNode x){
        return x == x.parentNode.leftchild;
    }
    private boolean isRightNodeOfParent(TreeNode x){
        return x == x.parentNode.rightchild;
    }
    public void clearDrawBuffer(){
        frameobject.drawableCirclesList.clear();
        frameobject.drawbleLinesList.clear();
    }
    
    public void drawTree(TreeNode tnode,int x, int y){
        System.out.println("\ndrawing:"+tnode.value.taskId);
        CircleClass a=new CircleClass(tnode.color, x, y, tnode.value.taskId, tnode.unfairnessvalue);
        frameobject.drawableCirclesList.add(a);
        TreeNode leftchild = tnode.leftchild;
        if(leftchild!=null){
            drawTree(leftchild, x-100, y+100);
            LineClass b = new LineClass(x, y, x-100, y+100);
            frameobject.drawbleLinesList.add(b);
        }
        TreeNode rightchild = tnode.rightchild;
        if(rightchild!=null){
            drawTree(rightchild, x+100, y+100);
            LineClass b = new LineClass(x, y, x+100, y+100);
            frameobject.drawbleLinesList.add(b);
        }
    }
    public void inOrderTraversal(TreeNode x){
        printValue(x);
        if(x.leftchild!=null)
            inOrderTraversal(x.leftchild);
        if(x.rightchild!=null)
            inOrderTraversal(x.rightchild);
    }
    private void printValue(TreeNode x){
            if(x.color==BLACK){
                System.out.print(x.value.taskId+"("+x.unfairnessvalue+")"+"-");
            }else{
                System.out.print(x.value.taskId+"("+x.unfairnessvalue+")"+"*");
            }
            
    }
    public TreeNode findMin(){
        TreeNode temp = root;
        while(temp.leftchild !=null){
            temp = temp.leftchild;
        }
        return temp;
    }
    
    public void deleteMinServeAddAgain(){
        //find min node
        
        TreeNode leftMostNode = findMin();
        
        //deletion of links of that node
        TreeNode pNode = leftMostNode.parentNode;
        
        if(isRed(leftMostNode)){
            if(pNode!=null)
                pNode.leftchild = null;
        
            this.numberOfNodes = this.numberOfNodes - 1;
            leftMostNode.parentNode=null;
            //serve the task:
            leftMostNode.value.unfairnessvalue++;
            //add that node again if it requires more cpu time
            if(leftMostNode.value.totalTimeRequired > leftMostNode.value.unfairnessvalue)
                insertNode(leftMostNode.value, leftMostNode.value.unfairnessvalue);
    //        System.out.println("\n\nTask served:"+leftMostNode.value.taskId);
        }else{
            
            deleteMinWhichIsBlack(leftMostNode);
        }
        
    }
    public void deleteMinWhichIsBlack(TreeNode x){
        boolean xyz=false;
        if(x.value.taskId==3&&x.unfairnessvalue==10){
            xyz=true;
        }
        
        if(x==root){
            
            
            this.numberOfNodes = this.numberOfNodes - 1;
            x.value.unfairnessvalue++;
            if(x.value.totalTimeRequired > x.value.unfairnessvalue)
                insertNode(x.value, x.value.unfairnessvalue);
            
        }else{
            
            if(x.rightchild!=null){
                TreeNode pNode = x.parentNode;
                TreeNode cRightNode = x.rightchild;
                pNode.leftchild = cRightNode;
                cRightNode.parentNode = pNode;
                cRightNode.color=BLACK;
                
                x.parentNode = null;
                x.rightchild = null;
                
                this.numberOfNodes = this.numberOfNodes - 1;
                x.value.unfairnessvalue++;
                if(x.value.totalTimeRequired > x.value.unfairnessvalue)
                    insertNode(x.value, x.value.unfairnessvalue);
                
            }else{
                
                TreeNode pNode = x.parentNode;
                TreeNode sNode = pNode.rightchild;
                if(!isRed(sNode)){
                    
                    
                    TreeNode c2 = sNode.rightchild;
                    TreeNode c1 = sNode.leftchild;
                    boolean isc2red = false;
                    boolean isc1red = false;
                    if(c2!=null){
                        if(isRed(c2))
                            isc2red=true;
                    }
                    if(c1!=null){
                        if(isRed(c1))
                            isc1red=true;
                    }
                    if(!isc1red && !isc2red){
                        //both children are black
                        while(!isRed(pNode)&&(pNode!=root)){
                            //till parent node's color is black
                            if(pNode.color==RED){
                                pNode.color=BLACK;
                                break;
                            }
                            sNode.color=RED;
                            pNode = pNode.parentNode;
                            sNode = pNode.rightchild;
                        }
                        sNode.color=RED;
                        
                        TreeNode parentNode = x.parentNode;
                        parentNode.leftchild=null;
                        x.parentNode=null;
                        this.numberOfNodes = this.numberOfNodes - 1;
                        x.value.unfairnessvalue++;

                        if(x.value.totalTimeRequired > x.value.unfairnessvalue)
                            insertNode(x.value, x.value.unfairnessvalue);
                    }else if(isc2red){
                        //right right case:

                        TreeNode aNode = pNode.parentNode;

                        sNode.parentNode = aNode;
                        if(aNode!=null){
                            aNode.leftchild = sNode;
                        }
                        sNode.leftchild = pNode;
                        pNode.parentNode = sNode;
                        sNode.rightchild = c2;
                        if(c2!=null){
                            c2.parentNode = sNode;
                            c2.color=BLACK;

                        }
                        pNode.rightchild=c1;
                        if(c1!=null){
                            c1.parentNode = pNode;
                        }

                        if(isRed(pNode)){
                            pNode.color=BLACK;
                            sNode.color=RED;
                        }
                        
                        if(aNode==null){
                            sNode.color=BLACK;
                            root=sNode;
                        }

                        //deletion of node and reinsertition.
                        pNode.leftchild=null;
                        x.parentNode=null;
                            
                        
                        
                        this.numberOfNodes = this.numberOfNodes - 1;
                        x.value.unfairnessvalue++;

                        if(x.value.totalTimeRequired > x.value.unfairnessvalue)
                            insertNode(x.value, x.value.unfairnessvalue);
                    }else{
                        //right left case:
                        pNode.rightchild = c1;
                        if(c1!=null){
                            c1.parentNode=pNode;
                            c1.color=BLACK;
                            c1.rightchild=sNode;
                        }

                        sNode.color=RED;
                        sNode.parentNode = c1;
                        sNode.leftchild=null;
                        deleteMinWhichIsBlack(x);

                    }
                }else{
                    //sibling node is red then:
                    TreeNode c1 = sNode.leftchild;
                    TreeNode c2 = sNode.rightchild;
                    TreeNode anode = pNode.parentNode;
                    
                    sNode.parentNode = anode;
                    if(anode!=null){
                        anode.leftchild = sNode;
                    }
                    sNode.color=BLACK;
                    
                    sNode.leftchild = pNode;
                    pNode.parentNode = sNode;
                    pNode.color = BLACK;
                    
                    pNode.rightchild = c1;
                    if(c1!=null){
                        c1.parentNode = pNode;
                        c1.color=RED;
                    }
                    
                    x.parentNode = null;
                    x.rightchild = null;
                    x.leftchild = null;
                    this.numberOfNodes = this.numberOfNodes - 1;
                    x.value.unfairnessvalue++;
                    if(x.value.totalTimeRequired > x.value.unfairnessvalue)
                        insertNode(x.value, x.value.unfairnessvalue);
                    }
            }
            
        }
    }
    private class CircleClass{
        int radius;
        int xpoint;
        int ypoint;
        int taskid;
        int unfairnessvalue;
        boolean ired;
        
        public CircleClass(boolean tisred, int txpoint, int typoint, int ttaskid, int tunfairnessvalue){
            this.ired =tisred;
            this.radius=50;
            this.xpoint=txpoint;
            this.ypoint=typoint;
            this.taskid= ttaskid;
            this.unfairnessvalue = tunfairnessvalue;
        }
    }
    private class LineClass{
        int xpoint1;
        int ypoint1;
        int xpoint2;
        int ypoint2;
        public LineClass(int x1,int y1,int x2,int y2){
            this.xpoint1=x1;
            this.ypoint1=y1;
            this.xpoint2=x2;
            this.ypoint2=y2;
        }
    }
    private class DrawingFrame extends JFrame{
        ArrayList<CircleClass> drawableCirclesList;
        ArrayList<LineClass> drawbleLinesList;
        public DrawingFrame(String x){
            this.setTitle(x);
            drawableCirclesList = new ArrayList<CircleClass>();
            drawbleLinesList = new ArrayList<LineClass>();
            
            
        }
        public void paint(Graphics g){
            for(LineClass e:drawbleLinesList){
                g.setColor(Color.black);
                g.drawLine(e.xpoint1+25, e.ypoint1+25, e.xpoint2+25, e.ypoint2+25);
            }
            for(CircleClass e: drawableCirclesList){
                g.drawOval(e.xpoint, e.ypoint, e.radius, e.radius);
                if(e.ired){
                    g.setColor(Color.red);
                    g.fillOval(e.xpoint, e.ypoint, e.radius, e.radius);
                    g.setColor(Color.black);
                    g.drawString(Integer.toString(e.taskid)+"("+Integer.toString(e.unfairnessvalue)+")", e.xpoint+(e.radius/4), e.ypoint+(4*e.radius/11));
                }else{
                    g.setColor(Color.black);
                    g.fillOval(e.xpoint, e.ypoint, e.radius, e.radius);
                    g.setColor(Color.red);
                    g.drawString(Integer.toString(e.taskid)+"("+Integer.toString(e.unfairnessvalue)+")", e.xpoint+(e.radius/4), e.ypoint+(4*e.radius/11));
                }
                
                
            }  
            
        }
    }
    public void drawJFrame(){
        if(frameobject==null){
            frameobject = new DrawingFrame("Circle and Square");
            frameobject.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frameobject.setSize(1000, 1000);
            frameobject.setResizable(false);
        }else{
            frameobject.drawableCirclesList.clear();
            frameobject.drawbleLinesList.clear();
        }
        
        
        drawTree(root, 450, 50);
        SwingUtilities.updateComponentTreeUI(frameobject);
//        CircleClass a=new CircleClass(RED, 250, 100, 1, 10);
//        frameobject.drawableCirclesList.add(a);
//        a=new CircleClass(BLACK, 250, 250, 2, 10);
//        frameobject.drawableCirclesList.add(a);
        
        frameobject.setVisible(true);
    }
    //    public void deleteMin(){
//        if(this.numberOfNodes==0){
//            System.out.println("tree is empty");
//        }else{
//            if(!isRed(root.leftchild) && !isRed(root.rightchild))
//                root.color=RED;
//            
//        }
//    }
//    public TreeNode deleteMin(TreeNode tempTreeNode){
//        if(tempTreeNode.leftchild == null){
//            return null;
//        }
//        if(!isRed(tempTreeNode.leftchild) && !isRed(tempTreeNode.leftchild.leftchild))
//            tempTreeNode=moveRedLeft(tempTreeNode);
//        
//        tempTreeNode.leftchild = deleteMin(tempTreeNode.leftchild);
//        return balance(tempTreeNode);
//        
//    }
//    public void flipColors(TreeNode tempTreeNode){
//        
//    }
//    public TreeNode moveRedLeft( TreeNode tempTreeNode){
//        flipColors(tempTreeNode);
//        if(isRed(tempTreeNode.rightchild.leftchild)){
//            tempTreeNode.rightchild = rota
//        }
//        return tempTreeNode;
//    }
//    public TreeNode balance(TreeNode tempTreeNode){
//        
//    }

}
