package strategy;

/**
 * @author mhh
 * @since 2018/4/2
 */
public class StrategyFactory {
    public static Strategy getStrategy(String type) {
        switch (type) {
            case "1":
                return new Strategy1();
            default:
                System.out.println("找不到所需的算法");
                return null;
        }
    }
}


