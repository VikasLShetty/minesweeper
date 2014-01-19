import javax.swing.*;
import javax.swing.event.*;
import java.awt.GridLayout;
import java.awt.event.*;
import java.awt.*;
import java.awt.event.ActionListener;

class Mine
{    
    int n=10;
	int x[][]=new int[n][n];  
   
    Mine()//Initializes Mine matrix with mines
    {   
        int count=0;//To count number of mines initialized
        while(count<=20)
        {
            double r=Math.floor((Math.random()*10));
            double c=Math.floor((Math.random()*10));
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
            for(int i=0;i<10;i++)
            {
                for(int j=0;j<10;j++)
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
                        else if(j==9)
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
                    else if(i==9)
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
                        else if(j==9)
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
                    else if(j==9)
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
        f.setLayout(new GridLayout(10,10));
        f.setSize(500,500);
        f.setResizable(false);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        b= new JButton[10][10];
        update();
        f.setVisible(true);                
    }     
    void update()
    {
        for(int i=0;i<10;i++)
        {
            for(int j=0;j<10;j++)
            {                            
                b[i][j]=new JButton();
                f.add(b[i][j]);      
                final int num=x[i][j];
                final int i_f=i,j_f=j;
              
                b[i][j].addMouseListener(new MouseAdapter(){
                	  public void mouseClicked(MouseEvent e){
                		  JButton btn = (JButton)e.getSource();
                		  check_win();
                		  if(num!=-1 && num!=0){
                		  if (e.getButton() == MouseEvent.BUTTON1) {
                	    	 btn.setText(num+"");
 							btn.setEnabled(false);
                	    }
                	    if (e.getButton() == MouseEvent.BUTTON3) {
                	    	btn.setIcon(new ImageIcon("D:\\files\\flag.ico"));
               	    }
                	  return;
                	    }
                	    if(num==0){
                	    	remove_zeroes(i_f,j_f);
                	    	 }
                	    else{
                	    	gameover();
                	    	
                	    }
                	  }
                });
            }
                
        }
    }
    void gameover()
    {
        for(int i=0;i<10;i++)
        {
            for(int j=0;j<10;j++)
            {                            
                if(x[i][j]==-1){
                	b[i][j].setText("X");
                	b[i][j].setBackground(Color.RED);
                }
            }
                
        }
        f.setTitle("Game Over!");
        f.setEnabled(false);
    }
    void remove_zeroes(int i,int j)
    {       
        if(i>n || j>n)
        {
            return;
        }
        try
        {
            if(x[i][j]>=1)
            {
                
                b[i][j].setText(x[i][j]+"");
                b[i][j].setEnabled(false);
                return;

            }
            
            if(x[i][j]==0 && b[i][j].isEnabled() )
            {
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
            
            
        }
        catch(ArrayIndexOutOfBoundsException e)
        {
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
    	if(count==21){
    		f.setTitle("CONGRATULATIONS!!! YOU WON THE GAME!!");
    		f.setEnabled(false);
    	}
    	}
    	
}

