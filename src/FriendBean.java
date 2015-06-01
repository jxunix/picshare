/*
 * Filename: FriendBean.java
 * Author: Jun Xu
 * Email: jxu.vimacs@gmail.com
 * Created Time: Fri 24 Apr 2015 02:14:40 PM EDT
 */

package photoshare;

import java.util.List;
import javax.servlet.http.HttpServletRequest;

/*
 * A bean that handles friends data
 */
public class FriendBean {
	private int userId;
	private int friendId;

	public int getUserId() {
		return userId;
	}

	public int getFriendId() {
		return friendId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public void setFriendId(int friendId) {
		this.friendId = friendId;
	}
}
