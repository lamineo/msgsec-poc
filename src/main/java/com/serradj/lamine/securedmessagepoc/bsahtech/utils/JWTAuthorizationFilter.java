package com.serradj.lamine.securedmessagepoc.bsahtech.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

@Slf4j
//@Component
//@Order(1)
public class JWTAuthorizationFilter implements Filter {
    private static final String HEADER_STRING = "Authorization";
    private static final String HEADER_RAT_STRING = "Resource-Token-Header";
    private static final String TOKEN_PREFIX = "Bearer ";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

    }

    @Override
    public void destroy() {

    }
//    private static final String RAT_KID = "RAT";
//    private final JWTKeys jwtKeyService;
//    private final boolean supportsRat;
//
//    public JWTAuthorizationFilter(AuthenticationManager authManager, JWTKeys jwtKeyService) {
//        super(authManager);
//        this.jwtKeyService = jwtKeyService;
//
//        if (this.jwtKeyService.hasService(RAT_KID)) {
//            log.info("Support for RAT HTTP Resource-Token-Header is enabled.");
//            this.supportsRat = true;
//        } else {
//            log.info("Support for RAT HTTP Resource-Token-Header is not enabled, if you wish to do so " +
//                    "please add a `bil.micro.security.keys[RAT]` configuration property.");
//            this.supportsRat = false;
//        }
//    }
//
//    /**
//     * Try to extract the token from the request.
//     */
//    @Override
//    protected void doFilterInternal(HttpServletRequest req,
//                                    HttpServletResponse res,
//                                    FilterChain chain) throws IOException, ServletException {
//        String header = req.getHeader(HEADER_STRING);
//        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
//            chain.doFilter(req, res);
//            return;
//        }
//
//        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        chain.doFilter(req, res);
//    }
//
//    /**
//     * Get authentication from the request.
//     */
//    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
//        String jwt = request.getHeader(HEADER_STRING).replace(TOKEN_PREFIX, "");
//        if (log.isDebugEnabled()) {
//            log.debug("Incoming JWT: " + jwt);
//        }
//
//        // parse the token.
//        try {
//            SignedJWT signedJwt = SignedJWT.parse(jwt);
//            String kid = getKid(signedJwt);
//            if (log.isDebugEnabled()) {
//                log.debug("Incoming JWT KID: " + kid);
//            }
//
//            ParsedToken authToken = jwtKeyService.getService(kid).verifySignedJwt(jwt);
//            String user = authToken.getSubject();
//
//            ParsedTokens parsedTokens = new ParsedTokens();
//            parsedTokens.setAuth(authToken);
//
//            if (supportsRat) {
//                String ratHeader = request.getHeader(HEADER_RAT_STRING);
//                if (log.isDebugEnabled()) {
//                    log.debug("Incoming RAT: " + ratHeader);
//                }
//
//                Option.of(ratHeader)
//                        .map(rat -> jwtKeyService.getService(RAT_KID).verifySignedJwt(rat))
//                        .peek(parsedTokens::setRat);
//            }
//
//            return user == null ? null
//                    : new UsernamePasswordAuthenticationToken(user, parsedTokens, authorities(parsedTokens));
//        } catch (ParseException e) {
//            log.error("Could note parse JWT.", e);
//            return null;
//        }
//    }
//
//    private List<GrantedAuthority> authorities(ParsedTokens tokens) {
//        final List<GrantedAuthority> authorities = new ArrayList<>();
//        authorities.add(new SimpleGrantedAuthority(PREFIX_ROLE_ISSUER + tokens.getAuth().getIssuer().toUpperCase()));
//        JWTClaimsSet claims = tokens.getAuth().getClaims();
//        Try.of(() -> claims.getStringClaim(CONNECTION_TYPE_JWT_CLAIM))
//                .map(ct -> authorities.add(new SimpleGrantedAuthority(PREFIX_ROLE_TYPE + ct)));
//        Try.of(() -> claims.getStringClaim(CHANNEL_JWT_CLAIM))
//                .map(ct -> authorities.add(new SimpleGrantedAuthority(PREFIX_ROLE_CHANNEL + ct)));
//        Try.of(() -> getRoles(claims)
//                .map(ct -> authorities.add(new SimpleGrantedAuthority(PREFIX_ROLE + ct))));
//        tokens.getRat().peek(t -> authorities.add(new SimpleGrantedAuthority(PREFIX_ROLE + "RAT")));
//        return authorities;
//    }
//
//    private String getKid(final SignedJWT jwt) {
//        return Option.of(jwt.getHeader().getKeyID())
//                .getOrElseTry(() -> (String) jwt.getJWTClaimsSet().getClaim("kid"));
//    }
//
//    private Seq<String> getRoles(JWTClaimsSet claims) {
//        return Try.of(() -> Vector.of(claims.getStringArrayClaim(ROLES_JWT_CLAIM)))
//                .getOrElse(Vector.empty());
//    }

}