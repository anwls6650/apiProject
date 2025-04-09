package com.kmj.apiProject.common.service;

import org.springframework.stereotype.Service;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;

import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class KakaoService {

    private static final String KAKAO_API_URL = "https://dapi.kakao.com/v2/local/search/address.json";
    // 카카오 key 개인정보
    private static final String KAKAO_API_KEY = "";

    @Autowired
    private ObjectMapper objectMapper;

    public Map<String, Double> getCoordinatesFromAddress(String address) {
        try {
            URI uri = UriComponentsBuilder
                    .fromUriString(KAKAO_API_URL)
                    .queryParam("query", address)
                    .build()
                    .encode()
                    .toUri();

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "KakaoAK " + KAKAO_API_KEY);

            HttpEntity<?> entity = new HttpEntity<>(headers);
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);

            JsonNode root = objectMapper.readTree(response.getBody());
            JsonNode documents = root.get("documents");

            if (documents.isArray() && documents.size() > 0) {
                JsonNode location = documents.get(0);
                double px = location.get("x").asDouble(); // 경도
                double py = location.get("y").asDouble(); // 위도

                Map<String, Double> coords = new HashMap<>();
                coords.put("PX", px);
                coords.put("PY", py);
                return coords;
            } else {
                throw new RuntimeException("카카오에서 주소 검색 결과가 없습니다.");
            }

        } catch (Exception e) {
            throw new RuntimeException("좌표 변환 중 오류 발생", e);
        }
    }
}

