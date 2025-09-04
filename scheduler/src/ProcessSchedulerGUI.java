import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ProcessSchedulerGUI extends JFrame {
    private ProcessPanel processPanel;
    private CPU cpu;
    private ProcessGenerator generator;
    private Scheduler scheduler;
    private List<Process> allProcesses;

    
    private JTextField quantumField;
    private JButton fcfsButton, sjfButton, rrButton, resetButton;

    public ProcessSchedulerGUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        generator = new ProcessGenerator();
        allProcesses = new ArrayList<>();

        setLayout(new BorderLayout());

        
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());

        
        quantumField = new JTextField("5", 5); 
        controlPanel.add(new JLabel("Quantum:"));
        controlPanel.add(quantumField);

        
        fcfsButton = new JButton("FCFS");
        sjfButton = new JButton("SJF");
        rrButton = new JButton("RR");
        resetButton = new JButton("Reset");

        controlPanel.add(fcfsButton);
        controlPanel.add(sjfButton);
        controlPanel.add(rrButton);
        controlPanel.add(resetButton);

        add(controlPanel, BorderLayout.NORTH);

        
        processPanel = new ProcessPanel();
        add(processPanel, BorderLayout.CENTER);

       
        fcfsButton.addActionListener(e -> startSimulation(Scheduler.Algorithm.FCFS));
        sjfButton.addActionListener(e -> startSimulation(Scheduler.Algorithm.SJF));
        rrButton.addActionListener(e -> startSimulation(Scheduler.Algorithm.RR));

        resetButton.addActionListener(e -> resetSimulation());

        setVisible(true);
    }

    
    public void startSimulation(Scheduler.Algorithm algorithm) {
        int quantum = 5;
        while (true) {
            
            try {
            quantum = Integer.parseInt(quantumField.getText());

            if (quantum > 0){
                break;
            }
            else{
                JOptionPane.showMessageDialog(this, "Invalid input. Type a number greater than 0!");
                quantumField.setText("");
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid input. Type a number!");
            quantumField.setText("");
            
            return;
        }

        }
        
        scheduler = new Scheduler(algorithm, quantum);
        cpu = new CPU(scheduler, generator, this, allProcesses);

        
        fcfsButton.setEnabled(false);
        sjfButton.setEnabled(false);
        rrButton.setEnabled(false);
        quantumField.setEnabled(false);

        
        new Thread(() -> cpu.execute()).start();
    }

 
    public void resetSimulation() {
        
        allProcesses.clear();
        processPanel.updateProcesses(allProcesses, null);

        
        fcfsButton.setEnabled(true);
        sjfButton.setEnabled(true);
        rrButton.setEnabled(true);
        quantumField.setText("");
        quantumField.setEnabled(true);

       
    }

    public void updateProcessDisplay(Process executingProcess) {
        processPanel.updateProcesses(allProcesses, executingProcess);
    }

    
    class ProcessPanel extends JPanel {
        private java.util.List<Process> processes = new ArrayList<>();
        private Process executingProcess;

        public void updateProcesses(List<Process> processes, Process executingProcess) {
            this.processes = processes;
            this.executingProcess = executingProcess;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            setBackground(Color.WHITE);

            if (processes.isEmpty()) return;

            int cubeSize = 80;
            int spacing = 20;
            int totalWidth = processes.size() * cubeSize + (processes.size() - 1) * spacing;
            int startX = (getWidth() - totalWidth) / 2;
            int y = (getHeight() - cubeSize) / 2;

            processes.sort((a, b) -> Integer.compare(a.getId(), b.getId()));

            for (int i = 0; i < processes.size(); i++) {
                Process p = processes.get(i);
                int x = startX + i * (cubeSize + spacing);

                if (p == executingProcess) {
                    g.setColor(Color.BLUE);
                } else if (p.finished()) {
                    g.setColor(Color.GREEN);
                } else {
                    g.setColor(Color.RED);
                }

                g.fillRect(x, y, cubeSize, cubeSize);
                g.setColor(Color.BLACK);
                g.drawRect(x, y, cubeSize, cubeSize);

                g.setColor(Color.WHITE);
                g.setFont(new Font("Arial", Font.BOLD, 14));

                String idText = "ID: " + p.getId();
                String instText = "Inst: " + p.getInstructions();

                FontMetrics fm = g.getFontMetrics();
                int idWidth = fm.stringWidth(idText);
                int instWidth = fm.stringWidth(instText);

                g.drawString(idText, x + (cubeSize - idWidth) / 2, y + cubeSize / 2 - 10);
                g.drawString(instText, x + (cubeSize - instWidth) / 2, y + cubeSize / 2 + 10);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ProcessSchedulerGUI::new);
    }
}
