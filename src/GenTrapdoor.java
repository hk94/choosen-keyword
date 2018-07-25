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

public class GenTrapdoor extends JFrame implements ActionListener 
{
    JButton Gen,Del;
    String keyword;
    byte[] trapdoor,ciphertext;
    JTextField keywordField;
	JScrollPane jsp;
	JTextArea jta;
	RSAPublicKey publicKey;
	RSAPrivateKey privateKey;

	ObjectInputStream ois;
	ObjectOutputStream oos;
	int i=1;
	libforclient lib=new libforclient();
    public static void main(String []args){
        new GenTrapdoor();
    }
    public GenTrapdoor()
    {
    	keywordField = new JTextField(12);
        this.setLayout(new FlowLayout());
        Gen=new JButton("Generate");
        Gen.addActionListener(this);
        Del=new JButton("Reset");
        Del.addActionListener(this);
        jta = new JTextArea(10, 30);
		jta.setEditable(false);
		jta.setLineWrap(true);
		jsp = new JScrollPane(jta);
		

        this.add(keywordField);
        this.add(Gen);
        this.add(Del);
		this.add(jsp);
        this.setSize(400,300);
        this.setVisible(true);
        this.setLocation(500, 200);
        try {
        	ois = new ObjectInputStream(new FileInputStream("G:\\choosen keyword\\publickey"));
    		publicKey=(RSAPublicKey) ois.readObject();
    		ois.close();
    		ois = new ObjectInputStream(new FileInputStream("G:\\choosen keyword\\privatekey"));
    		privateKey=(RSAPrivateKey) ois.readObject();
    		ois.close();
        } catch (Exception ex) {
			ex.printStackTrace();
		}
    }
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == Gen) {
			if (keywordField.getText().length() == 0) {
				JOptionPane.showMessageDialog(null,"Invaild Keyword");
			}
			else {
				try {
					keyword = keywordField.getText().trim();
					trapdoor= lib.gentrapdoor(keyword, privateKey,publicKey);
					/*oos=new ObjectOutputStream(new FileOutputStream("G:\\choosen keyword\\ct"+i));
					oos.writeObject(ciphertext);
					oos.close();*/
					oos=new ObjectOutputStream(new FileOutputStream("G:\\choosen keyword\\td"+i));
					oos.writeObject(trapdoor);
					oos.close();
					jta.append("Successfully generate trapdoor"+i+"\nHashcode is: "+trapdoor.hashCode()+"\nPath is: G:\\choosen keyword\\td"+i+"\n\n");
					i++;
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
			}
		}
		else if (e.getSource() == Del) {
			i=1;
			File f=new File("G:\\choosen keyword\\td"+i);
    		while (f.exists()){
    			f.delete();
        		i++;
        		f=new File("G:\\choosen keyword\\td"+i);
    		}
    		i=1;
    		jta.setText("");
    		JOptionPane.showMessageDialog(null,"All trapdoors have been deleted.");
		}
	}
}