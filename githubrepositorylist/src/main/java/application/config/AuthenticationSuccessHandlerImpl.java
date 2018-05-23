package application.config;

import application.modele.HistoryEntity;
import application.repository.HistoryRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

/**
 *  Handles successful authentication
 *  and add to history repository information about username and timestamp
 */
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler{

    private HistoryRepository historyRepository;

    public AuthenticationSuccessHandlerImpl(HistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        Date currentTime = Calendar.getInstance().getTime();
        historyRepository.save(new HistoryEntity(authentication.getName(), currentTime, true));
        httpServletResponse.sendRedirect("/");
    }
}
