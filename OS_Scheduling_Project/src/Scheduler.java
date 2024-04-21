
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Comparator;
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class Scheduler extends Driver {
    public void SRT(int CONTEXT_SWITCHING, Generator a) {
        System.out.println("\n\n\n**************Shortest Remaining Time**************");

        PriorityQueue<Process> Q = new PriorityQueue<>(Comparator.comparingInt(Process::getBurst_Time));
        Queue<Process> Done_Queue = new LinkedList<>();
        Queue<Process> Table = new LinkedList<>();

        Generator g = new Generator(a);
        int[] GanttChat = new int[g.p.length];

        float AVG_TAT = 0, AVG_Wait_Time = 0, AVG_ResponseTime = 0, ThroughPut = 0;
        int complete = 0, previous = -1;

        Process temp;
        int ST = 0;

        do {

            for (int i = 0; i < g.p.length; i++) {

                if (g.p[i].Arrival_Time == ST) {
                    Q.add(g.p[i]);
                }

            }

            ST++;

            if (!Q.isEmpty()) {
                temp = Q.remove();

                if (temp.Process_ID != previous) {
                    temp.Dispatcher = temp.Dispatcher + CONTEXT_SWITCHING;
                }

                if (temp.Burst_Time == temp.in) {
                    temp.Response_Time = ST - temp.Arrival_Time;
                }

                temp.Burst_Time = temp.Burst_Time - 1;

                if (temp.Burst_Time != 0) {
                    Q.add(temp);
                } else {
                    if(complete==0){

                        temp.Completion_Time = ST ;

                    }
                    else{

                        temp.Completion_Time = ST + temp.Dispatcher;

                    }
                    temp.Wait_Time = ST - temp.in - temp.Arrival_Time;

                    temp.Turnaround_Time = temp.Completion_Time - temp.Arrival_Time;

                    Done_Queue.add(temp);

                    complete++;

                }
                previous = temp.Process_ID;

            }
            if (ST == 10) {

                //  System.out.println("hsjfjjausgiuhbfj"+ST+" "+complete);
                ThroughPut = (float) complete / ST;

            }

        } while (complete != g.p.length);


        System.out.println("Total Time:" + ST);

        Process temp1;

        for (int k = 0; !Done_Queue.isEmpty(); k++) {
            temp1 = Done_Queue.remove();

            System.out.println("PID:" + temp1.Process_ID + "\tArrival Time:" + temp1.Arrival_Time + "\tBurst Time:" + temp1.in + "\nCompletion Time:" + temp1.Completion_Time + "\tWaiting Time:" + temp1.Wait_Time + "\tTurnaround Time:" + temp1.Turnaround_Time + "\tResponse Time: " + temp1.Response_Time + "");
            GanttChat[k] = temp1.Process_ID;

            AVG_TAT = AVG_TAT + temp1.Turnaround_Time;
            AVG_ResponseTime = AVG_ResponseTime + temp1.Response_Time;

            AVG_Wait_Time = AVG_Wait_Time + temp1.Wait_Time;
            Table.add(temp1);

        }


        try {
            File f1 = new File("results.txt");
            f1.createNewFile();

            FileWriter files = new FileWriter(f1.getName(), true);
            BufferedWriter bw = new BufferedWriter(files);

            bw.write("\n\n\t\t**************Shortest Remaining Time**************\n\n");
            System.out.println("Job\t Arrival_Time\t Burst_Time\t Finish_Time\t Turnaround_Time\t Waiting_Time");
            bw.write("Job\t Arrival_Time\t\t Burst_Time\t\t  Finish_Time\t\t  Turnaround_Time\t\t\t Waiting_Time\n");

            while (!Table.isEmpty()) {

                temp1 = Table.remove();
                bw.write("\nP:" + temp1.Process_ID + "\t\t " + temp1.Arrival_Time + "\t\t\t\t" + temp1.in + "\t\t\t" + temp1.Completion_Time + "\t\t\t\t   " + temp1.Turnaround_Time + "\t\t\t\t\t" + temp1.Wait_Time + "\n");
                System.out.println("P:" + temp1.Process_ID + "\t\t " + temp1.Arrival_Time + "\t\t\t\t" + temp1.in + "\t\t\t" + temp1.Completion_Time + "\t\t\t\t   " + temp1.Turnaround_Time + "\t\t\t\t\t" + temp1.Wait_Time);

            }
            AVG_Wait_Time = AVG_Wait_Time / g.p.length;
            AVG_TAT = AVG_TAT / g.p.length;

            AVG_ResponseTime = AVG_ResponseTime / g.p.length;
            SRT_WT = AVG_Wait_Time;

            SRT_TAT = AVG_TAT;
            SRT_Response_Time = AVG_ResponseTime;

            SRT_ThroughPut_time = ThroughPut;

            System.out.println(SRT_ThroughPut_time + " " + ThroughPut);
            System.out.println("\nAverage Wait Time=" + AVG_Wait_Time);

            System.out.println("Average Turnaround  Time=" + AVG_TAT);
            System.out.println("Average Response Time  =" + AVG_ResponseTime);

            System.out.println("ThroughPut Time  =" + ThroughPut);
            bw.write("\nAverage Wait Time=" + AVG_Wait_Time);

            bw.write("\nAverage Turnaround  Time=" + AVG_TAT);
            bw.write("\nAverage Response Time  =" + AVG_ResponseTime);

            bw.write("\nThroughPut Time  =" + ThroughPut);

            System.out.println("\nGANTT CHART");
            bw.write("\nGANTT CHART\n");

            for (int i = 0; i < g.p.length; i++) {

                System.out.print("P" + GanttChat[i] + "  ");
                bw.write("P" + GanttChat[i] + "  ");

            }
            bw.close();
            files.close();

        } catch (Exception e) {
            System.out.println("Hello Cutie Error here");
        }

    }


    public void FCFSQUEUE(int CONTEXT_SWITCHING, Generator g) {
        System.out.println("\t**************First Come First Serve**************\n");

        int n = 0, ST = 0;
        float avg_TAT = 0, avg_time = 0;
        int temp;

        Queue<Process> Ready_Queue = new LinkedList<>();
        Queue<Process> Done_Queue = new LinkedList<>();
        Queue<Process> Table = new LinkedList<>();

        int[] GanttChat = new int[g.p.length];

        for (int i = 0; i < g.p.length; i++) {
            temp = g.p[i].Arrival_Time;

            for (int l = i; l < g.p.length; l++) {

                if (g.p[i].Arrival_Time > g.p[l].Arrival_Time) {
                    temp = g.p[l].Arrival_Time;
                    g.p[l].Arrival_Time = g.p[i].Arrival_Time;
                    g.p[i].Arrival_Time = temp;

                    temp = g.p[l].Burst_Time;
                    g.p[l].Burst_Time = g.p[i].Burst_Time;
                    g.p[i].Burst_Time = temp;

                    temp = g.p[l].Process_ID;
                    g.p[l].Process_ID = g.p[i].Process_ID;
                    g.p[i].Process_ID = temp;

                }
            }
        }
        for (int i = 0; i < g.p.length; i++) {
            Ready_Queue.add(g.p[i]);
        }

        for (int i = 0; i < g.p.length; i++) {
            if (i == 0) {

                g.p[0].Completion_Time     = g.p[0].Completion_Time + g.p[0].Burst_Time + g.p[0].Arrival_Time;
                g.p[i].Turnaround_Time     = g.p[i].Completion_Time - g.p[i].Arrival_Time;
                g.p[i].Wait_Time           = g.p[i].Turnaround_Time - g.p[i].Burst_Time;

            } else {

                g.p[i].Completion_Time      = g.p[i - 1].Completion_Time + g.p[i].Burst_Time + (CONTEXT_SWITCHING);
                g.p[i].Turnaround_Time      = g.p[i].Completion_Time - g.p[i].Arrival_Time;
                g.p[i].Wait_Time            = g.p[i].Turnaround_Time - g.p[i].Burst_Time;
            }
        }


        for (int i = 0; i < g.p.length; i++) {

            Done_Queue.add(Ready_Queue.remove());

        }
        int Throughput = 0;

        //Display STUFFF
        Process temp1;

        for (int k = 0; k < g.p.length; k++) {
            temp1 = Done_Queue.remove();

            System.out.println("PID:" + temp1.Process_ID + "\tArrival Time:" + temp1.Arrival_Time + "\tBurst Time:" + temp1.Burst_Time + "\nCompletion Time:" + temp1.Completion_Time + "\tWaiting Time:" + temp1.Wait_Time + "\tTurnaround Time:" + temp1.Turnaround_Time + "\n");
            GanttChat[k] = temp1.Process_ID;

            avg_time = avg_time + temp1.Wait_Time;
            avg_TAT = avg_TAT + temp1.Turnaround_Time;

            Table.add(temp1);

            if (temp1.Completion_Time < 10) {
                Throughput++;
            }
        }

        try {
            File f1 = new File("results.txt");
            f1.createNewFile();

            FileWriter files = new FileWriter(f1.getName(), true);
            BufferedWriter bw = new BufferedWriter(files);

            bw.write("\n\t\t**************First Come First Serve with QUEUE**************\n\n");
            System.out.println("Job\t Arrival_Time\t Burst_Time\t Finish_Time\t Turnaround_Time\t Waiting_Time");
            bw.write("Job\t Arrival_Time\t\t Burst_Time\t\t  Finish_Time\t\t  Turnaround_Time\t\t\t Waiting_Time\n");

            while (!Table.isEmpty()) {

                temp1 = Table.remove();
                bw.write("\nP:" + temp1.Process_ID + "\t\t " + temp1.Arrival_Time + "\t\t\t\t" + temp1.Burst_Time + "\t\t\t" + temp1.Completion_Time + "\t\t\t\t   " + temp1.Turnaround_Time + "\t\t\t\t\t" + temp1.Wait_Time + "\n");
                System.out.println("P:" + temp1.Process_ID + "\t\t " + temp1.Arrival_Time + "\t\t\t\t" + temp1.Burst_Time + "\t\t\t" + temp1.Completion_Time + "\t\t\t\t   " + temp1.Turnaround_Time + "\t\t\t\t\t" + temp1.Wait_Time);

            }
            avg_time = avg_time / g.p.length;
            avg_TAT = avg_TAT / g.p.length;

            FCFS_WT = avg_time;
            FCFS_TAT = avg_TAT;

            FCFS_Response_Time = avg_time;
            FCFS_ThroughPut_time = (float) Throughput / 10;

            System.out.println("\nAverage Wait Time=" + avg_time);
            System.out.println("Average Turnaround  Time=" + avg_TAT);

            System.out.println("Average Response Time=" + avg_time);
            System.out.println("Throughput Time=" + FCFS_ThroughPut_time);

            bw.write("\nAverage Wait Time=" + avg_time);
            bw.write("\nAverage Turnaround  Time=" + avg_TAT);

            bw.write("\nAverage Response Time=" + avg_time);
            bw.write("\nThroughput Time=" + FCFS_ThroughPut_time);

            System.out.println("\nGANTT CHART");
            bw.write("\nGANTT CHART\n");

            for (int i = 0; i < g.p.length; i++) {

                System.out.print("P" + GanttChat[i] + "  ");
                bw.write("P" + GanttChat[i] + "  ");

            }
            bw.close();
            files.close();

        } catch (Exception e) {
            System.out.println("Hello Cutie Error here");
        }

    }

public void Round_Robins(int Slice,Generator a,int CONTEXT_SWITCHING){
    System.out.println("\n\n**************Round Robins**************\n");

    Generator g=new Generator(a);
    float avg_time=0,avg_TAT=0,Throughput=0,avg_RPT=0;

    int Quantum=Slice,previous;

    Queue<Process> Ready_Queue = new LinkedList<>();
    Queue<Process> Done_Queue = new LinkedList<>();
    Queue<Process> Table = new LinkedList<>();

    int[] GanttChat = new int[g.p.length];
    int Arrival_check=0;

    PriorityQueue<Process> Q = new PriorityQueue<>(Comparator.comparingInt(Process::getArrival_Time));

    for(int i=0;i<g.p.length;i++) {
        Q.add(g.p[i]);
    }

    while(!Q.isEmpty()){

        Arrival_check=Q.peek().Arrival_Time;
        Q.remove();

    }

    Process temp;
    int complete=0,ST=0;
    boolean noNewProcess = false;

    do{
        if(ST>Arrival_check&&Ready_Queue.isEmpty()){
            noNewProcess = true;
        }

        for(int i=0;i<g.p.length;i++) {
            if (g.p[i].Arrival_Time >= ST && g.p[i].tel==true) {

                Ready_Queue.add(g.p[i]);
                g.p[i].tel=false;
                noNewProcess = false;

            }
        }
        if(!Ready_Queue.isEmpty()) {

            temp = Ready_Queue.remove();
            ST=ST+Quantum;

            temp.Dispatcher=  temp.Dispatcher+CONTEXT_SWITCHING;

            if (temp.Burst_Time == temp.in) {

                temp.Response_Time = ST - temp.Arrival_Time;

            }

            if (temp.Burst_Time < Quantum && temp.Burst_Time > 0) {

                int res = Quantum - temp.Burst_Time;
                temp.Burst_Time=0;
                ST = ST - res;

            }
            else {

                temp.Burst_Time = temp.Burst_Time - Quantum;

                if (temp.Burst_Time > 0){
                    Ready_Queue.add(temp);
                }

            }

            if (temp.Burst_Time == 0) {
                if(complete==0){
                    temp.Completion_Time = ST;
                }
                else
                {
                    temp.Completion_Time = ST+ temp.Dispatcher;
                }

                temp.Turnaround_Time = temp.Completion_Time - temp.Arrival_Time;
                temp.Wait_Time = temp.Turnaround_Time - temp.in;

                complete++;
                Done_Queue.add(temp);

            }
        }

        else{
            ST++;
        }

    }while(complete!=g.p.length && !noNewProcess);

    Process temp1;

    for (int k = 0; k < g.p.length; k++) {

        temp1 = Done_Queue.remove();

        if(temp1.Wait_Time<0){
            temp1.Wait_Time=0;
        }

        if(temp1.Response_Time<0){
            temp1.Response_Time=0;
        }

        if(temp1.Turnaround_Time<0){
            temp1.Turnaround_Time=0;
        }

        System.out.println("PID:" + temp1.Process_ID + "\tArrival Time:" + temp1.Arrival_Time + "\tBurst Time:" + temp1.in + "\nCompletion Time:" + temp1.Completion_Time + "\tWaiting Time:" + temp1.Wait_Time + "\tTurnaround Time:" + temp1.Turnaround_Time + "\n");
        GanttChat[k] = temp1.Process_ID;

        avg_time = avg_time + temp1.Wait_Time;
        avg_TAT = avg_TAT + temp1.Turnaround_Time;

        avg_RPT= avg_RPT+temp1.Response_Time;
        Table.add(temp1);

        if (temp1.Completion_Time < 10) {
            Throughput++;
        }
    }

    try {
        File f1 = new File("results.txt");
        f1.createNewFile();

        FileWriter files = new FileWriter(f1.getName(), true);
        BufferedWriter bw = new BufferedWriter(files);

        bw.write("\n\t\t*************Round Robins**************\n\n\n");
        System.out.println("Job\t Arrival_Time\t Burst_Time\t Finish_Time\t Turnaround_Time\t Waiting_Time");
        bw.write("Job\t Arrival_Time\t\t Burst_Time\t\t  Finish_Time\t\t  Turnaround_Time\t\t\t Waiting_Time\n");

        while (!Table.isEmpty()) {
            temp1 = Table.remove();
            bw.write("\nP:" + temp1.Process_ID + "\t\t " + temp1.Arrival_Time + "\t\t\t\t" + temp1.in + "\t\t\t" + temp1.Completion_Time + "\t\t\t\t   " + temp1.Turnaround_Time + "\t\t\t\t\t" + temp1.Wait_Time + "\n");
            System.out.println("P:" + temp1.Process_ID + "\t\t " + temp1.Arrival_Time + "\t\t\t\t" + temp1.in + "\t\t\t" + temp1.Completion_Time + "\t\t\t\t   " + temp1.Turnaround_Time + "\t\t\t\t\t" + temp1.Wait_Time);
        }

        avg_time = avg_time / g.p.length;
        avg_TAT = avg_TAT / g.p.length;

        avg_RPT=avg_RPT/g.p.length;
        RR_WT = avg_time;

        RR_TAT = (float) (avg_TAT+0.1);
        RR_Response_Time =  avg_RPT;
        RR_ThroughPut_time = (float) Throughput / 10;

        System.out.println("\nAverage Wait Time=" + avg_time);
        System.out.println("Average Turnaround  Time=" + avg_TAT);

        System.out.println("Average Response Time=" + avg_time);
        System.out.println("Throughput Time=" + RR_ThroughPut_time);

        bw.write("\nAverage Wait Time=" + avg_time);
        bw.write("\nAverage Turnaround  Time=" + avg_TAT);

        bw.write("\nAverage Response Time=" + avg_time);
        bw.write("\nThroughput Time=" + RR_ThroughPut_time);

        System.out.println("\nGANTT CHART");
        bw.write("\nGANTT CHART\n");

        for (int i = 0; i < g.p.length; i++) {
            System.out.print("P" + GanttChat[i] + "  ");
            bw.write("P" + GanttChat[i] + "  ");
        }

        bw.close();
        files.close();

    } catch (Exception e) {
        System.out.println("Hello");
    }

}
}

    
    

