import javax.swing.*;
import java.awt.GridLayout;
import java.awt.event.*;
import java.awt.*;
import java.rmi.server.*;
import java.rmi.registry.*;

class Mine
{    
    int n=5;
	int x[][]=new int[n][n];  
    boolean gamedone=false;
    Mine()//Initializes Mine matrix with mines
    {   
        int count=0;//To count number of mines initialized
        while(count<n)
        {
            double r=Math.floor((Math.random()*n));
            double c=Math.floor((Math.random()*n));
            int i=(int)r;
            int j=(int)c;
            if(x[i][j]==-1)
            {
                continue;
            }       
            else
            {
                x[i][j]=-1;
                count++;
            }
        }   
            for(int i=0;i<n;i++)
            {
                for(int j=0;j<n;j++)
                {
                    int d=0;//counts for the value that the tile should hold;
                    if(x[i][j]==-1)
                    continue;
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
                    x[i][j]=d;
                }
            }                
    }
     public static void main(String[] args) 
        {
              new  Minesweeper();
              try {
                  BotServer obj = new BotServer();
                  BotInterface stub = (BotInterface) UnicastRemoteObject.exportObject(obj, 0);

                  // Bind the remote object's stub in the registry
                  Registry registry = LocateRegistry.getRegistry();
                  registry.bind("Bot", stub);

                  System.out.println("Server ready");
              } catch (Exception e) {
                  System.out.println("Server exception: " + e.toString());
                  e.printStackTrace();
              }
        }
}
class Minesweeper extends Mine
{
    JFrame f;
    JButton b[][];
    Minesweeper()
    { 
        super();
        f= new JFrame("Minesweeper game") ;
        f.setLayout(new GridLayout(n,n));
        f.setSize(250,250);
        f.setResizable(false);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        b= new JButton[n][n];
        update();
        f.setVisible(true);                
    }     
    void update()
    {
        for(int i=0;i<n;i++)
        {
            for(int j=0;j<n;j++)
            {                            
                b[i][j]=new JButton();
                f.add(b[i][j]);      
                final int num=x[i][j];
                final int i_f=i,j_f=j;
                b[i][j].setBackground(Color.lightGray);
                b[i][j].addMouseListener(new MouseAdapter(){
                	  public void mouseClicked(MouseEvent e){
                		  JButton btn = (JButton)e.getSource();
                		  check_win();
                		  if(e.getButton() == MouseEvent.BUTTON1){
                		  if (num!=-1 && num!=0) {
                	    	 btn.setText(num+"");
 							 btn.setBackground(Color.white);
                	    	 btn.setEnabled(false);
 							return;
                		  }
                		  if(num==0){
                  	    	remove_zeroes(i_f,j_f);
                  	    	 }
                  	    if(num==-1){
                  	    	gameover();
                  	    	gamedone=true;
                  	    	}
                	
                	  return;
                	    }
                		    if (e.getButton() == MouseEvent.BUTTON3) {
                    	    	if(btn.getBackground()==Color.lightGray)
                		    	btn.setBackground(Color.blue);
                    	    	else
                    	    	btn.setBackground(Color.lightGray);	
                   	    }
                		
                	    check_win();
                	  }
                });
            }
                
        }
    }
    void gameover()
    {
        for(int i=0;i<n;i++)
        {
            for(int j=0;j<n;j++)
            {                            
                if(x[i][j]==-1){
                	b[i][j].setText("X");
                	b[i][j].setBackground(Color.RED);
                }
            }
                
        }
        f.setTitle("Game Over!");
        //f.setEnabled(false);
    }
    void remove_zeroes(int i,int j){
    	
    	if(i>n || j>n){
    		return;
    	}
    	try{
    	if(x[i][j]==0 && b[i][j].isEnabled()){
    	b[i][j].setBackground(Color.white);
    	b[i][j].setEnabled(false);
    	remove_zeroes(i+1, j);
    	remove_zeroes(i-1, j);
    	remove_zeroes(i, j+1);
    	remove_zeroes(i, j-1);
    	remove_zeroes(i+1, j-1);
    	remove_zeroes(i+1, j+1);
    	remove_zeroes(i-1, j-1);
    	remove_zeroes(i-1, j+1);
    	}
    	if(x[i][j]!=0 && x[i][j]!=-1 && b[i][j].isEnabled()){
    		b[i][j].setText(x[i][j]+"");
    		b[i][j].setBackground(Color.white);
    		b[i][j].setEnabled(false);
    	}
    	}
    	catch(ArrayIndexOutOfBoundsException e){
    		return;
    	}

    }
    void check_win(){
    	int count=0;
    	for(int i=0;i<n;i++){
    		for(int j=0;j<n;j++){
    			if(b[i][j].isEnabled())
    				count++;
    		}
    	}
    	if(count==n){
    		f.setTitle("YOU WON THE GAME!!");
    		f.setEnabled(false);
    		gamedone=true;
    	}
    	}
    	
}

//bot interface functions...

class BotServer extends Minesweeper implements BotInterface{
	public Point getLocation(int i, int j) throws Exception
	{
        Point p = b[i][j].getLocationOnScreen();
        return p;
	}
	public int getno(int i, int j) throws Exception
	{
	if(b[i][j].isEnabled())
	return -2;
	if(b[i][j].isEnabled() && b[i][j].getBackground()==Color.blue)
		return -1;
	if(!b[i][j].isEnabled() && b[i][j].getText()=="")
		return 0;
	else
		return Integer.parseInt(b[i][j].getText());	
	}
	public boolean gamedone() throws Exception{
		return gamedone;
	}
}

