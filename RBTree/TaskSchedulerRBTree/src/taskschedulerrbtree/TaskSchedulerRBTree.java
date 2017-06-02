/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taskschedulerrbtree;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author meetbrahmbhatt
 */
public class TaskSchedulerRBTree {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        System.out.println("Sri Ganesay Namah!");
        //file location:
        // /Volumes/DATA-E/UTA/Fall\ 2015/Algo/input.txt
        try{
            BufferedReader filereader = new BufferedReader(new FileReader("/Volumes/DATA-E/UTA/Fall 2015/Algo/input.txt"));
            String line;
//            System.out.println("Printing the file:\n");
            boolean isFirstLine = true;
            int numberOfTasks=0;
            int durationOfRun=0;
            ArrayList<TaskClass> taskList = new ArrayList<TaskClass>() {};
            int t_startTime;
            int t_taskId;
            int t_totalTimeRequired;
            TaskClass tempObj=null;
            while((line=filereader.readLine())!=null){
//                System.out.println(line);
                if(isFirstLine){
                    isFirstLine=false;
                    String[] tempLine = line.split(" ");
                    numberOfTasks=Integer.parseInt(tempLine[0]);
                    durationOfRun=Integer.parseInt(tempLine[1]);
                }else{
                    String[] tempLine = line.split(",");
                    t_taskId = Integer.parseInt(tempLine[0]);
                    t_startTime = Integer.parseInt(tempLine[1]);
                    t_totalTimeRequired = Integer.parseInt(tempLine[2]);
                    tempObj= new TaskClass(t_taskId, t_startTime, t_totalTimeRequired);
                    taskList.add(tempObj);
                }
            }
//            System.out.println("Number of object in tasklist:"+taskList.size());
            
            //sort arraylist according to start time:
            
            Collections.sort(taskList);
            
            RBTClass rbtobj = new RBTClass();
            int timeSlice =0;
            
            while(timeSlice <= durationOfRun){
                ArrayList<TaskClass> resultList = findTaskTobeAddedintoRBTree(taskList,timeSlice);
                for(int x =0; x < resultList.size();x++){
                    TaskClass treenodeObj = resultList.get(x);
                    rbtobj.insertNode(treenodeObj, 0);
                }
                
                //delete min node from tree which will be the task with min unfairness value, serve that task
                //add that task to RB tree again
                rbtobj.deleteMinServeAddAgain();
                System.out.println("\nTree in order traversal at time = :"+timeSlice);
                timeSlice++;
                Thread.sleep(1000);
                
                rbtobj.inOrderTraversal(rbtobj.root);
//                rbtobj.clearDrawBuffer();
                rbtobj.drawJFrame();
            }
            
            
//            for(int i = 1 ; i < 11 ; i++){
//                System.out.println(i);
//                rbtobj.insertNode(taskList.get(1),i);
//                Thread.sleep(1000);
//                rbtobj.inOrderTraversal(rbtobj.root);
//                System.out.println("\n");
//            }
        }catch(Exception e){
            System.out.println("taskSchedulerRBTree exception: "+e);
        }
    }
    public static ArrayList<TaskClass> findTaskTobeAddedintoRBTree(ArrayList<TaskClass> givenList, int time){
        
        ArrayList<TaskClass> resultSet = new ArrayList<TaskClass>();
        for(int i=0;i<givenList.size();i++){
            
            if(((TaskClass)givenList.get(i)).startTime>time){
                break;
            }
            if(((TaskClass)givenList.get(i)).startTime==time){
                resultSet.add((TaskClass)givenList.get(i));
            }
        }
        
        return resultSet;
    }
//    public ArrayList<AssociationRuleResultClass> customSort(ArrayList<AssociationRuleResultClass> temptrackOfFoundData){
//        for(int i=0; i<temptrackOfFoundData.size(); i++)
//        {
//            AssociationRuleResultClass xtemp = temptrackOfFoundData.get(i);
//            for(int j=i + 1; j<temptrackOfFoundData.size(); j++)
//            {
//                AssociationRuleResultClass ytemp = temptrackOfFoundData.get(j);
//                if(xtemp.comparablenum > ytemp.comparablenum)
//                {
////                    Collections.swap(temptrackOfFoundData,i,j);
//////                     int temp = list[i];
//////                    list[i] = list[j];
//////                    list[j] = temp;
//                    AssociationRuleResultClass tempxy = xtemp;
//                    temptrackOfFoundData.set(i, ytemp);
//                    temptrackOfFoundData.set(j, tempxy);
//                    xtemp = ytemp;
//                    
//                }
//            }
//
//        }
//        return temptrackOfFoundData;
//    }
    
}
