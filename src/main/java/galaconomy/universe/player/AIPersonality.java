package galaconomy.universe.player;

import java.util.Random;

public class AIPersonality {
    
    private final AIStrategySet landsStrategy;
    private final AIStrategySet buildingsStrategy;
    private final AIStrategySet shipsStrategy;
    private final AIStrategySet goodsStrategy;

    public AIPersonality() {
        Random rand = new Random();
        landsStrategy = new AIStrategySet(rand.nextBoolean());
        buildingsStrategy = new AIStrategySet(rand.nextBoolean());
        shipsStrategy = new AIStrategySet(rand.nextBoolean());
        goodsStrategy = new AIStrategySet(rand.nextBoolean());
    }

    public AIPersonality(AIStrategySet landsStrategy, AIStrategySet buildingsStrategy, AIStrategySet shipsStrategy, AIStrategySet gooodsStrategy) {
        this.landsStrategy = landsStrategy;
        this.buildingsStrategy = buildingsStrategy;
        this.shipsStrategy = shipsStrategy;
        this.goodsStrategy = gooodsStrategy;
    }

    public AIStrategySet getLandsStrategy() {
        return landsStrategy;
    }

    public AIStrategySet getBuildingsStrategy() {
        return buildingsStrategy;
    }

    public AIStrategySet getShipsStrategy() {
        return shipsStrategy;
    }

    public AIStrategySet getGoodsStrategy() {
        return goodsStrategy;
    }
    
}
