package qa;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Order(1)
@Configuration
class H2SecurityConfig extends WebSecurityConfigurerAdapter {
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/console/**").permitAll().and().authorizeRequests().anyRequest()
				.permitAll().and().formLogin().defaultSuccessUrl("/index.jsp", true).and().cors().and().csrf()
				.ignoringAntMatchers("/console/**").ignoringAntMatchers("/index.jsp/**")
				.ignoringAntMatchers("/login/**").and().headers().disable().httpBasic();
	}
}