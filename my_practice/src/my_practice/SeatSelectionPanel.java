package my_practice;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;

public class SeatSelectionPanel extends JPanel {
    
    private static final long serialVersionUID = 1L;
    private MovieBookingSystem parentFrame;
    private JToggleButton[][] seatButtons = new JToggleButton[5][5];

    public SeatSelectionPanel(MovieBookingSystem parentFrame) {
        this.parentFrame = parentFrame;
        setLayout(new BorderLayout());

        JLabel guideLabel = new JLabel("<html>좌석을 선택해주세요<br>(붉은색: 예매불가)</html>", SwingConstants.CENTER);
        guideLabel.setFont(new Font("맑은 고딕", Font.BOLD, 15));
        add(guideLabel, BorderLayout.NORTH);

        JPanel seatGridPanel = new JPanel(new GridLayout(5, 5, 10, 10));
        seatGridPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        boolean[][] bookedSeats = parentFrame.getBookedSeats();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                String seatName = (char)('A' + i) + "" + (j + 1);
                JToggleButton seatBtn = new JToggleButton(seatName);
                seatBtn.setFont(new Font("Arial", Font.BOLD, 14));
                
                if (bookedSeats[i][j]) {
                    seatBtn.setEnabled(false);
                    seatBtn.setBackground(Color.RED);
                }
                
                seatBtn.addActionListener(e -> {
                    if (seatBtn.isSelected()) {
                        seatBtn.setBackground(Color.GREEN);
                    } else {
                        seatBtn.setBackground(null);
                    }
                });
                
                seatButtons[i][j] = seatBtn;
                seatGridPanel.add(seatBtn);
            }
        }
        add(seatGridPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout());
        JButton backButton = new JButton("이전");
        JButton bookButton = new JButton("예매 완료");

        backButton.addActionListener(e -> parentFrame.showMovieSelectionPanel());
        
        bookButton.addActionListener(e -> processBooking());

        bottomPanel.add(backButton);
        bottomPanel.add(bookButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }
    
    private void processBooking() {
        ArrayList<String> currentSelectedSeats = parentFrame.getSelectedSeats();
        currentSelectedSeats.clear();
        boolean[][] bookedSeats = parentFrame.getBookedSeats();

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (seatButtons[i][j].isSelected() && seatButtons[i][j].isEnabled()) {
                    currentSelectedSeats.add(seatButtons[i][j].getText());
                    bookedSeats[i][j] = true;
                    seatButtons[i][j].setEnabled(false);
                    seatButtons[i][j].setBackground(Color.RED);
                    seatButtons[i][j].setSelected(false);
                }
            }
        }

        if (currentSelectedSeats.isEmpty()) {
            JOptionPane.showMessageDialog(this, "좌석을 최소 한 개 이상 선택해주세요.");
            return;
        }

        parentFrame.showResultPanel();
    }
    
    public JToggleButton[][] getSeatButtons() {
        return seatButtons;
    }
}