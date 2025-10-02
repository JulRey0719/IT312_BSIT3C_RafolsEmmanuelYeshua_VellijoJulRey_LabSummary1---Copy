import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.*;
import java.util.List;
public class PBoxGUI {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainMenu().setVisible(true));
    }
}

class MainMenu extends JFrame {
    public MainMenu() {
        setTitle("PERMUTATIONS PBOX");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 1, 10, 10));

        JButton spBoxBtn = new JButton("SPBOX");
        spBoxBtn.setBackground(Color.CYAN);
        JButton cpBoxBtn = new JButton("CPBOX");
        cpBoxBtn.setBackground(Color.PINK);
        JButton epBoxBtn = new JButton("EPBOX");
        epBoxBtn.setBackground(Color.YELLOW);

        spBoxBtn.addActionListener(e -> new SPBoxFrame().setVisible(true));
        cpBoxBtn.addActionListener(e -> new CPBoxFrame().setVisible(true));
        epBoxBtn.addActionListener(e -> new EPBoxFrame().setVisible(true));

        add(spBoxBtn);
        add(cpBoxBtn);
        add(epBoxBtn);
    }
}

abstract class PBoxFrame extends JFrame {
    protected JTextField inputField;
    protected JTextArea resultArea;
    protected JTextArea explanationArea;

    public PBoxFrame(String title, Color bgColor) {
        setTitle(title);
        setSize(500, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.setBackground(bgColor);
        topPanel.add(new JLabel("Enter Plain Text:"));
        inputField = new JTextField(15);
        topPanel.add(inputField);

        JButton runBtn = new JButton("Generate");
        runBtn.addActionListener(e -> process());
        topPanel.add(runBtn);

        add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(2, 1, 10, 10));

        resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Monospaced", Font.BOLD, 18));
        resultArea.setBorder(BorderFactory.createTitledBorder("Cipher Text"));
        resultArea.setBackground(bgColor);
        centerPanel.add(new JScrollPane(resultArea));

        explanationArea = new JTextArea();
        explanationArea.setEditable(false);
        explanationArea.setFont(new Font("SansSerif", Font.PLAIN, 14));
        explanationArea.setBackground(Color.BLACK);
        explanationArea.setForeground(Color.WHITE);

        TitledBorder whiteBorder = BorderFactory.createTitledBorder("Explanation");
        whiteBorder.setTitleColor(Color.WHITE);
        explanationArea.setBorder(whiteBorder);

        centerPanel.add(new JScrollPane(explanationArea));

        add(centerPanel, BorderLayout.CENTER);
    }

    protected boolean isValidPlainText(String text) {
        return text.matches("[a-zA-Z]+");
    }

    protected abstract void process();
}

class SPBoxFrame extends PBoxFrame {
    public SPBoxFrame() {
        super("SPBOX", Color.CYAN);
    }

    @Override
    protected void process() {
        String data = inputField.getText().trim();

        if (!isValidPlainText(data)) {
            JOptionPane.showMessageDialog(this,
                "Invalid input! Please enter only plain text (letters only).");
            return;
        }

        List<Character> chars = new ArrayList<>();
        for (char c : data.toCharArray()) chars.add(c);
        Collections.shuffle(chars);

        StringBuilder result = new StringBuilder();
        for (char c : chars) result.append(c);

        resultArea.setText(result.toString());
        explanationArea.setText("SPBOX Rule:\nThe plain text letters were shuffled randomly to create a cipher text.\nUppercase and lowercase are preserved.");
    }
}

class CPBoxFrame extends PBoxFrame {
    public CPBoxFrame() {
        super("CPBOX", Color.PINK);
    }

    @Override
    protected void process() {
        String data = inputField.getText().trim();

        if (!isValidPlainText(data)) {
            JOptionPane.showMessageDialog(this,
                "Invalid input! Please enter only plain text (letters only).");
            return;
        }

        List<Character> chars = new ArrayList<>();
        for (char c : data.toCharArray()) chars.add(c);

        Collections.shuffle(chars);

        int half = chars.size() / 2;
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < half; i++) {
            result.append(chars.get(i));
        }

        resultArea.setText(result.toString());
        explanationArea.setText("CPBOX Rule:\nA random subset of half of the letters was selected from the plain text to form the cipher text.\nEvery time you press Generate, you may get a different result.");
    }
}

class EPBoxFrame extends PBoxFrame {
    public EPBoxFrame() {
        super("EPBOX", Color.YELLOW);
    }

    @Override
    protected void process() {
        String data = inputField.getText().trim();

        if (!isValidPlainText(data)) {
            JOptionPane.showMessageDialog(this,
                "Invalid input! Please enter only plain text (letters only).");
            return;
        }

        StringBuilder result = new StringBuilder();
        for (char c : data.toCharArray()) {
            result.append(c).append(c);
        }

        resultArea.setText(result.toString());
        explanationArea.setText("EPBOX Rule:\nEach plain text letter was duplicated to form the cipher text.\nInput length: "
            + data.length() + ", Cipher length: " + result.length());
    }
}
