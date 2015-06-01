/*
 * Filename: AlbumDao.java
 * Author: Jun Xu
 * Email: jxu.vimacs@gmail.com
 * Created Time: Tue 21 Apr 2015 12:38:00 PM EDT
 */

package photoshare;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/*
 * A data access object (DAO) to handle the Albums table
 */
public class AlbumDao {
	private static final String NEW_ALBUM_STMT =
		"INSERT INTO Albums (name, doc, owner_id) VALUES (?, ?, ?)";

	private static final String LOAD_ALBUM_STMT =
		"SELECT * FROM Albums WHERE album_id = ?";

	private static final String LOAD_ALBUM_OWNER_STMT =
		"SELECT * FROM Albums WHERE owner_id = ?";

	private static final String DELETE_ALBUM_STMT =
		"DELETE FROM Albums WHERE album_id = ?";

	public void create(AlbumBean album) {
		PreparedStatement stmt = null;
		Connection conn = null;

		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(NEW_ALBUM_STMT);
			stmt.setString(1, album.getName());
			stmt.setDate(2, album.getDoc());
			stmt.setInt(3, album.getOwnerId());
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

	public AlbumBean get(int albumId) {
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		AlbumBean album = null;

		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(LOAD_ALBUM_STMT);
			stmt.setInt(1, albumId);
			rs = stmt.executeQuery();

			if (rs.next()) {
				album = new AlbumBean();
				album.setId(rs.getInt(1));
				album.setName(rs.getString(2));
				album.setDoc(rs.getDate(3));
				album.setOwnerId(rs.getInt(4));
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

		return album;
	}

	public List<AlbumBean> getByOwnerId(int ownerId) {
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		AlbumBean album = null;
		List<AlbumBean> albums = new ArrayList<AlbumBean>();

		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(LOAD_ALBUM_OWNER_STMT);
			stmt.setInt(1, ownerId);
			rs = stmt.executeQuery();

			while (rs.next()) {
				album = new AlbumBean();
				album.setId(rs.getInt(1));
				album.setName(rs.getString(2));
				album.setDoc(rs.getDate(3));
				album.setOwnerId(rs.getInt(4));
				albums.add(album);
				album = null;
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

		return albums;
	}

	public boolean delete(int albumId) {
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;

		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(LOAD_ALBUM_STMT);
			stmt.setInt(1, albumId);
			rs = stmt.executeQuery();

			if (!rs.next()) {
				return false;
			}

			try {
				stmt.close();
			} catch (Exception e) { ; }

			stmt = conn.prepareStatement(DELETE_ALBUM_STMT);
			stmt.setInt(1, albumId);
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
}
