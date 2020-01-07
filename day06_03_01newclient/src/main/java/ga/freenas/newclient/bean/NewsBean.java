package ga.freenas.newclient.bean;

public class NewsBean {
	private String title;
	private String description;
	private String image;
	private int	type;
	private int comment; //评论条数

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getComment() {
		return comment;
	}

	public void setComment(int comment) {
		this.comment = comment;
	}

	@Override
	public String toString() {
		return "NewsBean{" +
				"title='" + title + '\'' +
				", description='" + description + '\'' +
				", image='" + image + '\'' +
				", type=" + type +
				", comment=" + comment +
				'}';
	}
}
