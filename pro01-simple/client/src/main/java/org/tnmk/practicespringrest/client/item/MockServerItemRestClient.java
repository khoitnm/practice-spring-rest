package org.tnmk.practicespringrest.client.item;

import org.springframework.stereotype.Component;
import org.tnmk.practicespringrest.server.rest.dto.ServerItemDto;

@Component
public class MockServerItemRestClient implements ServerItemRestClient {
    public ServerItemDto createServerItem(ServerItemDto serverItem) {
        return null;
    }

    public ServerItemDto updateServerItem(ServerItemDto serverItem) {
        return null;
    }

    public ServerItemDto findServerItemById(Integer itemId) {
        return null;
    }

    public void deleteServerItemById(Integer itemId) {

    }
}
