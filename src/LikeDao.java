/*
 * Filename: LikeDao.java
 * Author: Jun Xu
 * Email: jxu.vimacs@gmail.com
 * Created Time: Fri 24 Apr 2015 07:23:54 PM EDT
 */

package photoshare;

import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
 * A data access object (DAO) to handle the Likes table
 */
public class LikeDao {
	private static final String NEW_LIKE_STMT =
		"INSERT INTO Likes (user_id, photo_id) VALUES (?, ?)";

	private static final String LOAD_LIKE_STMT =
		"SELECT * FROM Likes WHERE user_id = ? AND photo_id = ?";

	private static final String LOAD_LIKE_USER_STMT =
		"SELECT * FROM Likes WHERE user_id = ?";

	private static final String LOAD_LIKE_PHOTO_STMT =
		"SELECT * FROM Likes WHERE photo_id = ?";

	private static final String COUNT_LIKE_PHOTO_STMT =
		"SELECT COUNT(*) FROM Likes WHERE photo_id = ?";

	private static final String DELETE_LIKE_STMT =
		"DELETE FROM Likes WHERE user_id = ? AND photo_id = ?";

	private static final String DELETE_LIKE_USER_STMT =
		"DELETE FROM Likes WHERE user_id = ?";

	private static final String DELETE_LIKE_PHOTO_STMT =
		"DELETE FROM like WHERE photo_id = ?";

	public void create(LikeBean like) {
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;

		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(LOAD_LIKE_STMT);
			stmt.setInt(1, like.getUserId());
			stmt.setInt(2, like.getPhotoId());
			rs = stmt.executeQuery();

			if (rs.next()) {
				return;
			}

			try {
				stmt.close();
			} catch (Exception e) { ; }

			stmt = conn.prepareStatement(NEW_LIKE_STMT);
			stmt.setInt(1, like.getUserId());
			stmt.setInt(2, like.getPhotoId());
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
	}

	public List<Integer> getByUser(int userId) {
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		List<Integer> photoIds = new ArrayList<Integer>();

		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(LOAD_LIKE_USER_STMT);
			stmt.setInt(1, userId);
			rs = stmt.executeQuery();

			while (rs.next()) {
				photoIds.add(rs.getInt(2));
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

		return photoIds;
	}

	public List<Integer> getByPhotoId(int photoId) {
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		List<Integer> userIds = new ArrayList<Integer>();

		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(LOAD_LIKE_PHOTO_STMT);
			stmt.setInt(1, photoId);
			rs = stmt.executeQuery();

			while (rs.next()) {
				userIds.add(rs.getInt(1));
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

		return userIds;
	}

	public int getCntByPhotoId(int photoId) {
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		int count = 0;

		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(COUNT_LIKE_PHOTO_STMT);
			stmt.setInt(1, photoId);
			rs = stmt.executeQuery();

			if (!rs.next()) {
				return count;
			}

			count = rs.getInt(1);

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

		return count;
	}

	public boolean delete(LikeBean like) {
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;

		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(LOAD_LIKE_STMT);
			stmt.setInt(1, like.getUserId());
			stmt.setInt(2, like.getPhotoId());
			rs = stmt.executeQuery();

			if (!rs.next()) {
				return false;
			}

			try {
				stmt.close();
			} catch (Exception e) { ; }

			stmt = conn.prepareStatement(DELETE_LIKE_STMT);
			stmt.setInt(1, like.getUserId());
			stmt.setInt(2, like.getPhotoId());
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

	public void deleteByPhotoId(int photoId) {
		PreparedStatement stmt = null;
		Connection conn = null;

		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(DELETE_LIKE_PHOTO_STMT);
			stmt.setInt(1, photoId);
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

	public void deleteByUserId(int userId) {
		PreparedStatement stmt = null;
		Connection conn = null;

		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(DELETE_LIKE_PHOTO_STMT);
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
