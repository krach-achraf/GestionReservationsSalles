package service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entities.Client;
import connexion.Connexion;
import dao.IDao;

public class ClientService implements IDao<Client> {

	@Override
	public boolean create(Client o) {
		String sql = "insert into clients values (null, ?, ?, ?, ?, ?)";
		String sqlC = "select count(*) from clients where (nom = ? and prenom = ?) or (login = ?) or (email = ?)";
		PreparedStatement psC = null;
		PreparedStatement ps = null;
		try {
			psC = Connexion.getInstane().getConnection().prepareStatement(sqlC);
			psC.setString(1, o.getNom());
			psC.setString(2, o.getPrenom());
			psC.setString(3, o.getLogin());
			psC.setString(4, o.getEmail());
			ResultSet rs = psC.executeQuery();
			if (rs.next()) {
				if(rs.getInt(1) == 0) {
					ps = Connexion.getInstane().getConnection().prepareStatement(sql);
					ps.setString(1, o.getNom());
					ps.setString(2, o.getPrenom());
					ps.setString(3, o.getLogin());
					ps.setString(4, o.getEmail());
					ps.setString(5, o.getPassword());
					if (ps.executeUpdate() == 1) {
						return true;
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				psC.close();
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	@Override
	public boolean delete(Client o) {
		String sql = "delete from clients where id  = ?";
		PreparedStatement ps = null;
		try {
			ps = Connexion.getInstane().getConnection().prepareStatement(sql);
			ps.setInt(1, o.getId());
			if (ps.executeUpdate() == 1) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	@Override
	public boolean update(Client o) {
		return false;
	}

	@Override
	public Client findById(int id) {
		String sql = "select * from clients where id  = ?";
		PreparedStatement ps = null;
		try {
			ps = Connexion.getInstane().getConnection().prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return new Client(rs.getInt("id"), 
						rs.getString("nom"), 
						rs.getString("prenom"), 
						rs.getString("login"),
						rs.getString("email"), 
						rs.getString("password"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public List<Client> findAll() {
		List<Client> clients = new ArrayList<Client>();
		String sql = "select * from clients";
		PreparedStatement ps = null;
		try {
			ps = Connexion.getInstane().getConnection().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				clients.add(new Client(rs.getInt("id"), 
						rs.getString("nom"), 
						rs.getString("prenom"),
						rs.getString("login"), 
						rs.getString("email"), 
						rs.getString("password")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return clients;
	}

	public Client findByLogin(String login) {
		String sql = "select * from clients where login  = ?";
		PreparedStatement ps = null;
		try {
			ps = Connexion.getInstane().getConnection().prepareStatement(sql);
			ps.setString(1, login);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return new Client(rs.getInt("id"), 
						rs.getString("nom"), 
						rs.getString("prenom"), 
						rs.getString("login"),
						rs.getString("email"), 
						rs.getString("password"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public Client findByEmail(String email) {
		String sql = "select * from clients where email  = ?";
		PreparedStatement ps = null;
		try {
			ps = Connexion.getInstane().getConnection().prepareStatement(sql);
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return new Client(rs.getInt("id"), 
						rs.getString("nom"), 
						rs.getString("prenom"), 
						rs.getString("login"),
						rs.getString("email"), 
						rs.getString("password"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
