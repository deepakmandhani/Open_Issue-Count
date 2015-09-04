
import org.kohsuke.github.GHIssue;
import org.kohsuke.github.GHIssueState;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RootPaneContainer;

interface Cursors {
	  Cursor WAIT_CURSOR = 
	    Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR);     
	  Cursor DEFAULT_CURSOR = 
	    Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);  
	}

public class RepositaryOpenIssue extends JFrame implements Cursors{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	JTable table;
	JTextField urlText;
	JLabel label;
	JLabel repo_label;
	JButton openIssueBtn;
		
	  /** Sets cursor for specified component to Wait cursor */
	  public static void startWaitCursor(JComponent component) {
	    RootPaneContainer root = 
	      (RootPaneContainer)component.getTopLevelAncestor();
	    root.getGlassPane().setCursor(Cursors.WAIT_CURSOR);
	    root.getGlassPane().setVisible(true);
	  }

	  /** Sets cursor for specified component to normal cursor */
	  public static void stopWaitCursor(JComponent component) {
	    RootPaneContainer root = 
	      (RootPaneContainer)component.getTopLevelAncestor();
	    root.getGlassPane().setCursor(Cursors.DEFAULT_CURSOR);
	    root.getGlassPane().setVisible(false);
	  }
	public RepositaryOpenIssue() {
				
		setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600, 200);
		setLocationRelativeTo(null);
		setTitle("GITHUB Open Issue Tracker");
		
		urlText=new JTextField();
		label=new JLabel();
		repo_label=new JLabel();
		openIssueBtn=new JButton();		
		label.setText("GITHUB Open Isssue Details for Repository");
		JPanel panel1=new JPanel(new BorderLayout());
		panel1.add(label,BorderLayout.WEST);
		panel1.add(repo_label,BorderLayout.CENTER);
		add(panel1,BorderLayout.NORTH);
		urlText.setText("Enter GITHUB Repository Path/URL ");
		openIssueBtn.setText("Track Repository Issue");
		add(panel1,BorderLayout.NORTH);
	    JPanel panel2 = new JPanel(new BorderLayout());
		panel2.add(this.urlText,BorderLayout.CENTER);
		panel2.add(this.openIssueBtn,BorderLayout.EAST);
		add(panel2,BorderLayout.SOUTH);
		setVisible(true);
		
		urlText.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                urlText.setText("");
            }
        });	
		
		openIssueBtn.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String git_repo=urlText.getText().toString();
				String repo_name;
				if(git_repo.contains("https://github.com/"))
				{
					System.out.println("ISSUE-");
					repo_name=git_repo.substring(git_repo.lastIndexOf(".com/")+5, git_repo.indexOf("/issues"));
					System.out.println("Repositary name- "+repo_name);
		            try {
		            	repo_label.setText("-\t     "+repo_name);
						drawTable(repo_name);
					} catch (Exception e) {
						e.printStackTrace();
						if (e.getMessage().equalsIgnoreCase("api.github.com")) {
			                System.out.println("An error has occurred reaching " + e.getMessage() + "! Please check your network connection.");
			            }
					}
				}
				else
				{
		            JOptionPane.showMessageDialog(table, "Hey please enter correct link to any public Github repository.");
					repo_label.setText("-     Invalid github URL or repository !!");
				}				
			}
		});
	}
	
	private void drawTable(String repository_name) throws Exception {

		startWaitCursor(openIssueBtn);
		int count1=0,count7=0,count=0,countMore=0;    
		
		GitHub github = GitHub.connectAnonymously();		
        GHRepository repository = github.getRepository(repository_name);
     
        for (GHIssue issue : repository.getIssues(GHIssueState.OPEN)) {         
            Date openDate=issue.getCreatedAt();
            Date today=new Date();
            long diff=(long) ((today.getTime() - openDate.getTime()) / (1000 * 60 * 60));
 
            if(diff<=24)
            count1++;
            else if(diff/24<7)
            	count7++;
            else
            	countMore++;        
            count++;
        }
        System.out.println("Open Issue- "+count);
        System.out.println("Open in 24 hour- "+count1);
        System.out.println("Open in 1-7 days- "+count7);
        System.out.println("Open in more than 7 days- "+countMore);
        System.out.println(count1+count7+countMore);
        if(table!=null)
        {
        	remove(table);
        	table=null;
        }
		String column[]={"Issue Filter Desc","Number of Issues"};
        String data[][]={{"Total open issues",String.valueOf(count)},{"Opened in the last 24 hours",String.valueOf(count1)},{"Opened 24 hours ago but less than 7 days ago",String.valueOf(count7)},{"Opened more than 7 days ago",String.valueOf(countMore)}};
        table=new JTable(data,column);		
        add(table,BorderLayout.CENTER);
        stopWaitCursor(openIssueBtn);
		setVisible(true);
	}
	
    public static void main(String[] args) {
    	
            RepositaryOpenIssue dispIssue=new RepositaryOpenIssue();            

        }
    }