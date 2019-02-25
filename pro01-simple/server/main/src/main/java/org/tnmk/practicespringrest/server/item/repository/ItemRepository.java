package org.tnmk.practicespringrest.server.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tnmk.practicespringrest.server.item.model.Item;

public interface ItemRepository extends JpaRepository<Item, Integer> {
}
