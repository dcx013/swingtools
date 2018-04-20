import form.Form;

/**
 * 指挥者
 *
 * @author mhh
 * @since 2018/4/2
 */
public class Director {
    private Form form;

    Director(Form form) {
        this.form = form;
    }

    /**
     * 指挥器定义窗体构建部件  主窗口 编辑窗口  日志窗口
     */
    public void buildForm() {
        form.createMainForm("测试窗体");
        form.createEditForm("1");
        form.createMessageArea();
        form.setFormVisible();
    }
}
