package form;

public interface AbstractForm {
    /**
     * 创建主窗体的布局 标题等
     * @param titleText 标题
     */
    void createMainForm(String titleText);

    /**
     * 创建不定数量的输入框和输出框
     * @param strategyType 使用什么类型的算法
     */
    void createEditForm(String strategyType);

    /**
     * 创建日志显示区域
     */
    void createMessageArea();
}
