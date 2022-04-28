
import java.util.Queue;
import java.util.concurrent.locks.ReentrantLock;

public class Child implements Runnable{
    Queue<Child> queue;
    static int counter;
    boolean token;
    private ReentrantLock mutex = new ReentrantLock();
    static Child winner;

    public Child(Queue<Child> queue){
        this.queue=queue;
        token=false;
        counter=1;
    }

    public void setTokenTrue(){
        this.token=true;
    }
    public void setTokenFalse(){
        this.token=false;
    }

    public static Child getWinner() {
        return winner;
    }

    @Override
    public void run() {
       /* System.out.println("başladı");
        System.out.println(this.token+"  "+ Thread.currentThread().toString());*/

        while (true) {
            synchronized (queue) {

                if (!queue.isEmpty()) {
                    if(queue.size()==1){
                        System.out.println("You are the winner "+ winner.toString());
                        System.exit(1);
                    }


                    if (token) {
                        boolean flag=false;
                        Child temp;

                        if (counter % 5 == 0 || counter % 3 == 0) {
                            System.out.println("BOOM");
                            counter++;

                            queue.peek().setTokenFalse();
                            temp = queue.poll();



                            queue.add(temp);
                            queue.peek().setTokenTrue();
                            /*if(Math.random()>0.98){
                                flag=true;
                            }
                            else{
                                flag=false;
                            }*/
                            flag=false;


                        } else {
                            System.out.println(counter);
                            counter++;
                            queue.peek().setTokenFalse();
                            temp = queue.poll();



                            queue.add(temp);
                            queue.peek().setTokenTrue();

                            /*if(Math.random()>0.98) {
                                flag = false;
                            }
                            else{
                                flag=true;
                            }*/
                            flag=true;
                        }
                        if((counter-1) % 5 == 0 || (counter-1) % 3 == 0){
                            if(flag){
                                queue.remove(temp);
                                //System.out.println(temp.toString() +" eliminated");
                                winner=temp;
                            }
                        }
                        else
                        {
                            if(!flag){
                                queue.remove(temp);
                                 //System.out.println(temp.toString() +" eliminated");
                                winner=temp;
                            }
                        }




                    } /*else {

                       // System.out.println("waiting  " + Thread.currentThread().toString());

                       // System.out.println(queue.peek().token + " waiting token");
                    }*/
                   // queue.notifyAll();
                    queue.notify();
                    try {
                        queue.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                }




            }


        }

    }
}
