/*
 * Filename: TagOnDao.java
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
 * A data access object (DAO) to handle the Tag_on table
 */
public class TagOnDao {
	private static final String NEW_TAGON_STMT =
		"INSERT INTO Tag_on (word, photo_id) VALUES (?, ?)";

	private static final String LOAD_TAGON_STMT =
		"SELECT * FROM Tag_on WHERE word = ? AND photo_id = ?";

	private static final String LOAD_TAGON_WORD_STMT =
		"SELECT * FROM Tag_on WHERE word = ?";

	private static final String LOAD_TAGON_PHOTO_STMT =
		"SELECT * FROM Tag_on WHERE photo_id = ?";

	private static final String LOAD_TAGON_OWNER_STMT =
		"SELECT Tag_on.word, COUNT(Tag_on.photo_id) FROM Tag_on, Photos, Albums, Users " +
		"WHERE Tag_on.photo_id = Photos.photo_id AND Photos.album_id = Albums.album_id AND Albums.owner_id = ? " +
		"GROUP BY Tag_on.word ORDER BY COUNT(Tag_on.photo_id) DESC";

	private static final String LOAD_TAGON_ALL_STMT =
		"SELECT Tag_on.word, COUNT(Tag_on.photo_id) FROM Tag_on, Photos, Albums, Users " +
		"WHERE Tag_on.photo_id = Photos.photo_id AND Photos.album_id = Albums.album_id " +
		"GROUP BY Tag_on.word ORDER BY COUNT(Tag_on.photo_id) DESC";

	private static final String DELETE_TAGON_STMT =
		"DELETE FROM Tag_on WHERE word = ? AND photo_id = ?";

	private static final String DELETE_TAGON_WORD_STMT =
		"DELETE FROM Tag_on WHERE word = ?";

	private static final String DELETE_TAGON_PHOTO_STMT =
		"DELETE FROM Tag_on WHERE photo_id = ?";

	public void create(TagOnBean tagOn) {
		PreparedStatement stmt = null;
		Connection conn = null;

		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(NEW_TAGON_STMT);
			stmt.setString(1, tagOn.getWord());
			stmt.setInt(2, tagOn.getPhotoId());
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

	public List<Integer> getByWord(String word) {
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		List<Integer> photoIds = new ArrayList<Integer>();

		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(LOAD_TAGON_WORD_STMT);
			stmt.setString(1, word);
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

	public List<String> getByPhotoId(int photoId) {
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		List<String> words = new ArrayList<String>();

		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(LOAD_TAGON_PHOTO_STMT);
			stmt.setInt(1, photoId);
			rs = stmt.executeQuery();

			while (rs.next()) {
				words.add(rs.getString(1));
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

		return words;
	}

	public List<String> getByOwner(int ownerId) {
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		List<String> words = new ArrayList<String>();

		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(LOAD_TAGON_OWNER_STMT);
			stmt.setInt(1, ownerId);
			rs = stmt.executeQuery();

			while (rs.next()) {
				words.add(rs.getString(1));
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

		return words;
	}

	public List<String> getAll() {
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		List<String> words = new ArrayList<String>();

		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(LOAD_TAGON_ALL_STMT);
			rs = stmt.executeQuery();

			while (rs.next()) {
				words.add(rs.getString(1));
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

		return words;
	}

	public boolean delete(TagOnBean tagOn) {
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;

		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(LOAD_TAGON_STMT);
			stmt.setString(1, tagOn.getWord());
			stmt.setInt(2, tagOn.getPhotoId());
			rs = stmt.executeQuery();

			if (!rs.next()) {
				return false;
			}

			try {
				stmt.close();
			} catch (Exception e) { ; }

			stmt = conn.prepareStatement(DELETE_TAGON_STMT);
			stmt.setString(1, tagOn.getWord());
			stmt.setInt(2, tagOn.getPhotoId());
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
			stmt = conn.prepareStatement(DELETE_TAGON_PHOTO_STMT);
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
}
