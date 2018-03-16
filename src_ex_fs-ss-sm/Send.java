import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import br.com.wyma.wydriver.*;

import static javax.swing.GroupLayout.Alignment.*;

@SuppressWarnings("serial")

public class Send extends JFrame implements ActionListener {

	static JButton btn1= new JButton("Comando Próximo");
	static JButton btn2 = new JButton("Comando Repete");
	static JLabel lbl1= new JLabel("Digite o IP do painel:");
	static JLabel lbl2=new JLabel("Status de comunicação:");
	static JLabel lbl3= new JLabel("Número do box:");
	static JLabel lbl4= new JLabel("Direção da seta:");
	static JTextField IP= new JTextField("192.168.0.161");
	static JTextField STATUS= new JTextField("");
	static JComboBox BOX = new JComboBox ();
	static JComboBox SETA = new JComboBox ();

	public Send() {
		super("Exemplo FS-SM-SS sequêncial");

		for (int i=0 ; i<32 ; i++)
			BOX.addItem(i);
		BOX.setSelectedItem(3);
		
		SETA.addItem("D");
		SETA.addItem("E");
		SETA.setSelectedItem("D");		

		btn1.addActionListener(this);
		btn2.addActionListener(this);
		STATUS.setEnabled(false);

		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(LEADING)
						.addGroup(layout.createSequentialGroup()
								.addGroup(layout.createParallelGroup(LEADING)
										.addComponent(lbl1))
								.addGroup(layout.createParallelGroup(LEADING)
										.addComponent(IP)))
						.addGroup(layout.createSequentialGroup()
								.addGroup(layout.createParallelGroup(LEADING)
										.addComponent(lbl3))
								.addGroup(layout.createParallelGroup(LEADING)
										.addComponent(BOX)))
						.addGroup(layout.createSequentialGroup()
								.addGroup(layout.createParallelGroup(LEADING)
										.addComponent(lbl4))
								.addGroup(layout.createParallelGroup(LEADING)
										.addComponent(SETA)))																
						.addGroup(layout.createSequentialGroup()
								.addGroup(layout.createParallelGroup(LEADING)
										.addComponent(btn1,100,200,300))
								.addGroup(layout.createParallelGroup(LEADING)
										.addComponent(btn2,100,200,300)))
						.addGroup(layout.createSequentialGroup()
								.addGroup(layout.createParallelGroup(LEADING)
										.addComponent(lbl2))
								.addGroup(layout.createParallelGroup(LEADING)
										.addComponent(STATUS))))
				);
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(BASELINE)
						.addComponent(lbl1)
						.addComponent(IP))
				.addGroup(layout.createParallelGroup(BASELINE)
						.addComponent(lbl3)
						.addComponent(BOX))
				.addGroup(layout.createParallelGroup(BASELINE)
						.addComponent(lbl4)
						.addComponent(SETA))											
				.addGroup(layout.createParallelGroup(BASELINE)
						.addComponent(btn1,20,30,40)
						.addComponent(btn2,20,30,40))
				.addGroup(layout.createParallelGroup(BASELINE)
						.addComponent(lbl2)
						.addComponent(STATUS))
				);
		pack();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Socket sock = new Socket();
		Packet pack;
		
		byte seta='D';
		if (SETA.getSelectedItem()=="E") seta='E';
		
		if (e.getActionCommand()=="Comando Próximo") {
			pack = new Packet((byte) 255, (byte) 255, 'S', (byte) 51, (byte) 10, (Integer)BOX.getSelectedItem(), 4, new byte[] { 4,1,1,seta });
			sock.Send(IP.getText(), pack.getPacket());
			if (sock.getStatus()==Const.FRM_OK)
				STATUS.setText("Comunicação bem sucedida.");
			else
				STATUS.setText("Ocorreu um erro.");			
		}
		else if (e.getActionCommand()=="Comando Repete") {
			pack = new Packet((byte) 255, (byte) 255, 'S', (byte) 51, (byte) 10, (Integer)BOX.getSelectedItem(), 4, new byte[] { 4,1,2,seta });
			sock.Send(IP.getText(), pack.getPacket());
			if (sock.getStatus()==Const.FRM_OK)
				STATUS.setText("Comunicação bem sucedida.");
			else
				STATUS.setText("Ocorreu um erro.");
		}
	}
}