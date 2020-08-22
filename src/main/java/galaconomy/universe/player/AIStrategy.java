package galaconomy.universe.player;

public enum AIStrategy {
    
    // AI doesn't care about this particular segment at all
    NONE,
    
    // pure random decisions
    RANDOM,
    
    
    // AI seeks for cheapest option
    CHEAPEST,
    
    // AI seeks for closest option
    CLOSEST,
    
    // AI seeks for most profitable option
    MOST_PROFITABLE,
    
    // AI keeps focusing on one specific base
    FOCUS_ON_BASE,
    
    // AI keeps focusing on one specific system
    FOCUS_ON_SYSTEM,
    
    // AI keeps focusing on one specific goods
    FOCUS_ON_GOODS,
    
    // AI performs task if it has enough credits
    IF_AFFORDABLE,
    
    // AI performs task if in need
    IF_NEEDED,
    
    // AI purchases if it sees good price
    BUY_IF_CHEAP,
    
    // AI sells if it sees good price
    SELL_IF_PROFITABLE,
    
    // AI keeps doing related task
    ALWAYS,
    
    // AI never does related task
    NEVER
}
