import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class keywordTest extends JFrame implements ActionListener 
{
    JButton Test;
    String keyword;
    byte[] ciphertext;
    byte[][] trapdoor=new byte[10][];
    JTextField keywordField;
	JScrollPane jsp;
	JTextArea jta;
	RSAPublicKey publicKey;

	ObjectInputStream ois;
	ObjectOutputStream oos;
	File f;
	int i=1;
	libforserver lib=new libforserver();
    public static void main(String []args){
        new keywordTest();
    }
    public keywordTest()
    {
    	keywordField = new JTextField(20);
        this.setLayout(new FlowLayout());
        Test=new JButton("Test");
        Test.addActionListener(this);
        jta = new JTextArea(10, 30);
		jta.setEditable(false);
		jta.setLineWrap(true);
		jsp = new JScrollPane(jta);
		

        this.add(keywordField);
        this.add(Test);
		this.add(jta);
        this.setSize(400,300);
        this.setVisible(true);
        this.setLocation(500, 200);
        try {
        	ois = new ObjectInputStream(new FileInputStream("G:\\choosen keyword\\publickey"));
    		publicKey=(RSAPublicKey) ois.readObject();
    		ois.close();
    		f=new File("G:\\choosen keyword\\td"+i);
    		while (f.exists()){
    			ois = new ObjectInputStream(new FileInputStream("G:\\choosen keyword\\td"+i));
    			trapdoor[i]=(byte[]) ois.readObject();
        		ois.close();
        		i++;
        		f=new File("G:\\choosen keyword\\td"+i);
    		}
    		jta.append("Successfully read "+(i-1)+" trapdoor.\n");
        } catch (Exception ex) {
			ex.printStackTrace();
		}
    }
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == Test) {
			if (keywordField.getText().length() == 0) {
				JOptionPane.showMessageDialog(null,"Invaild Keyword");
			}
			else {
				try {
					keyword = keywordField.getText().trim();
					ciphertext= lib.gencipher(keyword, publicKey);
					/*oos=new ObjectOutputStream(new FileOutputStream("G:\\choosen keyword\\ct"+i));
					oos.writeObject(ciphertext);
					oos.close();*/
					jta.setText("");
					jta.append("Successfully read "+(i-1)+" trapdoor.\n");
					if (i>1) {
						for (int j=1;j<i;j++) {
							if (lib.verify(publicKey, ciphertext, trapdoor[j])) {
								jta.append("Trapdoor"+j+": Match success.\n");
							}
							else{
								jta.append("Trapdoor"+j+": Match failed.\n");
							}
						}
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
			}
		}
	}
}