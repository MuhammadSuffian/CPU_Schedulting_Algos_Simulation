


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import    java.util.Scanner    ;
import    java.io.BufferedWriter    ;
import    java.io.File    ;
import    java.io.FileWriter   ;

/**
 *
 * @author MSPAK
 */
public class Driver{
    public    static float    FCFS_WT;
    public    static float     FCFS_TAT;
    public    static float    FCFS_ThroughPut_time;
    public    static float       FCFS_Response_Time;
    public    static float    SRT_WT;
    public    static float    SRT_TAT;
    public    static float    SRT_ThroughPut_time;
    public    static float    SRT_Response_Time;
    public    static float    RR_WT;
    public    static float    RR_TAT;
    public    static float    RR_ThroughPut_time;
    public    static float    RR_Response_Time;



    public static void  main   (String  [] args){
        Scheduler a=new Scheduler();

        int Quantum=2;
        Generator g = new Generator();

        Scanner input =new Scanner(System.in);
        System.out.print("Enter  CONTEXT  SWITCHING  TIME :  ");

         int CONTEXT_SWITCHING    =   input.nextInt();
        System.out.print("Enter  TIME  Quantum  for  Round  Robins  :  ");

        while(true){
             Quantum  =   input.nextInt();

            if(  Quantum  ==    0  ){
                System.out.println("  TIME Quantum for Round Robins cant be zero   ");

            }

            else{

                   break;
            }

        }

        try {
            File f1 = new File("results.txt");

            if (f1.exists())
                f1.delete();

            f1.createNewFile();
            FileWriter files= new FileWriter(f1.getName(),true );

            BufferedWriter bw= new BufferedWriter(files);
            bw.write("\nCONTEXT SWITCHING TIME:"+CONTEXT_SWITCHING+"s\n");

            bw.close();
            files.close();

        }

        catch(Exception e)

        {
            //System.out.println("error in login"+e);
        }

        a.FCFSQUEUE(CONTEXT_SWITCHING,g );

         a.SRT(CONTEXT_SWITCHING,g);

        a.Round_Robins(Quantum,g,CONTEXT_SWITCHING);


        System.out.println("\n\n**********FCFS**********");
        System.out.println("Average Wait Time= "+ FCFS_WT);
        System.out.println("Average Turnaround  Time= "+FCFS_TAT);
        System.out.println("Average Response Time= "+FCFS_Response_Time);
        System.out.println("ThroughPut Time= "+FCFS_ThroughPut_time);


        System.out.println("\n\n**********SRT**********");
        System.out.println("     Average Wait Time= "+ SRT_WT);
        System.out.println("     Average Turnaround  Time= "+SRT_TAT);
        System.out.println("     Average Response Time=  "+SRT_Response_Time);
        System.out.println("     ThroughPut Time= "+SRT_ThroughPut_time);


        System.out.println("\n\n**********Round Robins**********");
        System.out.println("     Average Wait Time= "+ RR_WT);
        System.out.println("     Average Turnaround  Time= "+RR_TAT);
        System.out.println("     Average Response Time= "+RR_Response_Time);
        System.out.println("     ThroughPut Time= "+RR_ThroughPut_time);
}
}
