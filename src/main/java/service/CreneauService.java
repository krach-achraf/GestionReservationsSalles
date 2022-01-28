package service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import connexion.Connexion;
import dao.IDao;
import entities.Creneau;

public class CreneauService implements IDao<Creneau>{
	@Override
    public boolean create(Creneau o) {
        String sql = "insert into creneaux values (null, ?, ?)";
        String sqlC = "select count(*) from creneaux where heureDebut  = ? and heureFin = ?";
        PreparedStatement psC = null;
        PreparedStatement ps = null;
        try {
        	psC = Connexion.getInstane().getConnection().prepareStatement(sqlC);
        	psC.setTime(1, o.getHeureDebut());
        	psC.setTime(2, o.getHeureFin());
        	ResultSet rs = psC.executeQuery();
        	if(rs.next()) {
        		if(rs.getInt(1) == 0) {
        			ps = Connexion.getInstane().getConnection().prepareStatement(sql);
                    ps.setTime(1, o.getHeureDebut());
                    ps.setTime(2, o.getHeureFin());
                    if (ps.executeUpdate() == 1) {
                        return true;
                    }
        		}
            }
        } catch (SQLException e) {
           e.printStackTrace();
        } finally {
			try {
				ps.close();
				psC.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
        return false;
    }

    @Override
    public boolean delete(Creneau o) {
        String sql = "delete from creneaux where id  = ?";
        PreparedStatement ps = null;
        try {
            ps = Connexion.getInstane().getConnection().prepareStatement(sql);
            ps.setInt(1, o.getId());
            if (ps.executeUpdate() == 1) {
                return true;
            }
        } catch (SQLException e) {
        	System.out.println(e.getMessage());
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
    public boolean update(Creneau o) {
        String sql = "update creneaux set heureDebut  = ? ,heureFin = ? where id  = ?";
        PreparedStatement ps = null;
        try {
            ps = Connexion.getInstane().getConnection().prepareStatement(sql);
            ps.setTime(1, o.getHeureDebut());
            ps.setTime(2, o.getHeureFin());
            ps.setInt(3, o.getId());
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
    public Creneau findById(int id) {
        String sql = "select * from creneaux where id  = ?";
        PreparedStatement ps = null;
        try {
            ps = Connexion.getInstane().getConnection().prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Creneau(rs.getInt("id"), rs.getTime("heureDebut"), rs.getTime("heureFin"));
            }
        } catch (SQLException e) {
            System.out.println("findById " + e.getMessage());
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
    public List<Creneau> findAll() {
        List<Creneau> creneaux = new ArrayList<Creneau>();
        String sql = "select * from creneaux";
        PreparedStatement ps = null;
        try {
            ps = Connexion.getInstane().getConnection().prepareStatement(sql);;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
            	creneaux.add(new Creneau(rs.getInt("id"), rs.getTime("heureDebut"), rs.getTime("heureFin")));
            }

        } catch (SQLException e) {
            System.out.println("findAll " + e.getMessage());
        } finally {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
        return creneaux;
    }
    
}
