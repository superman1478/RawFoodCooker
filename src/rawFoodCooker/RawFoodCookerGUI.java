package rawFoodCooker;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

import org.powerbot.script.rt6.ClientContext;

import methods.MyMethods;

@SuppressWarnings("serial")
public class RawFoodCookerGUI extends JFrame {

	private final JComboBox<RawFood> FoodCombobox = new JComboBox<RawFood>(RawFood.values());
	private JButton button1 = new JButton();
	private JCheckBox saveCheck = new JCheckBox("Save Settings");
	private JLabel label1 = new JLabel("Select Food Type:");

	private ClientContext ctx;
	private RawFoodCooker script;

	private File settingsFile;

	public RawFoodCookerGUI(ClientContext ctx) {
		this.ctx = ctx;
		this.script = (RawFoodCooker)ctx.controller.script();
		settingsFile = MyMethods.getSettingsFile(script, script.getDisplayName());
		initComponents();
	}

	private void initComponents() {

		setTitle("GUI");
		setAlwaysOnTop(true);
		setResizable(false);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		Container contentPane = getContentPane();
		contentPane.setLayout(null);

		addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent winEvt) {
				System.out.println("GUI exited");
				setVisible(false);
				ctx.controller.stop();
			}
		});


		label1.setBounds(6, 5, 100, label1.getPreferredSize().height);
		contentPane.add(label1);
		contentPane.add(FoodCombobox);		
		FoodCombobox.setBounds(5, 21, 130, FoodCombobox.getPreferredSize().height);

		saveCheck.setBounds(5, 175, 110, 15);
		contentPane.add(saveCheck);

		contentPane.add(button1);
		button1.setText("Start");
		button1.setBounds(5, 195, 130, button1.getPreferredSize().height);

		button1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				if (script.getDisplayName() != null && saveCheck.isSelected()) {
					writeSettings();
				}

				script.setSelectedFood((RawFood)FoodCombobox.getSelectedItem());
				setVisible(false);
			}
		});

		if (settingsFile.exists()) {
			readSettings();
			saveCheck.setSelected(true);
		}

		setSize(148, 251);
		setLocationRelativeTo(getOwner());

	}

	private void readSettings() {
		try {
			BufferedReader br = null;
			try {
				br = new BufferedReader(new FileReader(settingsFile));
				FoodCombobox.setSelectedItem(br.readLine().split(": ")[1]);
			} finally {
				if (br != null) br.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

	private void writeSettings() {
		try {
			BufferedWriter output = null;
			try {
				output = new BufferedWriter(new FileWriter(settingsFile));
				output.write("Food Type Selected: " + FoodCombobox.getSelectedItem() + "\r\n");
			} finally {
				if (output != null) output.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

}