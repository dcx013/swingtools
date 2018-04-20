package form;

import strategy.Strategy;

public interface AbstractForm {
    /**
     * 创建主窗体的布局 标题等
     * @param titleText 标题
     */
    void createMainForm(String titleText);

    /**
     * 创建不定数量的输入框和输出框
     * @param strategy 算法
     */
    void createEditForm(Strategy strategy);

    /**
     * 创建日志显示区域
     */
    void createMessageArea();

    /**
     * 需要单独拿出来放在最后调用 否则会引起界面程序的bug
     */
    void setFormVisible();
}
