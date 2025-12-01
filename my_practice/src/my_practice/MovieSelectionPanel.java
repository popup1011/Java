package my_practice;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class MovieSelectionPanel extends JPanel {
    
    private static final long serialVersionUID = 1L;
    private MovieBookingSystem parentFrame;
    private JLabel posterLabel;

    public MovieSelectionPanel(MovieBookingSystem parentFrame) {
        this.parentFrame = parentFrame;
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("예매할 영화를 선택하세요", SwingConstants.CENTER);
        titleLabel.setFont(new Font("맑은 고딕", Font.BOLD, 20));

        String[] movies = {"아바타: 물의 길", "어벤져스: 엔드게임", "범죄도시 3", "인셉션", "기생충"};
        JComboBox<String> movieComboBox = new JComboBox<>(movies);
        movieComboBox.setFont(new Font("맑은 고딕", Font.PLAIN, 16));

        posterLabel = new JLabel(); 
        posterLabel.setHorizontalAlignment(SwingConstants.CENTER);
        posterLabel.setPreferredSize(new Dimension(200, 300));
        posterLabel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        movieComboBox.addActionListener(new AbstractAction() {
            private static final long serialVersionUID = 1L;
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedMovie = (String) movieComboBox.getSelectedItem();
                updateMoviePoster(selectedMovie);
            }
        });

        updateMoviePoster((String) movieComboBox.getSelectedItem());

        JButton nextButton = new JButton("좌석 선택하기");
        nextButton.setFont(new Font("맑은 고딕", Font.BOLD, 15));
        
        nextButton.addActionListener(e -> {
            String selectedMovie = (String) movieComboBox.getSelectedItem();
            parentFrame.showSeatSelectionPanel(selectedMovie);
        });

        JPanel centerPanel = new JPanel(new BorderLayout(0, 15));
        centerPanel.add(movieComboBox, BorderLayout.NORTH);
        centerPanel.add(posterLabel, BorderLayout.CENTER);

        add(titleLabel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(nextButton, BorderLayout.SOUTH);
    }

    private void updateMoviePoster(String movieTitle) {
        String imagePath = "";
        switch (movieTitle) {
            case "아바타: 물의 길":
                imagePath = "my_practice/images/avatar.jpg";
                break;
            case "어벤져스: 엔드게임":
                imagePath = "my_practice/images/avengers.jpg";
                break;
            case "범죄도시 3":
                imagePath = "my_practice/images/crimecity3.jpg";
                break;
            case "인셉션":
                imagePath = "my_practice/images/inception.jpg";
                break;
            case "기생충":
                imagePath = "my_practice/images/parasite.jpg";
                break;
            default:
                imagePath = "my_practice/images/default.jpg";
                break;
        }

        try {
            ImageIcon originalIcon = new ImageIcon(getClass().getClassLoader().getResource(imagePath));
            
            Image originalImage = originalIcon.getImage();
            Image scaledImage = originalImage.getScaledInstance(posterLabel.getPreferredSize().width, posterLabel.getPreferredSize().height, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(scaledImage);
            
            posterLabel.setIcon(scaledIcon);
        } catch (Exception e) {
            posterLabel.setText("이미지 로드 실패");
            posterLabel.setIcon(null);
            System.err.println("Error loading image for " + movieTitle + ": " + e.getMessage());
        }
    }
}