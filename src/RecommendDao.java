/*
 * Filename: RecommendDao.java
 * Author: Jun Xu
 * Email: jxu.vimacs@gmail.com
 * Created Time: Sat 25 Apr 2015 11:58:58 PM EDT
 */

package photoshare;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

/*
 * A data access object (DAO) to handle the recommendation function
 */
public class RecommendDao {
	private static final String REC_PHOTO_STMT =
		"SELECT P2.photo_id " +
		"FROM Tag_on AS T2, Photos AS P2, Albums AS A2 " +
		"WHERE T2.photo_id = P2.photo_id AND P2.album_id = A2.album_id AND A2.owner_id != ?" +
		"AND NOT EXISTS ((" +
		"SELECT T.word FROM Tag_on AS T, Photos AS P, Albums AS A, Users AS U " +
		"WHERE T.photo_id = P.photo_id AND P.album_id = A.album_id AND A.owner_id = ? " +
		"GROUP BY T.word ORDER BY COUNT(T.photo_id) DESC LIMIT ?) " +
		"EXCEPT (" +
		"SELECT Tag_on.word FROM Tag_on WHERE Tag_on.photo_id = P2.photo_id)) " +
		"GROUP BY P2.photo_id ORDER BY COUNT(T2.word)";

	public List<String> getTag(String[] inTags) {
		if (inTags == null || inTags.length == 0) {
			return null;
		}

		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		List<String> outTags = new ArrayList<String>();
		String s;

		String REC_TAG_STMT = "SELECT T.word, COUNT(T.photo_id) " +
			"FROM Tag_on AS T WHERE T.word IN (" +
			"SELECT DISTINCT T2.word " +
			"FROM Tag_on AS T1, Tag_on AS T2 " +
			"WHERE T1.photo_id = T2.photo_id AND T1.word = ?";

		for (int i = 1; i < inTags.length; i++) {
			REC_TAG_STMT += " UNION SELECT DISTINCT T2.word " +
				"FROM Tag_on AS T1, Tag_on AS T2 " +
				"WHERE T1.photo_id = T2.photo_id AND T1.word = ?";
		}
			
		REC_TAG_STMT += ") GROUP BY T.word ORDER BY COUNT(T.photo_id) DESC";

		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(REC_TAG_STMT);
			for (int i = 0; i < inTags.length; i++) {
				stmt.setString(i+1, inTags[i]);
			}
			rs = stmt.executeQuery();

			while (rs.next()) {
				s = rs.getString(1);
				if (!Arrays.asList(inTags).contains(s)) {
					outTags.add(s);
				}
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

		return outTags;
	}

	public List<Integer> getPhotoId(int ownerId, int num) {
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		List<Integer> photoIds = new ArrayList<Integer>();

		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(REC_PHOTO_STMT);
			stmt.setInt(1, ownerId);
			stmt.setInt(2, ownerId);
			stmt.setInt(3, num);
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
}
