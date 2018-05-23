package application.controller;

import application.modele.GitHubRepository;
import application.modele.HistoryEntity;
import application.repository.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.security.Principal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
public class Controller {
    private final String REPOS_URI = "https://api.github.com/user/repos";

    @Autowired
    private HistoryRepository historyRepository;

    @Autowired
    private OAuth2RestTemplate restTemplate;

    @RequestMapping("/user")
    public Map<String, String> user(Principal principal) {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("name", principal.getName());
        return map;
    }

    @RequestMapping("/repos")
    public List<GitHubRepository> repositories() {
        ArrayList listTemp = restTemplate.getForObject(REPOS_URI, ArrayList.class);
        if (listTemp == null || listTemp.isEmpty())
            return new ArrayList<>();
        ArrayList<GitHubRepository> listResult = new ArrayList<>();
        for (Object o : listTemp) {
            Map repository = (Map)o;
            listResult.add(new GitHubRepository(repository.get("name").toString(),
                    repository.get("html_url").toString()));
        }
        return listResult;
    }

    @RequestMapping("/history/all")
    public List<HistoryEntity> getAllHistory(Principal principal) {
        ArrayList<HistoryEntity> result = new ArrayList<>();
        for (HistoryEntity curr : historyRepository.findAll()) {
            if (curr.getUsername().equals(principal.getName())) {
                curr.setCurrentUser(true);
            }
            result.add(curr);
        }
        return result;
    }

}
