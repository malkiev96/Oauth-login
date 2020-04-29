package ru.malkiev.oauth.config;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.InMemoryOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.ForwardedHeaderFilter;

import javax.servlet.Filter;
import javax.servlet.http.HttpServletRequest;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_DECORATION_FILTER_ORDER;

@Component
@AllArgsConstructor
@Log4j2
public class ZuulConfig extends ZuulFilter {

	private final OAuth2AuthorizedClientService clientService;

	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		return PRE_DECORATION_FILTER_ORDER + 1000;
	}

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() throws ZuulException {
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();

		if(request.getHeader("Authorization") == null){

			OAuth2AuthenticationToken oauthToken =
					(OAuth2AuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

			OAuth2AuthorizedClient client = clientService.loadAuthorizedClient(
							oauthToken.getAuthorizedClientRegistrationId(),
							oauthToken.getName());

			String accessToken = client.getAccessToken().getTokenValue();

			ctx.addZuulRequestHeader("Authorization", "bearer " + accessToken);

		}
		return null;
	}
}
