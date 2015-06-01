/*
 * Filename: FriendDao.java
 * Author: Jun Xu
 * Email: jxu.vimacs@gmail.com
 * Created Time: Fri 24 Apr 2015 03:00:35 PM EDT
 */

package photoshare;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/*
 * A data access object (DAO) to handle the Friends table
 */
public class FriendDao {
	private static final String NEW_FRIEND_STMT =
		"INSERT INTO Friends (user_id, friend_id) VALUES (?, ?)";

	private static final String LOAD_FRIEND_STMT =
		"SELECT * FROM Friends WHERE user_id = ? AND friend_id = ?";

	private static final String LOAD_FRIEND_USER_STMT =
		"SELECT * FROM Friends WHERE user_id = ?";

	private static final String DELETE_FRIEND_STMT =
		"DELETE FROM Friends WHERE user_id = ? AND friend_id = ?";

	private static final String DELETE_FRIEND_USER_STMT =
		"DELETE FROM Friends WHERE user_id = ?";

	private static final String DELETE_FRIEND_FRIEND_STMT =
		"DELETE FROM Friends WHERE friend_id = ?";

	public void create(FriendBean friend) {
		PreparedStatement stmt = null;
		Connection conn = null;

		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(NEW_FRIEND_STMT);
			stmt.setInt(1, friend.getUserId());
			stmt.setInt(2, friend.getFriendId());
			stmt.executeUpdate();

			try {
				stmt.close();
			} catch (Exception e) { ; }

			stmt = conn.prepareStatement(NEW_FRIEND_STMT);
			stmt.setInt(1, friend.getFriendId());
			stmt.setInt(2, friend.getUserId());
			stmt.executeUpdate();

			stmt.close();
			stmt = null;

			conn.close();
			conn = null;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
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

	public List<FriendBean> getByUserId(int userId) {
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		FriendBean friend = null;
		List<FriendBean> friends = new ArrayList<FriendBean>();

		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(LOAD_FRIEND_USER_STMT);
			stmt.setInt(1, userId);
			rs = stmt.executeQuery();

			while (rs.next()) {
				friend = new FriendBean();
				friend.setUserId(rs.getInt(1));
				friend.setFriendId(rs.getInt(2));
				friends.add(friend);
				friend = null;
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

		return friends;
	}

	public boolean delete(FriendBean friend) {
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;

		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(LOAD_FRIEND_STMT);
			stmt.setInt(1, friend.getUserId());
			stmt.setInt(2, friend.getFriendId());
			rs = stmt.executeQuery();

			if (!rs.next()) {
				return false;
			}

			try {
				stmt.close();
			} catch (Exception e) { ; }

			stmt = conn.prepareStatement(DELETE_FRIEND_STMT);
			stmt.setInt(1, friend.getUserId());
			stmt.setInt(2, friend.getFriendId());
			stmt.executeUpdate();

			try {
				stmt.close();
			} catch (Exception e) { ; }

			stmt = conn.prepareStatement(LOAD_FRIEND_STMT);
			stmt.setInt(1, friend.getFriendId());
			stmt.setInt(2, friend.getUserId());
			rs = stmt.executeQuery();

			if (!rs.next()) {
				return false;
			}

			try {
				stmt.close();
			} catch (Exception e) { ; }

			stmt = conn.prepareStatement(DELETE_FRIEND_STMT);
			stmt.setInt(1, friend.getFriendId());
			stmt.setInt(2, friend.getUserId());
			stmt.executeUpdate();

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

		return true;
	}

	public void deleteByUserId(int userId) {
		PreparedStatement stmt = null;
		Connection conn = null;

		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(DELETE_FRIEND_USER_STMT);
			stmt.setInt(1, userId);
			stmt.executeUpdate();

			try {
				stmt.close();
			} catch (Exception e) { ; }

			stmt = conn.prepareStatement(DELETE_FRIEND_FRIEND_STMT);
			stmt.setInt(1, userId);
			stmt.executeUpdate();

			stmt.close();
			stmt = null;

			conn.close();
			conn = null;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
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
}
