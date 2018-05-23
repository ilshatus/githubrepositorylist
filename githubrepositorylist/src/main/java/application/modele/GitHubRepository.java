package application.modele;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GitHubRepository {

    private String name;

    @JsonProperty("html_url")
    private String htmlURL;

    public GitHubRepository(String name, String htmlURL) {
        this.name = name;
        this.htmlURL = htmlURL;
    }

    public String getName() {
        return name;
    }

    public String getHtmlURL() {
        return htmlURL;
    }
}
