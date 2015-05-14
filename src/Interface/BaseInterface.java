package Interface;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class BaseInterface extends JFrame {
	public BaseInterface() {
		this( "FEEVALE" );
	}
	
	public BaseInterface( String titulo ) {
		
		setBounds( 120, 200, 700, 550 );
		setLayout( null );
		
		setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
	}
	
	protected JButton getJbutton( String title, int x, int y, int width, int height ){
		JButton jButton = new JButton( title );
		jButton.setBounds( x, y, width, height );
		return jButton;
	}
	
	protected JSeparator getJseparator( int x, int y, int width, int height ){
		JSeparator jSeparator = new JSeparator();
		jSeparator.setBounds( x, y, width, height );
		return jSeparator;
	}
	
	protected JTextField getJTextField( int x, int y, int width, int height ) {
		JTextField tf = new JTextField();
		tf.setBounds( x, y, width, height);
		
		return tf;
	}
	
	protected JTextArea getJTextArea( Boolean editable, int x, int y, int width, int height ) {
		JTextArea ta = new JTextArea();
		ta.setEditable( editable );
		ta.setBounds( x, y, width, height);
		
		return ta;
	}
	
	protected JLabel getJLabel( String title, int x, int y, int width, int height ) {
		JLabel label = new JLabel( title );
		label.setBounds(x, y, width, height);
		return label;
	}

}
