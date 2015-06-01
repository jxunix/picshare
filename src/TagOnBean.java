/*
 * Filename: TagOnBean.java
 * Author: Jun Xu
 * Email: jxu.vimacs@gmail.com
 * Created Time: Fri 24 Apr 2015 02:20:29 PM EDT
 */

package photoshare;

import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

/*
 * A bean that handles tag_on data
 */
public class TagOnBean {
	private String word = "";
	private int photoId;

	public String getWord() {
		return word;
	}

	public int getPhotoId() {
		return photoId;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public void setPhotoId(int photoId) {
		this.photoId = photoId;
	}

	public TagOnBean create(HttpServletRequest request, int photoId) {
		TagOnBean tagOn = new TagOnBean();

		String word = request.getParameter("tagWord");
		if (word == null || word.equals("")) {
			return null;
		}

		tagOn.setWord(word);
		tagOn.setPhotoId(photoId);

		return tagOn;
	}
}
