package BusinessLayer;

public interface Observable { 
public void addObserver(Observer o); 
public void removeObserver(); 
public void notifyObserver(MenuItem item); 
}
