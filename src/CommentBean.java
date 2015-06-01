/*
 * Filename: CommentBean.java
 * Author: Jun Xu
 * Email: jxu.vimacs@gmail.com
 * Created Time: Fri 24 Apr 2015 02:24:51 PM EDT
 */

package photoshare;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

/*
 * A bean that handles comments data
 */
public class CommentBean {
	private int id;
	private int userId;
	private int photoId;
	private String text = "";
	private Date doc = null;

	public int getId() {
		return id;
	}

	public int getUserId() {
		return userId;
	}

	public int getPhotoId() {
		return photoId;
	}

	public String getText() {
		return text;
	}

	public Date getDoc() {
		return doc;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public void setPhotoId(int photoId) {
		this.photoId = photoId;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setDoc(Date doc) {
		this.doc = doc;
	}

	public CommentBean create(HttpServletRequest request, int ownerId, int photoId) {
		CommentBean comment = new CommentBean();

		String text = request.getParameter("commentText");
		Date doc = new Date(Calendar.getInstance().getTimeInMillis());
		String email = request.getUserPrincipal().getName(); 
		int userId = new UserDao().getIdByEmail(email);

		if (text == null || text.equals("")) {
			return null;
		}
		if (userId == ownerId) {
			return null;
		}

		comment.setText(text);
		comment.setDoc(doc);
		comment.setUserId(userId);
		comment.setPhotoId(photoId);

		return comment;
	}
}
