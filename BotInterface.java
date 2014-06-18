import java.rmi.*;
import java.awt.*;
public interface BotInterface extends Remote{

	public Point getLocation(int i,int j) throws Exception;
	public int getno(int i, int j) throws Exception;
	public boolean gamedone() throws Exception;
}
