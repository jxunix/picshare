/*
 * Filename: Contribution.java
 * Author: Jun Xu
 * Email: jxu.vimacs@gmail.com
 * Created Time: Sat 25 Apr 2015 10:31:58 PM EDT
 */

package photoshare;

public class Contribution {
	private int userId;
	private int contribution;

	public int getUserId() {
		return userId;
	}

	public int getContribution() {
		return contribution;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public void setContribution(int contribution) {
		this.contribution = contribution;
	}

	public void addContribution(int contribution) {
		this.contribution += contribution;
	}
}
