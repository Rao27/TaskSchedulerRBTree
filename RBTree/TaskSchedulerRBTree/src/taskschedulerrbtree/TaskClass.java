/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taskschedulerrbtree;

import java.util.Collections;

/**
 *
 * @author meetbrahmbhatt
 */
public class TaskClass implements Comparable{
            int taskId;
            int unfairnessvalue;
            int startTime;
            int totalTimeRequired;
    public TaskClass(int t_taskId, int t_startTime, int t_totalTimeRequired){
        this.taskId=t_taskId;
        this.startTime=t_startTime;
        this.totalTimeRequired=t_totalTimeRequired;
        this.unfairnessvalue=0;
    }

    @Override
    public int compareTo(Object o) {
        int compare_Start_time = ((TaskClass)o).startTime;
        return this.startTime-compare_Start_time;
    }
}
