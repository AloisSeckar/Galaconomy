package galaconomy.universe.player;

import static galaconomy.universe.player.AIStrategy.*;

public class AIFactory {
    
    private AIFactory() {
    }
    
    public static AIPersonality AI_DUDE = new AIPersonality();
    
    public static AIPersonality AI_GLS = new AIPersonality(
            new AIStrategySet(true, NONE, NEVER, NEVER, ALWAYS),
            new AIStrategySet(true, NONE, NEVER, NEVER, ALWAYS),
            new AIStrategySet(true, NONE, NEVER, NEVER, ALWAYS),
            new AIStrategySet(true, NONE, NEVER, NEVER, ALWAYS)
    );
    
    public static AIPersonality AI_LANDLORD = new AIPersonality(
            new AIStrategySet(true, RANDOM, IF_AFFORDABLE, NEVER, SELL_IF_PROFITABLE),
            new AIStrategySet(false), 
            new AIStrategySet(false), 
            new AIStrategySet(false)
    );
    
    public static AIPersonality AI_LOCAL_LANDLORD = new AIPersonality(
            new AIStrategySet(true, FOCUS_ON_BASE, IF_AFFORDABLE, NEVER, SELL_IF_PROFITABLE),
            new AIStrategySet(false), 
            new AIStrategySet(false), 
            new AIStrategySet(false)
    );
    
    public static AIPersonality AI_BUILDER = new AIPersonality(
            new AIStrategySet(true, RANDOM, IF_AFFORDABLE, ALWAYS, NEVER),
            new AIStrategySet(true, RANDOM, IF_AFFORDABLE, ALWAYS, NEVER),
            new AIStrategySet(false), 
            new AIStrategySet(false)
    );
    
    public static AIPersonality AI_LOCAL_BUILDER = new AIPersonality(
            new AIStrategySet(true, FOCUS_ON_BASE, IF_AFFORDABLE, ALWAYS, NEVER),
            new AIStrategySet(true, FOCUS_ON_BASE, IF_AFFORDABLE, ALWAYS, NEVER),
            new AIStrategySet(false), 
            new AIStrategySet(false)
    );
    
    public static AIPersonality AI_STAR_TRADER = new AIPersonality(
            new AIStrategySet(false),
            new AIStrategySet(false),
            new AIStrategySet(true, RANDOM, IF_AFFORDABLE, ALWAYS, NEVER),
            new AIStrategySet(true, RANDOM, CHEAPEST, NEVER, SELL_IF_PROFITABLE)
    );
    
    public static AIPersonality AI_STAR_SUPPLIER = new AIPersonality(
            new AIStrategySet(false),
            new AIStrategySet(false),
            new AIStrategySet(true, FOCUS_ON_BASE, IF_AFFORDABLE, ALWAYS, NEVER),
            new AIStrategySet(true, RANDOM, CLOSEST, NEVER, ALWAYS)
    );
    
}
