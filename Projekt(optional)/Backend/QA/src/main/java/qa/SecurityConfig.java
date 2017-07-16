package qa;

import qa.service.MyUserDetailsService;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Order(2)
@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	// 3) Autowiring and beans for custom UserDetailsService
	private final MyUserDetailsService userDetailsService;

	@Autowired
	public SecurityConfig(MyUserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		List<String> l1 = new LinkedList<String>();
		List<String> l2 = new LinkedList<String>();
		List<String> l3 = new LinkedList<String>();

		l1.add("*");
		l2.add("HEAD");
		l2.add("GET");
		l2.add("POST");
		l2.add("PUT");
		l2.add("DELETE");
		l2.add("PATCH");
		l3.add("Authorization");
		l3.add("Cache-Control");
		l3.add("Content-Type");
		final CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(l1);
		configuration.setAllowedMethods(l2);
		// setAllowCredentials(true) is important, otherwise:
		// The value of the 'Access-Control-Allow-Origin' header in the response
		// must not be the wildcard '*' when the request's credentials mode is
		// 'include'.
		configuration.setAllowCredentials(true);
		// setAllowedHeaders is important! Without it, OPTIONS preflight request
		// will fail with 403 Invalid CORS request
		configuration.setAllowedHeaders(l3);
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public DaoAuthenticationProvider authProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService);
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/console/**").permitAll().and().authorizeRequests()
				.antMatchers("/register/**").permitAll().and().formLogin().defaultSuccessUrl("/index.jsp").and().cors()
				.and().csrf().ignoringAntMatchers("/register/**").ignoringAntMatchers("/console/**")
				.ignoringAntMatchers("/index.jsp/**").ignoringAntMatchers("/logout/**").ignoringAntMatchers("/login/**")
				.and().headers().disable().httpBasic();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
		auth.authenticationProvider(authProvider());
	}
}
