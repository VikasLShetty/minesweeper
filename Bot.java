import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.awt.Robot;
import java.awt.*;
import java.awt.event.InputEvent;

public class Bot {
	public static int n=5;
	public static BotInterface stub;
	public static void main(String[] args) {
		 
        String host = (args.length < 1) ? null : args[0];
        try {
            Registry registry = LocateRegistry.getRegistry(host);
            stub = (BotInterface) registry.lookup("Bot");
            new BotThread("bot");
           } catch (Exception e) {
            System.out.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}

class BotThread extends Bot implements Runnable{	
	String name;
	Thread t;
	int x[][]= new int[n][n];
	float p[][]= new float[n][n];
	int flag[][] = new int[n][n];
	int eff,uncl;
	//constructor
	public BotThread(String name) {
		this.name= name;
		t = new Thread(this,name);
		t.start();
	}	
	//heart of thread
	public void run(){
		try{
		while(!stub.gamedone()){
		setupx();
		setupp();
		setflags();
		printx();
		printp();
		randomclick();
		Thread.sleep(3000);
		}
		}catch (Exception e) {
			System.out.println("Gamedone exception: " + e.toString());
            e.printStackTrace();
		}
		}
	
	//set known values
	void setupx(){
	try{
	for(int i=0;i<n;i++)
		for(int j=0;j<n;j++)
			x[i][j]=stub.getno(i,j);
		
	}catch (Exception e) {
		System.out.println("Getno exception: " + e.toString());
        e.printStackTrace();
	}
	}
	
	//set probability
	void setupp(){

		for(int i=0;i<n;i++)
			for(int j=0;j<n;j++){
				if(x[i][j]>0){
					eff=x[i][j]-checkadjflag(i,j);
					uncl=checkadjuncl(i,j);
					System.out.println(eff+" "+uncl);
					setupp1(i,j);
				}
			}
	}
	
	void printx(){
		for(int i=0;i<n;i++){
			for(int j=0;j<n;j++)
				System.out.print(x[i][j]+" ");
		System.out.println();
		}
	}
	void printp(){
		for(int i=0;i<n;i++){
			for(int j=0;j<n;j++)
				System.out.print(p[i][j]+" ");
		System.out.println();
		}
	}
	//setflags
	void setflags(){
		for(int i=0;i<n;i++)
			for(int j=0;j<n;j++){
				if(p[i][j]==1 && x[i][j]==-2 && flag[i][j]!=1){
					flag[i][j]=1;
					setflag(i,j);
				}
			}
			
	}
	void randomclick(){
		int min[] =new int[2];
		float minval=1;
		for(int i=0;i<n;i++)
			for(int j=0;j<n;j++){
				if(p[i][j]<=minval && x[i][j]==-2){
					min[0]=i;
					min[1]=j;
					minval=p[i][j];
				}
			}
		click(min[0],min[1]);
	}
	
	void click(int i, int j){
		 try{
		 Robot robot = new Robot();
         Point p = stub.getLocation(i,j);
         robot.mouseMove(p.x, p.y);
         robot.mousePress(InputEvent.BUTTON1_MASK);
         robot.mouseRelease(InputEvent.BUTTON1_MASK);
		}catch (Exception e) {
			System.out.println("Click exception: " + e.toString());
            e.printStackTrace();
		}
	}
	void setflag(int i, int j){
		try{
			 Robot robot = new Robot();
	         Point p = stub.getLocation(i,j);
	         robot.mouseMove(p.x, p.y);
	         robot.mousePress(InputEvent.BUTTON3_MASK);
	         robot.mouseRelease(InputEvent.BUTTON3_MASK);
			}catch (Exception e) {
				System.out.println("Flag exception: " + e.toString());
	            e.printStackTrace();
			}
	}
	int checkadjflag(int i, int j){
		int d=0;
		if(i==0)
        {
            if(j==0)
            {
                if(x[i][j+1]==-1)
                d++;
                if(x[i+1][j]==-1)
                d++;
                if(x[i+1][j+1]==-1)
                d++;
            }                        
            else if(j==n-1)
            {
                if(x[i][j-1]==-1)
                d++;
                if(x[i+1][j-1]==-1)
                d++;
                if(x[i+1][j]==-1)
                d++;
            }
            else
            {
                if(x[i][j+1]==-1)
                d++;
                if(x[i+1][j]==-1)
                d++;
                if(x[i+1][j+1]==-1)
                d++;
                if(x[i][j-1]==-1)
                d++;
                if(x[i+1][j-1]==-1)
                d++;
            }
        }
        else if(i==n-1)
        {
            if(j==0)
            {
                if(x[i][j+1]==-1)
                d++;
                if(x[i-1][j]==-1)
                d++;
                if(x[i-1][j+1]==-1)
                d++;
            }                        
            else if(j==n-1)
            {
                if(x[i][j-1]==-1)
                d++;
                if(x[i-1][j-1]==-1)
                d++;
                if(x[i-1][j]==-1)
                d++;
            }
            else
            {
                if(x[i][j+1]==-1)
                d++;
                if(x[i-1][j]==-1)
                d++;
                if(x[i-1][j+1]==-1)
                d++;
                if(x[i][j-1]==-1)
                d++;
                if(x[i-1][j-1]==-1)
                d++;
            }
        }
        else if(j==0)
        {
            if(x[i][j+1]==-1)
            d++;
            if(x[i+1][j]==-1)
            d++;
            if(x[i+1][j+1]==-1)
            d++;
            if(x[i-1][j]==-1)
            d++;
            if(x[i-1][j+1]==-1)
            d++;
        }
        else if(j==n-1)
        {
            if(x[i][j-1]==-1)
            d++;
            if(x[i-1][j-1]==-1)
            d++;
            if(x[i-1][j]==-1)
            d++;
            if(x[i+1][j-1]==-1)
            d++;
            if(x[i+1][j]==-1)
            d++;
        }
        else
        {
            if(x[i][j-1]==-1)
            d++;
            if(x[i-1][j-1]==-1)
            d++;
            if(x[i-1][j]==-1)
            d++;
            if(x[i+1][j-1]==-1)
            d++;
            if(x[i+1][j]==-1)
            d++;
            if(x[i][j+1]==-1)
            d++;
            if(x[i-1][j+1]==-1)
            d++;
            if(x[i+1][j+1]==-1)
            d++;
        }
	return d;
	}

//check available adjacent unclicked buttons
	int checkadjuncl(int i, int j){
		int d=0;
		if(i==0)
        {
            if(j==0)
            {
                if(x[i][j+1]==-2)
                d++;
                if(x[i+1][j]==-2)
                d++;
                if(x[i+1][j+1]==-2)
                d++;
            }                        
            else if(j==n-1)
            {
                if(x[i][j-1]==-2)
                d++;
                if(x[i+1][j-1]==-2)
                d++;
                if(x[i+1][j]==-2)
                d++;
            }
            else
            {
                if(x[i][j+1]==-2)
                d++;
                if(x[i+1][j]==-2)
                d++;
                if(x[i+1][j+1]==-2)
                d++;
                if(x[i][j-1]==-2)
                d++;
                if(x[i+1][j-1]==-2)
                d++;
            }
        }
        else if(i==n-1)
        {
            if(j==0)
            {
                if(x[i][j+1]==-2)
                d++;
                if(x[i-1][j]==-2)
                d++;
                if(x[i-1][j+1]==-2)
                d++;
            }                        
            else if(j==n-1)
            {
                if(x[i][j-1]==-2)
                d++;
                if(x[i-1][j-1]==-2)
                d++;
                if(x[i-1][j]==-2)
                d++;
            }
            else
            {
                if(x[i][j+1]==-2)
                d++;
                if(x[i-1][j]==-2)
                d++;
                if(x[i-1][j+1]==-2)
                d++;
                if(x[i][j-1]==-2)
                d++;
                if(x[i-1][j-1]==-2)
                d++;
            }
        }
        else if(j==0)
        {
            if(x[i][j+1]==-2)
            d++;
            if(x[i+1][j]==-2)
            d++;
            if(x[i+1][j+1]==-2)
            d++;
            if(x[i-1][j]==-2)
            d++;
            if(x[i-1][j+1]==-2)
            d++;
        }
        else if(j==n-1)
        {
            if(x[i][j-1]==-2)
            d++;
            if(x[i-1][j-1]==-2)
            d++;
            if(x[i-1][j]==-2)
            d++;
            if(x[i+1][j-1]==-2)
            d++;
            if(x[i+1][j]==-2)
            d++;
        }
        else
        {
            if(x[i][j-1]==-2)
            d++;
            if(x[i-1][j-1]==-2)
            d++;
            if(x[i-1][j]==-2)
            d++;
            if(x[i+1][j-1]==-2)
            d++;
            if(x[i+1][j]==-2)
            d++;
            if(x[i][j+1]==-2)
            d++;
            if(x[i-1][j+1]==-2)
            d++;
            if(x[i+1][j+1]==-2)
            d++;
        }
	return d;
	}
	
	void setupp1(int i, int j){
		if(i==0)
        {
            if(j==0)
            {
                p[i][j+1]=max(p[i][j+1],(float)eff/uncl);
                
                p[i+1][j]=max(p[i+1][j],(float)eff/uncl);
                
                p[i+1][j+1]=max(p[i+1][j+1],(float)eff/uncl);
                
            }                        
            else if(j==n-1)
            {
                p[i][j-1]=max( p[i][j-1],(float)eff/uncl);
                
                p[i+1][j-1]=max(p[i+1][j-1],(float)eff/uncl);
                
                p[i+1][j]=max(p[i+1][j],(float)eff/uncl);
                
            }
            else
            {
                p[i][j+1]=max( p[i][j+1],(float)eff/uncl);
                
                p[i+1][j]=max(p[i+1][j],(float)eff/uncl);
                
                p[i+1][j+1]=max(p[i+1][j+1],(float)eff/uncl);
                
                p[i][j-1]=max(p[i][j-1],(float)eff/uncl);
                
                p[i+1][j-1]=max(p[i+1][j-1],(float)eff/uncl);
                
            }
        }
        else if(i==n-1)
        {
            if(j==0)
            {
                p[i][j+1]=max(p[i][j+1],(float)eff/uncl);
                
                p[i-1][j]=max(p[i-1][j],(float)eff/uncl);
                
                p[i-1][j+1]=max(p[i-1][j+1],(float)eff/uncl);
                
            }                        
            else if(j==n-1)
            {
                p[i][j-1]=max(p[i][j-1],(float)eff/uncl);
                
                p[i-1][j-1]=max(p[i-1][j-1],(float)eff/uncl);
                
                p[i-1][j]=max(p[i-1][j],(float)eff/uncl);
                
            }
            else
            {
                p[i][j+1]=max(p[i][j+1],(float)eff/uncl);
                
                p[i-1][j]=max(p[i-1][j],(float)eff/uncl);
                
                p[i-1][j+1]=max(p[i-1][j+1],(float)eff/uncl);
                
                p[i][j-1]=max( p[i][j-1],(float)eff/uncl);
                
                p[i-1][j-1]=max(p[i-1][j-1],(float)eff/uncl);
                
            }
        }
        else if(j==0)
        {
            p[i][j+1]=max(p[i][j+1],(float)eff/uncl);
            
            p[i+1][j]=max(p[i+1][j],(float)eff/uncl);
            
            p[i+1][j+1]=max(p[i+1][j+1],(float)eff/uncl);
            
            p[i-1][j]=max(p[i-1][j],(float)eff/uncl);
            
            p[i-1][j+1]=max(p[i-1][j+1],(float)eff/uncl);
            
        }
        else if(j==n-1)
        {
            p[i][j-1]=max(p[i][j-1],(float)eff/uncl);
            
            p[i-1][j-1]=max(p[i-1][j-1],(float)eff/uncl);
            
            p[i-1][j]=max(p[i-1][j],(float)eff/uncl);
            
            p[i+1][j-1]=max(p[i+1][j-1],(float)eff/uncl);
            
            p[i+1][j]=max(p[i+1][j],(float)eff/uncl);
            
        }
        else
        {
            p[i][j-1]=max(p[i][j-1],(float)eff/uncl);
            
            p[i-1][j-1]=max(p[i-1][j-1],(float)eff/uncl);
            
            p[i-1][j]=max(p[i-1][j],(float)eff/uncl);
            
            p[i+1][j-1]=max(p[i+1][j-1],(float)eff/uncl);
            
            p[i+1][j]=max(p[i+1][j],(float)eff/uncl);
            
            p[i][j+1]=max(p[i][j+1],(float)eff/uncl);
            
            p[i-1][j+1]=max(p[i-1][j+1],(float)eff/uncl);
            
            p[i+1][j+1]=max(p[i+1][j+1],(float)eff/uncl);
            
        }
	}
	float max(float a, float b){
			if(a>b)
				return a;
			else
				return b;
		}
}
