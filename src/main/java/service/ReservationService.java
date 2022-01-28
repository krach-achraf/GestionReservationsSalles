package service;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entities.Reservation;
import connexion.Connexion;
import dao.IDao;

public class ReservationService implements IDao<Reservation> {
	private SalleCreneauService os = new SalleCreneauService();
	private ClientService cs = new ClientService();

	@Override
	public boolean create(Reservation o) {
		String sql = "insert into reservations values (null, ?, ?, ?, ?)";
		PreparedStatement ps = null;
		try {
			ps = Connexion.getInstane().getConnection().prepareStatement(sql);
			ps.setInt(1, o.getSalleCreneau().getId());
			ps.setInt(2, o.getClient().getId());
			ps.setDate(3, new Date(o.getDateReservation().getTime()));
			ps.setString(4, o.getEtatReservation());
			if (ps.executeUpdate() == 1) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	@Override
	public boolean delete(Reservation o) {
		String sql = "delete from reservations where id  = ?";
		PreparedStatement ps = null;
		try {
			ps = Connexion.getInstane().getConnection().prepareStatement(sql);
			ps.setInt(1, o.getId());
			if (ps.executeUpdate() == 1) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	@Override
	public boolean update(Reservation o) {
		return false;
	}

	@Override
	public Reservation findById(int id) {
		String sql = "select * from reservations where id  = ?";
		PreparedStatement ps = null;
		try {
			ps = Connexion.getInstane().getConnection().prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return new Reservation(rs.getInt("id"), 
						os.findById(rs.getInt("idSalleCreneau")),
						cs.findById(rs.getInt("idClient")),
						rs.getDate("dateReservation"), 
						rs.getString("etatReservation"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public List<Reservation> findAll() {
		List<Reservation> reservations = new ArrayList<Reservation>();
		String sql = "select * from reservations";
		PreparedStatement ps = null;
		try {
			ps = Connexion.getInstane().getConnection().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				reservations.add(new Reservation(rs.getInt("id"), 
						os.findById(rs.getInt("idSalleCreneau")),
						cs.findById(rs.getInt("idlient")), 
						rs.getDate("dateReservation"), 
						rs.getString("etatReservation")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return reservations;
	}

	public List<Reservation> findByIdClient(int id) {
		List<Reservation> reservations = new ArrayList<Reservation>();
		String sql = "select * from reservations where idClient  = ?";
		PreparedStatement ps = null;
		try {
			ps = Connexion.getInstane().getConnection().prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				reservations.add(new Reservation(rs.getInt("id"), 
						os.findById(rs.getInt("idSalleCreneau")),
						cs.findById(rs.getInt("idClient")), 
						rs.getDate("dateReservation"), 
						rs.getString("etatReservation")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return reservations;
	}

	public List<Reservation> findReservationsValide() {
		List<Reservation> reservations = new ArrayList<Reservation>();
		String sql = "select * from reservations where etatReservation = 'valide'";
		PreparedStatement ps = null;
		try {
			ps = Connexion.getInstane().getConnection().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				reservations.add(new Reservation(rs.getInt("id"), 
						os.findById(rs.getInt("idSalleCreneau")),
						cs.findById(rs.getInt("idClient")), 
						rs.getDate("dateReservation"), 
						rs.getString("etatReservation")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return reservations;
	}

	public List<Reservation> findReservationsEnAttend() {
		List<Reservation> reservations = new ArrayList<Reservation>();
		String sql = "select * from reservations where etatReservation = 'en attend'";
		PreparedStatement ps = null;
		try {
			ps = Connexion.getInstane().getConnection().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				reservations.add(new Reservation(rs.getInt("id"), 
						os.findById(rs.getInt("idSalleCreneau")),
						cs.findById(rs.getInt("idClient")), 
						rs.getDate("dateReservation"), 
						rs.getString("etatReservation")));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return reservations;
	}

	public boolean valider(int id) {
		String sql = "update reservations set etatReservation = ? where id  = ?";
		PreparedStatement ps = null;
		try {
			ps = Connexion.getInstane().getConnection().prepareStatement(sql);
			ps.setString(1, "valide");
			ps.setInt(2, id);
			if (ps.executeUpdate() == 1) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public Map<String, Integer> nombreReservationsParSalle() {
		Map<String, Integer> nombreReservations = new HashMap<String, Integer>();
		String sql = "select salles.code, count(idSalle)  "
				+ "from sallescreneaux, reservations, salles "
				+ "where reservations.idsallecreneau = sallescreneaux.id and sallescreneaux.idSalle = salles.id "
				+ "group by salles.code;";
		PreparedStatement ps = null;
		try {
			ps = Connexion.getInstane().getConnection().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				nombreReservations.put(rs.getString(1), rs.getInt(2));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return nombreReservations;
	}
	
	public Map<Integer, Integer> nombreReservationsParMois() {
		Map<Integer, Integer> nombreReservations = new HashMap<Integer, Integer>();
		String sql = "SELECT EXTRACT(month FROM dateReservation) \"Mois\", "
				+ "COUNT(*) \"NbrReservations\" "
				+ "FROM reservations "
				+ "GROUP BY EXTRACT(month FROM dateReservation) "
				+ "ORDER BY \"NbrReservations\" DESC;";
		PreparedStatement ps = null;
		try {
			ps = Connexion.getInstane().getConnection().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				nombreReservations.put(rs.getInt(1), rs.getInt(2));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return nombreReservations;
	}
}
