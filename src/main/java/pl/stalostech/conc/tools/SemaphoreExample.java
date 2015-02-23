package pl.stalostech.conc.tools;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SemaphoreExample {
    
    public static void main(String... args) {
        Printer printer = new Printer();
        
        PrintJob prints[]=new PrintJob[10];
        for (int i=0; i<10; i++){
            prints[i]=new PrintJob(printer);
        }
        
        for (int i=0; i<10; i++){
            new Thread(prints[i]).start();
        }
    }
    
    public static class Printer{
        
        private final Semaphore sempahore;
        private boolean[] freePrinters;
        private Lock printerLock; 
        
        public Printer(){
            sempahore = new Semaphore(3); //we have 3 printers
            freePrinters = new boolean[3];
            for (int i = 0; i < 3; i ++){
                freePrinters[i] = true;
            }
            printerLock = new ReentrantLock();
        }
        
        public void print(){
            try {
                sempahore.acquire();
                int assignedPrinter=getPrinter();
                System.out.println("Printing... " + Thread.currentThread().getName() + ", by printer : " + assignedPrinter);
                Thread.sleep(2000);
                freePrinters[assignedPrinter]=true;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally{
                sempahore.release();
            }
        }
        
        private int getPrinter(){
            int ret = -1;
            printerLock.lock();
            for (int i = 0; i < 3; i++){
                if (freePrinters[i]){
                    ret = i;
                    freePrinters[i] = false;
                    break;
                }
            }
            printerLock.unlock();
            return ret;
        }
        
    }
    
    public static class PrintJob implements Runnable{
        
        private Printer printer;
        
        public PrintJob(Printer printer){
            this.printer = printer;
        }
        
        @Override
        public void run() {
            printer.print();
        }
        
    }
}
