package galaconomy.universe.player;

public class AIStrategySet {
    
    public static final AIStrategySet EMPTY_SET = new AIStrategySet(false);
    
    private final boolean focus;
    
    private final AIStrategy locationStrategy;
    private final AIStrategy buyStrategy;
    private final AIStrategy keepStrategy;
    private final AIStrategy sellStrategy;

    public AIStrategySet(boolean focus) {
        this.focus = focus;
        if (focus) {
            this.locationStrategy = AIStrategy.RANDOM;
            this.buyStrategy = AIStrategy.RANDOM;
            this.keepStrategy = AIStrategy.RANDOM;
            this.sellStrategy = AIStrategy.RANDOM;
        } else {
            this.locationStrategy = AIStrategy.NONE;
            this.buyStrategy = AIStrategy.NONE;
            this.keepStrategy = AIStrategy.NONE;
            this.sellStrategy = AIStrategy.NONE;
        }
    }
    
    public AIStrategySet(boolean focus, AIStrategy locationStrategy, AIStrategy buyStrategy, AIStrategy keepStrategy, AIStrategy sellStrategy) {
        this.focus = focus;
        this.locationStrategy = locationStrategy;
        this.buyStrategy = buyStrategy;
        this.keepStrategy = keepStrategy;
        this.sellStrategy = sellStrategy;
    }

    public boolean isFocus() {
        return focus;
    }

    public AIStrategy getLocationStrategy() {
        return locationStrategy;
    }

    public AIStrategy getBuyStrategy() {
        return buyStrategy;
    }

    public AIStrategy getKeepStrategy() {
        return keepStrategy;
    }

    public AIStrategy getSellStrategy() {
        return sellStrategy;
    }

}
