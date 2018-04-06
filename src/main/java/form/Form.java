package form;

import strategy.Strategy;
import strategy.StrategyFactory;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * @author mhh
 * @since 2018/4/2
 */
public class Form implements AbstractForm{

    private JFrame main = new JFrame("mainform");
    private static final int MAIN_WIDTH = 500;
    private static final int MAIN_HEIGHT = 500;

    private JPanel inputArea;
    private JPanel outputArea;
    private JPanel buttonArea;
    private JPanel messageArea;
    private JTextArea message = new JTextArea();
    private JButton executeButton = new JButton("执行");
    private JTextField[] inputFields;
    private JTextField[] outputFields;

    private int inputNum;

    private int outputNum;

    public Form(int inputNum, int outputNum) {
        this.inputNum = inputNum;
        this.outputNum = outputNum;
        inputFields = new JTextField[inputNum];
        outputFields = new JTextField[outputNum];
    }

    @Override
    public void createMainForm() {
        File iconFile = new File("src/main/resources/jpg/icon.jpg");
        if (iconFile.exists()) {
            try {
                main.setIconImage(ImageIO.read(iconFile));
            } catch (IOException e) {
                System.out.println("读取图片有问题");
                e.printStackTrace();
            }
        }
        //使窗口居中
        Toolkit toolkit = main.getToolkit();
        Dimension dimension = toolkit.getScreenSize();
        main.setLocation((int) (dimension.getWidth() - MAIN_WIDTH) / 2, (int) (dimension.getHeight() - MAIN_HEIGHT) / 2);
        main.setSize(MAIN_WIDTH, MAIN_HEIGHT);
        main.setLayout(new BorderLayout());
        main.setResizable(false);

        inputArea.setLayout(new GridLayout(inputNum * 2 + 1, 1));
        buttonArea.setLayout(new GridLayout(9, 1));
        outputArea.setLayout(new GridLayout(outputNum * 2 + 1, 1));

        main.add(inputArea, BorderLayout.WEST);
        main.add(buttonArea, BorderLayout.CENTER);
        main.add(outputArea, BorderLayout.EAST);
        main.add(messageArea, BorderLayout.SOUTH);
        main.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    @Override
    public void createEditForm() {
        inputArea.setPreferredSize(new Dimension(200, 500));
        createInputArea();

        buttonArea.setPreferredSize(new Dimension(100, 50));
        executeButton.addActionListener(e -> {
            System.out.println("点击按钮");
            Strategy strategy = StrategyFactory.getStrategy("1");
            StringBuilder messageHistory = new StringBuilder(message.getText());
            if (strategy == null) {
                messageHistory.append("算法不存在\n");
                message.setText(messageHistory.toString());
                return;
            }

            String[] args = new String[inputNum];
            messageHistory.append("参数： ");
            for (int i = 0; i < inputNum; i++) {
                args[i] = inputFields[i].getText();
                messageHistory.append(args[i]).append(" ");
            }
            messageHistory.append("  结果为: ");
            String[] results = strategy.working(args);
            for (int i = 0; i < outputNum; i++) {
                outputFields[i].setText(results[i]);
                messageHistory.append(results[i]).append(" ");
            }
            message.setText(messageHistory.toString() + "\n");
        });
        for (int i = 0; i < 4; i++) {
            buttonArea.add(new Label());
        }
        buttonArea.add(executeButton);

        outputArea.setPreferredSize(new Dimension(200, 500));
        createOutputArea();
    }

    private void createInputArea() {
        createArea(inputArea, inputFields, inputNum, true);
    }

    private void createOutputArea() {
        createArea(outputArea, outputFields, outputNum, false);
    }

    public void createMessageArea() {
        JScrollPane jScrollPane = new JScrollPane(message, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        message.setEditable(false);
        messageArea.add(jScrollPane);
        messageArea.setPreferredSize(new Dimension(500, 60));
    }

    public void setFormVisible() {
        main.setVisible(true);
    }

    
    private void createArea(JPanel jPanel, JTextField[] jTextFields, int num, boolean editable) {
        jPanel.add(new Label());
        for (int i = 0; i < num; i++) {
            jTextFields[i] = new JTextField(10);
            jTextFields[i].setEditable(editable);
            jTextFields[i].setHorizontalAlignment(JTextField.CENTER);
            jPanel.add(jTextFields[i]);
            jPanel.add(new Label());
        }
    }
}
