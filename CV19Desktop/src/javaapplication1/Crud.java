package javaapplication1;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javaapplication1.Admin;

public class Crud {
	PreparedStatement ps=null;
	ResultSet rs=null;
	private static Crud instance = null;

	private Crud() {
	}

	public static Crud getInstance() {
		if (instance == null) {
			instance = new Crud();
		}
		return instance;
	}
	

	/*
	public void inserimento(Utente u){
		int pKAccount=0;
		Utility.getInstance().openConnection();
		String sql1="INSERT INTO account (email,username,password,id_ruolo) VALUES(?,?,?,?)";
		try {
			ps=Utility.getInstance().con.prepareStatement(sql1);
			ps.setString(1, u.getMail());
			ps.setString(2, u.getUser());
			ps.setString(3, u.getPass());
			ps.setInt(4, u.getId_ruolo());
			ps.executeUpdate();
			
			String sql2="Select * From account";
			ps=Utility.getInstance().con.prepareStatement(sql2);
			rs=ps.executeQuery();
			if(rs.last()) {
				pKAccount=rs.getInt("id");
			}
			
			String sql3="INSERT INTO utenti(nome,cognome,eta,citta,cf,id_account)VALUES(?,?,?,?,?,?)";
			ps=Utility.getInstance().con.prepareStatement(sql3);
			ps.setString(1, u.getNome());
			ps.setString(2, u.getCognome());
			ps.setInt(3, u.getEta());
			ps.setString(4, u.getCitta());
			ps.setString(5, u.getCf());
			ps.setInt(6, pKAccount);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				ps.close();
				Utility.getInstance().closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

        */
        
	public void modifica(int id) {
		Utility.getInstance().openConnection();
		String sql="UPDATE Recensione SET Visibile=1 WHERE id=? ";
		try {
			ps=Utility.getInstance().con.prepareStatement(sql);
			ps.setInt(1,id);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				ps.close();
				Utility.getInstance().closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
	}

	public void elimina(int id) {
		Utility.getInstance().openConnection();
		String sql = "DELETE FROM Recensione WHERE id=?";
		try {
			ps=Utility.getInstance().con.prepareStatement(sql);
			ps.setInt(1,id);
			ps.executeUpdate();
			} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			ps.close();
			Utility.getInstance().closeConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	
	public ArrayList<Recensioni> visualizzaRecensioni() {
		ArrayList<Recensioni> array=new ArrayList<Recensioni>();
		Utility.getInstance().openConnection();
		String sql="Select * from Recensione";
		try {
			ps=Utility.getInstance().con.prepareStatement(sql);
			rs=ps.executeQuery();
			while(rs.next()) {
				Recensioni r = new Recensioni();
                                
                                r.setId(rs.getInt("Id"));
				r.setNomeStruttura(rs.getString("nomeStruttura"));
				r.setTesto(rs.getString("testo"));
				r.setValutazione(rs.getInt("valutazione"));
				r.setAutore(rs.getString("autore"));
				r.setData(rs.getString("data"));
				r.setVisibile(rs.getInt("visibile"));
				array.add(r);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
			try {
				rs.close();
				ps.close();
				Utility.getInstance().closeConnection();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		
		return array;
	}
        
        /*
	public boolean controlloDati(Utente u) {
		Utility.getInstance().openConnection();
		String sql="SELECT email FROM account WHERE email=?";
		boolean flag=false;
		try {
			ps=Utility.getInstance().con.prepareStatement(sql);
			ps.setString(1, u.getMail());
			rs=ps.executeQuery();
			while(rs.next()) {
				flag=true;
				}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return flag;
	}
	*/
	public int controlloAdmin(String nome, String pass) {
		Admin a= new Admin();
		Utility.getInstance().openConnection();
		String sql="select * from Amministratore where USERNAME= ? and PASSWORD= ?";
		try {
		ps=Utility.getInstance().con.prepareStatement(sql);
                ps.setString(1, nome);
		ps.setString(2, pass);
		rs=ps.executeQuery();
		if(rs.next()) {
                        a.setName(rs.getString("USERNAME"));
                        a.setPass(rs.getString("PASSWORD"));
                        a.setEmail(rs.getString("EMAIL"));
			return 1;
		}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				rs.close();
				ps.close();
				Utility.getInstance().closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}return 0;
	}
	
        /*
	public ArrayList<Ruolo> visualizzaRuoli() {
		Utility.getInstance().openConnection();
		String sql="Select * from ruoli";
		ArrayList<Ruolo> r= new ArrayList<>();
		try {
			ps=Utility.getInstance().con.prepareStatement(sql);
			rs=ps.executeQuery();
			while(rs.next()) {
				Ruolo ruolo= new Ruolo();
				ruolo.setId_ruolo(rs.getInt("id"));
				ruolo.setRole(rs.getString("ruolo"));
				r.add(ruolo);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				rs.close();
				ps.close();
				Utility.getInstance().closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}return r;
	}
	
	
	public boolean controlloLogin(Utente u) {
		int id_ruolo =0;
		boolean flag=false;
		Utility.getInstance().openConnection();
		String sql="Select * From account Where username=? and password=?";
		String sql1 ="select * from ruolo where id=?";
		try {
			ps=Utility.getInstance().con.prepareStatement(sql);
			ps.setString(1, u.getUser());
			ps.setString(2, u.getPass());
			rs=ps.executeQuery();
			if(rs.next()) {
				id_ruolo = rs.getInt("id_ruolo");
				flag=true;
			}
			ps = Utility.getInstance().con.prepareStatement(sql1);
			ps.setInt(1, id_ruolo);
			rs=ps.executeQuery();
			if(rs.next()) {
				u.setRole(rs.getString("ruoli"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				rs.close();
				ps.close();
				Utility.getInstance().closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return flag;
	}
        */
}
