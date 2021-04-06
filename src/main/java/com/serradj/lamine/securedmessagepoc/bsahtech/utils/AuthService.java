package com.serradj.lamine.securedmessagepoc.bsahtech.utils;

import com.serradj.lamine.securedmessagepoc.bsahtech.entities.UserEntity;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
public class AuthService {

    @Setter
    private UserEntity connecterUser;

//    /**
//     * Get the currently authenticated principal, or an authentication request token.
//     *
//     * @return the <code>Authentication</code> or <code>null</code> if no authentication
//     * information is available
//     */
//    public Authentication getAuthentication() {
//        return SecurityContextHolder.getContext().getAuthentication();
//    }
//
//    /**
//     * Get the currently authenticated principal token.
//     *
//     * @return the <code>Token</code> or <code>null</code> if no authentication.
//     */
//    public ParsedToken getToken() {
//        return getToken(getAuthentication());
//    }
//
//    /**
//     * Get the token from the given authentication.
//     *
//     * @return the <code>Token</code> or <code>null</code> if no authentication.
//     */
//    public ParsedToken getToken(Authentication authentication) {
//        if (authentication == null) {
//            return null;
//        }
//
//        return ((ParsedTokens) authentication.getCredentials()).getAuth();
//    }
//
//    /**
//     * Get the currently authenticated principal token.
//     *
//     * @return the <code>Token</code> or <code>null</code> if no authentication.
//     */
//    public ParsedTokens getTokens() {
//        return getTokens(getAuthentication());
//    }
//
//    /**
//     * Get the tokens from the given authentication.
//     *
//     * @return the <code>ParsedTokens</code> or <code>null</code> if no authentication.
//     */
//    public ParsedTokens getTokens(Authentication authentication) {
//        if (authentication == null) {
//            return null;
//        }
//
//        return (ParsedTokens) authentication.getCredentials();
//    }
//
//    /**
//     * Get the subject from the current authentication principal token (usually this is the Z actor).
//     *
//     * @return the <code>subject</code> or <code>null</code> if no authentication.
//     */
//    public String getSubject() {
//        return getSubject(getToken());
//    }
//
//    /**
//     * Get the subject from the given authentication token (usually this is the Z actor).
//     *
//     * @return the <code>subject</code> or <code>null</code> if no authentication.
//     */
//    public String getSubject(ParsedToken token) {
//        if (token == null) {
//            return null;
//        }
//
//        return token.getSubject();
//    }
//
//    /**
//     * Alias for {@link #getSubject()}.
//     */
//    public String subject() {
//        return getSubject();
//    }
//
//    /**
//     * @return
//     */
//    public Option<String> getConnectedEmployee() {
//        ParsedToken parsedToken = getToken();
//        if (parsedToken == null) {
//            return null;
//        }
//        if (parsedToken.getIssuer() != null && Issuer.BLSNET.toString().equals(parsedToken.getIssuer())) {
//            return Option.of(parsedToken.getSubject());
//        }
//        return Option.none();
//    }
//
//    /**
//     * Alias for {@link #getSubject()}.
//     * <p>
//     * Try to search the <code>Z actor id</code> from the RAT if any, otherwise fall back to
//     * Auth Token.
//     *
//     * @return the <code>Z actor id</code> or <code>null</code> if no authentication.
//     */
//    public String getConnectedActor() {
//        return getTokens().getRat()
//                .flatMap(this::ratActorClaim)
//                .getOrElse(this::getSubject);
//    }
//
//    /**
//     * Alias for {@link #getConnectedActor()}.
//     */
//    public String connectedActor() {
//        return getConnectedActor();
//    }
//
//    /**
//     * Get the processed actor from the current auth principal JWT claims.
//     *
//     * @return the <code>processed actor</code> or <code>null</code> if no authentication.
//     */
//    public String unsafeGetProcessedActor() {
//        return getProcessedActor().get();
//    }
//
//    /**
//     * Get the processed actor from the current auth principal JWT claims.
//     *
//     * @return the <code>processed actor</code> or <code>null</code> if no authentication.
//     */
//    public Option<String> getProcessedActor() {
//        ParsedTokens tokens = getTokens();
//        if (tokens.getRat().isDefined()) {
//            return tokens.getRat()
//                    .map(this::ratActorClaim).get();
//        }
//
//        final Option<ParsedToken> token = Option.of(tokens.getAuth());
//        return tokenClaim(PROCESSED_ACTOR_JWT_CLAIM)
//                .map(actor -> {
//                    if (StringUtils.isBlank(actor)) {
//                        // if connected as TIT and no processed actor can be found
//                        // just infer the connected actor is the same as processed actor
//                        if (getConnectionType().isDefined() && TIT.equals(getConnectionType().get())) {
//                            return token.map(ParsedToken::getSubject).get();
//                        } else {
//                            return actor;
//                        }
//                    }
//                    return actor;
//                });
//    }
//
//    /**
//     * Get the preferred_username (sa partyId) actor from the current auth principal JWT claims.
//     *
//     * @return the <code>preferred_username</code> or <code>null</code> if no authentication.
//     */
//    public Option<String> getPartyId() {
//        ParsedTokens tokens = getTokens();
//        return tokens.getRat()
//                .map(this::ratActorClaim)
//                .getOrElse(() -> tokenClaim(PARTY_ID_JWT_CLAIM, tokens.getAuth()));
//    }
//
//    /**
//     * Alias for {@link #getProcessedActor()}.
//     */
//    public Option<String> processedActor() {
//        return getProcessedActor();
//    }
//
//    /**
//     * Get the issuer from the current auth principal JWT claims.
//     *
//     * @return the <code>issuer</code>.
//     */
//    public Option<Issuer> getIssuer(ParsedToken token) {
//        if (token == null || token.getIssuer() == null) {
//            return null;
//        }
//        return Option.of(Issuer.valueOf(token.getIssuer().toUpperCase()));
//    }
//
//    /**
//     * Get the connection type from the current auth principal JWT claims.
//     *
//     * @return the <code>connection type</code>.
//     */
//    public Option<ConnectionType> getConnectionType() {
//        return tokenClaim(CONNECTION_TYPE_JWT_CLAIM)
//                .map(ConnectionType::valueOf);
//    }
//
//    /**
//     * Alias for {@link #getConnectionType()}.
//     */
//    public Option<ConnectionType> connectionType() {
//        return getConnectionType();
//    }
//
//    /**
//     * Get the Locale from the current auth principal JWT claims.
//     *
//     * @return the <code>locale</code>.
//     */
//    public Option<Locale> getUserLocale() {
//        return tokenClaim(LOCALE_JWT_CLAIM)
//                .map(Locale::new);
//    }
//
//    /**
//     * Alias for {@link #getUserLocale()}.
//     */
//    public Option<Locale> userLocale() {
//        return getUserLocale();
//    }
//
//    /**
//     * Get the channel from the current auth principal JWT claims.
//     *
//     * @return the <code>channel</code>.
//     */
//    public Option<Channel> getChannel() {
//        return tokenClaim(CHANNEL_JWT_CLAIM)
//                .map(Channel::valueOf);
//    }
//
//    /**
//     * Alias for {@link #getChannel()}.
//     */
//    public Option<Channel> channel() {
//        return getChannel();
//    }
//
//    /**
//     * Get the current principal user context.
//     *
//     * @return the <code>user context</code>
//     */
//    @Override
//    public UserContext getUserContext() {
//        final ParsedToken token = getToken();
//        if (token == null) {
//            return null;
//        }
//
//        return UserEntity.builder()
//                .partyId(get PartyId())
//                .connectedActor(getConnectedActor())
//                .channel(channelClaim(token))
//                .connectionType(connectionTypeClaim(token))
//                .locale(localeClaim(token))
//                .processedActor(getProcessedActor())
//                .connectedEmployee(getConnectedEmployee())
//                .build();
//    }
//
//    /**
//     * Alias for {@link #getUserContext()}.
//     */
//    public UserContext userContext() {
//        return getUserContext();
//    }


}
