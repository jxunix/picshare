/*
 * Filename: CommentDao.java
 * Author: Jun Xu
 * Email: jxu.vimacs@gmail.com
 * Created Time: Sat 25 Apr 2015 05:01:53 PM EDT
 */

package photoshare;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/*
 * A data access objec (DAO) to handle the Comments table
 */
public class CommentDao {
	private static final String NEW_COMMENT_STMT =
		"INSERT INTO Comments (text, doc, user_id, photo_id) VALUES (?, ?, ?, ?)";

	private static final String LOAD_COMMENT_PHOTO_STMT =
		"SELECT * FROM Comments WHERE photo_id = ? ORDER BY doc";

	public void create(CommentBean comment) {
		PreparedStatement stmt = null;
		Connection conn = null;

		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(NEW_COMMENT_STMT);
			stmt.setString(1, comment.getText());
			stmt.setDate(2, comment.getDoc());
			stmt.setInt(3, comment.getUserId());
			stmt.setInt(4, comment.getPhotoId());
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

	public List<CommentBean> getByPhotoId(int photoId) {
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		CommentBean comment = null;
		List<CommentBean> comments = new ArrayList<CommentBean>();

		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(LOAD_COMMENT_PHOTO_STMT);
			stmt.setInt(1, photoId);
			rs = stmt.executeQuery();

			while (rs.next()) {
				comment = new CommentBean();
				comment.setId(rs.getInt(1));
				comment.setText(rs.getString(2));
				comment.setDoc(rs.getDate(3));
				comment.setUserId(rs.getInt(4));
				comment.setPhotoId(rs.getInt(5));
				comments.add(comment);
				comment = null;
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

		return comments;
	}
}
