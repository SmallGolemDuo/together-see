package com.smallgolemduo.togethersee.client;

import com.smallgolemduo.togethersee.dto.kakao.GetKakaoTokenRequest;
import com.smallgolemduo.togethersee.dto.kakao.GetKakaoTokenResponse;
import com.smallgolemduo.togethersee.exception.KakaoApiWebClientException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RequiredArgsConstructor
public class KakaoApiClient {

    private final WebClient webClient;

    public Mono<GetKakaoTokenResponse> getOauthToken(GetKakaoTokenRequest tokenRequest) {
        return webClient.post()
                .uri("/oauth/token", uriBuilder ->
                        uriBuilder.queryParam("grant_type", tokenRequest.getGrantType())
                                .queryParam("client_id", tokenRequest.getClientId())
                                .queryParam("redirect_uri", tokenRequest.getRedirectUrl())
                                .queryParam("code", tokenRequest.getCode())
                                .build())
                .retrieve()
                .bodyToMono(GetKakaoTokenResponse.class)
                .timeout(Duration.ofSeconds(5))
                .onErrorMap(e -> new KakaoApiWebClientException("failed get token from kakao api", e));
    }

}
