package graduation.project.no18;

import graduation.project.no18.global.oauth.properties.AppProperties;
import graduation.project.no18.global.oauth.properties.CorsProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@SpringBootApplication
@EnableConfigurationProperties({AppProperties.class, CorsProperties.class})
@EnableJpaAuditing
public class No18Application {

	public static void main(String[] args) {
		SpringApplication.run(No18Application.class, args);
	}

}
