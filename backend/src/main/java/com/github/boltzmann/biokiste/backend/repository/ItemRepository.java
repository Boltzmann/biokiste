package com.github.boltzmann.biokiste.backend.repository;

import com.github.boltzmann.biokiste.backend.model.Item;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends MongoRepository<Item, String> {
    Item findItemByName(String name);
}
