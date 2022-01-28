package service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import entities.Salle;
import connexion.Connexion;
import dao.IDao;

public class SalleService implements IDao<Salle>{
	
	@Override
    public boolean create(Salle o) {
        String sql = "insert into salles values (null, ?, ?, ?)";
        String sqlC = "select count(*) from salles where code  = ? ";
        PreparedStatement ps = null;
        PreparedStatement psC = null;
        try {
        	psC = Connexion.getInstane().getConnection().prepareStatement(sqlC);
        	psC.setString(1, o.getCode());
            ResultSet rs = psC.executeQuery();
            if (rs.next()) {
                if(rs.getInt(1) == 0) {
                	ps = Connexion.getInstane().getConnection().prepareStatement(sql);
		            ps.setString(1, o.getCode());
		            ps.setInt(2, o.getCapacite());
		            ps.setString(3, o.getType());
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
    public boolean delete(Salle o) {
        String sql = "delete from salles where id  = ?";
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
    public boolean update(Salle o) {
        String sql = "update salles set code  = ?, capacite = ?, type = ? where id  = ?";
        PreparedStatement ps = null;
        try {
            ps = Connexion.getInstane().getConnection().prepareStatement(sql);
            ps.setString(1, o.getCode());
            ps.setInt(2, o.getCapacite());
            ps.setString(3, o.getType());
            ps.setInt(4, o.getId());
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
    public Salle findById(int id) {
        String sql = "select * from salles where id  = ?";
        PreparedStatement ps = null;
        try {
            ps = Connexion.getInstane().getConnection().prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Salle(rs.getInt("id"), rs.getString("code"), rs.getInt("capacite"), rs.getString("type"));
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
    public List<Salle> findAll() {
        List<Salle> salles = new ArrayList<Salle>();
        String sql = "select * from salles";
        PreparedStatement ps = null;
        try {
            ps = Connexion.getInstane().getConnection().prepareStatement(sql);;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
            	salles.add(new Salle(rs.getInt("id"), rs.getString("code"), rs.getInt("capacite"), rs.getString("type")));
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
        return salles;
    }
    
    public List<Salle> findSallesDispo(Date date) {
		List<Salle> salles = new ArrayList<Salle>();
		String sql = "select DISTINCT(salles.id), code, type, capacite " + "from salles " + "LEFT JOIN sallescreneaux "
				+ "ON salles.id = sallescreneaux.idSalle "
				+ "where sallescreneaux.id not in (select idsallecreneau from reservations where dateReservation = ?) "
				+ "or sallescreneaux.id is null;";
		PreparedStatement ps = null;
		try {
			ps = Connexion.getInstane().getConnection().prepareStatement(sql);
			ps.setDate(1, new java.sql.Date(date.getTime()));
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				salles.add(
						new Salle(rs.getInt("id"), rs.getString("code"), rs.getInt("capacite"), rs.getString("type")));
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
		return salles;
    }
}
