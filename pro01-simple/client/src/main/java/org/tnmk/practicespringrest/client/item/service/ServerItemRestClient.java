package org.tnmk.practicespringrest.client.item.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.tnmk.practicespringrest.server.rest.ItemRestApi;

@FeignClient(name = "item", url = "${feign.client.config.item.url}")
public interface ServerItemRestClient extends ItemRestApi {
}
