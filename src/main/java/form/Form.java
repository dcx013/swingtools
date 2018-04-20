package form;

import strategy.Strategy;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

/**
 * @author mhh
 * @since 2018/4/2
 */
public class Form implements AbstractForm {

    private JFrame main = new JFrame("JavaSwingTools");
    private static final int MAIN_WIDTH = 500;
    private static final int MAIN_HEIGHT = 500;

    private JPanel inputArea = new JPanel();
    private JPanel outputArea = new JPanel();
    private JPanel buttonArea = new JPanel();
    private JPanel messageArea = new JPanel();
    private JTextArea message = new JTextArea();
    private JButton executeButton = new JButton("执行");
    private JTextField[] inputFields;
    private JTextField[] outputFields;

    private int inputNum;

    private int outputNum;

    /**
     * 构造方法中直接实例化数组 方便后面可以使用lamda表达式
     *
     * @param inputNum  输入框数量
     * @param outputNum 输出框数量
     */
    public Form(int inputNum, int outputNum) {
        this.inputNum = inputNum;
        this.outputNum = outputNum;
        inputFields = new JTextField[inputNum];
        for (int i = 0; i < inputNum; i++) {
            inputFields[i] = new JTextField();
            inputFields[i].setColumns(10);
            inputFields[i].setHorizontalAlignment(JTextField.CENTER);
        }
        outputFields = new JTextField[outputNum];
        for (int i = 0; i < outputNum; i++) {
            outputFields[i] = new JTextField();
            outputFields[i].setColumns(10);
            outputFields[i].setHorizontalAlignment(JTextField.CENTER);
        }
    }

    @Override
    public void createMainForm(String titleText) {
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

        JTextField title = new JTextField();
        title.setHorizontalAlignment(JTextField.CENTER);
        title.setText(titleText);
        title.setPreferredSize(new Dimension(MAIN_WIDTH, 50));
        title.setEditable(false);
        main.add(title, BorderLayout.NORTH);
        main.add(inputArea, BorderLayout.WEST);
        main.add(buttonArea, BorderLayout.CENTER);
        main.add(outputArea, BorderLayout.EAST);
        main.add(messageArea, BorderLayout.SOUTH);
        main.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);//在关闭窗口的时候关闭所有线程
    }

    @Override
    public void createEditForm(Strategy strategy) {
        inputArea.setPreferredSize(new Dimension(200, MAIN_HEIGHT));
        createInputArea();

        buttonArea.setPreferredSize(new Dimension(100, 50));
        executeButton.addActionListener(e -> {
            System.out.println("点击按钮");
            StringBuilder messageHistory = new StringBuilder(message.getText());
            if (strategy == null) {
                messageHistory.append("算法不存在\n");
                message.setText(messageHistory.toString());
                return;
            }

            String[] args = new String[inputNum];
            messageHistory.append(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
            messageHistory.append("  参数： ");
            for (int i = 0; i < inputNum; i++) {
                args[i] = inputFields[i].getText();
                messageHistory.append(args[i]).append(" ");
            }
            messageHistory.append("  结果为: ");
            String[] results = strategy.working(args);
            for (int i = 0; i < Math.min(outputNum, results.length); i++) {
                outputFields[i].setText(results[i]);
                messageHistory.append(results[i]).append(" ");
            }
            message.setText(messageHistory.toString() + "\n");
        });
        for (int i = 0; i < 4; i++) {
            buttonArea.add(new JLabel());
        }
        buttonArea.add(executeButton);

        outputArea.setPreferredSize(new Dimension(200, MAIN_HEIGHT));
        createOutputArea();
    }

    /**
     * 输入框 在窗口的左侧
     */
    private void createInputArea() {
        createArea(inputArea, inputFields, true, "输入");
    }

    /**
     * 输出框 在窗口的右侧
     */
    private void createOutputArea() {
        createArea(outputArea, outputFields, false, "输出");
    }

    /**
     * 日志区
     */
    public void createMessageArea() {
        JScrollPane jScrollPane = new JScrollPane(message, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        message.setEditable(false);
        messageArea.add(jScrollPane);
        jScrollPane.setPreferredSize(new Dimension(MAIN_WIDTH, 60));
     //   messageArea.setPreferredSize(new Dimension(MAIN_WIDTH, 60));

    }

    /**
     * 窗口默认是不可见的
     */
    @Override
    public void setFormVisible() {
        main.setVisible(true);
    }

    /**
     * 创建区域
     *
     * @param jPanel      父布局
     * @param jTextFields 要在父布局中插入的文字框
     * @param editable    是否可编辑（预期中输入框为可编辑，输出框为不可编辑）
     * @param topText     顶部名称
     */
    private void createArea(JPanel jPanel, JTextField[] jTextFields, boolean editable, String topText) {
        JLabel topLabel = new JLabel(topText);
        topLabel.setHorizontalTextPosition(SwingConstants.CENTER);
        topLabel.setVerticalTextPosition(SwingConstants.TOP);
        jPanel.add(topLabel);//空第一格
        Arrays.stream(jTextFields).forEach(jTextField -> {
            jTextField.setEditable(editable);
            jPanel.add(jTextField);
            jPanel.add(new JLabel());//每个框中间留一个空隙
        });
    }
}
