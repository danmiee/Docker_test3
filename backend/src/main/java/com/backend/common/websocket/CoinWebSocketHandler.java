package com.backend.common.websocket;

import com.backend.web.historyCoin.dto.HistoryCoinDTO;
import com.backend.web.predictCoin.dto.PredictCoinDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class CoinWebSocketHandler extends TextWebSocketHandler {
    private final CoinWebSocketService coinWebSocketService;
    private List<WebSocketSession> list = new ArrayList<>();
    private int currentHistoryIdx = 19952;
    private int currentPredictIdx = 0;

    @Scheduled(fixedDelay = 1000)
    public void scheduledProcessing() throws IOException {
        if (list.size() == 0) return;
        currentHistoryIdx++; currentPredictIdx++;

        if (currentHistoryIdx >= 20001 && currentPredictIdx >= 49) {
            for (WebSocketSession session : list) {
                coinWebSocketService.onClose(session);
                list.remove(session);
                if (list.size() == 0) break;
            }
        }
        if (list.size() == 0) return;

        HistoryCoinDTO.Basic historyCoin = coinWebSocketService.findHistoryCoinByIdx(currentHistoryIdx);
        PredictCoinDTO.Basic predictCoin = coinWebSocketService.findPredictCoinByIdx(currentPredictIdx);

        String msg = "[" +
                "{" +
                    "\"idx\":\"" + historyCoin.getIdx() + "\", " +
                    "\"openingPrice\":\"" + historyCoin.getOpeningPrice() + "\", " +
                    "\"highPrice\":\"" + historyCoin.getHighPrice() + "\", " +
                    "\"lowPrice\":\"" + historyCoin.getLowPrice() + "\", " +
                    "\"tradePrice\":\"" + historyCoin.getTradePrice() + "\", " +
                    "\"candleAccTraceVolume\":\"" + historyCoin.getCandleAccTradeVolume() + "\", " +
                    "\"candleDateTimeKst\":\"" + historyCoin.getCandleDateTimeKst() + "\"" +
                "}, " +
                "{" +
                "\"idx\":\"" + predictCoin.getIdx() + "\", " +
                "\"price\":\"" + predictCoin.getPrice() + "\", " +
                "\"dateTime\":\"" + predictCoin.getDateTime() + "\"" +
                "}" +
            "]";

        TextMessage message = new TextMessage(msg.getBytes());

        for (WebSocketSession session : list) {
            try {
                handleTextMessage(session, message);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        session.sendMessage(message);
    }

    @Override
    // Client 접속 시 호출
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        list.add(session);
        log.info(session + " 클라이언트 접속");
    }

    @Override
    // Client 접속 해제 시 호출
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info(session + " 클라이언트 접속 해제");
        list.remove(session);
    }
}
