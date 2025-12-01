package my_practice;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class MovieBookingSystem extends JFrame {
    
    private static final long serialVersionUID = 1L; 

    private CardLayout cardLayout;
    private JPanel mainPanel;
    
    private String selectedMovieTitle = "";
    private ArrayList<String> selectedSeats = new ArrayList<>();
    private boolean[][] bookedSeats = new boolean[5][5];

    private JLabel resultLabel;

    public MovieBookingSystem() {
        setTitle("Java 영화 예매 시스템");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        mainPanel.add(new MovieSelectionPanel(this), "MOVIE");
        mainPanel.add(createResultPanel(), "RESULT");
        
        add(mainPanel);
    }
    
    public void showSeatSelectionPanel(String movieTitle) {
        this.selectedMovieTitle = movieTitle;
        if (mainPanel.getComponentCount() > 2) {
            mainPanel.remove(2);
        }
        mainPanel.add(new SeatSelectionPanel(this), "SEAT");
        cardLayout.show(mainPanel, "SEAT");
    }
    
    public void showMovieSelectionPanel() {
        cardLayout.show(mainPanel, "MOVIE");
    }

    public void showResultPanel() {
        updateResultLabel();
        cardLayout.show(mainPanel, "RESULT");
    }
    
    public JToggleButton[][] getSeatButtons() { return ((SeatSelectionPanel) mainPanel.getComponent(2)).getSeatButtons(); }
    public boolean[][] getBookedSeats() { return bookedSeats; }
    public ArrayList<String> getSelectedSeats() { return selectedSeats; }
    
    private JPanel createResultPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        resultLabel = new JLabel("", SwingConstants.CENTER);
        resultLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 16));
        
        JButton homeButton = new JButton("처음으로");
        homeButton.addActionListener(e -> showMovieSelectionPanel());

        panel.add(resultLabel, BorderLayout.CENTER);
        panel.add(homeButton, BorderLayout.SOUTH);
        
        return panel;
    }

    private void updateResultLabel() {
        StringBuilder sb = new StringBuilder();
        sb.append("<html><div style='text-align:center;'>");
        sb.append("<h2>예매가 완료되었습니다!</h2>");
        sb.append("<hr>");
        sb.append("<b>영화:</b> ").append(selectedMovieTitle).append("<br><br>");
        sb.append("<b>선택 좌석:</b> ").append(selectedSeats.toString()).append("<br><br>");
        sb.append("<b>총 인원:</b> ").append(selectedSeats.size()).append("명<br>");
        sb.append("<b>총 금액:</b> ").append(selectedSeats.size() * 12000).append("원");
        sb.append("</div></html>");
        resultLabel.setText(sb.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MovieBookingSystem().setVisible(true);
        });
    }
}