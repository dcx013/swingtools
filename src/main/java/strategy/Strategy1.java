package strategy;

/**
 * @author mhh
 * @since 2018/4/2
 */
public class Strategy1 implements Strategy {
    @Override
    public String[] working(String... args) {
        return args;
    }
}
