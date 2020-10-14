package school.cesar.poo.av2.agenda;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Secundaria extends JFrame {

	private JPanel contentPane;
	private final JLabel lbl = new JLabel("Nome:");
	private JLabel lblEndereo;
	private JLabel lblTelefone;
	private JTextField nome_input;
	private JTextField email_input;
	private JTextField endereco_input;
	private JTextField telefone_input;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Secundaria frame = new Secundaria();
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
	public Secundaria() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		lbl.setBounds(23, 12, 121, 33);
		contentPane.add(lbl);
		
		JLabel lblNewLabel_1 = new JLabel("Email:");
		lblNewLabel_1.setBounds(23, 78, 95, 15);
		contentPane.add(lblNewLabel_1);
		
		lblEndereo = new JLabel("Endereço:");
		lblEndereo.setBounds(23, 132, 110, 15);
		contentPane.add(lblEndereo);
		
		lblTelefone = new JLabel("Telefone:");
		lblTelefone.setBounds(23, 176, 95, 15);
		contentPane.add(lblTelefone);
		
		nome_input = new JTextField();
		nome_input.setBounds(202, 19, 181, 19);
		contentPane.add(nome_input);
		nome_input.setColumns(10);
		
		email_input = new JTextField();
		email_input.setBounds(202, 76, 181, 19);
		contentPane.add(email_input);
		email_input.setColumns(10);
		
		endereco_input = new JTextField();
		endereco_input.setBounds(202, 130, 181, 19);
		contentPane.add(endereco_input);
		endereco_input.setColumns(10);
		
		telefone_input = new JTextField();
		telefone_input.setBounds(202, 174, 181, 19);
		contentPane.add(telefone_input);
		telefone_input.setColumns(10);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
		        Principal sec = new Principal();
		        sec.setVisible(true);
			}
		});
		btnCancelar.setBounds(23, 220, 117, 25);
		contentPane.add(btnCancelar);
		
		JButton btnConfirmar = new JButton("Confirmar");
		btnConfirmar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				JSONObject contato = new JSONObject();
				contato.put("nome",nome_input.getText());
				
				contato.put("email",email_input.getText());
				
				contato.put("endereco",endereco_input.getText());
				
				contato.put("telefone",telefone_input.getText());
				
				write_db(contato);

				JOptionPane.showMessageDialog(null, "Adicionado");
				
				dispose();
				Principal sec = new Principal();
		        sec.setVisible(true);
			}

			private void write_db(JSONObject contato) {
				JSONObject contatos = read_db();
				ArrayList lista_contatos = (ArrayList) contatos.get("contatos");
				
				lista_contatos.add(contato);

				contatos.put("contatos",lista_contatos);
				try (FileWriter file = new FileWriter("src/contatos.json")) {
					 
		            file.write(contatos.toJSONString());
		            file.flush();
		 
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
				
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
		});
		btnConfirmar.setBounds(266, 220, 117, 25);
		contentPane.add(btnConfirmar);
	}
}
