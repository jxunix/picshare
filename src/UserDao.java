/*
 * Filename: UserDao.java
 * Author: Jun Xu
 * Email: jxu.vimacs@gmail.com
 * Created Time: Thu 23 Apr 2015 01:22:03 AM EDT
 */

package photoshare;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
 * A data access object (DAO) to handle the Users table
 */
public class UserDao {
	private static final String CHECK_EMAIL_STMT =
		"SELECT COUNT(*) FROM Users WHERE email = ?";

	private static final String NEW_USER_STMT =
		"INSERT INTO Users (first_name, last_name, email, dob, gender, password, hmtn_loc_id, curr_loc_id, education) " +
		"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

	private static final String LOAD_ID_EMAIL_STMT =
		"SELECT user_id FROM Users WHERE email = ?";

	private static final String LOAD_USER_STMT =
		"SELECT * FROM Users WHERE user_id = ?";

	private static final String LOAD_USER_FIRSTNAME_STMT =
		"SELECT * FROM Users WHERE first_name = ?";

	private static final String LOAD_USER_LASTNAME_STMT =
		"SELECT * FROM Users WHERE last_name = ?";

	private static final String LOAD_PHOTO_CNT_USER_STMT =
		"SELECT Users.user_id, COUNT(Photos.photo_id) " +
		"FROM Users LEFT JOIN Albums LEFT JOIN Photos " +
		"ON Albums.album_id = Photos.album_id " +
		"ON Users.user_id = Albums.owner_id " +
		"GROUP BY Users.user_id ORDER BY Users.user_id";

	private static final String LOAD_COMMENT_CNT_USER_STMT =
		"SELECT Users.user_id, COUNT(Comments.comment_id) " +
		"FROM Users LEFT JOIN Comments " +
		"ON Users.user_id = Comments.user_id " +
		"GROUP BY Users.user_id ORDER BY Users.user_id";

	public boolean create(UserBean user) {
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;

		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(CHECK_EMAIL_STMT);
			stmt.setString(1, user.getEmail());
			rs = stmt.executeQuery();

			if (!rs.next()) {
				return false;
			}

			int result = rs.getInt(1);
			if (result > 0) {
				return false; 
			}

			try {
				stmt.close();
			} catch (Exception e) { ; }

			stmt = conn.prepareStatement(NEW_USER_STMT);
			stmt.setString(1, user.getFirstName());
			stmt.setString(2, user.getLastName());
			stmt.setString(3, user.getEmail());
			stmt.setDate(4, user.getDob());
			stmt.setString(5, user.getGender());
			stmt.setString(6, user.getPassword());
			stmt.setInt(7, user.getHmtnLocId());
			stmt.setInt(8, user.getCurrLocId());
			stmt.setString(9, user.getEducation());
			stmt.executeUpdate();

			rs.close();
			rs = null;

			stmt.close();
			stmt = null;

			conn.close();
			conn = null;

			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) { ; }
				rs = null;
			}

			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) { ; }
				stmt = null;
			}

			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) { ; }
				conn = null;
			}
		}
	}

	public UserBean get(int id) {
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		UserBean user = null;

		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(LOAD_USER_STMT);
			stmt.setInt(1, id);
			rs = stmt.executeQuery();

			if (rs.next()) {
				user = new UserBean();
				user.setId(rs.getInt(1));
				user.setFirstName(rs.getString(2));
				user.setLastName(rs.getString(3));
				user.setEmail(rs.getString(4));
				user.setDob(rs.getDate(5));
				user.setGender(rs.getString(6));
				user.setPassword(rs.getString(7));
				user.setHmtnLocId(rs.getInt(8));
				user.setCurrLocId(rs.getInt(9));
				user.setEducation(rs.getString(10));
			}

			rs.close();
			rs = null;

			stmt.close();
			stmt = null;

			conn.close();
			conn = null;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) { ; }
				rs = null;
			}

			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) { ; }
				stmt = null;
			}

			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) { ; }
				conn = null;
			}
		}

		return user;
	}

	public int getIdByEmail(String email) {
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		int id = -1;

		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(LOAD_ID_EMAIL_STMT);
			stmt.setString(1, email);
			rs = stmt.executeQuery();

			if (rs.next()) {
				id = rs.getInt(1);
			}

			rs.close();
			rs = null;

			stmt.close();
			stmt = null;

			conn.close();
			conn = null;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) { ; }
				rs = null;
			}

			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) { ; }
				stmt = null;
			}

			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) { ; }
				conn = null;
			}
		}

		return id;
	}

	public List<UserBean> getByFirstName(String firstname) {
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		UserBean user = null;
		List<UserBean> users = new ArrayList<UserBean>();

		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(LOAD_USER_FIRSTNAME_STMT);
			stmt.setString(1, firstname);
			rs = stmt.executeQuery();

			while (rs.next()) {
				user = new UserBean();
				user.setId(rs.getInt(1));
				user.setFirstName(rs.getString(2));
				user.setLastName(rs.getString(3));
				user.setEmail(rs.getString(4));
				user.setDob(rs.getDate(5));
				user.setGender(rs.getString(6));
				user.setPassword(rs.getString(7));
				user.setHmtnLocId(rs.getInt(8));
				user.setCurrLocId(rs.getInt(9));
				user.setEducation(rs.getString(10));
				users.add(user);
				user = null;
			}

			rs.close();
			rs = null;

			stmt.close();
			stmt = null;

			conn.close();
			conn = null;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) { ; }
				rs = null;
			}

			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) { ; }
				stmt = null;
			}

			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) { ; }
				conn = null;
			}
		}

		return users;
	}

	public List<UserBean> getByLastName(String lastname) {
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		UserBean user = null;
		List<UserBean> users = new ArrayList<UserBean>();

		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(LOAD_USER_LASTNAME_STMT);
			stmt.setString(1, lastname);
			rs = stmt.executeQuery();

			while (rs.next()) {
				user = new UserBean();
				user.setId(rs.getInt(1));
				user.setFirstName(rs.getString(2));
				user.setLastName(rs.getString(3));
				user.setEmail(rs.getString(4));
				user.setDob(rs.getDate(5));
				user.setGender(rs.getString(6));
				user.setPassword(rs.getString(7));
				user.setHmtnLocId(rs.getInt(8));
				user.setCurrLocId(rs.getInt(9));
				user.setEducation(rs.getString(10));
				users.add(user);
				user = null;
			}

			rs.close();
			rs = null;

			stmt.close();
			stmt = null;

			conn.close();
			conn = null;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) { ; }
				rs = null;
			}

			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) { ; }
				stmt = null;
			}

			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) { ; }
				conn = null;
			}
		}

		return users;
	}

	public List<Contribution> getContributions() {
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		UserBean user = null;
		List<Contribution> contributions = new ArrayList<Contribution>();
		Contribution contribution = null;

		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(LOAD_PHOTO_CNT_USER_STMT);
			rs = stmt.executeQuery();

			while (rs.next()) {
				contribution = new Contribution();
				contribution.setUserId(rs.getInt(1));
				contribution.setContribution(rs.getInt(2));
				contributions.add(contribution);
				contribution = null;
			}

			try {
				stmt.close();
				stmt = null;
				rs.close();
				rs = null;
			} catch (Exception e) {
				return null;
			}

			stmt = conn.prepareStatement(LOAD_COMMENT_CNT_USER_STMT);
			rs = stmt.executeQuery();

			int i = 0;
			while (rs.next()) {
				contributions.get(i).addContribution(rs.getInt(2));
				i++;
			}

			Comparator<Contribution> comparator = new Comparator<Contribution>() {
				public int compare(Contribution c1, Contribution c2) {
					return c1.getContribution() - c2.getContribution();
				}
			};

			Collections.sort(contributions, comparator);
			Collections.reverse(contributions);

			rs.close();
			rs = null;

			stmt.close();
			stmt = null;

			conn.close();
			conn = null;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) { ; }
				rs = null;
			}

			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) { ; }
				stmt = null;
			}

			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) { ; }
				conn = null;
			}
		}

		return contributions;
	}
}
