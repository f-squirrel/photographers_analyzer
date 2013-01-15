/**
 * Created with IntelliJ IDEA.
 * User: ddanilov
 * Date: 1/8/13
 * Time: 1:32 AM
 * To change this template use File | Settings | File Templates.
 */

import java.util.Date;

public class Photographer {

	public enum Status {
		debutant,
		amateur_photographer,
		expert,
		master,
		pro}
	private String name = null;
	private Status status = null;
	private double votes;
	private String address = null;
	private long id = 0;
	private Date lastLikeDate = null;

	public Photographer(long id, String name, String address, double votes, Status status, Date lastLikeDate) {
		this.setName(name);
		this.setStatus(status);
		this.setVotes(votes);
		this.setAddress(address);
		this.setId(id);
		this.setLastLikeDate(lastLikeDate);
		this.votes = votes;
	}

	public String toString() {
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append(name);
		strBuilder.append(System.getProperty("line.separator"));
		strBuilder.append(votes);
		strBuilder.append(System.getProperty("line.separator"));
		strBuilder.append(status);
		strBuilder.append(System.getProperty("line.separator"));
		strBuilder.append(address);
		strBuilder.append(System.getProperty("line.separator"));
		strBuilder.append(id);
		return strBuilder.toString();
	}

	public boolean equals(Object obj) {
		//null instanceof Object will always return false
		if (!(obj instanceof Photographer))
			return false;
		if (obj == this)
			return true;
		return  this.id == ((Photographer) obj).id;
	}

	public int hashCode() {
		return (int)id/12 + 123456;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public double getVotes() {
		return votes;
	}

	public void setVotes(double votes) {
		this.votes = votes;
	}

	public void addVote(double vote) {
		this.votes += vote;
	}
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getLastLikeDate() {
		return lastLikeDate;
	}

	public void setLastLikeDate(Date lastLikeDate) {
		this.lastLikeDate = lastLikeDate;
	}
}
