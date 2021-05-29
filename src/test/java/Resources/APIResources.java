package Resources;

public enum APIResources {
	GetPlacAPI("/launches/latest"), InvalidPlacAPI("/launches/123");

	private String resource;

	APIResources(String resource) {
		this.resource = resource;
	}

	public String getResource() {
		return resource;
	}
}
