class CPU {
    private Scheduler scheduler;
    private ProcessGenerator generator;
    private ProcessSchedulerGUI gui;
    private java.util.List<Process> allProcesses;
    
    public CPU(Scheduler scheduler, ProcessGenerator generator, ProcessSchedulerGUI gui, java.util.List<Process> allProcesses) {
        this.scheduler = scheduler;
        this.generator = generator;
        this.gui = gui;
        this.allProcesses = allProcesses;
    }

   
    private void executeInstructions(Process process) {
        if (process.getInstructions() > 0) {
            process.setInstructions(process.getInstructions() - 1);
        }
    }
    
    public void execute() {
        for (int i = 0; i < 5; i++) {
            Process newProcess = generator.generateProcess();
            scheduler.addProcess(newProcess);
            allProcesses.add(newProcess);
        }
        
        updateGUI(null);
        
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        while (scheduler.existProcesses()) {
            Process process = scheduler.nextProcess();
            
            if (process == null) continue;
            
            updateGUI(process);
            
            System.out.println("Executing process: " + process.getId());
            
            if (scheduler.getAlgorithm() == Scheduler.Algorithm.RR) {
                for (int i = 0; i < scheduler.getQuantum(); i++) {
                    if (process.finished()) break;
                    
                    executeInstructions(process);
                    
                    updateGUI(process);
                    try {
                        Thread.sleep(100); 
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                
                if (!process.finished()) {
                    scheduler.addProcess(process);
                    System.out.println("Process " + process.getId() + " interrupted. Instructions missing: " + 
                                      process.getInstructions());
                } else {
                    System.out.println("Process " + process.getId() + " finished");
                }
            } else {
                while (!process.finished()) {
                    
                    executeInstructions(process);
                    
                    updateGUI(process);
                    try {
                        Thread.sleep(100); 
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("Process " + process.getId() + " finished");
            }
            
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
            updateGUI(null);
        }
    }
    
    private void updateGUI(Process executingProcess) {
        if (gui != null) {
            gui.updateProcessDisplay(executingProcess);
        }
    }
}