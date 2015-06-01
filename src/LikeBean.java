/*
 * Filename: LikeBean.java
 * Author: Jun Xu
 * Email: jxu.vimacs@gmail.com
 * Created Time: Fri 24 Apr 2015 02:22:39 PM EDT
 */

package photoshare;

import java.util.List;
import javax.servlet.http.HttpServletRequest;

/*
 * A bean that handles like data
 */
public class LikeBean {
	private int userId;
	private int photoId;

	public int getUserId() {
		return userId;
	}

	public int getPhotoId() {
		return photoId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public void setPhotoId(int photoId) {
		this.photoId = photoId;
	}
}
