
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class ErrorFreeBoom {
    private static  int threadNumber;

    public static void main(String[] args) throws InterruptedException {
        Scanner scn = new Scanner(System.in);
        System.out.println("enter child number: ");
        threadNumber=scn.nextInt();

        Thread[] threads = new Thread[threadNumber];
        Queue<Child> queue = new LinkedList<>();

        Child c=new Child(queue);
        c.setTokenTrue();
        queue.add(c);
        threads[0]=new Thread(c);
        for(int i=1;i<threadNumber;i++){
            Child child=new Child(queue);
            queue.add(child);
            //queue.poll(); queue dan çıkarma
            //queue.add(child); queue ekleme
            threads[i]=new Thread(child);
        }

        for (int i = 0; i < threadNumber; i++) {
            threads[i].start();
        }
        for (int i = 0; i < threadNumber; i++) {
            threads[i].join();
        }



    }

}
