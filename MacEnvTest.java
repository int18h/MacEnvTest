import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MacEnvTest {

	public static void main(String[] args) {
		JFrame frame = new JFrame("Mac Env Test application");
		frame.setSize(300, 150);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();
		frame.add(panel);
		placeComponents(panel);

		frame.setVisible(true);
	}

	private static void placeComponents(JPanel panel) {

		panel.setLayout(null);

		JLabel envLabel = new JLabel("Enter env variable: ");
		envLabel.setBounds(10, 10, 80, 25);
		panel.add(envLabel);

		JTextField envText = new JTextField(20);
		envText.setBounds(100, 10, 160, 25);
		panel.add(envText);

		JButton envButton = new JButton("Click Me");
		envButton.setBounds(180, 80, 80, 25);
		panel.add(envButton);
        
        envButton.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            String result = "";
            try {
              result = System.getenv(envText.getText());
              if (result.isEmpty()) {
                result = "Sorry, empty value.";
              }
            } catch (Exception ex) {
              result = "Oops, something went wrong";
            }
            finally {
              JOptionPane.showMessageDialog(null, result);
            }
          }
        });
	}

}
