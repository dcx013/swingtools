import form.AbstractForm;
import form.Form;
import strategy.Strategy;
import strategy.StrategyFactory;

/**
 * 指挥者
 *
 * @author mhh
 * @since 2018/4/2
 */
public class Director {
    private AbstractForm form;

    Director(AbstractForm form) {
        this.form = form;
    }

    /**
     * 指挥器定义窗体构建部件  主窗口 编辑窗口  日志窗口
     */
        public void buildForm(String strategyType) {
        form.createMainForm("测试窗体");
        form.createEditForm(StrategyFactory.getStrategy(strategyType));
        form.createMessageArea();
        form.setFormVisible();//必须在最后调用
    }
}
