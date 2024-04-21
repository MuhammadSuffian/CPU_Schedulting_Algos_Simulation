

public class Process {
    int Process_ID,Arrival_Time,Burst_Time,Wait_Time=0,Response_Time=0,Turnaround_Time=0,Completion_Time=0,in,Dispatcher;
    boolean tel;

    public Process(int Process_ID, int Arrival_Time,int Burst_Time) {

        this.Process_ID = Process_ID;

        this.Arrival_Time = Arrival_Time;

        this.Burst_Time=Burst_Time;

        in=Burst_Time;

        Dispatcher=0;

        tel=true;

    }

    public Process() {

    }

    public void setter(int Burst_Time,int Wait_Time,int Response_Time,int Turnaround_Time) {

        this.Burst_Time = Burst_Time;

        this.Wait_Time = Wait_Time;

           this.Response_Time = Response_Time;

            this.Turnaround_Time = Turnaround_Time;

    }

    public void setBurst_Time(int Burst_Time) {

        this.Burst_Time = Burst_Time;

    }


    public int getProcess_ID()   {
        return      Process_ID;
    }

    public int getArrival_Time()  {
         return       Arrival_Time;
      }

    public int getBurst_Time() {
        return       Burst_Time;
    }

    public int getWait_Time() {
        return      Wait_Time;
    }

    public int getResponse_Time() {
        return      Response_Time;
    }

    public int getTurnaround_Time() {
        return       Turnaround_Time;
    }

    
}
