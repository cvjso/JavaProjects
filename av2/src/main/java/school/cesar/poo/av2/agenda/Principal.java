package school.cesar.poo.av2.agenda;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.CardLayout;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableModel;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.JScrollPane;

public class Principal extends JFrame {

	private JPanel contentPane;
	private JButton btnSair;
	private JScrollPane scrollPane_1;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Principal frame = new Principal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Principal() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnAdicionar = new JButton("Adicionar");
		btnAdicionar.setBounds(314, 44, 100, 25);
		contentPane.add(btnAdicionar);
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
		        dispose();
		        Secundaria sec = new Secundaria();
		        sec.setVisible(true);
			}
		});
		
		JButton btnNewButton = new JButton("Remover");
		btnNewButton.setBounds(319, 98, 95, 25);
		contentPane.add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				
				try {
					int selectedRow = table.getSelectedRow();
					
					
					JSONObject db = read_db();
					
					ArrayList contatos = (ArrayList) db.get("contatos");
					
					contatos.remove(selectedRow);
					
					db.put("contatos", contatos);
					
					write_db(db);
					
					model.removeRow(selectedRow);
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, ex);
				}
			}
		});
		
		btnSair = new JButton("Sair");
		btnSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		btnSair.setBounds(352, 149, 62, 25);
		contentPane.add(btnSair);
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(24, 12, 283, 250);
		contentPane.add(scrollPane_1);
		
		
		
		
		JSONObject linha = read_db();
	    
	    Vector<Vector<String>> lista_items = new Vector<>();
	    
	    ArrayList lista_json = (ArrayList) linha.get("contatos");
	    for (int i = 0; i < lista_json.size(); i++) {

	        JSONObject item = (JSONObject) lista_json.get(i);
	        Vector<String> item_str = new Vector<>();

	        item_str.add((String) item.get("nome"));
	        item_str.add((String) item.get("email"));
	        item_str.add((String) item.get("endereco"));
	        item_str.add((String) item.get("telefone"));

	        lista_items.add(item_str);
	    }

	    Vector<String> colunas = new Vector<>();
	    colunas.add("nome");
	    colunas.add("email");
	    colunas.add("endereco");
	    colunas.add("telefone");
		
		
		
		table = new JTable(lista_items, colunas);
		

		scrollPane_1.setViewportView(table);

		
	}

	private JSONObject read_db() {
		JSONParser jsonParser = new JSONParser();
		try (FileReader reader = new FileReader("src/contatos.json"))
        {
            //Read JSON file
            JSONObject contatosList = (JSONObject) jsonParser.parse(reader);
 
            return contatosList;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
		return null;
		
		
	}
	
	private void write_db(JSONObject contato) {

		try (FileWriter file = new FileWriter("src/contatos.json")) {
			 
            file.write(contato.toJSONString());
            file.flush();
 
        } catch (IOException e) {
            e.printStackTrace();
        }
		
	}
}
