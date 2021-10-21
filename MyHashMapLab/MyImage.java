public class MyImage {
	
	private String url, caption, date;
	
	public MyImage(String url, String caption, String date) {
		this.url = url;
		this.caption = caption;
		this.date = date;
	}

	public String getUrl() {
		return url;
	}

	public String getCaption() {
		return caption;
	}

	public String getDate() {
		return date;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public void setDate(String date) {
		this.date = date;
	}

}
