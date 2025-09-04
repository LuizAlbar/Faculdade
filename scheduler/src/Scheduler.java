import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class Scheduler {
    
    public enum Algorithm { FCFS, SJF, RR }
    
    private Algorithm algorithm;
    private int quantum;
    private Queue<Process> queue;
    private PriorityQueue<Process> priorityQueue;
    
    public Scheduler(Algorithm algorithm, int quantum) {
        this.algorithm = algorithm;
        this.quantum = quantum;
        
        if (algorithm == Algorithm.SJF) {
            priorityQueue = new PriorityQueue<>(Comparator.comparingInt(Process::getInstructions));
        } else {
            queue = new LinkedList<>();
        }
    }
    
    public void addProcess(Process process) {
        if (algorithm == Algorithm.SJF) {
            priorityQueue.add(process);
        } else {
            queue.add(process);
        }
    }
    
    public Process nextProcess() {
        switch (algorithm) {
            case FCFS:
            case RR:
                return queue.poll();
                
            case SJF:
                return priorityQueue.poll();
                
            default:
                return null;
        }
    }
    
    public boolean existProcesses() {
        if (algorithm == Algorithm.SJF) {
            return !priorityQueue.isEmpty();
        }
        return !queue.isEmpty();
    }
    
    public int getQuantum() {
        return quantum;
    }
    
    public Algorithm getAlgorithm() {
        return algorithm;
    }
    
    
    public Queue<Process> getQueue() {
        return queue;
    }
    
    public PriorityQueue<Process> getPriorityQueue() {
        return priorityQueue;
    }
}
