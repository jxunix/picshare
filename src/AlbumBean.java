/*
 * Filename: AlbumBean.java
 * Author: Jun Xu
 * Email: jxu.vimacs@gmail.com
 * Created Time: Tue 21 Apr 2015 12:03:55 PM EDT
 */

package photoshare;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

/*
 * A bean that handles albums data
 */
public class AlbumBean {
	private int id;
	private String name = "";
	private Date doc = null;
	private int ownerId; 

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Date getDoc() {
		return doc;
	}

	public int getOwnerId() {
		return ownerId;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDoc(Date doc) {
		this.doc = doc;
	}

	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}

	public AlbumBean create(HttpServletRequest request) {
		AlbumBean album = new AlbumBean();

		String name = request.getParameter("albumName");
		Date doc = new Date(Calendar.getInstance().getTimeInMillis());
		String email = request.getUserPrincipal().getName(); 
		int ownerId = new UserDao().getIdByEmail(email);

		if (name == null) {
			return null;
		}

		album.setName(name);
		album.setDoc(doc);
		album.setOwnerId(ownerId);

		return album;
	}
}
