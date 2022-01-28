package service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import connexion.Connexion;
import dao.IDao;
import entities.Salle;
import entities.SalleCreneau;

public class SalleCreneauService implements IDao<SalleCreneau> {
	private SalleService ss = new SalleService();
	private CreneauService cs = new CreneauService();

	@Override
	public boolean create(SalleCreneau o) {
		String sql = "insert into sallescreneaux values (null, ?, ?)";
		String sqlC = "select count(*) from sallescreneaux where idCreneau = ? and idSalle = ?";
		PreparedStatement psC = null;
		PreparedStatement ps = null;
		try {
			psC = Connexion.getInstane().getConnection().prepareStatement(sqlC);
			psC.setInt(1, o.getCreneau().getId());
			psC.setInt(2, o.getSalle().getId());
			ResultSet rs = psC.executeQuery();
			if (rs.next()) {
				if (rs.getInt(1) == 0) {
					ps = Connexion.getInstane().getConnection().prepareStatement(sql);
					ps.setInt(2, o.getCreneau().getId());
					ps.setInt(1, o.getSalle().getId());
					if (ps.executeUpdate() == 1) {
						return true;
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
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
	public boolean delete(SalleCreneau o) {
		String sql = "delete from sallescreneaux where id  = ?";
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
	public boolean update(SalleCreneau o) {
		String sql = "update sallescreneaux set idCreneau = ? , idSalle = ? where id  = ?";
		PreparedStatement ps = null;
		try {
			ps = Connexion.getInstane().getConnection().prepareStatement(sql);
			ps.setInt(1, o.getCreneau().getId());
			ps.setInt(2, o.getSalle().getId());
			ps.setInt(3, o.getId());
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
	public SalleCreneau findById(int id) {
		String sql = "select * from sallescreneaux where id  = ?";
		PreparedStatement ps = null;
		try {
			ps = Connexion.getInstane().getConnection().prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return new SalleCreneau(rs.getInt("id"), ss.findById(rs.getInt("idSalle")),
						cs.findById(rs.getInt("idCreneau")));
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
	public List<SalleCreneau> findAll() {
		List<SalleCreneau> sallescreneaux = new ArrayList<SalleCreneau>();
		String sql = "select * from sallescreneaux";
		PreparedStatement ps = null;
		try {
			ps = Connexion.getInstane().getConnection().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				sallescreneaux.add(new SalleCreneau(rs.getInt("id"), ss.findById(rs.getInt("idSalle")),
						cs.findById(rs.getInt("idCreneau"))));
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
		return sallescreneaux;
	}

	public List<SalleCreneau> findDispos(Date date) {
		List<SalleCreneau> sallescreneaux = new ArrayList<SalleCreneau>();
		String sql = "select * from sallescreneaux where id not in (select idsallecreneau from reservations where dateReservation = ?)";
		PreparedStatement ps = null;
		try {
			ps = Connexion.getInstane().getConnection().prepareStatement(sql);
			ps.setDate(1, new java.sql.Date(date.getTime()));
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				sallescreneaux.add(new SalleCreneau(rs.getInt("id"), ss.findById(rs.getInt("idSalle")),
						cs.findById(rs.getInt("idCreneau"))));
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
		return sallescreneaux;
	}

	
}
