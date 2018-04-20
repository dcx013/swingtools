package strategy;

/**
 * 测试策略类  输入什么就输出什么
 * @author mhh
 * @since 2018/4/2
 */
public class TestStrategy implements Strategy {

    @Override
    public String[] working(String... args) {
        return args;
    }
}
