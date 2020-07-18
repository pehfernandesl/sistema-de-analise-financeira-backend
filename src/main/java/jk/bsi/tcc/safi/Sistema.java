package jk.bsi.tcc.safi;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import javax.annotation.PostConstruct;
import jk.bsi.tcc.safi.config.ActiveProfilesConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

@Slf4j
@RequiredArgsConstructor
@SpringBootApplication
public class Sistema {
  private final Environment env;

  public static void main(String[] args) {
    SpringApplication sistema = new SpringApplication(Sistema.class);
    ConfigurableEnvironment env = sistema.run(args).getEnvironment();
    logApplicationStartup(env);
  }

  /**
   * Spring profiles can be configured with a program argument --spring.profiles.active=your-active-profile
   */
  @PostConstruct
  public void initApplication() {
    boolean containsDevProfile = Arrays.asList(env.getActiveProfiles()).contains(
      ActiveProfilesConstants.SPRING_DEVELOPMENT_PROFILE);
    boolean containsProdProfile = Arrays.asList(env.getActiveProfiles())
      .contains(ActiveProfilesConstants.SPRING_PRODUCTION_PROFILE);

    if (containsDevProfile && containsProdProfile) {
      log.error(
        "You have misconfigured your application! It should not run with both the 'dev' and 'prod' profiles at the same time.");
    }
  }

  private static void logApplicationStartup(Environment env) {
    String protocol = "http";
    if (env.getProperty("server.ssl.key-store") != null) {
      protocol = "https";
    }
    String serverPort = env.getProperty("server.port");
    String contextPath = env.getProperty("server.servlet.context-path");
    if (StringUtils.isEmpty(contextPath)) {
      contextPath = "/";
    }
    String hostAddress = "localhost";
    try {
      hostAddress = InetAddress.getLocalHost().getHostAddress();
    } catch (UnknownHostException e) {
      log.warn("The host name could not be determined, using `localhost` as fallback");
    }
    log.info("\n----------------------------------------------------------\n\t" +
        "Application '{}' is running! Access URLs:\n\t" +
        "Local: \t\t{}://localhost:{}{}\n\t" +
        "External: \t{}://{}:{}{}\n\t" +
        "Profile(s): \t{}\n----------------------------------------------------------",
      env.getProperty("spring.application.name"),
      protocol,
      serverPort,
      contextPath,
      protocol,
      hostAddress,
      serverPort,
      contextPath,
      env.getActiveProfiles());
  }
}
