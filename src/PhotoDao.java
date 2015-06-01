package photoshare;

import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
 * A data access object (DAO) to handle photos objects
 *
 * @author G. Zervas <cs460tf@bu.edu>
 */
public class PhotoDao {
	private static final String SAVE_PHOTO_STMT =
		"INSERT INTO Photos (caption, imgdata, album_id, size, content_type, thumbdata) VALUES (?, ?, ?, ?, ?, ?)";

	private static final String LOAD_PHOTO_STMT =
		"SELECT * FROM Photos WHERE photo_id = ?";

	private static final String LOAD_OWNER_STMT =
		"SELECT Albums.owner_id FROM Albums, Photos WHERE Albums.album_id = Photos.album_id AND photo_id = ?";

	private static final String LOAD_PHOTO_ALBUM_STMT =
		"SELECT * FROM Photos WHERE album_id = ?";

	private static final String LOAD_PHOTO_OWNER_STMT =
		"SELECT * FROM Photos, Albums WHERE Photos.album_id = Albums.album_id AND Albums.owner_id = ?";

	private static final String LOAD_PHOTO_OWNER_TAG_STMT =
		"SELECT Photos.photo_id, Photos.caption, Photos.imgdata, Photos.album_id, Photos.size, Photos.content_type, Photos.thumbdata " +
		"FROM Tag_on, Photos, Albums WHERE Tag_on.photo_id = Photos.photo_id " +
		"AND Photos.album_id = Albums.album_id AND Albums.owner_id = ? AND Tag_on.word = ?";

	private static final String LOAD_PHOTO_TAG_STMT =
		"SELECT Photos.photo_id, Photos.caption, Photos.imgdata, Photos.album_id, Photos.size, Photos.content_type, Photos.thumbdata " +
		"FROM Tag_on, Photos, Albums WHERE Tag_on.photo_id = Photos.photo_id " +
		"AND Photos.album_id = Albums.album_id AND Tag_on.word = ?";

	private static final String LOAD_PHOTO_IDS_STMT =
		"SELECT photo_id FROM Photos ORDER BY photo_id DESC";

	private static final String DELETE_PHOTO_STMT =
		"DELETE FROM Photos WHERE photo_id = ?";

	public void save(PhotoBean photo) {
		PreparedStatement stmt = null;
		Connection conn = null;

		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(SAVE_PHOTO_STMT);
			stmt.setString(1, photo.getCaption());
			stmt.setBytes(2, photo.getData());
			stmt.setInt(3, photo.getAlbumId());
			stmt.setLong(4, photo.getSize());
			stmt.setString(5, photo.getContentType());
			stmt.setBytes(6, photo.getThumbdata());
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

	public int getOwner(int id) {
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		PhotoBean photo = null;
		int owner_id = -1;

		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(LOAD_OWNER_STMT);
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			
			if (rs.next()) {
				owner_id = rs.getInt(1);
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
				try { rs.close();
			} catch (SQLException e) { ; }
				rs = null;
			}

			if (stmt != null) {
				try { stmt.close();
			} catch (SQLException e) { ; }
				stmt = null;
			}

			if (conn != null) {
				try { conn.close();
			} catch (SQLException e) { ; }
				conn = null;
			}
		}

		return owner_id;
	}

	public PhotoBean load(int id) {
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		PhotoBean photo = null;

		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(LOAD_PHOTO_STMT);
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			
			if (rs.next()) {
				photo = new PhotoBean();
				photo.setId(rs.getInt(1));
				photo.setCaption(rs.getString(2));
				photo.setData(rs.getBytes(3));
				photo.setAlbumId(rs.getInt(4));
				photo.setSize(rs.getLong(5));
				photo.setContentType(rs.getString(6));
				photo.setThumbdata(rs.getBytes(7));
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
				try { rs.close();
			} catch (SQLException e) { ; }
				rs = null;
			}

			if (stmt != null) {
				try { stmt.close();
			} catch (SQLException e) { ; }
				stmt = null;
			}

			if (conn != null) {
				try { conn.close();
			} catch (SQLException e) { ; }
				conn = null;
			}
		}

		return photo;
	}

	public List<PhotoBean> getByAlbumId(int albumId) {
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		PhotoBean photo = null;
		List<PhotoBean> photos = new ArrayList<PhotoBean>();

		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(LOAD_PHOTO_ALBUM_STMT);
			stmt.setInt(1, albumId);
			rs = stmt.executeQuery();

			while (rs.next()) {
				photo = new PhotoBean();
				photo.setId(rs.getInt(1));
				photo.setCaption(rs.getString(2));
				photo.setData(rs.getBytes(3));
				photo.setAlbumId(rs.getInt(4));
				photo.setSize(rs.getLong(5));
				photo.setContentType(rs.getString(6));
				photo.setThumbdata(rs.getBytes(7));
				photos.add(photo);
				photo = null;
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

		return photos;
	}

	public List<PhotoBean> getByOwnerId(int ownerId) {
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		PhotoBean photo = null;
		List<PhotoBean> photos = new ArrayList<PhotoBean>();

		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(LOAD_PHOTO_OWNER_STMT);
			stmt.setInt(1, ownerId);
			rs = stmt.executeQuery();

			while (rs.next()) {
				photo = new PhotoBean();
				photo.setId(rs.getInt(1));
				photo.setCaption(rs.getString(2));
				photo.setData(rs.getBytes(3));
				photo.setAlbumId(rs.getInt(4));
				photo.setSize(rs.getLong(5));
				photo.setContentType(rs.getString(6));
				photo.setThumbdata(rs.getBytes(7));
				photos.add(photo);
				photo = null;
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

		return photos;
	}

	public List<PhotoBean> getByOwnerTag(int ownerId, String word) {
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		PhotoBean photo = null;
		List<PhotoBean> photos = new ArrayList<PhotoBean>();

		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(LOAD_PHOTO_OWNER_TAG_STMT);
			stmt.setInt(1, ownerId);
			stmt.setString(2, word);
			rs = stmt.executeQuery();

			while (rs.next()) {
				photo = new PhotoBean();
				photo.setId(rs.getInt(1));
				photo.setCaption(rs.getString(2));
				photo.setData(rs.getBytes(3));
				photo.setAlbumId(rs.getInt(4));
				photo.setSize(rs.getLong(5));
				photo.setContentType(rs.getString(6));
				photo.setThumbdata(rs.getBytes(7));
				photos.add(photo);
				photo = null;
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

		return photos;
	}

	public List<PhotoBean> getByTag(String word) {
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		PhotoBean photo = null;
		List<PhotoBean> photos = new ArrayList<PhotoBean>();

		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(LOAD_PHOTO_TAG_STMT);
			stmt.setString(1, word);
			rs = stmt.executeQuery();

			while (rs.next()) {
				photo = new PhotoBean();
				photo.setId(rs.getInt(1));
				photo.setCaption(rs.getString(2));
				photo.setData(rs.getBytes(3));
				photo.setAlbumId(rs.getInt(4));
				photo.setSize(rs.getLong(5));
				photo.setContentType(rs.getString(6));
				photo.setThumbdata(rs.getBytes(7));
				photos.add(photo);
				photo = null;
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

		return photos;
	}

	public List<Integer> allPhotoIds() {
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		List<Integer> photoIds = new ArrayList<Integer>();
		
		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(LOAD_PHOTO_IDS_STMT);
			rs = stmt.executeQuery();

			while (rs.next()) {
				photoIds.add(rs.getInt(1));
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

	public boolean delete(int id) {
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;

		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(LOAD_PHOTO_STMT);
			stmt.setInt(1, id);
			rs = stmt.executeQuery();

			if (!rs.next()) {
				return false;
			}

			try {
				stmt.close();
			} catch (Exception e) { ; }

			stmt = conn.prepareStatement(DELETE_PHOTO_STMT);
			stmt.setInt(1, id);
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
