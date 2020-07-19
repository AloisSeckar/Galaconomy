package galaconomy.universe;

public interface IEngineSubscriber {
    
    public void engineTaskFinished(long stellarTime);
    
    public boolean isActive();
    
}
