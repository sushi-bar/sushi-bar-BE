package org.virtualsushibar.backend.app.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.virtualsushibar.backend.app.dao.document.OrderDocument;
import org.virtualsushibar.backend.app.dao.document.OrderStatus;


@Service
@RequiredArgsConstructor
@Slf4j
public class OrderDocumentService {
    private final MongoTemplate mongoTemplate;

    public void findAndUpdate(String orderId, OrderStatus orderStatus) {
        Query query = new Query();
        query.addCriteria(Criteria.where("orderId").is(orderId));

        Update update = new Update();
        update.set("orderStatus", orderStatus);

        OrderDocument save = mongoTemplate.findAndModify(
                query, update,
                new FindAndModifyOptions().returnNew(true), OrderDocument.class);
        log.info("order with id: {} and orderID: {} has been updated!", save.getId(), save.getOrderId());

    }
}
