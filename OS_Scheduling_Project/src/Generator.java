
public class Generator {
Process[] p;
 int i;
    public Generator() {
   
        while(true){
             i=((int) (Math.random()*10));
             if(i>4 && i<7){
                 break;
             }
        }
    int AT, BT;
    p = new Process [i];
    for (int k=0;k<i;k++) {
        AT = ((int)(Math.random()*10));
        while(true){
            BT = ((int) (Math.random()*15));
            if(BT > 0&& BT < 9){
                break;
            }
        }
             p[k] = new Process(k,AT,BT);
         }
   
    }
    public Generator(Generator a){
       i = a.p.length;
        p  =  new Process[i];
        int AT , BT=0;
        for (int k=0 ; k<i ; k++){
            AT = a.p[k].Arrival_Time;
            BT = a.p[k].Burst_Time;
            p[k] = new Process (k,AT,BT);
        }

    }

    }

